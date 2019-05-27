package tournament;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import controller.NotEnoughPartsException;

/**
 * A type of tournament. 
 * This is the most simple tournament, where the looser of a match loose the tournament. 
 * The winner is the participant that have winned each of his matches.
 * @author Group
 * @version 1.0
 */
public class SimpleElimination extends Tournament {

	private Match[] matchs;
	
	/**
	 * Create an empty SimpleElimination tournament.
	 */
	public SimpleElimination(Game game) {
		this(game, "");
	}
	
	/**
	 * Create a SimpleElimination tournament.
	 * @param location The location of the tournament.
	 */
	public SimpleElimination(Game game, String location) {
		super(game, location);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Initialize the SimpleElimination tournament tree.
	 * @param partipants The parcipants of the tournament.
	 */
	public void initializeMatchs(Set<Participant> participants) {
		assert participants != null;

		int nbParts = participants.size();
		
		try {
			if (!(nbParts > 2) && ((nbParts & (nbParts - 1)) == 0 )) {
				throw new NotEnoughPartsException();
			}
		} catch (NotEnoughPartsException e) {
			System.out.println(e.getMessage());
			System.out.println("There must be a number of participants "
					+ "equal to a power of 2 (2, 4, 8, 16...)");
		}
		
		this.matchs = new Match[nbParts-1];
		List<Participant> parts = new ArrayList<>(participants);
		
		for (int i=1; i<(nbParts/2); i++) {
			this.matchs[i] = null;
		}
		for (int i=(nbParts/2); i<(nbParts-1); i++) {
			this.matchs[i] = new Match(parts.get((i*2)-nbParts), 
										parts.get(((i*2)-nbParts)-1), 
										this.getGame());
		}
	}
	
	
}
