package view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;

@SuppressWarnings("serial")
public class ViewListTeam extends JPanel {

	private Controller controller;
	private ImageIcon imgDelete, imgEdit;
	private JLabel title;
	private boolean deleteGame, editGame;
	private ViewAddGame viewAdd;
}
