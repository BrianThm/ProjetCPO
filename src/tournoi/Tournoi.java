package tournoi;
import java.util.ArrayList;

import participant.Participant;

/**
 * @author Groupe
 * @version 1.0
 * 
 * Classe impl�mentant le terme g�n�rale de tournoi. 
 * Elle sera donc sp�cifi� par les diff�rents types de tournoi. 
 *
 */
public abstract class Tournoi {
	
	/**
	 * Liste des participants du tournoi
	 */
	protected ArrayList<Participant> listeParticipants;
	
	/**
	 * Addresse du tournoi
	 */
	private String adresse;
	
	/**
	 * Constructeur de la classe qui initialise le tournoi
	 * @param lieu Adresse du tournoi
	 */
	public Tournoi(String lieu) {
		assert (lieu != null && lieu.length() > 0) : 
			"Le tournoi doit avoir une adresse !";
		listeParticipants = new ArrayList<Participant>();
		adresse = lieu;
	}
	
	public void ajoutParticipant(Participant p) {
		assert (p != null) :
			"Le participant a ajout� doit �tre initialis� !";
		listeParticipants.add(p);
	}
	
}
