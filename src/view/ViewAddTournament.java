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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.CompoundBorder;

import controller.Controller;
import controller.exceptions.TeamAlreadyExistsException;
import tournament.DoubleElimination;
import tournament.Game;
import tournament.Participant;
import tournament.Player;
import tournament.SimpleElimination;
import tournament.Team;
import tournament.Tournament;

/**
 * The ViewAddTeam is the view wich allow to create and edit a tournaments.
 * @author Group
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ViewAddTournament extends JPanel {

	private Controller controller;
	private JPanel content, panelSave, editCancel, panelType, panelCB, panelParticipants;
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
		this.isEditing = false;
		
		/* Initialization of the components */
		JPanel panelName = new JPanel(new FlowLayout());
		JLabel locationTournament = new JLabel("Location ");
		JLabel typeTournament = new JLabel("Type ");
		JLabel labelGame = new JLabel("Game of the tournament");
		JLabel participants = new JLabel("Participants");
		JButton addParticipant = new CustomButton("Add Participant");
		JButton btnSave = new CustomButton("Save the tournament");
		JButton btnEdit = new CustomButton("Edit the tournament");
		JButton btnCancel = new CustomButton("Cancel");
		List<Participant> listP = new ArrayList<Participant>(this.controller.getSortedPlayers());
		listP.addAll(new ArrayList<Participant>(this.controller.getSortedTeams()));
		Participant[] participantArray = listP.toArray(new Participant[listP.size()]);
		this.panelSave = new JPanel(new FlowLayout());
		this.title = new JLabel("Add a tournament");
		this.title.setFont(new Font("defaultFont", Font.BOLD, 15));
		this.title.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
		this.editCancel = new JPanel(new FlowLayout());
		this.cbTournament = new JComboBox<String>();
		this.comboBox = new JComboBox<Game>();
		this.listParticipant = new JList<Participant>(participantArray);
		this.listParticipant.setVisibleRowCount(5);
		this.listParticipant.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		panelName.add(locationTournament);
		panelName.add(textLocation);
		panelType.add(typeTournament);
		panelType.add(cbTournament);
		panelCB.add(labelGame);
		panelCB.add(comboBox);
		panelParticipants.add(participants);
		panelParticipants.add(new JScrollPane(this.listParticipant));
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
				addTournament(game, textLocation.getText(), type);
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

		this.controller.addTournament(tournament);
		JOptionPane.showMessageDialog(this, "The tournament on "+ game + " has been successfully added!", "Tournament " + game + " added", JOptionPane.INFORMATION_MESSAGE);

		if (viewList != null)
			viewList.makeList();

		clear();
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
