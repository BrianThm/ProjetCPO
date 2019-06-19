package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

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
public class ViewAddTeam extends ViewAdd<Team> {

	private JPanel panelCB, panelPlayers;
	private JTextField textTeam;
	private JLabel membersDisplay;
	private JComboBox<Game> comboBox;
	private Game preferredGame;
	private JList<Player> listPlayers;

	public ViewAddTeam(Controller controller, ViewList<Team> viewList) {
		this(controller);
		this.viewList = viewList;
	}

	public ViewAddTeam(Controller controller) {
		super(controller, "team");
		/* Initialization of the attributes */
		this.textTeam = new JTextField(20);
		this.panelCB = new JPanel(new FlowLayout());
		this.panelPlayers = new JPanel(new FlowLayout());
		this.membersDisplay = new JLabel();
		this.isEditing = false;

		/* Initialization of the components */
		JPanel panelName = new JPanel(new FlowLayout());
		JLabel nameTeam = new JLabel("Name ");
		JLabel labelGame = new JLabel("Preferred game ");
		JLabel members = new JLabel("Members of the team ");
		List<Player> listP = this.controller.getSortedPlayers();
		Player[] playersArray = listP.toArray(new Player[listP.size()]);
		
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
		content.add(panelName);

		/* All is centered */
		panelName.setAlignmentX(CENTER_ALIGNMENT);
		panelCB.setAlignmentX(CENTER_ALIGNMENT);

		this.displaySave();

		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					preferredGame = (Game) event.getItem();
				} else {
					preferredGame = null;
				}
			}
		});
	}

	@Override
	protected void displaySave() {
		super.displaySave();

		ArrayList<Game> games = (ArrayList<Game>) this.controller.getSortedGames();

		comboBox.removeAllItems();
		comboBox.addItem(null);
		
		for (Game g : games)
			comboBox.addItem(g);
		
		content.add(panelCB);
		content.remove(panelPlayers);
		content.add(panelPlayers);

		refreshPanel();
	}

	
		
	
		//content.remove(panelCB);
	@Override
	protected void displayEdit(Team team) {
		super.displayEdit(team);
		clear();
		
		textTeam.setText(toEdit.getName());

		Set<Player> teamPlayers = team.getMembers();
		List<Integer> selectedPlayers = new ArrayList<Integer>();
		
		for (int i = 0; i < listPlayers.getModel().getSize(); i++) {
			if (teamPlayers.contains(listPlayers.getModel().getElementAt(i)))
				selectedPlayers.add(i);
		}
		
		int[] arraySelected = new int[selectedPlayers.size()];
		
		for (int i = 0; i < arraySelected.length; i++)
			arraySelected[i] = selectedPlayers.get(i);
		
		ArrayList<Player> listEffectivePlayers = new ArrayList<Player>(team.getMembers());
		membersDisplay.setText("Members: {");
		for (int i=0; i<listEffectivePlayers.size()-1; i++) {
			String texte = membersDisplay.getText();
			membersDisplay.setText(texte + listEffectivePlayers.get(i).getName() + ", ");
		}
		membersDisplay.setText(membersDisplay.getText()+ listEffectivePlayers.get(listEffectivePlayers.size()-1).getName());
		membersDisplay.setText(membersDisplay.getText() + "}");
		
		listPlayers.setSelectedIndices(arraySelected);
		content.add(membersDisplay);
		this.add(editCancel, BorderLayout.SOUTH);
		refreshPanel();
	}

	@Override
	protected void edit() {
		if (!checkFields()) // Check all the fields
			return;
		
		List<Player> newPlayers = listPlayers.getSelectedValuesList();
		Team team = new Team(textTeam.getText());
		
		for (Player p : newPlayers)
			team.addMember(p);
		
		if (controller.teamExists(toEdit, team)) {
			JOptionPane.showMessageDialog(this, "The team " + textTeam.getText() + " already exists!",
					"Editing not possible", JOptionPane.ERROR_MESSAGE);
			return;
		}

		toEdit.setName(textTeam.getText());
		Set<Player> oldPlayers = toEdit.getMembers();

		for (Player p : new ArrayList<Player>(oldPlayers)) {
			if (!newPlayers.contains(p))
				toEdit.removeMember(p);
		}
		
		for (Player p : new ArrayList<Player>(newPlayers)) {
			if (!oldPlayers.contains(p))
				toEdit.addMember(p);
		}

		JOptionPane.showMessageDialog(this, "The team " + toEdit.getName() + " has successfully been updated!",
				"Team edited", JOptionPane.INFORMATION_MESSAGE);
		isEditing = false;
		displaySave();

		if (viewList != null)
			viewList.makeList();
	}

	@Override
	protected boolean checkFields() {
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

	@Override
	protected void save() {
		String name = textTeam.getText();
		Game game = preferredGame;
		
		Team team = (game == null) ? new Team(name) : new Team(name, game);
		List<Player> players = listPlayers.getSelectedValuesList();

		for (Player p : players)
			team.addMember(p);

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

	@Override
	protected void clear() {
		textTeam.setText("");
		membersDisplay.setText("");
		listPlayers.clearSelection();
		comboBox.setSelectedItem(null);
	}
}
