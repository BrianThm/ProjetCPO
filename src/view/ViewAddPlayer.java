package view;

import javax.swing.JPanel;

import controller.Controller;
import tournament.Player;

@SuppressWarnings("serial")
public class ViewAddPlayer extends JPanel {
	
	private Controller controller;
	private ViewListPlayer viewList;
	
	public ViewAddPlayer(Controller controller, ViewListPlayer viewList) {
		this(controller);
		this.viewList = viewList;
	}
	
	public ViewAddPlayer(Controller controller) {
		super();
		/* Initialization of the attributes */
		this.controller = controller;
		this.viewList = null;
	}
	
	void displayEditPlayer(Player player) {
		// TODO
		System.out.println("Edit player " + player.getName());
	}
}
