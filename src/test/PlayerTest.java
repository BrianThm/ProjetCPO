package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Class which test, with unit test, methods of the class Player
 * @author Group
 * @version 1.0
 */
public class PlayerTest extends SetupTest {

	@Test
	public void testPlayerString() {
		assertEquals("His pseudonym is not the good one !", pseudo, player.getName());
		assertEquals("He must not have any first name !", "", player.getFName());
		assertEquals("He must not have any name !", "", player.getLName());
		assertEquals("His preferred team must be null !", null, player.getPreferredTeam());
		assertEquals("His preferred game must be null !", null, player.getPreferredGame());
	}

	@Test
	public void testPlayerStringGame() {
		assertEquals("His pseudonym is not the good one !", pseudo1, player1.getName());
		assertEquals("He must not have any first name !", "", player1.getFName());
		assertEquals("He must not have any name !", "", player1.getLName());
		assertEquals("His preferred team must be null !", null, player1.getPreferredTeam());
		assertEquals("His preferred game is incorrect and hasn't been initialize correctly !", game1, player1.getPreferredGame());
	}

	@Test
	public void testPlayerStringStringString() {
		assertEquals("His pseudonym is not the good one !", pseudo2, player2.getName());
		assertEquals("His first name is not good !", firstname2, player2.getFName());
		assertEquals("His name is not good !", name2, player2.getLName());
		assertEquals("His preferred team must be null !", null, player2.getPreferredTeam());
		assertEquals("His preferred game must be null !", null, player2.getPreferredGame());
	}

	@Test
	public void testPlayerStringStringStringGame() {
		assertEquals("His pseudonym is not the good one !", pseudo3, player3.getName());
		assertEquals("His first name is not good !", firstname3, player3.getFName());
		assertEquals("His name is not good !", name3, player3.getLName());
		assertEquals("His preferred team must be null !", null, player3.getPreferredTeam());
		assertEquals("His preferred game is not the good one !", game3, player3.getPreferredGame());
	}

	@Test
	public void testGetFName() {
		assertEquals("The first name of player3 is not good !", firstname3, player3.getFName());
		assertEquals("The first name of player2 is not good !", firstname2, player2.getFName());
		assertEquals("The first name of player is not good !", "", player.getFName());
		assertEquals("The first name of player is not good !", "", player.getFName());
	}

	@Test
	public void testSetFName() {
		player1.setFName(firstname1);
		player.setFName(firstname);
		player2.setFName(firstname3);
		player3.setFName(firstname2);
		assertEquals("The first name of player is not good !", firstname1, player1.getFName());
		assertEquals("The first name of player is not good !", firstname, player.getFName());
		assertEquals("The first name of player2 is not good !", firstname3, player2.getFName());
		assertEquals("The first name of player3 is not good !", firstname2, player3.getFName());
	}

	@Test
	public void testGetLName() {
		assertEquals("The last name of player3 is not good !", name3, player3.getLName());
		assertEquals("The last name of player2 is not good !", name2, player2.getLName());
		assertEquals("The last name of player is not good !", "", player.getLName());
		assertEquals("The last name of player is not good !", "", player.getLName());
	}

	@Test
	public void testSetLName() {
		player1.setLName(name1);
		player.setLName(name);
		player2.setLName(name3);
		player3.setLName(name2);
		assertEquals("The last name of player is not good !", name1, player1.getLName());
		assertEquals("The last name of player is not good !", name, player.getLName());
		assertEquals("The last name of player2 is not good !", name3, player2.getLName());
		assertEquals("The last name of player3 is not good !", name2, player3.getLName());
	}

	@Test
	public void testPlaysIn() {
		player.playsIn(team3);
		assertEquals("The team is not good !", team3, player.getPreferredTeam());
		player.playsIn(team);
		player.playsIn(team);
		assertEquals("The team is not good !", team, player.getPreferredTeam());
		player1.playsIn(team1);
		assertEquals("The team is not good !", team1, player1.getPreferredTeam());
		player1.playsIn(team2);
		player1.playsIn(team2);
		assertEquals("The team is not good !", team2, player1.getPreferredTeam());
	}

	@Test
	public void testGetPreferredTeam() {
		player1.playsIn(team2);
		player3.playsIn(team);
		assertEquals("The team is not good !", null, player.getPreferredTeam());
		assertEquals("The team is not good !", team2, player1.getPreferredTeam());
		assertEquals("The team is not good !", null, player2.getPreferredTeam());
		assertEquals("The team is not good !", team, player3.getPreferredTeam());
	}

	@Test
	public void testEqualsObject() {
		assertEquals("The result must be false !", false, player.equals(player1));
		player1 = player; 
		assertEquals("The result must be true !", true, player.equals(player1));
		player2.setFName(firstname3);
		assertEquals("The result must be false !", false, player2.equals(player3));
		player2.setLName(name3);
		assertEquals("The result must be false !", false, player2.equals(player3));
		player2.setName(pseudo3);
		assertEquals("The result must be true !", true, player2.equals(player3));
	}
}
