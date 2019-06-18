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
import controller.exceptions.GameUsedException;
import tournament.Game;

/**
 * View which displays the list of the games.
 * @author Group
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ViewListGame extends ViewList<Game> {

	/**
	 * Creates a new view to list the games. The view can displays delete or edit buttons according to way to open it.
	 * @param controller The controller.
	 * @param deleteGame True if the games can be deleted, else if not.
	 */
	public ViewListGame(Controller controller, boolean deleteGame) {
		/* Initialization of the attributes */
		super(controller, deleteGame, "game");
	}

	/**
	 * Get the games of the controller and displays it.
	 * Displays edit or delete buttons according to the way to open the viewListGame.
	 */
	@Override
	void makeList() {
		Set<Game> games = this.controller.getGames();
		
		for (Game game : games) {
			JPanel line = new JPanel(new BorderLayout());
			JLabel label = new JLabel(game.getName());
			JLabel labelImg = new JLabel(imgDelete); // The label that contains the delete image
			JLabel labelImgEdit = new JLabel(imgEdit); // The label that contains the edit image

			/* Listener for the delete button */
			labelImg.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					delete(game, line);
				}
			});

			/* Listener for the edit button */
			labelImgEdit.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					viewAdd.displayEdit(game);
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
			if (edit) {
				JPanel panelImg = new JPanel(new GridLayout(0, 2));
				panelImg.add(labelImgEdit);
				panelImg.add(labelImg);
				line.add(panelImg, BorderLayout.EAST);
			} else if (delete) {
				line.add(labelImg, BorderLayout.EAST);
			}
			// Each line is add to the main panel
			this.add(line);
		}

		// When there isn't any game, the panel displays a sentence.
		if (controller.getNbGames() == 0) {
			noElement();
		}

		refreshList();
	}

	/**
	 * Delete a game from the list of games.
	 * @param game The game to delete.
	 * @param line The JPanel line where the game is displayed.
	 */
	@Override 
	void delete(Game game, JPanel line) {
		try {
			int answer = JOptionPane.showConfirmDialog(this,
					"The game " + game.getName() + " will be removed from the list of games. Are you sure?",
					"Delete " + game.getName(), JOptionPane.YES_NO_OPTION);
			if (answer == 0) {
				this.controller.removeGame(game);
				this.remove(line);

				if (controller.getNbGames() == 0)
					noElement();

				refreshList();

				// Signal to the viewAddGame that the game has been deleted
				if (viewAdd != null)
					viewAdd.deleted(game);
			}
		} catch(GameUsedException e) {
			JOptionPane.showMessageDialog(this,
					"The game " + game.getName() + " is used in a tournament and can't be deleted!",
					"Deletion error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
