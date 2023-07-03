package edu.westga.cs6910.nim.view;

import edu.westga.cs6910.nim.model.Game;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Defines the panel that displays either the number of sticks left on the
 * pile or the winner if the game is over.
 * 
 * @author 	CS6910
 * @version Summer 2023
 */
public class StatusPane extends GridPane implements InvalidationListener {
	private Game theGame;
	private Label lblStatus;
	
	/**
	 * Creates a new status pane that observes the specified game. 
	 * 
	 * @param theGame	the model object from which this pane gets its data
	 * 
	 * @requires 	theGame != null
	 * @ensures		theGame.countObservers() = theGame.countObservers()@prev + 1
	 */
	public StatusPane(Game theGame) {
		if (theGame == null) {
			throw new IllegalArgumentException("Invalid game");
		}
		this.theGame = theGame;
		
		this.theGame.addListener(this);
		
		this.buildPane();
	}

	private void buildPane() {
		this.add(new Label("~~ Game Info ~~"), 0, 0);
		
		this.lblStatus = new Label(this.theGame.toString());
		this.add(this.lblStatus, 0, 1);
	}

	@Override
	public void invalidated(Observable observable) {
		this.update();
	}

	/**
	 * Signals the pane to update its information
	 */
	public void update() {
		this.lblStatus.setText(this.theGame.toString());
	}
}
