package tournament;

import java.util.ArrayList;
import java.util.List;

import tournament.exceptions.NotEnoughParticipantsException;

/**
 * A type of tournament. 
 * This is the most simple tournament, where the looser of a match loose the tournament. 
 * The winner is the participant that have winned each of his matches.
 * @author Group
 * @version 1.0
 */
public class SimpleElimination extends Tournament {
	
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
	
	
	@Override
	public void initializeMatchs() throws NotEnoughParticipantsException {
		assert participants != null;

		int nbParts = participants.size();
		
		if (!((nbParts > 2) && ((nbParts & (nbParts - 1)) == 0 ))) {
			throw new NotEnoughParticipantsException();
		}
		
		super.matchs = new Match[nbParts];
		List<Participant> parts = new ArrayList<>(participants);
		
		for (int i=1; i<(nbParts/2); i++) {
			super.matchs[i] = null;
		}
		for (int i=(nbParts/2); i<(nbParts); i++) {
			super.matchs[i] = new Match(parts.get((i*2)-nbParts), parts.get(((i*2)-nbParts)+1), this.getGame());
		}
	}
	
	
}
