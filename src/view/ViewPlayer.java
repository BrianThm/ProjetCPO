package view;

import java.awt.GridLayout;

import javax.swing.JPanel;

import controller.Controller;
import tournament.Player;

/**
 * ViewPlayer which displays the ViewListPlayer next to the ViewAddPlayer.
 * @author Group
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ViewPlayer extends JPanel {

	private Controller controller;

	/**
	 * Constructor to create the view with the list of players and the form to add a player.
	 * @param controller The controller.
	 */
	public ViewPlayer(Controller controller) {
		this.controller = controller;
		// Grid layout with two columns, one for each view
		this.setLayout(new GridLayout(0, 2));

		// The two views need to be linked by each other when there is a modification in one of them.
		ViewList<Player> listPlayer = new ViewListPlayer(this.controller, true);
		ViewAdd<Player> addPlayer = new ViewAddPlayer(this.controller, listPlayer);
		listPlayer.setViewAdd(addPlayer);

		this.add(listPlayer);
		this.add(addPlayer);
	}
}
