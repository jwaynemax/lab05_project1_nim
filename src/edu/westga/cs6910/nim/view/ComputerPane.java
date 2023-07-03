package edu.westga.cs6910.nim.view;

import edu.westga.cs6910.nim.model.ComputerPlayer;
import edu.westga.cs6910.nim.model.Game;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Defines the pane that lets the user tell the computer player to
 * take its turn and that displays the number of sticks the computer
 * player took on its turn.
 * This class was started by CS6910
 * 
 * @author 	CS6910
 * @version Summer 2023
 */
public class ComputerPane extends GridPane implements InvalidationListener {
	private Game theGame;
	private Label lblNumberTaken;
	private ComputerPlayer theComputer;
	private Button btnTakeTurn;

	/**
	 * Creates a new ComputerPane that observes the specified game. 
	 * 
	 * @param theGame	the model object from which this pane gets its data
	 * 
	 * @requires 	theGame != null
	 * @ensures		theGame.countObservers() = theGame.countObservers()@prev + 1
	 */
	public ComputerPane(Game theGame) {
		if (theGame == null) {
			throw new IllegalArgumentException("Invalid game");
		}
		this.theGame = theGame;

		this.theGame.addListener(this);
		
		this.theComputer = this.theGame.getComputerPlayer();
		
		this.buildPane();
	}
	
	private void buildPane() {
		this.add(new Label("~~ " + this.theComputer.getName() + " ~~"), 0, 0);

		this.add(new Label("Number of sticks taken: "), 0, 1);
		this.lblNumberTaken = new Label("0");
		this.add(this.lblNumberTaken, 1, 1);

		this.btnTakeTurn = new Button("Take Turn");
		this.btnTakeTurn.setOnAction(new TakeTurnListener());
		this.add(this.btnTakeTurn, 0, 2);
	}

	@Override
	public void invalidated(Observable arg0) {
		if (this.theGame.isGameOver()) {
			this.lblNumberTaken.setText("" + ComputerPane.this.theComputer.getSticksOnThisTurn());
			this.setDisable(true);
			return;
		}
		
		boolean myTurn = this.theGame.getCurrentPlayer() == this.theComputer;
		
		if (!myTurn) {
			this.lblNumberTaken.setText("" + ComputerPane.this.theComputer.getSticksOnThisTurn());
		} 
		this.setDisable(!myTurn);
	}

	/* 
	 * Defines the listener for takeTurnButton.
	 */
	private class TakeTurnListener implements EventHandler<ActionEvent> {

		/* 
		 * Tells the Game to have its current player (i.e., the computer player)
		 * take its turn.	
		 * 
		 * @see javafx.event.EventHandler#handle(T-extends-javafx.event.Event)
		 */
		@Override
		public void handle(ActionEvent arg0) {
			if (!ComputerPane.this.theGame.isGameOver()) {
				ComputerPane.this.theComputer.setPileForThisTurn(ComputerPane.this.theGame.getPile());
				ComputerPane.this.theComputer.setNumberSticksToTake();
				ComputerPane.this.theGame.play();
			}
		}
	}

	/**
	 * Resets the number of sticks taken to 0
	 */
	public void resetNumberTaken() {
		this.lblNumberTaken.setText("0");
	}
}
