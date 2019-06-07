package tournament;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tournament.exceptions.NotEnoughParticipantsException;

@SuppressWarnings("deprecation")
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
	public DoubleElimination(Date date, Game game) {
		super(date, game);
	}
	
	/**
	 * Create a DoubleElimination tournament.
	 * @param location The location of the tournament.
	 */
	public DoubleElimination(Date date, Game game, String location) {
		super(date, game, location);
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
		// the number of participants need to be a power of 2
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
			this.winnersBracket[i].addObserver(this);
			this.loosersBracket[i] = null;
		}
		
		super.setMatchs(mergeBrackets());
	}

	@Override
	public void updateMatchs() {
		updateWinnersBracket();
		updateLooserBracket();
		super.setMatchs(mergeBrackets());
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
					this.winnersBracket[i/2].addObserver(this);
				}				
				if (i%2 == 0) {
					this.winnersBracket[i/2].setParticipant1(this.winnersBracket[i].getWinner());
				} else {
					this.winnersBracket[i/2].setParticipant2(this.winnersBracket[i].getWinner());
				}
				
				// Initialization of the firsts looser's bracket matchs 
				// (loosers vs loosers)
				if (i >= (nbParts/2)) {
					int index = (i/2)-(nbParts/4);
					if (this.loosersBracket[index] == null) {
						this.loosersBracket[index] = new Match(defaultPlayer, defaultPlayer, this.getGame());
						this.loosersBracket[index].addObserver(this);
					}
					if (i%2 == 0) {
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
		// winners of looser's brackets (1st turn) 
		// 		vs loosers of winner's bracket (2nd turn) 
		for (int i=(nbParts/4); i<(nbParts/2); i++) {
			if (this.loosersBracket[i] == null) {
				this.loosersBracket[i] = new Match(defaultPlayer, defaultPlayer, this.getGame());
				this.loosersBracket[i].addObserver(this);
			}
			if (this.loosersBracket[i-(nbParts/4)] != null
					&& this.loosersBracket[i-(nbParts/4)].getWinner() != null) {
				this.loosersBracket[i].setParticipant1(this.loosersBracket[i-(nbParts/4)].getWinner());
			}
			if (this.winnersBracket[i] != null
					&& this.winnersBracket[i].getWinner() != null) {
				this.loosersBracket[i].setParticipant2(this.winnersBracket[i].getLooser());
			}
	}
		// 3rd turn
		// between winners of looser's bracket (2nd turn)
		int j=0;
		int start=(nbParts/2);
		for (int i=start; i<(start+(nbParts/8)); i++) {
			if (this.loosersBracket[i] == null) {
				this.loosersBracket[i] = new Match(defaultPlayer, defaultPlayer, this.getGame());
				this.loosersBracket[i].addObserver(this);
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
		// 4th turn
		// winners of looser's bracket (2nd turn)
		//		vs loosers of winner's bracket (3rd turn)
		start = (nbParts/2)+(nbParts/8);
		for (int i=start; i<(start+(nbParts/8)); i++) {
			if (this.loosersBracket[i] == null) {
				this.loosersBracket[i] = new Match(defaultPlayer, defaultPlayer, this.getGame());
				this.loosersBracket[i].addObserver(this);
			}
			if (this.loosersBracket[i-(nbParts/8)] != null
					&& this.loosersBracket[i-(nbParts/8)].getWinner() != null) {
				this.loosersBracket[i].setParticipant1(this.loosersBracket[i-(nbParts/2)].getWinner());
			}
			if (this.winnersBracket[i-(nbParts/2)] != null
					&& this.winnersBracket[i-(nbParts/2)].getWinner() != null) {
				this.loosersBracket[i].setParticipant2(this.winnersBracket[i-(nbParts/2)].getLooser());		
			}
		}
		// 5th turn
		// IN PROGRESS
	}
	
}
