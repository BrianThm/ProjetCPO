package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import controller.Controller;

public class ViewMain extends JFrame {
	
	Controller controller;
	
	public ViewMain(Controller controller, String title) {
		super(title);
		this.controller = controller;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container cont = this.getContentPane();		
		
		JMenuBar menubar = new JMenuBar();
		JMenu menuGame = new JMenu("Game");
		JMenuItem addGame = new JMenuItem("Add a game");
		JMenuItem deleteGame = new JMenuItem("Delete a game");
		JMenuItem displayGames = new JMenuItem("Display all games");
		JMenuItem manageGames = new JMenuItem("Manage games");
		
		menubar.add(menuGame);
		menuGame.add(addGame);
		menuGame.add(deleteGame);
		menuGame.add(displayGames);
		menuGame.add(manageGames);
		
		this.setJMenuBar(menubar);
		this.setSize(500, 500);
		this.setVisible(true);
	}
}
