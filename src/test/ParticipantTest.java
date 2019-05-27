package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import tournament.Game;
import tournament.Participant;
import tournament.Player;
import tournament.Team;

public class ParticipantTest {
	
	private Game g1, g2;
	private Participant p1, p2, p3, p4;
	
	@Before
	public void setup() {
		g1 = new Game("game1");
		g2 = new Game("game2");
		
		p1 = new Player("player1", g1);
		p2 = new Player("player2");
		p3 = new Team("team1", g2);
		p4 = new Team("team2");
		
		// p2 plays 2 times g2
		p2.plays(g2);
		p2.plays(g2);
		
		// p3 plays 1 time g1
		p3.plays(g1);
	}

	@Test
	public void testGetName() {
		assertEquals("player1", p1.getName());
		assertEquals("player2", p2.getName());
		assertEquals("team1", p3.getName());
		assertEquals("team2", p4.getName());
	}
	
	@Test
	public void testSetName() {
		p1.setName("test");
		p2.setName("test");
		p3.setName("test");
		p4.setName("test");
		
		assertEquals("test", p1.getName());
		assertEquals("test", p2.getName());
		assertEquals("test", p3.getName());
		assertEquals("test", p4.getName());
	}
	
	@Test
	public void testGetPreferredGame() {
		assertEquals(g1, p1.getPreferredGame());
		assertEquals(g2, p2.getPreferredGame());
		assertEquals(g1, p3.getPreferredGame());
		assertEquals(null, p4.getPreferredGame());
	}
	
	@Test
	public void testGetGames() {
		Map<Game, Integer> games;
		int played;
		
		games = p1.getGames();
		assertEquals(1, games.size());
		played = games.get(g1);
		assertEquals(0, played);
		
		games = p2.getGames();
		assertEquals(1, games.size());
		played = games.get(g2);
		assertEquals(2, played);
		
		games = p3.getGames();
		assertEquals(2, games.size());
		played = games.get(g1);
		assertEquals(1, played);
		played = games.get(g2);
		assertEquals(0, played);
		
		games = p4.getGames();
		assertEquals(0, games.size());
	}
	
	@Test
	public void testPlays() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testRemoveGame() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testTournament() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testToString() {
		fail("Not yet implemented");
	}
}
