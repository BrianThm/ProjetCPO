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
import controller.exceptions.GameUsedException;
import tournament.Game;

/**
 * View which displays the list of the games.
 * @author Group
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ViewListGame extends JPanel {

	private Controller controller;
	private ImageIcon imgDelete, imgEdit;
	private JLabel title;
	private boolean deleteGame, editGame;
	private ViewAddGame viewAdd;

	/**
	 * Creates a new view to list the games. The view can displays delete or edit buttons according to way to open it.
	 * @param controller The controller.
	 * @param deleteGame True if the games can be deleted, else if not.
	 */
	public ViewListGame(Controller controller, boolean deleteGame) {
		/* Initialization of the attributes */
		this.controller = controller;
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.deleteGame = deleteGame;
		this.editGame = false;

		title = new JLabel("List of games");
		title.setFont(new Font("defaultFont", Font.BOLD, 15));
		
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
	 * Get the games of the controller and displays it.
	 * Displays edit or delete buttons according to the way to open the viewListGame.
	 */
	void makeList() {
		Set<Game> games = this.controller.getGames();
		this.removeAll();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(title);
		
		/* Empty border for the outside (kind of margin) and gray border for the inside */
		title.setBorder(new CompoundBorder(
				BorderFactory.createEmptyBorder(15, 0, 15, 0),
				BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray)));
		for (Game game : games) {
			JPanel line = new JPanel(new BorderLayout());
			JLabel label = new JLabel(game.getName());
			JLabel labelImg = new JLabel(imgDelete); // The label that contains the delete image
			JLabel labelImgEdit = new JLabel(imgEdit); // The label that contains the edit image

			/* Listener for the delete button */
			labelImg.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					deleteGame(game, line);
				}
			});

			/* Listener for the edit button */
			labelImgEdit.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					viewAdd.displayEditGame(game);
				}
			});

			/* Empty borders to set some margins */
			label.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 50));
			labelImg.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 15));
			labelImgEdit.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 5));
			line.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));

			// Adding all the right components to the line
			line.add(label, BorderLayout.CENTER);

			// If the user can edit a game, he can also delete it
			if (editGame) {
				JPanel panelImg = new JPanel(new GridLayout(0, 2));
				panelImg.add(labelImgEdit);
				panelImg.add(labelImg);
				line.add(panelImg, BorderLayout.EAST);
			} else if (deleteGame) {
				line.add(labelImg, BorderLayout.EAST);
			}
			// Each line is add to the main panel
			this.add(line);
		}

		// When there isn't any game, the panel displays a sentence.
		if (controller.getNbGames() == 0) {
			noGame();
		}

		refreshList();
	}

	/**
	 * This is used if the list of games is used next to the view to add a game.
	 * @param viewAdd The viewAddGame to set.
	 */
	void setViewAddGame(ViewAddGame viewAdd) {
		this.viewAdd = viewAdd;

		// Adding a viewAddGame means that the games can be edited.
		this.editGame = true;

		// The list has to be made again.
		makeList();
	}

	/**
	 * Delete a game from the list of games.
	 * @param game The game to delete.
	 * @param line The JPanel line where the game is displayed.
	 */
	private void deleteGame(Game game, JPanel line) {
		try {
			int answer = JOptionPane.showConfirmDialog(this,
					"The game " + game.getName() + " will be removed from the list of games. Are you sure?",
					"Delete " + game.getName(), JOptionPane.YES_NO_OPTION);
			if (answer == 0) {
				this.controller.removeGame(game);
				this.remove(line);

				if (controller.getNbGames() == 0)
					noGame();

				refreshList();

				// Signal to the viewAddGame that the game has been deleted
				if (viewAdd != null)
					viewAdd.gameDeleted(game);
			}
		} catch(GameUsedException e) {
			JOptionPane.showMessageDialog(this,
					"The game " + game.getName() + " is used in a tournament and can't be deleted!",
					"Deletion error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Refresh the list when a displays changes.
	 */
	private void refreshList() {
		this.repaint();
		this.revalidate();
	}

	/**
	 * This is called when there isn't any game to display.
	 */
	private void noGame() {
		this.removeAll();
		this.setLayout(new GridBagLayout());
		JLabel empty = new JLabel("There isn't any game. You can add one via the Game menu.");
		this.add(empty);
		refreshList();
	}
}
