package tournament.exceptions;

@SuppressWarnings("serial")
/**
 * An exception throw if there isn't enought
 * participants to organize a specific tournament.
 * @author Group
 * @version 1.0
 */
public class NotEnoughParticipantsException extends Exception {

	public NotEnoughParticipantsException() {
		super("There isn't the correct number of participants to organise this tournament !"
				+ "\nIt must have a number of participants equal to a power of two (4, 8, 16, 32...).");
	}
}
