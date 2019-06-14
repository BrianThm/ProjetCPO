package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;

import controller.Controller;
import tournament.Game;
import tournament.Team;

@SuppressWarnings("serial")
public class ViewListTeam extends JPanel {

	private Controller controller;
	private ImageIcon imgDelete, imgEdit;
	private JLabel title;
	private boolean deleteTeam, editTeam;
	private ViewAddTeam viewAdd;
	
	public ViewListTeam(Controller controller, boolean deleteTeam) {
		/* Initialization of the attributes */
		this.controller = controller;
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.deleteTeam = deleteTeam;
		this.editTeam = false;

		title = new JLabel("List of teams");
		title.setFont(new Font("defaultFont", Font.BOLD, 15));
		title.setAlignmentX(CENTER_ALIGNMENT);
		title.setBorder(new CompoundBorder(
				BorderFactory.createEmptyBorder(15, 0, 15, 0),
				BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray)));

		/* Creation of the edit image button */
		imgEdit = new ImageIcon(getClass().getResource("/res/edit.png"));
		Image imageEdit = imgEdit.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
		imgEdit = new ImageIcon(imageEdit);

		/* Creation of the delete image button */
		imgDelete = new ImageIcon(getClass().getResource("/res/delete.png"));
		Image image = imgDelete.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
		imgDelete = new ImageIcon(image);

		/* Creates and displays the list of games */
		makeList();

		/* Empty border for the outside (kind of margin) and gray border for the inside */
		this.setBorder(new CompoundBorder(
				BorderFactory.createEmptyBorder(20, 20, 20, 20),
				BorderFactory.createMatteBorder(2, 2, 2, 2, Color.gray)));
	}
	
	/**
	 * This is used if the list of teams is used next to the view to add a team.
	 * @param viewAdd The viewAddTeam to set.
	 */
	void setViewAddTeam(ViewAddTeam viewAdd) {
		this.viewAdd = viewAdd;

		// Adding a viewAddPlayer means that the players can be edited.
		this.editTeam = true;

		// The list has to be made again.
		makeList();
	}

	void makeList() {
		Set<Team> teams = controller.getTeams();
		this.removeAll();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(title);

		for (Team t : teams) {
			this.add(getPanel(t));
		}

		// When there isn't any team, the panel displays a sentence.
		if (controller.getNbTeams() == 0) {
			noTeam();
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
				deleteTeam(team, line);
			}
		});

		/* Listener for the edit button */
		labelImgEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				viewAdd.displayEditTeam(team);
			}
		});

		// If the user can edit a game, he can also delete it
		if (editTeam) {
			JPanel panelImg = new JPanel(new GridLayout(0, 2));
			panelImg.add(labelImgEdit);
			panelImg.add(labelImgDel);
			line.add(panelImg, BorderLayout.EAST);
		} else if (deleteTeam) {
			line.add(labelImgDel, BorderLayout.EAST);
		}

		line.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
		return line;
	}
	
	private void deleteTeam(Team team, JPanel line) {
		int answer = JOptionPane.showConfirmDialog(this,
				"The team " + team.getName() + " will be removed from the list of teams. Are you sure?",
				"Delete " + team.getName(), JOptionPane.YES_NO_OPTION);
		if (answer == 0) {
			this.controller.removeTeam(team);
			this.remove(line);

			if (controller.getNbTeams() == 0)
				noTeam();

			refreshList();

			// Signal to the viewAddGame that the game has been deleted
			if (viewAdd != null)
				viewAdd.teamDeleted(team);
		}
	}
	
	/**
	 * This is called when there isn't any team to display.
	 */
	private void noTeam() {
		this.removeAll();
		this.setLayout(new GridBagLayout());
		JLabel empty = new JLabel("There isn't any team. You can add one via the Team menu.");
		this.add(empty);
		refreshList();
	}

	/**
	 * Refresh the list when a displays changes.
	 */
	private void refreshList() {
		this.repaint();
		this.revalidate();
	}
}
