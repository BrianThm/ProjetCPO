package tournament;

import tournament.exceptions.MatchDrawException;

/**
 * Class Match. A match is played by two Participant. 
 * It can have a winner, or be a draw.
 * @author Group
 * @version 1.0
 */
public class Match {
	
	private Tournament tournament;
	private Participant part1, part2;
	private int[] score = new int[2];
	
	/**
	 * Constructor of a Match with two participants and a game.
	 * @param p1 The first participant, a team or a player.
	 * @param p2 The second participant.
	 * @param game The match game.
	 */
	public Match(Tournament tournament, Participant p1, Participant p2) {
		assert tournament != null;
		assert p1 != null;
		assert p2 != null;
		
		this.tournament = tournament;
		this.part1 = p1;
		this.part2 = p2;
		score[0] = -1;
		score[1] = -1;
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
		assert part != part2;
		assert part.getClass().isInstance(part2);
		
		this.part1 = part;
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
		assert part != part1;
		assert part.getClass().isInstance(part1);
		
		this.part2 = part;
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
	 * @throws MatchDrawException 
	 */
	public void setScore(int scorePart1, int scorePart2) throws MatchDrawException {
		assert scorePart1 >= 0;
		assert scorePart2 >= 0;
		
		if (!isPlayed()) {
			gamePlayed();
		}
		
		score[0] = scorePart1;
		score[1] = scorePart2;
		
		try {
			tournament.updateMatchs(this);
		} catch (MatchDrawException e) {
			score[0] = -1;
			score[1] = -1;
			throw e;
		}
	}
	
	/**
	 * Tells if the match has been played or not.
	 * @return True if the match has been played, false if not.
	 */
	public boolean isPlayed() {
		return getWinner() != null || isDraw();
	}
	
	/**
	 * Method to indicate that the match cas played
	 */
	private void gamePlayed() {
		this.part1.plays(tournament.getGame());
		this.part2.plays(tournament.getGame());
	}
	
	/**
	 * Get the winner of the match if there is a winner. 
	 * @return The winner or null if the match isn't finished or if 
	 * there is no winner.
	 */
	public Participant getWinner() {
		if (score[0] > score[1]) {
			return part1;
		} else if (score[1] > score[0]) {
			return part2;
		}

		return null;
	}
	
	public Participant getLooser() {
		if (score[0] > score[1]) {
			return part2;
		} else if (score[1] > score[0]) {
			return part1;
		}
		
		return null;
	}
	
	/**
	 * Tell if a match ended with a draw or not.
	 * @return True if the match ended with a draw, false if not.
	 */
	public boolean isDraw() {
		if (score[0] == -1 || score[1] == -1)
			return false;
		
		return score[0] == score[1];
	}
	
	public String toString() {
		if (score[0] == -1 || score[1] == -1) {
			return part1.getName() + "\n" + part2.getName();
		}
		return part1.getName() + ": "+ score[0]+ "\n" + part2.getName()+": "+ score[1]+ "\n" ;
	}
}
