package controller.exceptions;

@SuppressWarnings("serial")
/**
 * An exception thrown if the save was impossible.
 * @author Group
 * @version 1.0
 */
public class SaveImpossibleException extends Exception {
	
	public SaveImpossibleException(String msg) {
		super(msg);
	}
}
