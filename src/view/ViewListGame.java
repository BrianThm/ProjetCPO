package view;

import java.awt.BorderLayout;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;
import tournament.Game;

public class ViewListGame extends JPanel {
	
	private boolean deleteGame;
	private Controller controller;
	
	public ViewListGame(Controller controller, boolean deleteGame) {
		this.controller = controller;
		this.deleteGame = deleteGame;

		Set<Game> games = this.controller.getGames();
		games.add(new Game("Game 1"));
		games.add(new Game("Game 2"));
		
		JPanel list = new JPanel(new BorderLayout());
		ImageIcon imgDelete = new ImageIcon(getClass().getResource("img/delete.png"));
		JLabel img = new JLabel(imgDelete);
		
		for (Game game : games) {
			JPanel line = new JPanel(new BorderLayout());
			JLabel label = new JLabel(game.getName());
			
			list.add(line);
			line.add(label, BorderLayout.CENTER);
			
			if (deleteGame)
				line.add(img, BorderLayout.EAST);
		}
	}
}
