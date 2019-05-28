package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import tournament.*;
import tournament.exceptions.*;

public class ParticipantTest extends TestSetup{
	
	private Game g1, g2;
	private Participant p1, p2, p3, p4;


	@Test
	public void testGetName() {
		assertEquals(pseudo, participants.get(0).getName() );
		assertEquals(pseudo1, participants.get(1).getName());
		assertEquals(nomTeam, participants.get(8).getName());
		assertEquals(nomTeam3, participants.get(11).getName());
	}
	
	@Test
	public void testSetName() {
		participants.get(0).setName(name3);
		participants.get(1).setName(name2);
		participants.get(8).setName(nomTeam2);
		participants.get(11).setName(nomTeam1);
		assertEquals(name3, participants.get(0).getName());
		assertEquals(name2, participants.get(1).getName());
		assertEquals(nomTeam2, participants.get(8).getName());
		assertEquals(nomTeam1, participants.get(11).getName());
	}
	
	@Test
	public void testGetPreferredGame() {
		participants.get(0).plays(game1);
		participants.get(1).plays(game3);
		participants.get(8).plays(game);
		assertEquals(game1, participants.get(0).getPreferredGame());
		assertEquals(game3, participants.get(1).getPreferredGame());
		assertEquals(game, participants.get(8).getPreferredGame());
		assertEquals(null, participants.get(11).getPreferredGame());
	}
	
	@Test
	public void testGetGames() {
		Map<Game, Integer> games;
		
		participants.get(0).plays(game1);
		participants.get(1).plays(game3);
		participants.get(8).plays(game);
		
		int played;
		
		games = participants.get(0).getGames();
		assertEquals(1, games.size());
		played = games.get(game);
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
		Map<Game, Integer> games;
		int played;
		
		for (int i = 1; i < 100; i++) {
			p4.plays(g1);
			games = p4.getGames();
			played = games.get(g1);
			assertEquals(i, played);
		}
	}
	
	@Test
	public void testRemoveGame() throws GamePlayedException {
		Map<Game, Integer> games;
		
		p1.removeGame(g1);
		games = p1.getGames();
		assertEquals(0, games.size());
		
		p2.removeGame(g2);
		games = p2.getGames();
		assertEquals(0, games.size());
		
		p3.removeGame(g1);
		p3.removeGame(g2);
		games = p3.getGames();
		assertEquals(0, games.size());
	}
	
	@Test
	public void testTournament() {
		Set<Tournament> tournaments = p1.getTournaments();
		assertEquals(0, tournaments.size());
		
		Tournament t = new SimpleElimination(g1);
		
		p1.addTournament(t);
		tournaments = p1.getTournaments();
		assertEquals(1, tournaments.size());
		
		p1.removeTournament(t);
		tournaments = p1.getTournaments();
		assertEquals(0, tournaments.size());
	}
	
	@Test (expected = GamePlayedException.class)
	public void testGamePlayedException() throws GamePlayedException {
		Tournament t = new SimpleElimination(g1);
		p1.addTournament(t);
		p1.removeGame(g1);
	}
	
	@Test
	public void testToString() {
		assertEquals("Player \"player\", Game: game1", p1.toString());
		assertEquals("Player \"player2\", Game: game2", p2.toString());
		assertEquals("Participant team2", p4.toString());
		
		boolean found = false;
		if (p3.toString().equals("Participant team1, Game: game1")) {
			found = true;
		}
		if (p3.toString().equals("Participant team1, Game: game2")) {
			found = true;
		}
		assertEquals(true, found);
	}
}
