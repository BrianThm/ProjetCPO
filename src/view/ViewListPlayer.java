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
import tournament.Player;
import tournament.Team;

@SuppressWarnings("serial")
public class ViewListPlayer extends JPanel {

	private Controller controller;
	private boolean deletePlayer, editPlayer;
	private ImageIcon imgDelete, imgEdit;
	private ViewAddPlayer viewAdd;
	private JLabel title;

	public ViewListPlayer(Controller controller, boolean deletePlayer) {
		super();
		this.controller = controller;
		this.deletePlayer = deletePlayer;
		this.editPlayer = false;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.title = new JLabel("List of players");
		this.title.setAlignmentX(CENTER_ALIGNMENT);
		this.title.setFont(new Font("defaultFont", Font.BOLD, 15));
		/* Empty border for the outside (kind of margin) and gray border for the inside */
		this.title.setBorder(new CompoundBorder(
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

		/* Fill list of players */
		makeList();

		/* Empty border for the outside (kind of margin) and gray border for the inside */
		this.setBorder(new CompoundBorder(
				BorderFactory.createEmptyBorder(20, 20, 20, 20),
				BorderFactory.createMatteBorder(2, 2, 2, 2, Color.gray)));
	}

	private JPanel getPanel(Player player) {
		JPanel line = new JPanel(new BorderLayout());
		JPanel infosPlayer = new JPanel(new GridLayout(0, 2));
		JLabel labelImgDel = new JLabel(imgDelete); // The label that contains the delete image
		JLabel labelImgEdit = new JLabel(imgEdit); // The label that contains the edit image
		String labelTextName, labelTextGame = "<html>";

		String nickname = player.getName();
		String fname = player.getFName();
		String lname = player.getLName();

		Game game = player.getPreferredGame();
		Team team = player.getPreferredTeam();

		labelTextName = "<html>Nickname: " + nickname + "<br/>";
		if (!fname.isEmpty()) {
			labelTextName += "Firstname: " + fname + "<br/>";
			labelTextName += "Lastname: " + lname;
		}
		labelTextName += "</html>";

		if (game != null) {
			labelTextGame += "Preferred game: " + game.getName() + "<br>";
		}

		if (team != null) {
			labelTextGame += "Preferred team: " + team.getName();
		}
		labelTextGame += "</html>";

		JLabel textName = new JLabel(labelTextName);
		JLabel textGame = new JLabel(labelTextGame);
		infosPlayer.add(textName);
		infosPlayer.add(textGame);
		textName.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 10));
		textGame.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 40));

		line.add(infosPlayer, BorderLayout.CENTER);

		labelImgDel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 15));
		labelImgEdit.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 5));

		/* Listener for the delete button */
		labelImgDel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deletePlayer(player, line);
			}
		});

		/* Listener for the edit button */
		labelImgEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				viewAdd.displayEditPlayer(player);
			}
		});

		// If the user can edit a game, he can also delete it
		if (editPlayer) {
			JPanel panelImg = new JPanel(new GridLayout(0, 2));
			panelImg.add(labelImgEdit);
			panelImg.add(labelImgDel);
			line.add(panelImg, BorderLayout.EAST);
		} else if (deletePlayer) {
			line.add(labelImgDel, BorderLayout.EAST);
		}

		line.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
		return line;
	}

	/**
	 * This is used if the list of players is used next to the view to add a player.
	 * @param viewAdd The viewAddPlayer to set.
	 */
	void setViewAddPlayer(ViewAddPlayer viewAdd) {
		this.viewAdd = viewAdd;

		// Adding a viewAddPlayer means that the players can be edited.
		this.editPlayer = true;

		// The list has to be made again.
		makeList();
	}

	void makeList() {
		Set<Player> players = controller.getPlayers();
		this.removeAll();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(title);

		for (Player p : players) {
			this.add(getPanel(p));
		}

		// When there isn't any player, the panel displays a sentence.
		if (controller.getNbPlayers() == 0) {
			noPlayer();
		}

		refreshList();
	}

	private void deletePlayer(Player player, JPanel line) {
		int answer = JOptionPane.showConfirmDialog(this,
				"The player " + player.getName() + " will be removed from the list of players. Are you sure?",
				"Delete " + player.getName(), JOptionPane.YES_NO_OPTION);
		if (answer == 0) {
			this.controller.removePlayer(player);
			this.remove(line);

			if (controller.getNbPlayers() == 0)
				noPlayer();

			refreshList();

			// Signal to the viewAddGame that the game has been deleted
			if (viewAdd != null)
				viewAdd.playerDeleted(player);
		}
	}

	/**
	 * This is called when there isn't any player to display.
	 */
	private void noPlayer() {
		this.removeAll();
		this.setLayout(new GridBagLayout());
		JLabel empty = new JLabel("There isn't any player. You can add one via the Player menu.");
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
