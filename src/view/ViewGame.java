package view;

import java.awt.GridLayout;

import javax.swing.JPanel;

import controller.Controller;

@SuppressWarnings("serial")
public class ViewGame extends JPanel {
	
	private Controller controller;
	
	public ViewGame(Controller controller) {
		this.controller = controller;
		this.setLayout(new GridLayout(0, 2));
		
		ViewListGame listGame = new ViewListGame(this.controller, true);
		ViewAddGame addGame = new ViewAddGame(this.controller);
		
		this.add(listGame);
		this.add(addGame);
	}
}
