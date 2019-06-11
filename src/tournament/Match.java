package tournament;

import java.util.Observable;

/**
 * Class Match. A match is played by two Participant. 
 * It can have a winner, or be a draw.
 * @author Group
 * @version 1.0
 */
public class Match extends Observable {
	
	private Participant part1, part2, winner;
	private int[] score; 
	private Game game;
	private boolean draw;
	
	/**
	 * Constructor of a Match with two participants and a game.
	 * @param p1 The first participant, a team or a player.
	 * @param p2 The second participant.
	 * @param game The match game.
	 */
	public Match(Participant p1, Participant p2, Game game) {
		assert p1 != null;
		assert p2 != null;
		assert game != null;
		
		this.score = new int[2];
		this.part1 = p1;
		this.part2 = p2;
		this.game = game;
		this.winner = null;
		this.draw = false;
	}
	
	/**
	 * Get the first participant.
	 * @return The first participant.
	 */
	public Participant getParticipant1() {
		return this.part1;
	}
	
	/**
	 * Set the first participant.
	 * @param part The first participant.
	 */
	public void setParticipant1(Participant part) {
		assert part != null;
		if (part.getClass().isInstance(part2)) {
			if (part != part2) {
				this.part1 = part;
			}
		}
	}
	
	/**
	 * Get the second participant.
	 * @return The second participant.
	 */
	public Participant getParticipant2() {
		return this.part2;
	}
	
	/**
	 * Set the second participant.
	 * @param part The second participant.
	 */
	public void setParticipant2(Participant part) {
		assert part != null;
		if (part.getClass().isInstance(part1)) {
			if (part != part1) {
				this.part2 = part;
			}
		}
	}
	
	/**
	 * get the score of the match
	 * @return
	 */
	public int[] getScore() {
		return score; 
	}
	
	/**
	 * Set the score of the match and initialize the winner
	 * @param scorePart1 the score of the participant 1
	 * @param scorePart2 the score of the participant 2
	 */
	public void setScore(int scorePart1, int scorePart2) {
		assert scorePart1 >= 0; 
		assert scorePart2 >= 0; 
		
		score[0] = scorePart1; 
		score[1] = scorePart2;
		
		boolean alreadyPlayed = false; 
		
		if (!isPlayed()) {
			alreadyPlayed = true;
		}
		
		if (score[0] > score[1]) {
			this.winner = part1;
			this.draw = false;  
		} else { 
			if (score[1] > score[0]) {
				this.winner = part2;
				this.draw = false;  
			} else {
				this.winner = null;
				this.draw = true;
			}
 		}
		
		this.setChanged();
		this.notifyObservers();
		
		if (!alreadyPlayed) {
			endGame();
		}
		
		
	}
	
	/**
	 * Tells if the match has been played or not.
	 * @return True if the match has been played, false if not.
	 */
	public boolean isPlayed() {
		return winner != null || draw;
	}
	
	/**
	 * Method to indicate that the match ended with a draw.
	 */
	public void endGame() {
		if (isPlayed()) {
			this.part1.plays(game);
			this.part2.plays(game);
		}
	}
	
	/**
	 * Get the winner of the match if there is a winner. 
	 * @return The winner or null if the match isn't finished or if 
	 * there is no winner.
	 */
	public Participant getWinner() {
		return this.winner;
	}
	
	public Participant getLooser() {
		Participant looser = null;
		if (winner != null) {
			if (this.winner.equals(this.part1)) {
				looser = this.part2;
			} else {
				looser = this.part1;
			}
		}
		return looser;
	}
	
	/**
	 * Tell if a match ended with a draw or not.
	 * @return True if the match ended with a draw, false if not.
	 */
	public boolean isDraw() {
		return this.draw;
	}
	
	public String toString() {
		return "" + part1.getName() + ": "+ score[0]+ "\n" + part2.getName()+": "+ score[1]+ "\n" ; 
	}
	
}
