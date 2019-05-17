package tournament;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 
 * @version 1.0 
 * Class which defines the tournament type Championship. 
 * In this case of tournament, every participant are facing 
 * two times and we build the ranking according to the scores obtained.
 */
public class Championship extends Tournament {
	
	private Map<Participant, Integer> ranking;
	
	/**
	 * Championship constructor.
	 * @param adresse Tournament address.
	 */
	public Championship(String location) {
		super(location);
		this.ranking = new HashMap<Participant, Integer>();
	}
	
	public void addParticipant(Participant p) {
		super.addParticipant(p);
	}
}
