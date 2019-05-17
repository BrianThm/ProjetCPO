package view;

import java.awt.BorderLayout;
import java.awt.Image;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import controller.Controller;
import tournament.Game;

@SuppressWarnings("serial")
public class ViewListGame extends JPanel {
	
	private Controller controller;
	
	public ViewListGame(Controller controller, boolean deleteGame) {
		this.controller = controller;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel list = new JPanel(new BorderLayout());
		Set<Game> games = this.controller.getGames();
		
		games.add(new Game("Game 1 ----------------------------A"));
		games.add(new Game("Game 2 ----------------------------B"));
		games.add(new Game("Game 3 ----------------------------B"));
		games.add(new Game("Game 4 ----------------------------B"));
		games.add(new Game("Game 5 ----------------------------B"));
		games.add(new Game("Game 6 -----------B"));
		games.add(new Game("Game 5 ----------------------------------------C"));
	
		ImageIcon imgDelete = new ImageIcon(getClass().getResource("/res/delete.png"));
		Image image = imgDelete.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		imgDelete = new ImageIcon(image);
		
		for (Game game : games) {
			JPanel line = new JPanel(new BorderLayout());
			JLabel label = new JLabel(game.getName());
			JLabel labelImg = new JLabel(imgDelete);
			
			labelImg.setBorder(BorderFactory.createEmptyBorder(3, 40, 3, 2));
			line.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
			this.add(new JSeparator());
			list.add(line);
			line.add(label, BorderLayout.CENTER);
			if (deleteGame)
				line.add(labelImg, BorderLayout.EAST);
			this.add(line);
		}
		this.add(new JSeparator());	
		this.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
	}
}
