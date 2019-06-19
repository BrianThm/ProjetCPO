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
import tournament.Player;
import tournament.Team;

/**
 * Class ViewListPlayer subclass of ViewList<Player>. Displays the list of players.
 * @author Group
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ViewListPlayer extends ViewList<Player> {

	/**
	 * Constructor of ViewListPlayer. Initialize the edit and delete image and set the borders.
	 * @param controller The controller.
	 * @param deletePlayer If the lsit allows the user to delete a player.
	 */
	public ViewListPlayer(Controller controller, boolean deletePlayer) {
		super(controller, deletePlayer, "players");
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
				delete(player, line);
			}
		});

		/* Listener for the edit button */
		labelImgEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				viewAdd.displayEdit(player);
			}
		});

		// If the user can edit a game, he can also delete it
		if (edit) {
			JPanel panelImg = new JPanel(new GridLayout(0, 2));
			panelImg.add(labelImgEdit);
			panelImg.add(labelImgDel);
			line.add(panelImg, BorderLayout.EAST);
		} else if (delete) {
			line.add(labelImgDel, BorderLayout.EAST);
		}

		line.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
		return line;
	}

	/**
	 * Makes the list of players.
	 */
	@Override
	void makeList() {
		super.makeList();
		
		Set<Player> players = controller.getPlayers();

		for (Player p : players) {
			this.add(getPanel(p));
		}

		// When there isn't any player, the panel displays a sentence.
		if (controller.getNbPlayers() == 0) {
			noElement();
		}

		refreshList();
	}

	/**
	 * Delete a player and remove the line which displays it.
	 */
	@Override
	void delete(Player player, JPanel line) {
		int answer = JOptionPane.showConfirmDialog(this,
				"The player " + player.getName() + " will be removed from the list of players. Are you sure?",
				"Delete " + player.getName(), JOptionPane.YES_NO_OPTION);
		if (answer == 0) {
			this.controller.removePlayer(player);
			this.remove(line);

			if (controller.getNbPlayers() == 0)
				noElement();

			refreshList();

			// Signal to the viewAddGame that the game has been deleted
			if (viewAdd != null)
				viewAdd.deleted(player);
		}
	}
}
