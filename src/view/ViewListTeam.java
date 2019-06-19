package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.Controller;
import tournament.Game;
import tournament.Team;

/**
 * ViewListTeam is a subclass of ViewList<Team>. Displays the list of teams.
 * @author Group
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ViewListTeam extends ViewList<Team> {
	
	/**
	 * Constructor of ViewListTeam. Initialize the edit and delete image and set the borders.
	 * @param controller The controller.
	 * @param deleteTeam If the user is allowed to delete a team via the displayed list.
	 */
	public ViewListTeam(Controller controller, boolean deleteTeam) {
		super(controller, deleteTeam, "teams");
	}

	/**
	 * Makes the list of teams.
	 */
	@Override
	void makeList() {
		super.makeList();
		
		Set<Team> teams = controller.getTeams();

		for (Team t : teams) {
			this.add(getPanel(t));
		}

		// When there isn't any team, the panel displays a sentence.
		if (controller.getNbTeams() == 0) {
			noElement();
		}

		refreshList();
	}
	
	private JPanel getPanel(Team team) {
		JPanel line = new JPanel(new BorderLayout());
		JPanel infosTeam = new JPanel(new GridLayout(0, 2));
		JLabel labelImgDel = new JLabel(imgDelete); // The label that contains the delete image
		JLabel labelImgEdit = new JLabel(imgEdit); // The label that contains the edit image
		String labelTextName, labelTextGame = "<html>";

		String name = team.getName();
		int nbPlayer = team.getMembers().size();

		Game game = team.getPreferredGame();

		labelTextName = "<html>Name: " + name + "<br/>";
		labelTextName += "Number of members: " + nbPlayer + "<br/> </html>";

		if (game != null) {
			labelTextGame += "Preferred game: " + game.getName() + "<br/>";
		}

		JLabel textName = new JLabel(labelTextName);
		JLabel textGame = new JLabel(labelTextGame);
		infosTeam.add(textName);
		infosTeam.add(textGame);
		textName.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 10));
		textGame.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 40));

		line.add(infosTeam, BorderLayout.CENTER);

		labelImgDel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 15));
		labelImgEdit.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 5));

		/* Listener for the delete button */
		labelImgDel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				delete(team, line);
			}
		});

		/* Listener for the edit button */
		labelImgEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				viewAdd.displayEdit(team);
			}
		});

		// If the user can edit a game, he can also delete it
		if (edit) {
			JPanel panelImg = new JPanel(new GridLayout(0, 2));
			panelImg.add(labelImgEdit);
			panelImg.add(labelImgDel);
			line.add(panelImg, BorderLayout.EAST);
		} else if (edit) {
			line.add(labelImgDel, BorderLayout.EAST);
		}

		line.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
		return line;
	}

	/**
	 * Delete a team and remove the line which displays it.
	 */
	@Override
	void delete(Team team, JPanel line) {
		int answer = JOptionPane.showConfirmDialog(this,
				"The team " + team.getName() + " will be removed from the list of teams. Are you sure?",
				"Delete " + team.getName(), JOptionPane.YES_NO_OPTION);
		if (answer == 0) {
			this.controller.removeTeam(team);
			this.remove(line);

			if (controller.getNbTeams() == 0)
				noElement();

			refreshList();

			// Signal to the viewAddGame that the game has been deleted
			if (viewAdd != null)
				viewAdd.deleted(team);
		}
	}
}
