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
	private Player defaultPlayer = new Player("?");
	
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
			matchs[i] = this.winnersBracket[i];
		}
		for (int i=0; i<this.loosersBracket.length; i++) {
			matchs[i+this.winnersBracket.length] = this.loosersBracket[i];
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
		int nbParts = participants.size();
		for (int i=2; i<nbParts; i++) {
			if (this.winnersBracket[i] != null
						&& this.winnersBracket[i].getWinner() != null) {
				if (this.winnersBracket[i/2] == null) {
					this.winnersBracket[i/2] = new Match(defaultPlayer, defaultPlayer, this.getGame());
				}				
				if (i%2 == 0) {
					this.winnersBracket[i/2].setParticipant1(this.winnersBracket[i].getWinner());
				} else {
					this.winnersBracket[i/2].setParticipant2(this.winnersBracket[i].getWinner());
				}
				
				// Initialization of the firsts looser's bracket matchs 
				if (i >= (nbParts/2)) {
					int index = (i/2)-(nbParts/4);
					if (this.loosersBracket[index] == null) {
						this.loosersBracket[index] = new Match(defaultPlayer, defaultPlayer, this.getGame());
					}
					if (index%2 == 0) {
						this.loosersBracket[index].setParticipant1(this.winnersBracket[i].getLooser());
					} else {
						this.loosersBracket[index].setParticipant2(this.winnersBracket[i].getLooser());
					}
				}
			}
		}
	}
	
	/**
	 * Update the looser's bracket of this tournament
	 * at the end of each match.
	 */
	public void updateLooserBracket() {
		int nbParts = participants.size();
		// 2nd turn of the looser's bracket
		for (int i=(nbParts/4); i<(nbParts/2); i++) {
			if (this.winnersBracket[i] != null
					&& this.winnersBracket[i].getWinner() != null) {
				if (this.loosersBracket[i] == null) {
					this.loosersBracket[i] = new Match(defaultPlayer, this.winnersBracket[i].getLooser(), 
													this.getGame());
				}
				if (this.loosersBracket[i-(nbParts/2)] != null
						&& this.loosersBracket[i-(nbParts/2)].getWinner() != null) {
					this.loosersBracket[i].setParticipant1(this.loosersBracket[i-(nbParts/2)].getWinner());
				}
			}
		}
		// 3rd turn
		int j=0;
		for (int i=(nbParts/2); i<(i+(nbParts/8)); i++) {
			if (this.loosersBracket[i] == null) {
				this.loosersBracket[i] = new Match(defaultPlayer, defaultPlayer, this.getGame());
			}
			if (this.loosersBracket[(i/2)+j] != null
					&& this.loosersBracket[(i/2)+j].getWinner() != null) {
				this.loosersBracket[i].setParticipant1(this.loosersBracket[(i/2)+j].getWinner());
			}
			j++;
			if (this.loosersBracket[(i/2)+j] != null
					&& this.loosersBracket[(i/2)+j].getWinner() != null) {
				this.loosersBracket[i].setParticipant2(this.loosersBracket[(i/2)+j].getWinner());
			}
			j++;
		}
	}
	
}
