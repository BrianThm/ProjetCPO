package controller;

@SuppressWarnings("serial")
public class GameUsedException extends Exception {
	
	public GameUsedException() {
		super("The game is used, it can't be removed");
	}
}
