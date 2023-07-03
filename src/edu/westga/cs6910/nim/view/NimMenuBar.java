package edu.westga.cs6910.nim.view;

import java.time.LocalDate;

import edu.westga.cs6910.nim.model.Game;
import edu.westga.cs6910.nim.model.strategy.CautiousStrategy;
import edu.westga.cs6910.nim.model.strategy.GreedyStrategy;
import edu.westga.cs6910.nim.model.strategy.NumberOfSticksStrategy;
import edu.westga.cs6910.nim.model.strategy.RandomStrategy;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
	private NimHelpDialog showHelpDialog;
	private ToggleGroup tglStrategy;
	private Menu mnuSettings;
	private FullNimPane fullNimPane;
	
	/**
	 * Constructor fpr NimMenuBar
	 * @param theGame to retrieve the currernt game object
	 * @param fullNimPane to retrieve the current FullNimPane objects to call NewGame event from menu
	 * @param showHelpDialog to retrieve the current game object for displaying the showHelpDialog
	 */
	public NimMenuBar(Game theGame, FullNimPane fullNimPane, NimHelpDialog showHelpDialog) {
		this.theGame = theGame;
		this.fullNimPane = fullNimPane;
		this.showHelpDialog = showHelpDialog;
		this.tglStrategy = new ToggleGroup();
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
		
		Menu mnuHelp = this.createHelpMenu();
				
		mnuMain.getMenus().addAll(mnuGame, mnuSettings, mnuHelp);
		vbxMenuHolder.getChildren().addAll(mnuMain);
		return vbxMenuHolder;
	}

	/**
	 * Create the strategy menu
	 * @return menuSettings
	 */
	private Menu createStrategyMenu() {
		this.mnuSettings = new Menu("_Strategy");
		this.mnuSettings.setMnemonicParsing(true);
		
		RadioMenuItem mnuCautious = new RadioMenuItem();
		mnuCautious = this.addStrategyItem("_Cautious", KeyCode.C, "cautious");
		
		RadioMenuItem mnuGreedy = new RadioMenuItem();
		mnuGreedy = this.addStrategyItem("Gr_eedy", KeyCode.E, "greedy");
		
		RadioMenuItem mnuRandom = new RadioMenuItem();
		mnuRandom = this.addStrategyItem("_Random", KeyCode.R, "random");
		
		NumberOfSticksStrategy currentStrategy = this.theGame.getComputerPlayer().getStrategy();
		if (currentStrategy.getClass() == CautiousStrategy.class) {
			mnuCautious.setSelected(true);
		} else if (currentStrategy.getClass() == RandomStrategy.class) {
			mnuRandom.setSelected(true);
		} else {
			mnuGreedy.setSelected(true);
		}

		this.mnuSettings.getItems().addAll(mnuCautious, mnuGreedy, mnuRandom);

		return this.mnuSettings;
	}
	
	private RadioMenuItem addStrategyItem(String mnuItmTxt, KeyCode shortCutKey, String strategy) {
		RadioMenuItem menuItem = new RadioMenuItem(mnuItmTxt);
		menuItem.setAccelerator(new KeyCodeCombination(shortCutKey, KeyCombination.SHORTCUT_DOWN));
		
		if (strategy.equals("random")) {
			menuItem.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent theEventObject) {
					NimMenuBar.this.theGame.getComputerPlayer().setStrategy(new RandomStrategy());
				}
			});
			
		} else if (strategy.equals("greedy")) {
			menuItem.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					NimMenuBar.this.theGame.getComputerPlayer().setStrategy(new GreedyStrategy());
				}
			});
			
		} else {
			menuItem.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					NimMenuBar.this.theGame.getComputerPlayer().setStrategy(new CautiousStrategy());
				}
			});
		}
		
		menuItem.setMnemonicParsing(true);
		menuItem.setToggleGroup(this.tglStrategy);
		
		return menuItem;
	}

	private Menu createGameMenu() {
		Menu mnuFile = new Menu("_Game");
		mnuFile.setMnemonicParsing(true);
	
		MenuItem mnuNew = new MenuItem("_New");
		mnuNew.setMnemonicParsing(true);
		mnuNew.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN));
		mnuNew.setOnAction(event -> {
			NewGame newGame = new NewGame();
			newGame.handle(null);
		});
		
		MenuItem mnuExit = new MenuItem("E_xit");
		mnuExit.setMnemonicParsing(true);
		mnuExit.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.SHORTCUT_DOWN));
		mnuExit.setOnAction(event -> System.exit(0));
		
		mnuFile.getItems().addAll(mnuNew, mnuExit);
		return mnuFile;
	}
	
	/** 
	 * Defines the listener for New Game NimMenuBar.
	 */	
	public class NewGame implements EventHandler<ActionEvent> {
		/** 
		 * Sets up user interface and starts a new game. 
		 * Event handler for a click in the human player button.
		 */
		@Override
		public void handle(ActionEvent theEventObject) {
			NimMenuBar.this.fullNimPane.getPnChooseFirstPlayer().reset();
			NimMenuBar.this.fullNimPane.getPnChooseFirstPlayer().setDisable(false);
			NimMenuBar.this.fullNimPane.getPnHumanPlayer().setDisable(true);
			NimMenuBar.this.fullNimPane.getPnComputerPlayer().setDisable(true);
			NimMenuBar.this.fullNimPane.getPnComputerPlayer().resetNumberTaken();
			NimMenuBar.this.fullNimPane.getPnHumanPlayer().resetNumberToTakeComboBox();
			NimMenuBar.this.showHelpDialog.showHelpDialog();
		}
	}
	
	private Menu createHelpMenu() {
		Menu mnuHelp = new Menu("_Help");
		mnuHelp.setMnemonicParsing(true);
	
		MenuItem mnuHelpItem = new MenuItem("He_lp");
		mnuHelpItem.setMnemonicParsing(true);
		mnuHelpItem.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.SHORTCUT_DOWN));
		mnuHelpItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				NimMenuBar.this.showHelpDialog.setShouldShowHelpDialog(true);
				NimMenuBar.this.showHelpDialog.showHelpDialog();
			}
		});

		MenuItem mnuAbout = new MenuItem("_About");
		mnuAbout.setMnemonicParsing(true);
		mnuAbout.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.SHORTCUT_DOWN));
		mnuAbout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LocalDate currentDate = LocalDate.now();
				Alert aboutMsg = new Alert(AlertType.INFORMATION);
				aboutMsg.setTitle("CS6910: Better Nim");
				aboutMsg.setHeaderText("About");
				aboutMsg.setHeaderText("Justin Maxwell \n" + currentDate);
				aboutMsg.showAndWait();
			}
		});
		
		mnuHelp.getItems().addAll(mnuHelpItem, mnuAbout);
		return mnuHelp;
	}

}
