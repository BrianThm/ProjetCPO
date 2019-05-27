package tournament;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
	 * Create a championship with a game.
	 * @param game The game played in the championship.
	 */
	public Championship(Game game) {
		this(game, "");
	}
	
	/**
	 * Championship constructor.
	 * @param game The game of the tournament.
	 * @param location The tournament location.
	 */
	public Championship(Game game, String location) {
		super(game, location);
		this.ranking = new HashMap<Participant, Integer>();
	}
	
	@Override
	/**
	 * When a participant is added, his score is initialized at 0.
	 */
	public void addParticipant(Participant participant) {
		super.addParticipant(participant);
		this.ranking.put(participant, 0);
	}
	
	@Override
	/**
	 * When a participant is removed, his score is removed.
	 */
	public void removeParticipant(Participant participant) {
		super.removeParticipant(participant);
		this.ranking.remove(participant);
	}

	@Override
	public void initializeMatchs(Set<Participant> participants) {
		// TODO Auto-generated method stub
		
	}
}
