package tournament;
import javax.swing.ImageIcon;

/**
 * @author Groupe
 * @version 1.0
 *
 * Les jeux disponible dans notre application de gestion
 * 
 */
public class Game {
	
	private String name;
	private ImageIcon image;
	
	/**
	 * Creates a new game without image.
	 * @param name The name of the game.
	 */
	public Game(String name) {
		this(name, null);
	}
	
	/**
	 * Creates a new game with an image.
	 * @param name The name of the game.
	 * @param image The image associated to the game.
	 */
	public Game(String name, ImageIcon image) {
		this.name = name;
		this.image = image;
	}
	
	/**
	 * Get the name of the game.
	 * @return The name of the game.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set the name of the game.
	 * @param name The new name of the game.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get the image associated to the game. 
	 * If there isn't an image for the game, throw AssertionError.
	 * @return The image of the game. 
	 */
	public ImageIcon getImage() {
		assert hasImage();
		return this.image;
	}
	
	/**
	 * Set the image of the game.
	 * @param image The new image of the game.
	 */
	public void setImage(ImageIcon image) {
		this.image = image;
	}
	
	/**
	 * Tell if the game has an image or not.
	 * @return True if the game has an image, false if not.
	 */
	public boolean hasImage() {
		return this.image != null;
	}
}
