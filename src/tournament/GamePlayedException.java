package tournament;

@SuppressWarnings("serial")
/**
 * An exception thrown if a game is removed from a participant 
 * while he plays in a tournament with this game.
 * @author Group
 * @version 1.0
 */
public class GamePlayedException extends Exception {

	public GamePlayedException() {
		super("This participant plays in a tournament with this game.");
	}
}
