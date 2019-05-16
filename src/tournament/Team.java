package tournament;
import java.util.ArrayList;

/**
 * @author Groupe
 * @version 1.0 
 * A team is composed of players.
 * A team can participate to a tournament.
 */
public class Team extends Participant {

	private ArrayList<Player> members;
	
	/**
	 * Create a new team with a name.
	 * @param name The name of the team.
	 */
	public Team(String name) {
		super(name);
		members = new ArrayList<Player>();
	}
}
