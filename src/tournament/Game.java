package tournament;

import javax.swing.ImageIcon;

/**
 * Class Game, each tournament has a game associated.
 * @author Group
 * @version 1.0
 */
public class Game {
	
	private String name;
	private ImageIcon image;
	
	/**
	 * Creates a new game without image.
	 * @param name The name of the game.
	 */
	public Game(String name) {
		assert name != null;
		
		this.name = name;
	}
	
	/**
	 * Creates a new game with an image.
	 * @param name The name of the game.
	 * @param image The image associated to the game.
	 */
	public Game(String name, ImageIcon image) {
		this(name);
		assert image != null;
		
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
		assert name != null;
		
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
		assert image != null;
		
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
