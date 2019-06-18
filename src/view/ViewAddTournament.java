package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.CompoundBorder;

import controller.Controller;
import tournament.DoubleElimination;
import tournament.Game;
import tournament.Participant;
import tournament.SimpleElimination;
import tournament.Tournament;
import tournament.exceptions.NotEnoughParticipantsException;

/**
 * The ViewAddTeam is the view wich allow to create and edit a tournaments.
 * @author Group
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ViewAddTournament extends JPanel {

	private ViewMain fenetre;
	private Controller controller;
	private JPanel content, panelSave, editCancel;
	private JPanel panelType, panelCB, panelRadio, panelParticipants;
	private ViewListTeam viewList;
	private JTextField textLocation;
	private JLabel title;
	private JComboBox<String> cbTournament;
	private JComboBox<Game> comboBox;
	private String type;
	private Game game;
	private Tournament tournamentToEdit;
	private JList<Participant> listParticipant;
	private boolean isEditing;
	private boolean isPlayerSelected;
	private Date today = new Date(); 

	public ViewAddTournament(Controller controller, ViewListTeam viewList) {
		this(controller);
		this.viewList = viewList;
	}

	public ViewAddTournament(Controller controller) {
		super();
		/* Initialization of the attributes */
		this.controller = controller;
		this.viewList = null;
		this.setLayout(new BorderLayout());
		this.content = new JPanel();
		this.content.setLayout(new BoxLayout(this.content, BoxLayout.Y_AXIS));
		this.textLocation = new JTextField(20);
		this.panelSave = new JPanel(new FlowLayout());
		this.panelType = new JPanel(new FlowLayout());
		this.panelCB = new JPanel(new FlowLayout());
		this.panelParticipants = new JPanel(new FlowLayout());
		this.panelRadio = new JPanel(new GridLayout(2, 0));
		this.isEditing = false;
		
		/* Initialization of the components */
		JPanel panelName = new JPanel(new FlowLayout());
		JLabel locationTournament = new JLabel("Location ");
		JLabel typeTournament = new JLabel("Type ");
		JLabel labelGame = new JLabel("Game of the tournament");
		JRadioButton rbtnPlayers = new JRadioButton("Players");
		JRadioButton rbtnTeams = new JRadioButton("Teams");
		JButton addParticipant = new CustomButton("Add");
		JButton btnSave = new CustomButton("Save the tournament");
		JButton btnEdit = new CustomButton("Edit the tournament");
		JButton btnCancel = new CustomButton("Cancel");
		ButtonGroup btnGrp = new ButtonGroup();
		
		/* By default, players are displayed */
		isPlayerSelected = true;
		List<Participant> listP = new ArrayList<Participant>(this.controller.getSortedPlayers());
		Participant[] playersArray = listP.toArray(new Participant[listP.size()]);
		
		this.panelSave = new JPanel(new FlowLayout());
		this.title = new JLabel("Add a tournament");
		this.title.setFont(new Font("defaultFont", Font.BOLD, 15));
		this.title.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
		this.editCancel = new JPanel(new FlowLayout());
		this.cbTournament = new JComboBox<String>();
		this.comboBox = new JComboBox<Game>();
		this.listParticipant = new JList<Participant>(playersArray);
		this.listParticipant.setVisibleRowCount(5);
		this.listParticipant.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		rbtnPlayers.setSelected(true);
		
		panelName.add(locationTournament);
		panelName.add(textLocation);
		panelType.add(typeTournament);
		panelType.add(cbTournament);
		panelCB.add(labelGame);
		panelCB.add(comboBox);
		btnGrp.add(rbtnPlayers);	// Group the two radio buttons
		btnGrp.add(rbtnTeams);
		panelRadio.add(rbtnPlayers);
		panelRadio.add(rbtnTeams);
		panelParticipants.add(panelRadio);
		panelParticipants.add(new JScrollPane(this.listParticipant));
		panelParticipants.add(addParticipant);
		editCancel.add(btnEdit);
		editCancel.add(btnCancel);
		panelSave.add(btnSave);
		content.add(title);
		content.add(Box.createRigidArea(new Dimension(0, 20)));
		content.add(panelName);

		content.setBorder(BorderFactory.createEmptyBorder(15, 5, 15, 5));
		panelSave.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
		editCancel.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));

		/* All is centered */
		title.setAlignmentX(CENTER_ALIGNMENT);
		panelName.setAlignmentX(CENTER_ALIGNMENT);
		panelType.setAlignmentX(CENTER_ALIGNMENT);
		panelCB.setAlignmentX(CENTER_ALIGNMENT);
		panelParticipants.setAlignmentX(CENTER_ALIGNMENT);
		editCancel.setAlignmentX(CENTER_ALIGNMENT);
		panelSave.setAlignmentX(CENTER_ALIGNMENT);

		/* Adding all the components to the main panel */
		this.add(content, BorderLayout.CENTER);
		this.add(panelSave, BorderLayout.SOUTH);
		this.displayAddTournament();

		/* Check the selected type of tournament */
		cbTournament.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED){
					type = (String) arg0.getItem();
				} else {
					type = null;
				}
			}
		});
		
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					game = (Game) event.getItem();
				} else {
					game = null;
				}
			}
		});
		
		/* When a radio button is selected to display players
		 * or teams in the list of participants */
		rbtnPlayers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				isPlayerSelected = true;
				List<Participant> listP = new ArrayList<Participant>(controller.getSortedPlayers());
				Participant[] playersArray = listP.toArray(new Participant[listP.size()]);
				listParticipant.setListData(playersArray);
				refreshPanel();
			}
		});
		
		rbtnTeams.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				isPlayerSelected = false;
				List<Participant> listP = new ArrayList<Participant>(controller.getSortedTeams());
				Participant[] teamsArray = listP.toArray(new Participant[listP.size()]);
				listParticipant.setListData(teamsArray);
				refreshPanel();
			}
		});
		
		/* When the button add participant is clicked, a popup will appear 
		 * to add players or teams.
		 * When the popup is closed by the user, listParticpant is updated. 
		 * */
		addParticipant.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (isPlayerSelected) {
					JDialog popup = new popup(new ViewAddPlayer(controller));
					popup.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent e) {
						    rbtnPlayers.doClick();
						 }
					});
				} else {
					JDialog popup = new popup(new ViewAddTeam(controller));
					popup.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent e) {
						    rbtnTeams.doClick();
						 }
					});
				}
			}
		});
		
		/* When the button to cancel an editing is clicked */
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				displayAddTournament();
			}
		});

		/* When the button to edit a player is clicked */
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				editTournament();
			}
		});

		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (type == null) {
					JOptionPane.showMessageDialog(content, "You must select a type of tournament !",
							"Any type selected !", JOptionPane.ERROR_MESSAGE);
				} else if (game == null) {
					JOptionPane.showMessageDialog(content, "You must select a game for the tournament !",
							"Any game selected !", JOptionPane.ERROR_MESSAGE);
				} else {
					addTournament(game, textLocation.getText(), type);
				}
			}
		});

		/* Empty border outside, gray border inside */
		this.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20),
				BorderFactory.createMatteBorder(2, 2, 2, 2, Color.gray)));
	}
	

	private void displayAddTournament() {
		clear();
		this.isEditing = false;
		title.setText("Add a tournament");
		this.remove(editCancel);
		ArrayList<Game> games = (ArrayList<Game>) this.controller.getSortedGames();

		cbTournament.removeAllItems();
		cbTournament.addItem(null);
		cbTournament.addItem("Simple Elimination");
		cbTournament.addItem("Double Elimination");		
		
		comboBox.removeAllItems();
		comboBox.addItem(null);
		for (Game g : games) {
			comboBox.addItem(g);
		}
		content.add(panelType);
		content.add(panelCB);
		content.remove(panelParticipants);
		content.add(panelParticipants);
		this.add(panelSave, BorderLayout.SOUTH);
		refreshPanel();
	}

	void displayEditTournament(Tournament tournament) {
		this.title.setText("Edit a tournament");
		content.remove(panelCB);
		this.remove(panelSave);
		this.isEditing = true;
		this.tournamentToEdit = tournament;

		textLocation.setText(tournament.getLocation());

		List<Participant> tournamentParticipants = tournament.getParticipants();
		List<Integer> selectedParticipants = new ArrayList<Integer>();
		for (int i = 0; i < listParticipant.getModel().getSize(); i++) {
			if (tournamentParticipants.contains(listParticipant.getModel().getElementAt(i)))
				selectedParticipants.add(i);
		}
		int[] arraySelected = new int[selectedParticipants.size()];
		
		for (int i = 0; i < arraySelected.length; i++)
			arraySelected[i] = selectedParticipants.get(i);
		
		listParticipant.setSelectedIndices(arraySelected);
		this.add(editCancel, BorderLayout.SOUTH);
		refreshPanel();
	}

	public void tournamentDeleted(Tournament tournament) {
		if (isEditing && tournamentToEdit == tournament) {
			displayAddTournament();
		}
	}

	
	private void editTournament() {

		List<Participant> newParticipants = listParticipant.getSelectedValuesList();
		Tournament tournament = new SimpleElimination(today, tournamentToEdit.getGame());
		
		for (Participant p : newParticipants)
			tournament.addParticipant(p);
		
		/*if (controller.teamExists(teamToEdit, team)) {
			JOptionPane.showMessageDialog(this, "The team " + textTeam.getText() + " already exists!",
					"Editing not possible", JOptionPane.ERROR_MESSAGE);
			return;
		}*/

		tournamentToEdit.setLocation(textLocation.getText());
		List<Participant> oldParticipants = tournamentToEdit.getParticipants();

		for (Participant p : new ArrayList<Participant>(oldParticipants)) {
			if (!newParticipants.contains(p))
				tournamentToEdit.removeParticipant(p);
		}
		
		for (Participant p : new ArrayList<Participant>(newParticipants)) {
			if (!oldParticipants.contains(p))
				tournamentToEdit.addParticipant(p);
		}

		JOptionPane.showMessageDialog(this, "The tournament on " + tournamentToEdit.getGame() + " has successfully been updated!",
				"Team edited", JOptionPane.INFORMATION_MESSAGE);
		isEditing = false;
		displayAddTournament();

		if (viewList != null)
			viewList.makeList();
	}

	
	private void addTournament(Game game, String location, String type) {
		Tournament tournament;
		if (type == "Simple Elimination") {
			tournament = (location == null || location.length() == 0) 
					? new SimpleElimination(today, game) : new SimpleElimination(today, game, location);
		} else {
			tournament = (location == null || location.length() == 0) 
					? new DoubleElimination(today, game) : new DoubleElimination(today, game, location);
		}
		List<Participant> parts =  listParticipant.getSelectedValuesList();

		for (Participant p : parts)
			tournament.addParticipant(p);

		try {
			tournament.initializeMatchs();
			this.controller.addTournament(tournament);
			JOptionPane.showMessageDialog(this, "The tournament on "+ game + " has been successfully added!", "Tournament " + game + " added", JOptionPane.INFORMATION_MESSAGE);
		} catch (NotEnoughParticipantsException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Bad number of participants !", JOptionPane.ERROR_MESSAGE);
		}
		
		if (viewList != null)
			viewList.makeList();

		clear();
	}

	private class popup extends JDialog {
		public popup(JPanel jp) {
			super(fenetre, "Add");
			super.setAlwaysOnTop(true);
			super.add(jp);
			super.pack();
			super.setVisible(true);
		}
	}
	
	
	private void clear() {
		textLocation.setText("");
		listParticipant.clearSelection();
		comboBox.setSelectedItem(null);
	}

	/**
	 * Refresh the panel when the display changes.
	 */
	private void refreshPanel() {
		this.repaint();
		this.revalidate();
	}
}
