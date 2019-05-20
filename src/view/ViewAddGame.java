package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import controller.Controller;

@SuppressWarnings("serial")
public class ViewAddGame extends JPanel {
	
	private Controller controller;
	
	public ViewAddGame(Controller controller) {
		this.controller = controller;
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JLabel labelAdd = new JLabel("Add a game");
		JLabel nameGame = new JLabel("Name : ");
		JTextField textGame = new JTextField(20);
		JPanel nameText = new JPanel(new FlowLayout());
		JCheckBox hasImage = new JCheckBox("Image associated to the game");
		JButton btnAddImg = new JButton("Add an image");
		JPanel checkBtn = new JPanel(new FlowLayout());
		JButton btnSave = new JButton("Save the game");
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.darkGray);
		
		labelAdd.setFont(new Font("defaultFont", Font.BOLD, 15));
		
		nameText.add(nameGame);
		nameText.add(textGame);
		checkBtn.add(hasImage);
		checkBtn.add(btnAddImg);
		
		this.add(labelAdd);
		this.add(separator);
		this.add(nameText);
		this.add(checkBtn);
		this.add(btnSave);
		this.setBorder(BorderFactory.createEmptyBorder(10, 20, 50, 20));
	}
}
