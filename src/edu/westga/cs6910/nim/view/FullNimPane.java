package edu.westga.cs6910.nim.view;

import edu.westga.cs6910.nim.model.Game;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class FullNimPane {
	
	private Game theGame;
	private NimPane nimPane;
	
	public FullNimPane(Game theGame, NimPane nimPane) {
		this.theGame = theGame;
		this.nimPane = nimPane;
//	}
	
	public HBox pnFirstPlayer() {
		this.nimPane.setPnChooseFirstPlayer(new NewGamePane(this.theGame, this.nimPane));	
		HBox box = this.createHBoxHolder(this.nimPane.getPnChooseFirstPlayer(), false);
		return box;
	}
	
	public HBox pnHumanPlayer() {
		this.nimPane.setPnHumanPlayer(new HumanPane(this.theGame));		
		HBox box = this.createHBoxHolder(this.nimPane.getPnHumanPlayer(), true);
		return box;
	}
	
	public HBox pnComputerPlayer() {
		this.nimPane.setPnComputerPlayer(new ComputerPane(this.theGame));		
		HBox box = this.createHBoxHolder(this.nimPane.getPnComputerPlayer(), true);
		return box;
	}
	
	public HBox pnGameInfo() {
		this.nimPane.setPnGameInfo(new StatusPane(theGame));
		HBox box = this.createHBoxHolder(this.nimPane.getPnGameInfo(), true);
		return box;
	}
	
	private HBox createHBoxHolder(Pane newPane, boolean disable) {
		newPane.setDisable(disable);
		HBox leftBox = new HBox();
		leftBox.getStyleClass().add("pane-border");	
		leftBox.getChildren().add(newPane);
		return leftBox;
	}
}
