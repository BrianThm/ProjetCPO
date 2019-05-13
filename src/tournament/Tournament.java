package tournament;
import java.util.ArrayList;

/**
 * @author Group
 * @version 1.0
 * Class Tournament, will be specified by each type of tournament.
 */
public abstract class Tournament {
	
	/**
	 * List of the participants of a tournament.
	 */
	protected ArrayList<Participant> participants;
	
	/**
	 * Location of the tournament
	 */
	private String location;
	
	/**
	 * Constructor of an empty tournament.
	 */
	public Tournament() {
		this("");
	}
	
	/**
	 * Constructor of a tournament with a location.
	 * @param location The location of the tournament.
	 */
	public Tournament(String location) {
		participants = new ArrayList<Participant>();
		this.location = location;
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
		this.location = location;
	}
	
	/**
	 * Add a participant to a tournament.
	 * @param p The new participant.
	 */
	public void addParticipant(Participant p) {
		assert p != null;
		
		participants.add(p);
	}
	
}
