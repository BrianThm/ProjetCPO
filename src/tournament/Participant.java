package tournament;

/**
 * @author 
 * @version 1.0
 * 
 * Classe d�finissant la notion de participant
 * Un participant sera sp�cifi� par Equipe ou Joueur
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
	 * utilis� uniquement dans les sous-classes
	 * @param name le nom du participant
	 */
	protected Participant(String name) {
		assert (name != null) : 
			"Le nom doit �tre d�fini !";

		this.Nom = name;
	}

}
