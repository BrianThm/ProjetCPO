package view;

import java.util.Set;

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
		
	}
}
