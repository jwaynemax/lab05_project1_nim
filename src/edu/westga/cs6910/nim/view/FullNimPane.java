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
	}
	
	public HBox pnFirstPlayer() {
		this.nimPane.setPnChooseFirstPlayer(new NewGamePane(this.theGame, this.nimPane));	
		HBox topBox = this.createHBoxHolder(this.nimPane.getPnChooseFirstPlayer(), false);
		return topBox;
	}
	
	private HBox createHBoxHolder(Pane newPane, boolean disable) {
		newPane.setDisable(disable);
		HBox leftBox = new HBox();
		leftBox.getStyleClass().add("pane-border");	
		leftBox.getChildren().add(newPane);
		return leftBox;
	}

}
