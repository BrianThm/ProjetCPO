package view;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.Controller;

@SuppressWarnings("serial")
public class ViewAddGame extends JPanel {
	
	private Controller controller;
	
	public ViewAddGame(Controller controller) {
		this.controller = controller;
		
		JLabel labelAdd = new JLabel("Add a game");
		JTextField textGame = new JTextField(100);
		JCheckBox hasImage = new JCheckBox("Image associated to the game");
		JButton btnAddImg = new JButton("Add an image");
		JButton btnSave = new JButton("Save the game");
	}
}
