package tournament;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Group
 * @version 1.0
 * Class which defines a tournament participant. 
 * A participant is either a Team or a Player.
 */
public abstract class Participant {
	
	private String name;
	private Map<Game, Integer> games;
	private Set<Tournament> tournaments;
	
	/**
	 * Create a participant with a name.
	 * @param name The participant name.
	 */
	public Participant(String name) {
		assert name != null;
		
		this.name = name;
		this.games = new HashMap<Game, Integer>();
		this.tournaments = new HashSet<Tournament>();
	}
	
	/**
	 * Create a participant with a name and a base game.
	 * @param name The name of the participant.
	 * @param game The base game of the participant.
	 */
	public Participant(String name, Game game) {
		this(name);
		assert game != null;
		
		this.games.put(game, 0);
	}

	/**
	 * Get the name of the participant.
	 * @return The name of the participant.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set the name of the participant.
	 * @param name The name of the participant.
	 */
	public void setName(String name) {
		assert name != null;
		
		this.name = name;
	}
	
	/**
	 * Update the number of times a participant has played a game. 
	 * @param game The game played by the participant.
	 */
	public void plays(Game game) {
		assert game != null;
		
		Integer timesPlayed = this.games.get(game);
		if (timesPlayed == null) { // never played this game before
			this.games.put(game, 1);
		} else {
			this.games.put(game, timesPlayed + 1);
		}
	}
	
	// TODO javadoc
	public void removeGame(Game game) {
		assert game != null;
		
		this.games.remove(game);
	}
	
	/**
	 * Get the most played game of a participant. 
	 * If the participant was created with a base game, 
	 * this will return it even if it has never been played.
	 * @return The preferred game, or null if there isn't any.
	 */
	public Game getPreferredGame() {
		Game game = null;
		int timesPlayed = 0;
		
		for (Game g : this.games.keySet()) {
			int times = this.games.get(g);
			if (times >= timesPlayed) {
				game = g;
				timesPlayed = times;
			}
		}
		
		return game;
	}
	
	/**
	 * Add a tournaments played by this participant.
	 * @param tournament The tournament played.
	 */
	public void addTournament(Tournament tournament) {
		assert tournament != null;
		
		this.tournaments.add(tournament);
	}
	
	/**
	 * Remove a tournament played by this participant.
	 * @param tournament The tournament to remove.
	 */
	public void removeTournament(Tournament tournament) {
		assert tournament != null;
		
		this.tournaments.remove(tournament);
	}
	
	/**
	 * Get the tournaments played by this participant.
	 * @return A set of tournamets played.
	 */
	public Set<Tournament> getTournaments() {
		return this.tournaments;
	}
}
