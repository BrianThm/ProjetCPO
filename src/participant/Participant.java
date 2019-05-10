package participant;

/**
 * @author 
 * @version 1.0
 * 
 * Classe définissant la notion de participant
 * Un participant sera spécifié par Equipe ou Joueur
 *
 */
public abstract class Participant {
	
	/**
	 * Le nom du participant 
	 * Pour le joueur (nom de famille ?)
	 */
	protected String Nom; 
	
	
	/**
	 * Constructeur de la classe,
	 * utilisé uniquement dans les sous-classes
	 * @param name le nom du participant
	 */
	protected Participant(String name) {
		assert (name != null) : 
			"Le nom doit être défini !";

		this.Nom = name;
	}

}
