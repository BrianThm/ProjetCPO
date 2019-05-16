package tournament;


/**
 * @author 
 * @version 1.0 
 * Class which defines the tournament type Championship. 
 * In this case of tournament, every participant are facing 
 * two times and we build the ranking according to the scores obtained.
 */
public class Championship extends Tournament {
	
	
	
	/**
	 * Championship constructor.
	 * @param adresse Tournament address.
	 */
	public Championship(String location) {
		super(location);
	}
	
	public void addParticipant(Participant p) {
		super.addParticipant(p);
		Ranking r = new Ranking(p);
	}
	
}
