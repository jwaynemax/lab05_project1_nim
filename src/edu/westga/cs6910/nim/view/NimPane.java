package edu.westga.cs6910.nim.view;

import edu.westga.cs6910.nim.model.Game;
import javafx.scene.layout.BorderPane;
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
		
		this.menuBar = new NimMenuBar(this.theGame, this, this.shouldShowHelpDialog);
		this.setTop(this.menuBar.createMenu());

		this.pnChooseFirstPlayer = new NewGamePane(theGame, this);	
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
	 * getter for pnHumanPlayer
	 * @return pnHumanPlayer
	 */
	public HumanPane getPnHumanPlayer() {
		return this.pnHumanPlayer;
	}
	
	/**
	 * getter for pnComputerPlayer
	 * @return pnComputerPlayer
	 */
	public ComputerPane getPnComputerPlayer() {
		return this.pnComputerPlayer;
	}
	
	/**
	 * getter for pnGameInfo
	 * @return pnGameInfo
	 */
	public StatusPane getPnGameInfo() {
		return this.pnGameInfo;
	}
	
	/**
	 * getter for pnChooseFirstPlayer
	 * @return pnChooseFirstPlayer
	 */
	public NewGamePane getPnChooseFirstPlayer() {
		return this.pnChooseFirstPlayer;
	}
}
