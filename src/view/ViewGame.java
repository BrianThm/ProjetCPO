package view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import controller.Controller;

@SuppressWarnings("serial")
public class ViewGame extends JPanel {
	
	private Controller controller;
	
	public ViewGame(Controller controller) {
		this.controller = controller;
		
		ViewListGame listGame = new ViewListGame(this.controller, true);
		ViewAddGame addGame = new ViewAddGame(this.controller);
		
		this.add(listGame, BorderLayout.WEST);
		this.add(addGame, BorderLayout.EAST);
	}
}
