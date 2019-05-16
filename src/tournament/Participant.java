package tournament;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Group
 * @version 1.0
 * Class which defines a tournament participant. 
 * A participant is either a Team or a Player.
 */
public abstract class Participant {
	
	private String name;
	private Map<Game, Integer> games;
	
	/**
	 * Create a participant with a name.
	 * @param name The participant name.
	 */
	public Participant(String name) {
		assert (name != null);
		this.name = name;
		this.games = new HashMap<Game, Integer>();
	}
	
	/**
	 * Create a participant with a name and a base game.
	 * @param name The name of the participant.
	 * @param game The base game of the participant.
	 */
	public Participant(String name, Game game) {
		this(name);
		games.put(game, 0);
	}

	/**
	 * Get the name of the participant.
	 * @return The name of the participant.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the name of the participant.
	 * @param name The name of the participant.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Update the number of times a participant has played a game. 
	 * @param game The game played by the participant.
	 */
	public void plays(Game game) {
		Integer timesPlayed = games.get(game);
		if (timesPlayed == null) { // never played this game before
			games.put(game, 1);
		} else {
			games.put(game, timesPlayed + 1);
		}
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
		
		for (Game g : games.keySet()) {
			int times = games.get(g);
			if (times >= timesPlayed) {
				game = g;
				timesPlayed = times;
			}
		}
		
		return game;
	}
}
