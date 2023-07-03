package edu.westga.cs6910.nim.view;

import edu.westga.cs6910.nim.model.Game;
import edu.westga.cs6910.nim.model.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * Defines a GUI for the 1-pile Nim game.
 * This class was started by CS6910
 * @author 	Justin Maxwell
 * @version Summer 2023
 */
public class NimPane extends BorderPane {
	private Game theGame;
	private BorderPane pnContent;
	private HumanPane pnHumanPlayer;
	private ComputerPane pnComputerPlayer;
	private StatusPane pnGameInfo;
	private NewGamePane pnChooseFirstPlayer;
	private NimHelpDialog shouldShowHelpDialog;
	private NimMenuBar menuBar;
	
	/**
	 * Creates a pane object to provide the view for the specified
	 * Game model object.
	 * 
	 * @param theGame	the domain model object representing the Nim game
	 * 
	 * @requires theGame != null
	 * @ensures	 the pane is displayed properly
	 */
	public NimPane(Game theGame) {
		if (theGame == null) {
			throw new IllegalArgumentException("Invalid game");
		}
		this.theGame = theGame;
		
		this.shouldShowHelpDialog = new NimHelpDialog(true);	
		this.shouldShowHelpDialog.showHelpDialog();

		this.pnContent = new BorderPane();
		
		this.menuBar = new NimMenuBar(this.theGame, this, shouldShowHelpDialog);
		this.setTop(this.menuBar.createMenu());

		this.pnChooseFirstPlayer = new NewGamePane(theGame);	
		HBox topBox = this.createHBoxHolder(this.pnChooseFirstPlayer, false);
		this.pnContent.setTop(topBox);	

		this.pnHumanPlayer = new HumanPane(theGame);		
		HBox leftBox = this.createHBoxHolder(this.pnHumanPlayer, true);
		this.pnContent.setLeft(leftBox);	

		this.pnComputerPlayer = new ComputerPane(theGame);
		HBox centerBox = this.createHBoxHolder(this.pnComputerPlayer, true);
		this.pnContent.setCenter(centerBox);
		
		this.pnGameInfo = new StatusPane(theGame);
		HBox bottomBox = this.createHBoxHolder(this.pnGameInfo, false);
		this.pnContent.setBottom(bottomBox);

		this.setCenter(this.pnContent);
	}

	private HBox createHBoxHolder(Pane newPane, boolean disable) {
		newPane.setDisable(disable);
		HBox leftBox = new HBox();
		leftBox.getStyleClass().add("pane-border");	
		leftBox.getChildren().add(newPane);
		return leftBox;
	}
	
	/** 
	 * Defines the listener for New Game NimMenuBar.
	 */	
	public class NewGame implements EventHandler<ActionEvent> {
		/** 
		 * Sets up user interface and starts a new game. 
		 * Event handler for a click in the human player button.
		 */
		@Override
		public void handle(ActionEvent theEventObject) {
			NimPane.this.pnChooseFirstPlayer.reset();
			NimPane.this.pnChooseFirstPlayer.setDisable(false);
			NimPane.this.pnHumanPlayer.setDisable(true);
			NimPane.this.pnComputerPlayer.setDisable(true);
			NimPane.this.pnComputerPlayer.resetNumberTaken();
			NimPane.this.pnHumanPlayer.resetNumberToTakeComboBox();
			NimPane.this.shouldShowHelpDialog.showHelpDialog();
		}
	}

	/**
	 * Defines the panel in which the user selects which Player plays first.
	 */
	private final class NewGamePane extends GridPane {
		private RadioButton radHumanPlayer;
		private RadioButton radComputerPlayer;
		private RadioButton radRandomPlayer;
		private ComboBox<Integer> cmbInitialSize;
		
		private Game theGame;
		private Player theHuman;
		private Player theComputer;

		private NewGamePane(Game theGame) {		
			if (theGame == null) {
				throw new IllegalArgumentException("Invalid game");
			}
			this.theGame = theGame;
			
			this.theHuman = this.theGame.getHumanPlayer();
			this.theComputer = this.theGame.getComputerPlayer();
			
			this.buildPane();
		}

		private void buildPane() {
			this.setHgap(20);
			
			this.createInitialPileSizeItems();
			
			this.createFirstPlayerItems();	
		}

