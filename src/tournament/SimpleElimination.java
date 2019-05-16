package tournament;

/**
 * @author Groupe
 * @version 1.0
 * A type of tournament.
 * This is the most simple tournament, where the looser of a match loose the tournament.
 * The winner is the participant that have winned each of his matches.
 */
public class SimpleElimination extends Tournament {

	/**
	 * Create an empty SimpleElimination tournament.
	 */
	public SimpleElimination() {
		this("");
	}
	
	/**
	 * Create a SimpleElimination tournament.
	 * @param location The location of the tournament.
	 */
	public SimpleElimination(String location) {
		super(location);
		// TODO Auto-generated constructor stub
	}

}
