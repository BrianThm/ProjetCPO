package view;

import java.awt.BorderLayout;
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
		this.setLayout(new BorderLayout());
		
		JPanel content = new JPanel();
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
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		
		content.add(labelAdd);
		content.add(separator);
		content.add(nameText);
		content.add(checkBtn);
		content.add(btnSave);
		content.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.gray));
		this.add(content, BorderLayout.CENTER);
		this.setBorder(BorderFactory.createEmptyBorder(10, 20, 50, 20));
	}
}