		private void createFirstPlayerItems() {
			Label lblFirstPlayer = new Label("Who plays first? ");
			this.add(lblFirstPlayer, 2, 0);
			
			this.radHumanPlayer = new RadioButton(this.theHuman.getName() + " first");	
			this.radHumanPlayer.setOnAction(new HumanFirstListener());

			this.radComputerPlayer = new RadioButton(this.theComputer.getName() + " first");
			this.radComputerPlayer.setOnAction(new ComputerFirstListener());
			
			this.radRandomPlayer = new RadioButton("Random Player");
			this.radRandomPlayer.setOnAction(new RandomFirstListener());

			ToggleGroup group = new ToggleGroup();
			this.radHumanPlayer.setToggleGroup(group);
			this.radComputerPlayer.setToggleGroup(group);
			this.radRandomPlayer.setToggleGroup(group);
			
			this.add(this.radHumanPlayer, 3, 0);
			this.add(this.radComputerPlayer, 4, 0);
			this.add(this.radRandomPlayer, 5, 0);
		}

		private void createInitialPileSizeItems() {
			Label lblInitialPileSize = new Label("Initial Pile Size: ");
			this.add(lblInitialPileSize, 0, 0);
			
			this.cmbInitialSize = new ComboBox<Integer>();
			this.cmbInitialSize.getItems().addAll(9, 16, 21);
			this.cmbInitialSize.setValue(9);
			this.cmbInitialSize.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					int pileSize = NewGamePane.this.cmbInitialSize.getValue();
					NimPane.this.theGame.setPileSize(pileSize);
					NimPane.this.pnGameInfo.update();
				}
			});
			this.add(this.cmbInitialSize, 1, 0);
		}
	
		/**
		 * Resets the radio buttons for new selection
		 */
		public void reset() {
			NewGamePane.this.theGame.setPileSize(NewGamePane.this.cmbInitialSize.getValue());
			this.radHumanPlayer.setSelected(false);
			this.radComputerPlayer.setSelected(false);
			this.radRandomPlayer.setSelected(false);
		}

		/** 
		 * Defines the listener for computer player first button.
		 */		
		private class RandomFirstListener implements EventHandler<ActionEvent> {
			@Override
			/** 
			 * Enables the ComputerPlayerPanel and starts a new game. 
			 * Event handler for a click in the computerPlayerButton.
			 */
			public void handle(ActionEvent theEventObject) {
				int initialPileSize = NewGamePane.this.cmbInitialSize.getValue();
				
				NimPane.this.pnChooseFirstPlayer.setDisable(true);

				if (Math.random() * 10 <= 4) {
					NimPane.this.pnComputerPlayer.setDisable(false);
					NimPane.this.theGame.startNewGame(NewGamePane.this.theComputer, initialPileSize);					
				} else {
					NimPane.this.pnHumanPlayer.setDisable(false);
					NimPane.this.theGame.startNewGame(NewGamePane.this.theHuman, initialPileSize);
				}
			}
		}
		
		/**
		 * Defines the listener for computer player first button.
		 */		
		private class ComputerFirstListener implements EventHandler<ActionEvent> {
			@Override
			/** 
			 * Enables the ComputerPlayerPanel and starts a new game. 
			 * Event handler for a click in the computerPlayerButton.
			 */
			public void handle(ActionEvent artheEventObjectg0) {
				int initialPileSize = NewGamePane.this.cmbInitialSize.getValue();
				
				NimPane.this.pnComputerPlayer.setDisable(false);
				NimPane.this.pnChooseFirstPlayer.setDisable(true);
				NimPane.this.theGame.startNewGame(NewGamePane.this.theComputer, initialPileSize);
			}
		}

		/** 
		 * Defines the listener for human player first button.
		 */	
		private class HumanFirstListener implements EventHandler<ActionEvent> {
			/** 
			 * Sets up user interface and starts a new game. 
			 * Event handler for a click in the human player button.
			 */
			@Override
			public void handle(ActionEvent theEventObject) {
				int initialPileSize = NewGamePane.this.cmbInitialSize.getValue();
				NimPane.this.pnChooseFirstPlayer.setDisable(true);
				NimPane.this.pnHumanPlayer.setDisable(false);
				NimPane.this.theGame.startNewGame(NewGamePane.this.theHuman, initialPileSize);
			}
		}
	}
}
