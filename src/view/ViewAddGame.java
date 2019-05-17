package view;

import javax.swing.JPanel;

import controller.Controller;

@SuppressWarnings("serial")
public class ViewAddGame extends JPanel {
	
	private Controller controller;
	
	public ViewAddGame(Controller controller) {
		this.controller = controller;
	}
}
