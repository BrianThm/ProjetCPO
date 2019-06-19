 package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

/**
 * Main View which displays a home page and allow the user to use all the application functionalities.
 * @author Group
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ViewMain extends JFrame {

	private Container cont;
	private Controller controller;

	/**
	 * Constructor of the ViewMain which displays the menuBar and a home page.
	 * @param controller The controller
	 * @param title The frame title.
	 */
	public ViewMain(Controller controller, String title) {
		super(title);
		this.controller = controller;
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
		JMenuItem displayTournaments = new JMenuItem("Display all tournaments");
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
		menuTournament.add(displayTournaments);
		menuTournament.add(deleteTournament);

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
		
		displayTournaments.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeView(ViewList(controller, false));
			}
		});
		
		deleteTournament.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeView(ViewList(controller, true));
			}
		});

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		homePage();
		this.setBounds(0, 0, screenSize.width, screenSize.height);
		this.setJMenuBar(menubar);		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private void homePage() {
		cont.setLayout(new BoxLayout(cont, BoxLayout.Y_AXIS));
		
		JLabel title = new JLabel("Welcome to Esport tournament management software!");

		title.setFont(new Font("defaultFont", Font.BOLD, 30));
		title.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
		
		JLabel info = new JLabel("This application allows you to manage esport tournaments.");

		JPanel panelInfo = new JPanel(new FlowLayout());
		JPanel panelGame = new JPanel(new FlowLayout());
		JPanel panelPlayer = new JPanel(new FlowLayout());
		JPanel panelTeam = new JPanel(new FlowLayout());
		JPanel panelTournament = new JPanel(new FlowLayout());
		JPanel panelLoad = new JPanel(new FlowLayout());
		
		JLabel labelGame = new JLabel("Add a game via the games manager ");
		JLabel labelPlayer = new JLabel("Add a player via the players manager ");
		JLabel labelTeam = new JLabel("If you have any player, you can add a team via the teams manager ");
		JLabel labelTournament = new JLabel("You're now able to add a tournament ");
		JLabel labelLoad = new JLabel("If you've already saved your data, you can load it here ");
		
		JButton manageGame = new CustomButton("Manage games");
		JButton managePlayer = new CustomButton("Manage players");
		JButton manageTeam = new CustomButton("Manage teams");
		JButton manageTournament = new CustomButton("Add a tournament");
		JButton loadFile = new CustomButton("Load previous save");
		
		manageGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeView(new ViewGame(controller));
			}
		});
		
		managePlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeView(new ViewPlayer(controller));
			}
		});
		
		manageTeam.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeView(new ViewTeam(controller));
			}
		});
		
		manageTournament.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeView(new ViewAddTournament(controller));
			}
		});
		
		loadFile.addActionListener(new ActionListener() {
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
		
		panelInfo.add(info);
		panelGame.add(labelGame);
		panelGame.add(manageGame);
		panelPlayer.add(labelPlayer);
		panelPlayer.add(managePlayer);
		panelTeam.add(labelTeam);
		panelTeam.add(manageTeam);
		panelTournament.add(labelTournament);
		panelTournament.add(manageTournament);
		panelLoad.add(labelLoad);
		panelLoad.add(loadFile);
		
		cont.add(title);
		cont.add(Box.createRigidArea(new Dimension(0, 50)));
		cont.add(panelInfo);
		cont.add(panelGame);
		cont.add(panelPlayer);
		cont.add(panelTeam);
		cont.add(panelTournament);
		cont.add(panelLoad);
	}
	
	/**
	 * Changes the view of the main window.
	 * @param view The view to display.
	 */
	public void changeView(JPanel view) {
		cont.setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(view);
		cont.removeAll();
		cont.add(scrollPane, BorderLayout.CENTER);
		view.updateUI();
		refresh();
	}
	
	private ViewListTournament ViewList(Controller controller,boolean delete) {
		return new ViewListTournament(controller, delete, this);
	}

	private void refresh() {
		this.revalidate();
		this.repaint();
	}
}
