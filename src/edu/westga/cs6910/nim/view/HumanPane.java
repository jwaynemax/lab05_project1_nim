package edu.westga.cs6910.nim.view;

import edu.westga.cs6910.nim.model.Game;
import edu.westga.cs6910.nim.model.HumanPlayer;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Defines the pane that lets the user indicate the number of sticks
 * to take from the pile and to take the turn.
 * 
 * @author 	CS6910
 * @version Summer 2023
 */
public class HumanPane extends GridPane implements InvalidationListener {
	private ComboBox<Integer> cmbNumberToTake;
	private Button btnTakeSticks;
	
	private HumanPlayer theHuman;
	private Game theGame;

	/**
	 * Creates a new HumanPane that observes the specified game. 
	 * 
	 * @param theGame	the model object from which this pane gets its data
	 * 
	 * @requires 	theGame != null
	 * @ensures		theGame.countObservers() = theGame.countObservers()@prev + 1
	 */
	public HumanPane(Game theGame) {
		if (theGame == null) {
			throw new IllegalArgumentException("Invalid game");
		}
		this.theGame = theGame;
		
		this.theGame.addListener(this);
		
		this.theHuman = this.theGame.getHumanPlayer();
		
		this.buildPane();
	}
	
	private void buildPane() {
		this.add(new Label("~~ " + this.theHuman.getName() + " ~~"), 0, 0);
		
		this.add(new Label("Number of sticks to take: "), 0, 1);
		
		this.cmbNumberToTake = new ComboBox<Integer>();
		this.cmbNumberToTake.getItems().addAll(1, 2, 3);
		this.add(this.cmbNumberToTake, 1, 1);

		this.btnTakeSticks = new Button("Take Sticks");
		this.btnTakeSticks.setOnAction(new TakeTurnListener());
		this.add(this.btnTakeSticks, 3, 1);
	}

	@Override
	public void invalidated(Observable observable) {
		if (this.theGame.isGameOver()) {
			this.setDisable(true);
			return;
		}
		
		boolean myTurn = this.theGame.getCurrentPlayer() == this.theHuman;
		
		if (myTurn) {
			this.resetNumberToTakeComboBox();
		}

		this.setDisable(!myTurn);
	}

	/**
	 *  Clears the numbers in the combo box, then adds back in all the
	 *  valid numbers so the user can't try to take more sticks than allowed.
	 */
	public void resetNumberToTakeComboBox() {	
		this.cmbNumberToTake.getItems().clear();
		
		int max = Math.min(this.theGame.getSticksLeft() - 1, Game.MAX_STICKS_PER_TURN);
		for (int number = 0; number < max; number++) {
			this.cmbNumberToTake.getItems().add(number + 1);
		}	
		this.cmbNumberToTake.setValue(1);
	}

	private class TakeTurnListener implements EventHandler<ActionEvent> {
		/* 
		 * Tells the Game to have its current player (i.e., the human Player)
		 * take its turn.	
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void handle(ActionEvent event) {
			if (!HumanPane.this.theGame.isGameOver()) {
				HumanPane.this.theHuman.setPileForThisTurn(HumanPane.this.theGame.getPile());
				HumanPane.this.theHuman.setNumberSticksToTake((int) HumanPane.this.cmbNumberToTake
						.getValue());
				HumanPane.this.theGame.play();
			}
		}
	}
}
