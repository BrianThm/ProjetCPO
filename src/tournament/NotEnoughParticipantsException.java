package tournament;

@SuppressWarnings("serial")
/**
 * An exception throw if there isn't enought
 * participants to organize a specific tournament.
 * @author Group
 * @version 1.0
 */
public class NotEnoughParticipantsException extends Exception {

	public NotEnoughParticipantsException() {
		super("There is not enough participants to organise this tournament.");
	}
}
