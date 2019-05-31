package tournament;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tournament.exceptions.NotEnoughParticipantsException;

/**
 * Class Tournament, will be specified by each type of tournament.
 * @author Group
 * @version 1.0
 */
public abstract class Tournament {
	
	private Game game;
	private String location;
	protected List<Participant> participants;
	protected Match[] matchs;
	
	/**
	 * Constructor of an empty tournament, composed of a game.
	 */
	public Tournament(Game game) {
		this(game, "");
	}
	
	/**
	 * Constructor of a tournament with a location and a game.
	 * @param game The game of the tournament.
	 * @param location The location of the tournament.
	 */
	public Tournament(Game game, String location) {
		assert game != null;
		assert location != null;
		
		this.game = game;
		this.location = location;
		participants = new ArrayList<Participant>();
	}
	
	/**
	 * Get the game of the tournament.
	 * @return The game of the tournament.
	 */
	public Game getGame() {
		return this.game;
	}
	
	/**
	 * Change the game of the tournament.
	 * @param game The new game of the tournament.
	 */
	public void setGame(Game game) {
		assert game != null;
		
		this.game = game;
	}
	
	/**
	 * Get the location of the tournament.
	 * @return The location of the tournament.
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * Set the location of the tournament.
	 * @param location The new location.
	 */
	public void setLocation(String location) {
		assert location != null;
		
		this.location = location;
	}
	
	/**
	 * Add a participant to a tournament.
	 * @param participant The new participant.
	 */
	public void addParticipant(Participant participant) {
		assert participant != null;
		
		participants.add(participant);
		participant.addTournament(this);
	}
	
	/**
	 * Remove a participant to a tournament.
	 * @param participant The participant to remove.
	 */
	public void removeParticipant(Participant participant) {
		assert participant != null;
		
		participants.remove(participant);
		participant.removeTournament(this);
	}
	
	/**
	 * Get the participants of this tournament.
	 * @return A set of the participants.
	 */
	public List<Participant> getParticipants() {
		return participants;
	}
	
	/**
	 * Get the matchs of this tournament.
	 * @return A tree of matchs.
	 */
	public final Match[] getMatchs() {
		return this.matchs;
	}
	
	/**
	 * Initialize the SimpleElimination tournament tree.
	 * @param partipants The parcipants of the tournament.
	 * @throws NotEnoughParticipantsException If there is not enough participants to start the tournament.
	 */
	public abstract void initializeMatchs() throws NotEnoughParticipantsException;
}
