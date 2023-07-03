package edu.westga.cs6910.nim.view;

import edu.westga.cs6910.nim.model.Game;
import edu.westga.cs6910.nim.model.strategy.CautiousStrategy;
import edu.westga.cs6910.nim.model.strategy.GreedyStrategy;
import edu.westga.cs6910.nim.model.strategy.NumberOfSticksStrategy;
import edu.westga.cs6910.nim.model.strategy.RandomStrategy;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;

/**
 * Create menu bar for NimPane
 * @author 	Justin Maxwell
 * @version Summer 2023
 */
public class NimMenuBar {
	
	private Game theGame;
	private NimPane nimPane;
	
	/**
	 * Constructor fpr NimMenuBar
	 * @param theGame to retrieve the currernt game object
	 * @param nimPane to retrieve the current NimPane object to call NewGame event from menu
	 */
	public NimMenuBar(Game theGame, NimPane nimPane) {
		this.theGame = theGame;
		this.nimPane = nimPane;
	}
	
	/**
	 * layout for menu bar
	 * @return vbxMenuHolder to set top of border pane in NimPane
	 */
	public VBox createMenu() {
		VBox vbxMenuHolder = new VBox();
		
		MenuBar mnuMain = new MenuBar();
		
		Menu mnuGame = this.createGameMenu();
		
		Menu mnuSettings = this.createStrategyMenu();
				
		mnuMain.getMenus().addAll(mnuGame, mnuSettings);
		vbxMenuHolder.getChildren().addAll(mnuMain);
		return vbxMenuHolder;
	}

	/**
	 * Create the strategy menu
	 * @return menuSettings
	 */
	private Menu createStrategyMenu() {
		Menu mnuSettings = new Menu("_Strategy");
		mnuSettings.setMnemonicParsing(true);
		
		ToggleGroup tglStrategy = new ToggleGroup();
		
		RadioMenuItem mnuCautious = new RadioMenuItem("_Cautious");
		mnuCautious.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.SHORTCUT_DOWN));
		mnuCautious.setOnAction(new CautiousComputerListner());
		mnuCautious.setMnemonicParsing(true);
		mnuCautious.setToggleGroup(tglStrategy);
		
		RadioMenuItem mnuGreedy = new RadioMenuItem("Gr_eedy");
		mnuGreedy.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.SHORTCUT_DOWN));
		mnuGreedy.setOnAction(new GreedyComputerListner());
		mnuGreedy.setMnemonicParsing(true);
		mnuGreedy.setToggleGroup(tglStrategy);
		
		RadioMenuItem mnuRandom = new RadioMenuItem("_Random");
		mnuRandom.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.SHORTCUT_DOWN));
		mnuRandom.setOnAction(new RandomComputerListner());
		mnuRandom.setMnemonicParsing(true);
		mnuRandom.setToggleGroup(tglStrategy);
		
		NumberOfSticksStrategy currentStrategy = this.theGame.getComputerPlayer().getStrategy();
		if (currentStrategy.getClass() == CautiousStrategy.class) {
			mnuCautious.setSelected(true);
		} else if (currentStrategy.getClass() == RandomStrategy.class) {
			mnuRandom.setSelected(true);
		} else {
			mnuGreedy.setSelected(true);
		}

		mnuSettings.getItems().addAll(mnuCautious, mnuGreedy, mnuRandom);
		return mnuSettings;
	}
	
	/*
	 * Defines the listener to set strategy to cautious.
	 */
	private class CautiousComputerListner implements EventHandler<ActionEvent> {
		/*
		 * Sets strategy to cautious
		 */

		@Override
		public void handle(ActionEvent event) {
			CautiousStrategy strategy = new CautiousStrategy();
			NimMenuBar.this.theGame.getComputerPlayer().setStrategy(strategy);
		}
	}

	/*
	 * Defines the listener to set strategy to Greedy.
	 */
	private class GreedyComputerListner implements EventHandler<ActionEvent> {
		/*
		 * Sets strategy to Greedy
		 */

		@Override
		public void handle(ActionEvent event) {
			GreedyStrategy strategy = new GreedyStrategy();
			NimMenuBar.this.theGame.getComputerPlayer().setStrategy(strategy);
		}
	}

	/*
	 * Defines the listener to set strategy to Random.
	 */
	private class RandomComputerListner implements EventHandler<ActionEvent> {
		/*
		 * Sets strategy to Random
		 */

		@Override
		public void handle(ActionEvent event) {
			RandomStrategy strategy = new RandomStrategy();
			NimMenuBar.this.theGame.getComputerPlayer().setStrategy(strategy);
		}
	}

	private Menu createGameMenu() {
		Menu mnuFile = new Menu("_Game");
		mnuFile.setMnemonicParsing(true);
	
		MenuItem mnuNew = new MenuItem("_New");
		mnuNew.setMnemonicParsing(true);
		mnuNew.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN));
		mnuNew.setOnAction(this.nimPane.new NewGame());
		
		MenuItem mnuExit = new MenuItem("E_xit");
		mnuExit.setMnemonicParsing(true);
		mnuExit.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.SHORTCUT_DOWN));
		mnuExit.setOnAction(event -> System.exit(0));
		
		mnuFile.getItems().addAll(mnuNew, mnuExit);
		return mnuFile;
	}

}
