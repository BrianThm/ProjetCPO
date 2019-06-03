package test;

import static org.junit.Assert.assertEquals;

import javax.swing.ImageIcon;

import org.junit.Test;

/**
 * Class which test, with unit tests, methods of the class Game.
 * @author Group
 * @version 1.0
 */
public class GameTest extends SetupTest {


	/**
	 * Test method for {@link tournament.Game#Game(java.lang.String)}.
	 */
	@Test
	public void testGameString() {
		assertEquals("Le nom doit être Overwatch !", "Overwatch", game.getName()); 
		assertEquals("L'image doit être nulle", false, game.hasImage());
	}

	/**
	 * Test method for {@link tournament.Game#Game(java.lang.String, javax.swing.ImageIcon)}.
	 */
	@Test
	public void testGameStringImageIcon() {
		assertEquals("Le nom doit être CS:GO !", "CS:GO", game1.getName());
		assertEquals("L'image doit être la même que 'image test.jpg'", new ImageIcon("Image test.jpg").getImage(), game1.getImage().getImage());
	}

	/**
	 * Test method for {@link tournament.Game#getName()}.
	 */
	@Test
	public void testGetName() {
		assertEquals("Le nom doit être Overwatch", "Overwatch", game.getName());
		assertEquals("Le nom doit être CS:GO", "CS:GO", game1.getName());
	}

	/**
	 * Test method for {@link tournament.Game#setName(java.lang.String)}.
	 */
	@Test
	public void testSetName() {
		game.setName("Overwatch");
		assertEquals("Le nouveau nom doit etre Overwatch !", "Overwatch", game.getName());
		game1.setName("Bonjour"); 
		assertEquals("Le nouveau nom doit etre Bonjour", "Bonjour", game1.getName());
	}

	/**
	 * Test method for {@link tournament.Game#getImage()}.
	 */
	@Test
	public void testGetImage() {
		game.setImage(new ImageIcon("ImageTest2.jpg"));
		assertEquals("L'image doit être ImageTest2.jpg !", new ImageIcon("ImageTest2.jpg").getImage(), game.getImage().getImage());
		assertEquals("L'image doit être Image test.jpg !", new ImageIcon("Image test.jpg").getImage(), game1.getImage().getImage());
	}

	/**
	 * Test method for {@link tournament.Game#setImage(javax.swing.ImageIcon)}.
	 */
	@Test
	public void testSetImage() {
		game.setImage(new ImageIcon("Image test.jpg"));
		game1.setImage(new ImageIcon("ImageTest2.jpg"));
		assertEquals("L'image doit être Image test.jpg !", new ImageIcon("Image test.jpg").getImage(), game.getImage().getImage());
		assertEquals("L'image doit être ImageTest2.jpg !", new ImageIcon("ImageTest2.jpg").getImage(), game1.getImage().getImage());
	}

	/**
	 * Test method for {@link tournament.Game#hasImage()}.
	 */
	@Test
	public void testHasImage() {
		assertEquals("Le test doit renvoyer faux car game n'a pas d'image !", false, game.hasImage());
		assertEquals("Le test doit renvoyer vrai car game1 a une image !", true, game1.hasImage());
	}

	/**
	 * Test method for {@link tournament.Game#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		assertEquals("Il doit renvoyer faux car les deux gamex sont diff�rents ! ", false, game.equals(game1));
		game.setName("CS:GO");
		game.setImage(new ImageIcon("Image test.jpg"));
		assertEquals("Il doit renvoyer vrai car ils sont egaux maintenant !", true, game.equals(game1));
	}

}
