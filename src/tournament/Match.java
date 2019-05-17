package tournament;

/**
 * Class Match. A match is played by two Participant. 
 * It can have a winner, or be a draw.
 * @author Group
 * @version 1.0
 */
public class Match {
	
	private Participant part1, part2, winner;
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
		
		this.part2 = part;
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
		this.draw = true;
	}
	
	/**
	 * Method to indicate the end of the match with a winner.
	 * @param winner The winner of the match.
	 */
	public void endGame(Participant winner) {
		assert winner != null;
		
		if (isPlayed()) {
			this.part1.plays(game);
			this.part2.plays(game);
		}
		this.winner = winner;
	}
	
	/**
	 * Get the winner of the match if there is a winner. 
	 * @return The winner or null if the match isn't finished or if 
	 * there is no winner.
	 */
	public Participant getWinner() {
		return this.winner;
	}
	
	/**
	 * Tell if a match ended with a draw or not.
	 * @return True if the match ended with a draw, false if not.
	 */
	public boolean isDraw() {
		return this.draw;
	}
}
