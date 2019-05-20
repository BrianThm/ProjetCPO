package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
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

import controller.Controller;
import controller.GameUsedException;
import tournament.Game;

@SuppressWarnings("serial")
public class ViewListGame extends JPanel {
	
	private Controller controller;
	
	public ViewListGame(Controller controller, boolean deleteGame) {
		this.controller = controller;
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		try {
			controller.addGame(new Game("Game 1 ----------------------------A"));
			controller.addGame(new Game("Game 2 ----------------------------B"));
			controller.addGame(new Game("Game 3 ----------------------------B"));
			controller.addGame(new Game("Game 4 ----------------------------B"));
			controller.addGame(new Game("Game 5 ----------------------------B"));
			controller.addGame(new Game("Game 6 -----------B"));
			controller.addGame(new Game("Game 5 ----------------------------------------C"));
		} catch (Exception e) { }
		
		Set<Game> games = this.controller.getGames();
		
		ImageIcon imgDelete = new ImageIcon(getClass().getResource("/res/delete.png"));
		Image image = imgDelete.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		imgDelete = new ImageIcon(image);
		
		for (Game game : games) {
			JPanel line = new JPanel(new BorderLayout());
			JLabel label = new JLabel(game.getName());
			JLabel labelImg = new JLabel(imgDelete);
			
			labelImg.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    deleteGame(game, line);
                }

            });
			
			label.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 50));
			labelImg.setBorder(BorderFactory.createEmptyBorder(3, 2, 3, 10));
			line.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
			line.add(label, BorderLayout.CENTER);
			if (deleteGame)
				line.add(labelImg, BorderLayout.EAST);
			// line.setPreferredSize(new Dimension(this.getWidth(), 100));
			this.add(line);
		}
		if (controller.getNbGames() == 0) {
			noGame();
		}
		this.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
	}
	
	private void deleteGame(Game game, JPanel line) {
		try {
			int answer = JOptionPane.showConfirmDialog(this,
				"The game " + game.getName() + " will be removed from the list of games. Are you sure ?",
				"Delete " + game.getName(), JOptionPane.YES_NO_OPTION);
			if (answer == 0) {
				this.controller.removeGame(game);
				this.remove(line);
				
				if (controller.getNbGames() == 0)
					noGame();
				
				refreshList();
			}
		} catch(GameUsedException e) {
			JOptionPane.showMessageDialog(this,
			    "The game " + game.getName() + " is used in a tournament and can't be deleted !",
			    "Deletion error",
			    JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void refreshList() {
		this.repaint();
		this.revalidate();
	}
	
	private void noGame() {
		this.removeAll();
		this.setLayout(new GridBagLayout());
		JLabel empty = new JLabel("There isn't any game. You can add one via the Game menu.");
		this.add(empty);
		refreshList();
	}
}
