package view;

import java.awt.GridLayout;

import javax.swing.JPanel;

import controller.Controller;

@SuppressWarnings("serial")
public class ViewPlayer extends JPanel {

private Controller controller;
	
	/**
	 * Constructor to create the view with the lsit of players and the form to add a player.
	 * @param controller The controller.
	 */
	public ViewPlayer(Controller controller) {
		this.controller = controller;
		// Grid layout with two columns, one for each view
		this.setLayout(new GridLayout(0, 2));
		
		// The two views need to be linked by each other when there is a modification in one of them.
		ViewListPlayer listPlayer = new ViewListPlayer(this.controller, true);
		ViewAddPlayer addPlayer = new ViewAddPlayer(this.controller, listPlayer);
		listPlayer.setViewAddPlayer(addPlayer);
		
		this.add(listPlayer);
		this.add(addPlayer);
	}
}
