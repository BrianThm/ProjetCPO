package view;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Controller;
import controller.LoadImpossibleException;
import controller.SaveImpossibleException;

@SuppressWarnings("serial")
public class ViewMain extends JFrame {

	Controller controller;

	public ViewMain(Controller controller, String title) {
		super(title);
		this.controller = controller;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container cont = this.getContentPane();

		JMenuBar menubar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		JMenu menuGame = new JMenu("Game");
		JMenu menuTeam = new JMenu("Team");
		JMenu menuPlayer = new JMenu("Player");
		JMenuItem saveData = new JMenuItem("Save the data");
		JMenuItem loadData = new JMenuItem("Load the data");
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

		menubar.add(menuFile);
		menubar.add(menuGame);
		menubar.add(menuTeam);
		menubar.add(menuPlayer);
		menuFile.add(saveData);
		menuFile.add(loadData);
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

		cont.setLayout(new BorderLayout());

		saveData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileNameExtensionFilter("Tournaments save", "txt"));
				fileChooser.showSaveDialog(null);

				File file = fileChooser.getSelectedFile();
				if (file == null) {
					return;
				}
				String filename = file.toString();
				
				if (!filename.endsWith(".txt")) {
					filename += ".txt";
				}
				try {
					controller.save(filename);
				} catch (SaveImpossibleException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		loadData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileNameExtensionFilter("Tournaments save", "txt"));
				fileChooser.showOpenDialog(null);

				File file = fileChooser.getSelectedFile();
				if (file == null) {
					return;
				}

				String filename = file.toString();
				try {
					controller.load(filename);
				} catch (LoadImpossibleException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		addGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel viewAdd = new ViewAddGame(controller);
				JScrollPane scrollPane = new JScrollPane(viewAdd);
				cont.removeAll();
				cont.add(scrollPane, BorderLayout.CENTER);
				viewAdd.updateUI();
				refresh();
			}
		});

		deleteGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel viewList = new ViewListGame(controller, true);
				JScrollPane scrollPane = new JScrollPane(viewList);
				cont.removeAll();
				cont.add(scrollPane, BorderLayout.CENTER);
				viewList.updateUI();
				refresh();
			}
		});

		displayGames.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel viewList = new ViewListGame(controller, false);
				JScrollPane scrollPane = new JScrollPane(viewList);
				cont.removeAll();
				cont.add(scrollPane, BorderLayout.CENTER);
				viewList.updateUI();
				refresh();
			}
		});

		manageGames.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel viewGame = new ViewGame(controller);
				JScrollPane scrollPane = new JScrollPane(viewGame);
				cont.removeAll();
				cont.add(scrollPane, BorderLayout.CENTER);
				viewGame.updateUI();
				refresh();
			}
		});

		addPlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel viewAdd = new ViewAddPlayer(controller);
				JScrollPane scrollPane = new JScrollPane(viewAdd);
				cont.removeAll();
				cont.add(scrollPane, BorderLayout.CENTER);
				viewAdd.updateUI();
				refresh();
			}
		});

		deletePlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel viewList = new ViewListPlayer(controller, true);
				JScrollPane scrollPane = new JScrollPane(viewList);
				cont.removeAll();
				cont.add(scrollPane, BorderLayout.CENTER);
				viewList.updateUI();
				refresh();
			}
		});

		displayPlayers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel viewList = new ViewListPlayer(controller, false);
				JScrollPane scrollPane = new JScrollPane(viewList);
				cont.removeAll();
				cont.add(scrollPane, BorderLayout.CENTER);
				viewList.updateUI();
				refresh();
			}
		});

		managePlayers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel viewPlayer = new ViewPlayer(controller);
				JScrollPane scrollPane = new JScrollPane(viewPlayer);
				cont.removeAll();
				cont.add(scrollPane, BorderLayout.CENTER);
				viewPlayer.updateUI();
				refresh();
			}
		});

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(0, 0, screenSize.width, screenSize.height);

		this.setJMenuBar(menubar);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void refresh() {
		this.revalidate();
		this.repaint();
	}
}
