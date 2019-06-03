package tournament;

import java.util.ArrayList;
import java.util.List;

import tournament.exceptions.NotEnoughParticipantsException;

@SuppressWarnings("deprecation")
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
			super.matchs[i] = new Match(parts.get((i*2)-nbParts), 
					parts.get(((i*2)-nbParts)+1), this.getGame());
			super.matchs[i].addObserver(this);
		}
	}
	
	@Override
	public void updateMatchs() {
		int nbParts = participants.size();
		Player defaultPlayer = new Player("?");
		
		for (int i=2; i<nbParts; i++) {
			if ((super.matchs[i] != null) 
					&& (super.matchs[i].getWinner() != null)) {
				if (super.matchs[i/2] == null) {
					super.matchs[i/2] = new Match(defaultPlayer, defaultPlayer, this.getGame());
					super.matchs[i/2].addObserver(this);
				}
				if (i%2 == 0) {
					super.matchs[i/2].setParticipant1(super.matchs[i].getWinner());
				} else {
					super.matchs[i/2].setParticipant2(super.matchs[i].getWinner());
				}
			}
		}
		if (super.matchs[1] != null 
				&& super.matchs[1].getWinner() != null) {
			super.setWinner(super.matchs[1].getWinner());
		}
	}
	
	
}
