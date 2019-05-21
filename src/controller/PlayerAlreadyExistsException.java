package controller;

@SuppressWarnings("serial")
/**
 * An exception thrown if a player is added while a player with the 
 * same name already exists
 * @author Group
 * @version 1.0
 */
public class PlayerAlreadyExistsException extends Exception {

	public PlayerAlreadyExistsException() {
		super("A player with the same name already exists.");
	}
}
