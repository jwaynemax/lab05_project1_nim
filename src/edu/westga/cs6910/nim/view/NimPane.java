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
		this.buildNimPane = new FullNimPane(this.theGame, this);
				
		this.menuBar = new NimMenuBar(this.theGame, this.buildNimPane, this.shouldShowHelpDialog);
		this.setTop(this.menuBar.createMenu());
		
		this.pnContent.setTop(this.buildNimPane.pnFirstPlayer());	
		this.pnContent.setLeft(this.buildNimPane.pnHumanPlayer());	
		this.pnContent.setCenter(this.buildNimPane.pnComputerPlayer());
		this.pnContent.setBottom(this.buildNimPane.pnGameInfo());
		
		this.setCenter(this.pnContent);
	}
}
