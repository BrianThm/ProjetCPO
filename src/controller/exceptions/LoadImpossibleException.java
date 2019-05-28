package controller.exceptions;

@SuppressWarnings("serial")
/**
 * An exception thrown if the load is impossible.
 * @author Group
 * @version 1.0
 */
public class LoadImpossibleException extends Exception {
	
	public LoadImpossibleException(String msg) {
		super(msg);
	}
}
