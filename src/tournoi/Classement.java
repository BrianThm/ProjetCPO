package tournoi;
import participant.Participant;

public class Classement {
	private Participant p; 
	private int points;
	
	public Classement(Participant part) {
		this.p = part;
		this.points = 0;
	}
}
