package edu.westga.cs6910.nim.view;

import edu.westga.cs6910.nim.model.Game;
import edu.westga.cs6910.nim.model.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

/**
 * Create NewGame for NimPane
 * @author 	Justin Maxwell
 * @version Summer 2023
 */
public class NewGamePane extends GridPane {
	private RadioButton radHumanPlayer;
	private RadioButton radComputerPlayer;
	private RadioButton radRandomPlayer;
	private ComboBox<Integer> cmbInitialSize;
	
	private Game theGame;
	private Player theHuman;
	private Player theComputer;
	private FullNimPane fullNimPane;

	/**
	 * Constructor to create a NewGamePane
	 * @param theGame retrieves the current Game object from NimPane
	 * @param nimPane retrieves the current NimPane object from NimPane
	 */
	public NewGamePane(Game theGame, FullNimPane fullNimPane) {		
		if (theGame == null) {
			throw new IllegalArgumentException("Invalid game");
		}
		this.theGame = theGame;
		this.theHuman = this.theGame.getHumanPlayer();
		this.theComputer = this.theGame.getComputerPlayer();
		this.fullNimPane = fullNimPane;
		
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
				NewGamePane.this.theGame.setPileSize(pileSize);
				NewGamePane.this.fullNimPane.getPnGameInfo().update();
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
			
			NewGamePane.this.fullNimPane.getPnChooseFirstPlayer().setDisable(true);

			if (Math.random() * 10 <= 4) {
				NewGamePane.this.fullNimPane.getPnComputerPlayer().setDisable(false);
				NewGamePane.this.theGame.startNewGame(NewGamePane.this.theComputer, initialPileSize);					
			} else {
				NewGamePane.this.fullNimPane.getPnHumanPlayer().setDisable(false);
				NewGamePane.this.theGame.startNewGame(NewGamePane.this.theHuman, initialPileSize);
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
			
			NewGamePane.this.fullNimPane.getPnComputerPlayer().setDisable(false);
			NewGamePane.this.fullNimPane.getPnChooseFirstPlayer().setDisable(true);
			NewGamePane.this.theGame.startNewGame(NewGamePane.this.theComputer, initialPileSize);
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
			NewGamePane.this.fullNimPane.getPnChooseFirstPlayer().setDisable(true);
			NewGamePane.this.fullNimPane.getPnHumanPlayer().setDisable(false);
			NewGamePane.this.theGame.startNewGame(NewGamePane.this.theHuman, initialPileSize);
		}
	}
}
