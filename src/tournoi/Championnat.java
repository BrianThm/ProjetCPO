package tournoi;
import java.util.ArrayList;

import participant.Participant;


/**
 * @author 
 * @version 1.0 
 *
 * Classe qui spécifie le type de tournoi Championnat
 * Dans ce type de tournoi tous les participants s'affrontent
 * deux fois et l'on construis le classement selon un nombre 
 * de points obtenus
 *
 */
public class Championnat extends Tournoi {
	
	/**
	 * Classement du tournoi
	 */
	private ArrayList<Classement> classement;
	
	/**
	 * Constructeur de la classe 
	 * @param adresse adresse du tournoi
	 */
	public Championnat(String adresse) {
		super(adresse);
		classement = new ArrayList<Classement>();
	}
	
	public void ajoutParticipant(Participant p) {
		super.ajoutParticipant(p);
		Classement c = new Classement(p);
		classement.add(c);
	}
	
}
