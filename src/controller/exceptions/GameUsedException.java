package controller.exceptions;

@SuppressWarnings("serial")
/**
 * An exception thrown if a game used in a tournament is removed.
 * @author Group
 * @version 1.0
 */
public class GameUsedException extends Exception {
	
	public GameUsedException() {
		super("The game is used, it can't be removed.");
	}
}
