package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
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
import tournament.Game;
import tournament.Player;
import tournament.Team;

/**
 * The ViewAddTeam is the view wich allow to create and edit a team.
 * @author Group
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ViewAddTeam extends JPanel {

	private Controller controller;
	private JPanel content, panelSave, editCancel, panelCB, panelPlayers;
	private ViewListTeam viewList;
	private JTextField textTeam;
	private JLabel title;
	private JComboBox<Game> comboBox;
	private Game preferredGame;
	private Team teamToEdit;
	private JList listPlayers;
	private boolean isEditing;

	public ViewAddTeam(Controller controller, ViewListTeam viewList) {
		this(controller);
		this.viewList = viewList;
	}

	public ViewAddTeam(Controller controller) {
		super();
		/* Initialization of the attributes */
		this.controller = controller;
		this.viewList = null;
		this.setLayout(new BorderLayout());
		this.content = new JPanel();
		this.content.setLayout(new BoxLayout(this.content, BoxLayout.Y_AXIS));
		this.textTeam = new JTextField(20);
		this.panelSave = new JPanel(new FlowLayout());
		this.panelCB = new JPanel(new FlowLayout());
		this.panelPlayers = new JPanel(new FlowLayout());
		this.isEditing = false;

		/* Initialization of the components */
		JPanel panelName = new JPanel(new FlowLayout());
		JLabel nameTeam = new JLabel("Name ");
		JLabel labelGame = new JLabel("Preferred game ");
		JLabel members = new JLabel("Members of the team ");
		JButton btnSave = new CustomButton("Save the team");
		JButton btnEdit = new CustomButton("Edit the team");
		JButton btnCancel = new CustomButton("Cancel");
		Set<Player> players = this.controller.getPlayers();
		Player[] playersArray = (Player[]) (new ArrayList<Player>(players)).toArray();
		this.panelSave = new JPanel(new FlowLayout());
		this.title = new JLabel("Add a team");
		this.title.setFont(new Font("defaultFont", Font.BOLD, 15));
		this.title.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
		this.editCancel = new JPanel(new FlowLayout());
		this.comboBox = new JComboBox<Game>();
		this.listPlayers = new JList<Player>(playersArray);
		this.listPlayers.setVisibleRowCount(5);
		this.listPlayers.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		panelName.add(nameTeam);
		panelName.add(textTeam);
		panelCB.add(labelGame);
		panelCB.add(comboBox);
		panelPlayers.add(members);
		panelPlayers.add(new JScrollPane(this.listPlayers));
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
		panelCB.setAlignmentX(CENTER_ALIGNMENT);
		panelPlayers.setAlignmentX(CENTER_ALIGNMENT);
		editCancel.setAlignmentX(CENTER_ALIGNMENT);
		panelSave.setAlignmentX(CENTER_ALIGNMENT);

		/* Adding all the components to the main panel */
		this.add(content, BorderLayout.CENTER);
		this.add(panelSave, BorderLayout.SOUTH);
		this.displayAddTeam();

		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					preferredGame = (Game) event.getItem();
				} else {
					preferredGame = null;
				}
			}
		});

		/* When the button to cancel an editing is clicked */
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				displayAddTeam();
			}
		});

		/* When the button to edit a player is clicked */
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				editTeam();
			}
		});

		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (checkFields())
					addTeam(textTeam.getText(), preferredGame);
			}
		});

		/* Empty border outside, gray border inside */
		this.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20),
				BorderFactory.createMatteBorder(2, 2, 2, 2, Color.gray)));
	}

	private void displayAddTeam() {
		clear();
		this.isEditing = false;
		title.setText("Add a team");
		this.remove(editCancel);
		Set<Game> games = this.controller.getGames();
		
		comboBox.removeAllItems();
		comboBox.addItem(null);
		for (Game g : games) {
			comboBox.addItem(g);
		}
		content.add(panelCB);
		this.add(panelSave, BorderLayout.SOUTH);
		refreshPanel();
	}

	void displayEditTeam(Team team) {
		this.title.setText("Edit a team");
		content.remove(panelCB);
		this.remove(panelSave);
		this.add(panelPlayers);
		this.isEditing = true;
		this.teamToEdit = team;

		textTeam.setText(teamToEdit.getName());

		Set<Player> teamPlayers = team.getMembers();
		for (int i = 0; i < listPlayers.getModel().getSize(); i++) {
			if (teamPlayers.contains(listPlayers.getModel().getElementAt(i)))
				listPlayers.setSelectedIndex(i);
		}

		this.add(editCancel, BorderLayout.SOUTH);
		refreshPanel();
	}

	public void teamDeleted(Team team) {
		if (isEditing && teamToEdit == team) {
			displayAddTeam();
		}
	}

	private void editTeam() {
		if (!checkFields()) // Check all the fields
			return;

		Team team = new Team(textTeam.getText());
		if (controller.teamExists(teamToEdit, team)) {
			JOptionPane.showMessageDialog(this, "The team " + textTeam.getText() + " already exists!",
					"Editing not possible", JOptionPane.ERROR_MESSAGE);
			return;
		}

		teamToEdit.setName(textTeam.getText());

		JOptionPane.showMessageDialog(this, "The team " + teamToEdit.getName() + " has successfully been updated!",
				"Team edited", JOptionPane.INFORMATION_MESSAGE);
		isEditing = false;
		displayAddTeam();

		if (viewList != null)
			viewList.makeList();
	}

	private boolean checkFields() {
		int len = textTeam.getText().length();

		if (len == 0) {
			JOptionPane.showMessageDialog(this, "The team must have a name!", "No name", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (len > 50) {
			JOptionPane.showMessageDialog(this, "The name of the team can't exceed 50 characters!", "Name too long", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	private void addTeam(String name, Game game) {
		Team team = (game == null) ? new Team(name) : new Team(name, game);

		try {
			this.controller.addTeam(team);
			JOptionPane.showMessageDialog(this, "The team " + name + " has been successfully added!", "Team " + name + " added", JOptionPane.INFORMATION_MESSAGE);

			if (viewList != null)
				viewList.makeList();

			clear();
		} catch (TeamAlreadyExistsException e) {
			JOptionPane.showMessageDialog(this, "The team " + name + " already exists, you can't add it twice!", "Existing team", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void clear() {
		textTeam.setText("");
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
