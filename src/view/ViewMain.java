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
import controller.exceptions.LoadImpossibleException;
import controller.exceptions.SaveImpossibleException;

@SuppressWarnings("serial")
public class ViewMain extends JFrame {

	//private Controller controller;
	private Container cont;

	public ViewMain(Controller controller, String title) {
		super(title);
		//this.controller = controller;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		cont = this.getContentPane();

		JMenuBar menubar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		JMenu menuGame = new JMenu("Game");
		JMenu menuTeam = new JMenu("Team");
		JMenu menuPlayer = new JMenu("Player");
		JMenu menuTournament = new JMenu("Tournament");
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
		JMenuItem addTournament = new JMenuItem("Add a tournament");
		JMenuItem deleteTournament = new JMenuItem("Delete a tournament");

		menubar.add(menuFile);
		menubar.add(menuGame);
		menubar.add(menuTeam);
		menubar.add(menuPlayer);
		menubar.add(menuTournament);
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
		menuTournament.add(addTournament);
		menuTournament.add(deleteTournament);


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
				changeView(new ViewAddGame(controller));
			}
		});

		deleteGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeView(new ViewListGame(controller, true));
			}
		});

		displayGames.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeView(new ViewListGame(controller, false));
			}
		});

		manageGames.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeView(new ViewGame(controller));
			}
		});

		addPlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeView(new ViewAddPlayer(controller));
			}
		});

		deletePlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeView(new ViewListPlayer(controller, true));
			}
		});

		displayPlayers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeView(new ViewListPlayer(controller, false));
			}
		});

		managePlayers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeView(new ViewPlayer(controller));
			}
		});
		
		addTeam.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeView(new ViewAddTeam(controller));
			}
		});
		
		deleteTeam.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeView(new ViewListTeam(controller, true));
			}
		});
		
		displayTeams.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeView(new ViewListTeam(controller, false));
			}
		});

		manageTeams.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeView(new ViewTeam(controller));
			}
		});
		
		addTournament.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeView(new ViewAddTournament(controller));
			}
		});
		
		deleteTournament.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeView(new ViewAddTournament(controller));
			}
		});

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(0, 0, screenSize.width, screenSize.height);

		this.setJMenuBar(menubar);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private void changeView(JPanel view) {
		JScrollPane scrollPane = new JScrollPane(view);
		cont.removeAll();
		cont.add(scrollPane, BorderLayout.CENTER);
		view.updateUI();
		refresh();
	}

	private void refresh() {
		this.revalidate();
		this.repaint();
	}
}
