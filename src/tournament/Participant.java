package tournament;

/**
 * @author 
 * @version 1.0
 * Class which defines a tournament participant. 
 * A participant is either a Team or a Player.
 */
public abstract class Participant {
	
	private String name; 
	
	/**
	 * Participant constructor. 
	 * Used only in the inherited classes.
	 * @param name The participant name.
	 */
	public Participant(String name) {
		assert (name != null);
		this.name = name;
	}

}
