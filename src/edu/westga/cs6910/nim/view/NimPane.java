package edu.westga.cs6910.nim.view;

import edu.westga.cs6910.nim.model.Game;
import javafx.scene.layout.BorderPane;

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
	private FullNimPane buildNimPane;
	
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
		
		this.buildNimPane = new FullNimPane(this.theGame, this);

		this.pnContent.setTop(this.buildNimPane.pnFirstPlayer());	
		this.pnContent.setLeft(this.buildNimPane.pnHumanPlayer());	
		this.pnContent.setCenter(this.buildNimPane.pnComputerPlayer());
		this.pnContent.setBottom(this.buildNimPane.pnGameInfo());
		
		this.setCenter(this.pnContent);
	}
	
	/**
	 * getter for pnHumanPlayer
	 * @return pnHumanPlayer
	 */
	public HumanPane getPnHumanPlayer() {
		return this.pnHumanPlayer;
	}
	
	/**
	 * setter for pnHumanPlayer
	 * @param pnHumanPlayer sets this.pnHumanPlayer
	 */
	public void setPnHumanPlayer(HumanPane pnHumanPlayer) {
		this.pnHumanPlayer = pnHumanPlayer;
	}
	
	/**
	 * getter for pnComputerPlayer
	 * @return pnComputerPlayer
	 */
	public ComputerPane getPnComputerPlayer() {
		return this.pnComputerPlayer;
	}
	
	/**
	 * setter for pnComputerPlayer
	 * @param pnComputerPlayer sets this.pnComputerPlayer
	 */
	public void setPnComputerPlayer(ComputerPane pnComputerPlayer) {
		this.pnComputerPlayer = pnComputerPlayer;
	}
	
	/**
	 * getter for pnGameInfo
	 * @return pnGameInfo
	 */
	public StatusPane getPnGameInfo() {
		return this.pnGameInfo;
	}
	
	/**
	 * setter for pnGameInfo
	 * @param pnGameInfo sets this.pnGameInfo
	 */
	public void setPnGameInfo(StatusPane pnGameInfo) {
		this.pnGameInfo = pnGameInfo;
	}
	
	/**
	 * getter for pnChooseFirstPlayer
	 * @return pnChooseFirstPlayer
	 */
	public NewGamePane getPnChooseFirstPlayer() {
		return this.pnChooseFirstPlayer;
	}
	
	/**
	 * setter for pnChooseFirstPlayer
	 * @param pnChooseFirstPlayer sets this.pnChooseFirstPlayer
	 */
	public void setPnChooseFirstPlayer(NewGamePane pnChooseFirstPlayer) {
		this.pnChooseFirstPlayer = pnChooseFirstPlayer;
	}
}
