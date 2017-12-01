/*
 * Written by : Emil Nilsson (c13enn)
 *
 * Projectwork: Anti Tower Defence
 * Group: 5
 *
 * Applikationsprogrammering i Java, HT 2014
 */
package antiTD;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import listeners.updateOutputListener;

/**
 * The GUI of the game.
 * 
 * @author c13enn
 */
public class AntiTDGUI implements updateOutputListener {

	private JFrame frame;
	private JMenuBar menuBar;
	private GameView gameBoard;
	private JPanel buttonContainer;

	// First menu
	private JMenu menuFile;
	private JMenuItem quit;
	private JMenuItem newGame;
	private JMenuItem restartLevel;
	private JMenuItem pause;

	// Second menu
	private JMenu menuHelp;
	private JMenuItem about;
	private JMenuItem help;

	// Buttons
	private JButton buyWalking;
	private JButton buyFlying;
	private JButton buyTeleporter;
	private JButton pauseBtn;
	private JButton swapIntersection;
	private JButton dropTeleport;

	private JTextPane output;

	/**
	 * Creates the GUI for the antitower defense.
	 * 
	 * @param sem
	 *            Semaphore for thread saftey.
	 */
	public AntiTDGUI(Semaphore sem) {
		frame = new JFrame("Anti TD");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		buildMenu();
		buildBoard(sem);

		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}

	/**
	 * Builds the board and the button panel.
	 * 
	 * @param sem
	 *            Semaphore for thread saftey.
	 */
	private void buildBoard(Semaphore sem) {

		// Game board
		gameBoard = new GameView(sem);
		gameBoard.setPreferredSize(new Dimension(500, 500));
		gameBoard.setBackground(Color.LIGHT_GRAY);
		frame.add(gameBoard, BorderLayout.WEST);

		// Button container
		buttonContainer = new JPanel();
		buttonContainer.setLayout(new GridBagLayout());
		buttonContainer.setBackground(Color.WHITE);

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);

		output = new JTextPane();
		output.setEditable(false);
		output.setContentType("text/html");
		c.gridy = 0;
		buttonContainer.add(output, c);

		buyWalking = new JButton("Buy Walking");
		c.gridy = 1;
		buttonContainer.add(buyWalking, c);

		buyFlying = new JButton("Buy Flying");
		c.gridy = 2;
		buttonContainer.add(buyFlying, c);

		buyTeleporter = new JButton("Buy Teleporter");
		c.gridy = 3;
		buttonContainer.add(buyTeleporter, c);

		pauseBtn = new JButton("Pause");
		c.gridy = 4;
		buttonContainer.add(pauseBtn, c);

		swapIntersection = new JButton("Swap intersections");
		c.gridy = 5;
		buttonContainer.add(swapIntersection, c);

		dropTeleport = new JButton("Drop teleporter");
		c.gridy = 6;
		buttonContainer.add(dropTeleport, c);

		frame.add(buttonContainer, BorderLayout.CENTER);
	}

	/**
	 * Builds the menu.
	 */
	private void buildMenu() {
		menuBar = new JMenuBar();

		// First menu
		menuFile = new JMenu("File");
		newGame = new JMenuItem("New game");
		restartLevel = new JMenuItem("Restart level");
		quit = new JMenuItem("Quit");
		pause = new JMenuItem("Pause");

		menuFile.add(pause);
		menuFile.add(newGame);
		menuFile.add(restartLevel);
		menuFile.add(quit);

		// Second menu
		menuHelp = new JMenu("Help");
		about = new JMenuItem("About");
		help = new JMenuItem("Help");

		menuHelp.add(about);
		menuHelp.add(help);

		// The menubar
		menuBar.add(menuFile);
		menuBar.add(menuHelp);
		frame.setJMenuBar(menuBar);
	}

	/**
	 * The textpane where the score is written.
	 * 
	 * @return The JTextPane
	 */
	public JTextPane getOutput() {
		return output;
	}

	/**
	 * Gets the button that will buy a walking robot.
	 * 
	 * @return The JButton;
	 */
	public JButton getBuyWalking() {
		return buyWalking;
	}

	/**
	 * Gets the button that will buy a flying robot.
	 * @return The JButton.
	 */
	public JButton getBuyFlying() {
		return buyFlying;
	}

	/**
	 * Gets the button that will buy a teleporting robot.
	 * @return The JButton.
	 */
	public JButton getBuyTeleporter() {
		return buyTeleporter;
	}

	/**
	 * Gets the pause button.
	 * @return The pause button.
	 */
	public JButton getPauseBtn() {
		return pauseBtn;
	}

	/**
	 * Gets the swap intersections button.
	 * @return The swap intersections button.
	 */
	public JButton getSwapIntersection() {
		return swapIntersection;
	}

	/**
	 * Gets the drop teleport button.
	 * @return The teleport button.
	 */
	public JButton getDropTeleport() {
		return dropTeleport;
	}

	/**
	 * Returns the gameboard.
	 * @return The gameboard.
	 */
	public GameView getGameBoard() {
		return gameBoard;
	}

	/**
	 * Sets the output text to the given String.
	 */
	public void setOutput(String text) {
		output.setText(text);
	}

	/**
	 * Gets the quit option in the menu.
	 * @return The quit option.
	 */
	public JMenuItem getQuit() {
		return quit;
	}

	/**
	 * Gets the new game option in the menu.
	 * @return The new game option.
	 */
	public JMenuItem getNewGame() {
		return newGame;
	}

	/**
	 * Gets the restart level option in the menu.
	 * @return The restart level option.
	 */
	public JMenuItem getRestartLevel() {
		return restartLevel;
	}

	/**
	 * Gets the pause option in the menu.
	 * @return The pause option.
	 */
	public JMenuItem getPause() {
		return pause;
	}

	/**
	 * Gets the window frame.
	 * @return The wondow frame.
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * Gets the about option in the menu.
	 * @return The about option.
	 */
	public JMenuItem getAbout() {
		return about;
	}

	/**
	 * Gets the help option in the menu.
	 * @return The help option.
	 */
	public JMenuItem getHelp() {
		return help;
	}

}
