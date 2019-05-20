package controller;

@SuppressWarnings("serial")
/**
 * An exception thrown if a game is added while a game with the 
 * same name already exists
 * @author Group
 * @version 1.0
 */
public class GameAlreadyExistsException extends Exception {

	public GameAlreadyExistsException() {
		super("A game with the same name already exists.");
	}
}
