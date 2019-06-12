package tournament.exceptions;

@SuppressWarnings("serial")
/**
 * This exception is throw when a match is finished with
 * a draw game in a simple or double elimination tournament.
 * A match cannot be draw in these kind of tournament, it must 
 * have a winner.
 * @author Group
 */
public class MatchDrawException extends RuntimeException {
	
	public MatchDrawException() {
		super("Error : A match cannot be draw in this kind of tournament."
				+ "Please replay the match again or set a different score.");
	}
}
