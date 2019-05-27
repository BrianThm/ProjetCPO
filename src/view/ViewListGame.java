package view;

import java.awt.BorderLayout;
import java.awt.Color;
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
import controller.GameUsedException;
import tournament.Game;

@SuppressWarnings("serial")
public class ViewListGame extends JPanel {
	
	private Controller controller;
	private ImageIcon imgDelete;
	private ImageIcon imgEdit;
	private boolean deleteGame;
	private boolean editGame;
	private ViewAddGame viewAdd;
	
	public ViewListGame(Controller controller, boolean deleteGame) {
		this.controller = controller;
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.deleteGame = deleteGame;
		this.editGame = false;
		
//		try {
//			Game g1 = new Game("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
//			Game g2 = new Game("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
//			this.controller.addGame(g1);
//			this.controller.addGame(g2);
//		} catch (Exception e) {}
		
		imgEdit = new ImageIcon(getClass().getResource("/res/edit.png"));
		Image imageEdit = imgEdit.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		imgEdit = new ImageIcon(imageEdit);
		
		imgDelete = new ImageIcon(getClass().getResource("/res/delete.png"));
		Image image = imgDelete.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		imgDelete = new ImageIcon(image);
		
		makeList();
		
		this.setBorder(new CompoundBorder(
				BorderFactory.createEmptyBorder(20, 20, 20, 20),
				BorderFactory.createMatteBorder(2, 2, 2, 2, Color.gray)));
	}
	
	void makeList() {
		Set<Game> games = this.controller.getGames();
		this.removeAll();
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		for (Game game : games) {
			JPanel line = new JPanel(new BorderLayout());
			JLabel label = new JLabel(game.getName());
			JLabel labelImg = new JLabel(imgDelete);
			JLabel labelImgEdit = new JLabel(imgEdit);
			
			labelImg.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    deleteGame(game, line);
                }
            });
			
			labelImgEdit.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    viewAdd.displayEditGame(game);
                }
            });
			
			label.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 50));
			labelImg.setBorder(BorderFactory.createEmptyBorder(3, 2, 3, 15));
			labelImgEdit.setBorder(BorderFactory.createEmptyBorder(3, 2, 3, 5));
			line.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
			line.add(label, BorderLayout.CENTER);
			if (editGame) {
				JPanel panelImg = new JPanel(new GridLayout(0, 2));
				panelImg.add(labelImgEdit);
				panelImg.add(labelImg);
				line.add(panelImg, BorderLayout.EAST);
			} else if (deleteGame) {
				line.add(labelImg, BorderLayout.EAST);
			}
			this.add(line);
		}
		if (controller.getNbGames() == 0) {
			noGame();
		}
		refreshList();
	}
	
	void setViewAddGame(ViewAddGame viewAdd) {
		this.viewAdd = viewAdd;
		this.editGame = true;
		makeList();
	}
	
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
