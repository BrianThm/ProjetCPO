package tournament;

import java.util.ArrayList;
import java.util.List;

import tournament.exceptions.NotEnoughParticipantsException;

/**
 * A type of tournament. 
 * In the double elimination tournament, a participant can be 
 * eliminated only if he have loose two matchs during the tounament.
 * In this scenario, there are Winner's and Looser's Brackets.
 * @author Group
 * @version 1.0
 */
public class DoubleElimination extends Tournament {

	private Match[] winnersBracket;
	private Match[] loosersBracket;	
	
	/**
	 * Create an empty DoubleElimination tournament.
	 */
	public DoubleElimination(Game game) {
		super(game);
	}
	
	/**
	 * Create a DoubleElimination tournament.
	 * @param location The location of the tournament.
	 */
	public DoubleElimination(Game game, String location) {
		super(game, location);
	}
	
	/**
	 * Get the matchs of the winner's bracket.
	 * @return The Winner's Bracket.
	 */
	public Match[] getWinnersBracket() {
		return this.winnersBracket;
	}
	
	/**
	 * Get the matchs of the looser's bracket.
	 * @return The Looser's Bracket.
	 */
	public Match[] getLossersBracket() {
		return this.loosersBracket;
	}
	
	/**
	 * Concatenate the winner's and looser's brackets
	 * to set up the matchs list of the tournament.
	 */
	public Match[] mergeBrackets() {
		Match[] matchs = new Match[this.winnersBracket.length 
		                         + this.loosersBracket.length];
		
		for (int i=0; i<this.winnersBracket.length; i++) {
			super.matchs[i] = this.winnersBracket[i];
		}
		for (int i=0; i<this.loosersBracket.length; i++) {
			super.matchs[i+this.winnersBracket.length] = this.loosersBracket[i];
		}		
		return matchs;
	}
	
	
	@Override
	public void initializeMatchs() throws NotEnoughParticipantsException {
		assert participants != null;
		
		int nbParts = participants.size();
		if (!((nbParts > 2) && ((nbParts & (nbParts - 1)) == 0 ))) {
			throw new NotEnoughParticipantsException();
		}
		
		this.winnersBracket = new Match[nbParts];
		this.loosersBracket = new Match[nbParts];
		List<Participant> parts = new ArrayList<>(participants);
		
		for (int i=1; i<(nbParts/2); i++) {
			this.winnersBracket[i] = null;
			this.loosersBracket[i] = null;
		}
		for (int i=(nbParts/2); i<(nbParts); i++) {
			this.winnersBracket[i] = new Match(parts.get((i*2)-nbParts), 
					parts.get(((i*2)-nbParts)+1), this.getGame());
			this.loosersBracket[i] = null;
		}
		
		super.matchs = mergeBrackets();
	}

	@Override
	public void updateMatchs() {
		updateWinnersBracket();
		updateLooserBracket();
		mergeBrackets();
	}

	/**
	 * Update the winner's bracket of this tournament
	 * at the end of each match.
	 */
	public void updateWinnersBracket() {
		//TODO
	}
	
	/**
	 * Update the looser's bracket of this tournament
	 * at the end of each match.
	 */
	public void updateLooserBracket() {
		//TODO
	}
	
}
