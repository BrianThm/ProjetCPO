package tournament;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Group
 * Class Player. 
 * A player has one or several team(s) and game(s).
 */
public class Player extends Participant {
	
	private String fname, lname;
	private Map<Team, Integer> teams;
	
	/**
	 * Constructor of a player. 
	 * A player has a nickname and a list of teams he belongs to.
	 * @param nickname The nickname of the player.
	 */
	public Player(String nickname) {		
		this("", "", nickname);
	}
	
	/**
	 * Constructor of a player. 
	 * A player has a nickname, a default game and a list of teams he belongs to.
	 * @param nickname The nickname of the player.
	 * @param game The default/preferred game of the player.
	 */
	public Player(String nickname, Game game) {
		super(nickname, game);
	}
	
	/**
	 * Constructor of a player. 
	 * A player has a forename, a lastname, a nickname and a 
	 * list of teams he belongs to.
	 * @param fname The forename of the player.
	 * @param lname The lastname of the player.
	 * @param nickname The nickname of the player.
	 */
	public Player(String fname, String lname, String nickname) {
		super(nickname);
		
		assert fname != null;
		assert lname != null;
		
		this.fname = fname;
		this.lname = lname;
		teams = new HashMap<Team, Integer>();
	}
	
	/**
	 * Constructor of a player. 
	 * A player has a forename, a lastname, a nickname and a 
	 * default/preferred game and a list of teams he belongs to.
	 * @param fname The forename of the player.
	 * @param lname The lastname of the player.
	 * @param nickname The nickname of the player.
	 * @param game The default/preferred game of the player.
	 */
	public Player(String fname, String lname, String nickname, Game game) {
		this(nickname, game);
		
		assert fname != null;
		assert lname != null;
		
		this.fname = fname;
		this.lname = lname;
		teams = new HashMap<Team, Integer>();
	}
	
	
	/**
	 * Get the forename of the player.
	 * @return The forename of the player.
	 */
	public String getFName() {
		return this.fname;
	}
	
	/**
	 * Set the forename of the player.
	 * @param fname The forename to set.
	 */
	public void setFName(String fname) {
		assert fname != null;
		
		this.fname = fname;
	}
	
	/**
	 * Get the lastname of the player.
	 * @return The lastname of the player.
	 */
	public String getLName() {
		return this.lname;
	}
	
	/**
	 * Set the lastname of the player.
	 * @param lname The lastname to set.
	 */
	public void setLName(String lname) {
		assert lname != null;
		
		this.lname = lname;
	}
	
	/**
	 * Add a team in the list of the player teams or 
	 * increments the number of times the player played with it.
	 * @param team The team the player played with.
	 */
	public void playsIn(Team team) {
		assert team != null;
		
		Integer nbGame = this.teams.get(team);
		
		if (nbGame == null) {
			this.teams.put(team, 1);
		} else {
			this.teams.put(team, nbGame + 1);
		}
	}
	
	/**
	 * Get the team that the player most played with.
	 * @return The preferred team.
	 */
	public Team getPreferredTeam() {
		Team team = null;
		int nbGames = 0;
		
		for (Team t : teams.keySet()) {
			int nb = teams.get(t);
			if (nb > nbGames) {
				team = t;
				nbGames = nb;
			}
		}
		
		return team;
	}
}
