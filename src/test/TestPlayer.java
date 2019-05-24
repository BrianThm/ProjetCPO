package test;

import tournament.Game;
import tournament.Player;
import tournament.Team;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Brian
 * @version
 * 
 * Class which test, with unit test, methods of the classPlayer
 *
 */
public class TestPlayer {
	
	private static Player player1; 
	private static Player player2;
	private static Player player3; 
	private static Player player4;
	
	private static String pseudo; 
	private static String pseudo1;
	private static String pseudo2;
	private static String pseudo3; 
	private static String pseudo4;
	private static String name; 
	private static String name1;
	private static String name2;
	private static String name3; 
	private static String name4;	
	private static String firstname; 
	private static String firstname1;
	private static String firstname2;
	private static String firstname3;
	private static String firstname4;
	
	private static Game game1; 
	private static Game game2;
	private static Game game3; 
	private static Game game4;
	
	private static Team team1; 
	private static Team team2;
	private static Team team3; 
	private static Team team4;
	
	
	@Before
	public void setUp() throws Exception {
		pseudo = "nono23";
		name = "Martin";
		firstname = "Bruno";
		game1 = new Game("game1");
		team1 = new Team("Team1");
		player1 = new Player(pseudo);
		
		pseudo1 = "Elareron";
		name1 = "Delaunay";
		firstname1 = "Gabriel";
		game2 = new Game("game2");
		team2 = new Team("Team2");
		player2 = new Player(pseudo1, game2);
		
		pseudo2 = "Laxul";
		name2 = "Dupont";
		firstname2 = "Renaud";
		game3 = new Game("game2");
		team3 = new Team("Team2");
		player3 = new Player(pseudo2, name2 ,firstname2);
		
		pseudo3 = "Laxu";
		name3 = "Dupont";
		firstname3 = "Renaud";
		game4 = new Game("game2");
		team4 = new Team("Team2");
		player4 = new Player(pseudo3, name3, firstname3, game4);
	}

	@Test
	public void testPlayerString() {
		assertEquals("Son pseudo doit être nono23 !",pseudo,player1.getName());
		assertEquals("Il ne dois pas avoir de prenom !","",player1.getFName());
		assertEquals("Il ne dois pas avoir de nom !","",player1.getLName());
		assertEquals("Son equipe préféré doit être nulle !", null, player1.getPreferredTeam());
		assertEquals("Son jeu préféré doit être nul !", null, player1.getPreferredGame());
	}

	@Test
	public void testPlayerStringGame() {
		assertEquals("Son pseudo n'est pas le bon !",pseudo1,player2.getName());
		assertEquals("Son equipe préféré doit être nulle !", null, player2.getPreferredTeam());
		assertEquals("Son jeu préféré doit être nul !", game1, player2.getPreferredGame());
	}

	@Test
	public void testPlayerStringStringString() {
		assertEquals("Son pseudo n'est pas le bon !",pseudo1,player2.getName());
		assertEquals("Son equipe préféré doit être nulle !", null, player2.getPreferredTeam());
		assertEquals("Son jeu préféré doit être nul !", game1, player2.getPreferredGame());
	}

	@Test
	public void testPlayerStringStringStringGame() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFName() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetFName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetLName() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetLName() {
		fail("Not yet implemented");
	}

	@Test
	public void testPlaysIn() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPreferredTeam() {
		fail("Not yet implemented");
	}

	@Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

}
