package test;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import tournament.*;
import tournament.exceptions.*;

public class ParticipantTest extends SetupTest{
	
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
		assertEquals(null, participants.get(9).getPreferredGame());
	}
	
	@Test
	public void testGetGames() {
		Map<Game, Integer> games;
		int played;
		
		games = participants.get(1).getGames();
		assertEquals(1, games.size());
		played = games.get(game1);
		assertEquals(0, played);
		
		participants.get(0).plays(game);
		participants.get(0).plays(game);
		games = participants.get(0).getGames();
		assertEquals(1, games.size());
		played = games.get(game);
		assertEquals(2, played);
		
		participants.get(11).plays(game2);
		
		games = participants.get(11).getGames();
		assertEquals(2, games.size());
		played = games.get(game2);
		assertEquals(1, played);
		played = games.get(game3);
		assertEquals(0, played);
		
		games = participants.get(8).getGames();
		assertEquals(0, games.size());
	}
	
	@Test
	public void testPlays() {
		Map<Game, Integer> games;
		int played;
		
		for (int i = 1; i < 100; i++) {
			participants.get(3).plays(game2);
			games = participants.get(3).getGames();
			played = games.get(game2);
			assertEquals(i, played);
		}
	}
	
	@Test
	public void testRemoveGame() throws GamePlayedException {
		Map<Game, Integer> games;
		
		participants.get(1).removeGame(game1);
		games = participants.get(1).getGames();
		assertEquals(0, games.size());
		
		participants.get(0).plays(game);
		participants.get(0).plays(game);
		participants.get(0).removeGame(game);
		games = participants.get(0).getGames();
		assertEquals(0, games.size());
		
		participants.get(11).plays(game2);
		participants.get(11).removeGame(game3);
		participants.get(11).removeGame(game2);
		games = participants.get(11).getGames();
		assertEquals(0, games.size());
	}
	
	@Test
	public void testAddTournament() {
		
		participants.get(0).addTournament(tournament);
		for (Tournament t : participants.get(0).getTournaments()) {
			assertEquals("The tournament is not good !",tournament,t);
		}
		
		participants.get(0).addTournament(tournament1); 
		assertEquals("The tournaments are not good !",tournaments,participants.get(0).getTournaments());
		
	}
	
	@Test
	public void testGetTournament() {
		
		HashSet<Tournament> t = new HashSet<Tournament>(); 
		t.add(tournament);
		participants.get(0).addTournament(tournament);
		assertEquals("The set returns is not good !",t,participants.get(0).getTournaments());
		
		t.add(tournament1); 
		participants.get(8).addTournament(tournament);
		participants.get(8).addTournament(tournament1);
		assertEquals("The set returns is not good !",t,participants.get(8).getTournaments());
		
	}
	
	@Test
	public void testRemoveTournament() {
		//already test
		HashSet<Tournament> t = new HashSet<Tournament>();
		participants.get(0).addTournament(tournament);
		participants.get(0).removeTournament(tournament);
		assertEquals("The set returns is not good !",t,participants.get(0).getTournaments());
		
		participants.get(8).addTournament(tournament);
		participants.get(8).addTournament(tournament1);
		participants.get(8).removeTournament(tournament);
		participants.get(8).removeTournament(tournament1);
		assertEquals("The set returns is not good !",t,participants.get(8).getTournaments());
		
	}
	
	@Test (expected = GamePlayedException.class)
	public void testGamePlayedException() throws GamePlayedException {
		participants.get(0).addTournament(tournament);
		participants.get(0).removeGame(game);
	}
	
	@Test
	public void testToString() {
		assertEquals("Player \""+pseudo1+"\", Game: "+game1, participants.get(1).toString());
		assertEquals("Participant \""+nomTeam3+"\", Game: "+game3, participants.get(11).toString());
		assertEquals("Participant \""+nomTeam+"\"", participants.get(8).toString());
		
		boolean found = false;
		if (participants.get(3).toString().equals("Player "+firstname3+" \""+pseudo3+"\" "+name3+", Game: "+ game3)) {
			found = true;
		}
		assertEquals(true, found);
		found = false;
		if (participants.get(9).toString().equals("Participant \""+ nomTeam1+"\"")) {
			found = true;
		}
		assertEquals(true, found);
	}
}
