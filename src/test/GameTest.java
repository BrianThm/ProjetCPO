package test;

import static org.junit.Assert.assertEquals;

import javax.swing.ImageIcon;

import org.junit.Before;
import org.junit.Test;

import tournament.Game;

/**
 * Class which test, with unit tests, methods of the class Game.
 * @author Group
 * @version 1.0
 */
public class GameTest {
	
	private static Game jeu;
	private static Game jeu1;
	
	@Before
	public void setUp() throws Exception {
		jeu = new Game("Morpion");
		jeu1 = new Game("Tetris", new ImageIcon("Image test.jpg"));
	}

	/**
	 * Test method for {@link tournament.Game#Game(java.lang.String)}.
	 */
	@Test
	public void testGameString() {
		assertEquals("Le nom doit être Morpion !", "Morpion", jeu.getName()); 
		assertEquals("L'image doit être nulle", false, jeu.hasImage());
	}

	/**
	 * Test method for {@link tournament.Game#Game(java.lang.String, javax.swing.ImageIcon)}.
	 */
	@Test
	public void testGameStringImageIcon() {
		assertEquals("Le nom doit être Tetris !", "Tetris", jeu1.getName());
		assertEquals("L'image doit être la même que 'image test.jpg'", new ImageIcon("Image test.jpg").getImage(), jeu1.getImage().getImage());
	}

	/**
	 * Test method for {@link tournament.Game#getName()}.
	 */
	@Test
	public void testGetName() {
		assertEquals("Le nom doit être Morpion", "Morpion", jeu.getName());
		assertEquals("Le nom doit être Tetris", "Tetris", jeu1.getName());
	}

	/**
	 * Test method for {@link tournament.Game#setName(java.lang.String)}.
	 */
	@Test
	public void testSetName() {
		jeu.setName("OverWatch");
		assertEquals("Le nouveau nom doit etre OverWatch !", "OverWatch", jeu.getName());
		jeu1.setName("Bonjour"); 
		assertEquals("Le nouveau nom doit etre Bonjour", "Bonjour", jeu1.getName());
	}

	/**
	 * Test method for {@link tournament.Game#getImage()}.
	 */
	@Test
	public void testGetImage() {
		jeu.setImage(new ImageIcon("ImageTest2.jpg"));
		assertEquals("L'image doit être ImageTest2.jpg !", new ImageIcon("ImageTest2.jpg").getImage(), jeu.getImage().getImage());
		assertEquals("L'image doit être Image test.jpg !", new ImageIcon("Image test.jpg").getImage(), jeu1.getImage().getImage());
	}

	/**
	 * Test method for {@link tournament.Game#setImage(javax.swing.ImageIcon)}.
	 */
	@Test
	public void testSetImage() {
		jeu.setImage(new ImageIcon("Image test.jpg"));
		jeu1.setImage(new ImageIcon("ImageTest2.jpg"));
		assertEquals("L'image doit être Image test.jpg !", new ImageIcon("Image test.jpg").getImage(), jeu.getImage().getImage());
		assertEquals("L'image doit être ImageTest2.jpg !", new ImageIcon("ImageTest2.jpg").getImage(), jeu1.getImage().getImage());
	}

	/**
	 * Test method for {@link tournament.Game#hasImage()}.
	 */
	@Test
	public void testHasImage() {
		assertEquals("Le test doit renvoyer faux car jeu n'a pas d'image !", false, jeu.hasImage());
		assertEquals("Le test doit renvoyer vrai car jeu1 a une image !", true, jeu1.hasImage());
	}

	/**
	 * Test method for {@link tournament.Game#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		assertEquals("Il doit renvoyer faux car les deux jeux sont diff�rents ! ", false, jeu.equals(jeu1));
		jeu.setName("Tetris");
		jeu.setImage(new ImageIcon("Image test.jpg"));
		assertEquals("Il doit renvoyer vrai car ils sont egaux maintenant !", true, jeu.equals(jeu1));
	}

}
