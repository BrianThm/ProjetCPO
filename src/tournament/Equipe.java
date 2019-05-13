package tournament;
/**
 * 
 */
import java.util.ArrayList;
/**
 * @author Groupe
 * @version 1.0 
 * 
 * Impl�mentant la notion d'equipe. 
 * L'equipe peut participer � un tournoi,
 * C'est donc un participant au tournoi!
 *
 */
public class Equipe extends Participant {

	private ArrayList<Joueur> membres;
	/**
	 * Constructeur de la classe
	 * @param name Nom de l'�quipe
	 */
	public Equipe(String name) {
		super(name);
		membres = new ArrayList<Joueur>();
	}

}
