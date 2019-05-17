package tournament;

import java.util.HashMap;
import java.util.Map;

/**
 * Class which defines the tournament type Championship. 
 * In this case of tournament, every participant are facing 
 * two times and we build the ranking according to the scores obtained.
 * @author Group
 * @version 1.0
 */
public class Championship extends Tournament {
	
	private Map<Participant, Integer> ranking;
	
	/**
	 * Championship constructor.
	 * @param game The game of the tournament.
	 * @param location The tournament location.
	 */
	public Championship(Game game, String location) {
		super(game, location);
		this.ranking = new HashMap<Participant, Integer>();
	}
}
