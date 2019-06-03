package controller.exceptions;

@SuppressWarnings("serial")
/**
 * An exception thrown if a team is added while a team with the 
 * same name already exists
 * @author Group
 * @version 1.0
 */
public class TeamAlreadyExistsException extends Exception {

	public TeamAlreadyExistsException() {
		super("A team with the same name already exists.");
	}
}
