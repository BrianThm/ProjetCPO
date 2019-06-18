package view;

import java.awt.GridLayout;

import javax.swing.JPanel;

import controller.Controller;
import tournament.Game;

/**
 * View of the list of games and the view to add a game.
 * @author Group
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ViewGame extends JPanel {

	private Controller controller;

	/**
	 * Constructor to create the view with the lsit of games and the form to add a game.
	 * @param controller The controller.
	 */
	public ViewGame(Controller controller) {
		this.controller = controller;
		// Grid layout with two columns, one for each view
		this.setLayout(new GridLayout(0, 2));

		// The two views need to be linked by each other when there is a modification in one of them.
		ViewList<Game> listGame = new ViewListGame(this.controller, true);
		ViewAdd<Game> addGame = new ViewAddGame(this.controller, listGame);
		listGame.setViewAdd(addGame);

		this.add(listGame);
		this.add(addGame);
	}
}
