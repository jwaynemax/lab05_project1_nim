package edu.westga.cs6910.nim.view;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class NimHelpDialog {
	
	private boolean shouldShowHelpDialog;

	/**
	 * NimHelpDialog constructor
	 */
	public NimHelpDialog(boolean shouldShowHelpDialog) {
		this.shouldShowHelpDialog = shouldShowHelpDialog;
	}
	
	public boolean showHelpDialog() {
		
		if (this.shouldShowHelpDialog) {
			Alert message = new Alert(AlertType.CONFIRMATION);
			message.setTitle("CS6910: Better Nim");
			
			String helpMessage = 
					"Nim rules: \nPlay against the computer.\n"
					+ "Alternate taking turns, removing 1 to 3 sticks per turn.\n"
					+ "The player who takes the last stick loses.\n"
					+ "You may set the number of sticks on the pile at the start "
					+ "of each game,\n  and switch what strategy the computer uses "
					+ "at any time.";
			message.setHeaderText(helpMessage);
			message.setContentText("Would you like to see this dialog at the start of the next game?");
			
			ButtonType btnYes = new ButtonType("Yes");
			ButtonType btnNo = new ButtonType("No");
			message.getButtonTypes().setAll(btnYes, btnNo);
			
			Optional<ButtonType> result = message.showAndWait();
			this.shouldShowHelpDialog = result.get() == btnYes;
		} else {
			this.shouldShowHelpDialog = false;
		}
				
		return this.shouldShowHelpDialog; 
	}
}
