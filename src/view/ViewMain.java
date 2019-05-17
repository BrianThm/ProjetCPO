package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		JMenu menuTeam = new JMenu("Team");
		JMenu menuPlayer = new JMenu("Player");
		JMenuItem addGame = new JMenuItem("Add a game");
		JMenuItem deleteGame = new JMenuItem("Delete a game");
		JMenuItem displayGames = new JMenuItem("Display all games");
		JMenuItem manageGames = new JMenuItem("Manage games");
		JMenuItem addTeam = new JMenuItem("Add a team");
		JMenuItem deleteTeam = new JMenuItem("Delete a team");
		JMenuItem displayTeams = new JMenuItem("Display all teams");
		JMenuItem manageTeams = new JMenuItem("Manage teams");
		JMenuItem addPlayer = new JMenuItem("Add a player");
		JMenuItem deletePlayer = new JMenuItem("Delete a player");
		JMenuItem displayPlayers = new JMenuItem("Display all players");
		JMenuItem managePlayers = new JMenuItem("Manage players");
		
		menubar.add(menuGame);
		menubar.add(menuTeam);
		menubar.add(menuPlayer);
		menuGame.add(addGame);
		menuGame.add(deleteGame);
		menuGame.add(displayGames);
		menuGame.add(manageGames);
		menuTeam.add(addTeam);
		menuTeam.add(deleteTeam);
		menuTeam.add(displayTeams);
		menuTeam.add(manageTeams);
		menuPlayer.add(addPlayer);
		menuPlayer.add(deletePlayer);
		menuPlayer.add(displayPlayers);
		menuPlayer.add(managePlayers);
		
		displayGames.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new ViewListGame(controller, true);
			}
		});
		
		this.setJMenuBar(menubar);
		this.setSize(500, 500);
		this.setVisible(true);
	}
}
