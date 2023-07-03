package edu.westga.cs6910.nim.view;

import edu.westga.cs6910.nim.model.Game;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * Build pnContent in NimPane
 * @author Justin Maxwell
 * @version Summer 2023
 */
public class FullNimPane {
	
	private Game theGame;
	private HumanPane pnHumanPlayer;
	private ComputerPane pnComputerPlayer;
	private StatusPane pnGameInfo;
	private NewGamePane pnChooseFirstPlayer;
	
	/**
	 * Constructor to build FullNimPane
	 * @param theGame to retrieve the current game
	 * @param nimPane to retrieve the current NimPane and use in pnFirstPlayer
	 */
	public FullNimPane(Game theGame, NimPane nimPane) {
		this.theGame = theGame;
	}
	
	/**
	 * initialize pnFirstPlayer
	 * @return box
	 */
	public HBox pnFirstPlayer() {
		this.pnChooseFirstPlayer = new NewGamePane(this.theGame, this);	
		HBox box = this.createHBoxHolder(this.pnChooseFirstPlayer, false);
		return box;
	}
	
	/**
	 * initialize pnHumanPlayer
	 * @return box
	 */
	public HBox pnHumanPlayer() {
		this.pnHumanPlayer = new HumanPane(this.theGame);		
		HBox box = this.createHBoxHolder(this.pnHumanPlayer, true);
		return box;
	}
	
	/**
	 * initialize pnComputerPlayer
	 * @return box
	 */
	public HBox pnComputerPlayer() {
		this.pnComputerPlayer = new ComputerPane(this.theGame);		
		HBox box = this.createHBoxHolder(this.pnComputerPlayer, true);
		return box;
	}
	
	/**
	 * initialize pnGameInfo
	 * @return box
	 */
	public HBox pnGameInfo() {
		this.pnGameInfo = new StatusPane(this.theGame);
		HBox box = this.createHBoxHolder(this.pnGameInfo, true);
		return box;
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