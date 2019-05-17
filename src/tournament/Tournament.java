package tournament;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Group
 * @version 1.0
 * Class Tournament, will be specified by each type of tournament.
 */
public abstract class Tournament {
	
	private Game game;
	private String location;
	private Set<Participant> participants;
	
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
		participants = new HashSet<Participant>();
	}
	
	// TODO javadoc
	public Game getGame() {
		return this.game;
	}
	
	// TODO javadoc
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
	 * @param p The new participant.
	 */
	public void addParticipant(Participant p) {
		assert p != null;
		
		participants.add(p);
		p.addTournament(this);
	}
	
	/**
	 * Remove a participant to a tournament.
	 * @param p The participant to remove.
	 */
	public void removeParticipant(Participant p) {
		assert p != null;
		
		participants.remove(p);
		p.removeTournament(this);
	}
	
	public Set<Participant> getParticipants() {
		return participants;
	}
}
