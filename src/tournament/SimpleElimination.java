package tournament;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tournament.exceptions.MatchDrawException;
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
	public SimpleElimination(Date date, Game game) {
		this(date, game, "");
	}
	
	/**
	 * Create a SimpleElimination tournament.
	 * @param location The location of the tournament.
	 */
	public SimpleElimination(Date date, Game game, String location) {
		super(date, game, location);
	}
	
	
	@Override
	public void initializeMatchs() throws NotEnoughParticipantsException {
		assert participants != null;

		int nbParts = participants.size();
		
		// the number of participants need to be a power of 2
		if (!((nbParts > 2) && ((nbParts & (nbParts - 1)) == 0 ))) {
			throw new NotEnoughParticipantsException();
		}
		
		matchs = new Match[nbParts];
		List<Participant> parts = new ArrayList<>(participants);
		
		for (int i = nbParts/2; i < nbParts; i++) {
			matchs[i] = new Match(this, parts.get((i*2)-nbParts), parts.get(((i*2)-nbParts)+1));
		}
	}
	
	@Override
	public void updateMatchs(Match m) throws MatchDrawException {
		if (m.isDraw()) {
			throw new MatchDrawException();
		}
		
		int nbParts = participants.size();
		Participant defaultPart = new Team("?");
		
		if (participants.iterator().next() instanceof Player) {
			defaultPart = new Player("?");
		}
		
		for (int i = 2; i < nbParts; i++) {
			if (matchs[i] == m) { // match updated
				if (matchs[i / 2] == null) { // default match
					matchs[i/2] = new Match(this, defaultPart, defaultPart);
				}
				
				if (i%2 == 0) {
					matchs[i / 2].setParticipant1(matchs[i].getWinner());
				} else {
					matchs[i / 2].setParticipant2(matchs[i].getWinner());
				}
			}
		}
		
		if (matchs[1] != null) {
			setWinner(matchs[1].getWinner());
		}
	}
}
