package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import controller.*;
import controller.exceptions.*;
import tournament.*;

public class FileOperationTest {
	
	Controller controller;
	String filename = "/tmp/FileOperationTestSave.txt";
	
	@Before
	public void setup() throws GameAlreadyExistsException, PlayerAlreadyExistsException {
		controller = new Controller();
		
		Game cs = new Game("CSGO");
		Game tm = new Game("Trackmania");
		Game lol = new Game("League of Legends");
		
		Player mo = new Player("Morgane", "Cadeau", "Enaxom", cs);
		Player wi = new Player("William", "Mateille", "Wyzedix", cs);
		Player vi = new Player("Ravioli");
		Player ep = new Player("Epsilon");
		
		Team famille = new Team("Famille");
		famille.addMember(mo);
		famille.addMember(wi);
		famille.addMember(vi);
		famille.addMember(ep);
		
		Team chats = new Team("Chats");
		chats.addMember(vi);
		chats.addMember(ep);
		
		Team epsilon = new Team("Epsilon", cs);
		epsilon.addMember(mo);
		epsilon.addMember(wi);
		
		controller.addGame(cs);
		controller.addGame(tm);
		controller.addGame(lol);
		
		controller.addPlayer(mo);
		controller.addPlayer(wi);
		controller.addPlayer(vi);
		controller.addPlayer(ep);
		
		controller.addTeam(famille);
		controller.addTeam(chats);
		controller.addTeam(epsilon);
	}
	
	@Test
	public void testSimpleSave() {
		try {
			controller.save(filename);
		} catch (SaveImpossibleException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testSimpleLoad() {
		try {
			controller.save(filename);
			controller.load(filename);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testLoadInchanged1() throws SaveImpossibleException, LoadImpossibleException {
		Set<Game> games = controller.getGames();
		Set<Player> players = controller.getPlayers();
		Set<Team> teams = controller.getTeams();
		
		controller.save(filename);
		controller.load(filename);
		
		Set<Game> gamesAfter = controller.getGames();
		Set<Player> playersAfter = controller.getPlayers();
		Set<Team> teamsAfter = controller.getTeams();
		
		assertTrue("Games not corresponding after load.", gamesMatch(games, gamesAfter));
		assertTrue("Players not corresponding after load.", playersMatch(players, playersAfter));
		assertTrue("Teams not corresponding after load.", teamsMatch(teams, teamsAfter));
	}
	
	private boolean gamesMatch(Set<Game> games1, Set<Game> games2) {
		boolean found;
		
		for (Game game1 : games1) {
			found = false;
			for (Game game2 : games2) {
				if (game1.equals(game2)) {
					found = true;
					break;
				}
			}
			if (!found) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean playersMatch(Set<Player> players1, Set<Player> players2) {
		boolean found;
		Set<Game> games1, games2;
		
		for (Player player1 : players1) {
			found = false;
			for (Player player2 : players2) {
				if (player1.equals(player2)) {
					games1 = player1.getGames().keySet();
					games2 = player2.getGames().keySet();
					found = this.gamesMatch(games1, games2);
					break;
				}
			}
			if (!found) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean teamsMatch(Set<Team> teams1, Set<Team> teams2) {
		boolean found;
		Set<Player> players1, players2;
		
		for (Team team1 : teams1) {
			found = false;
			for (Team team2 : teams2) {
				if (team1.equals(team2)) {
					players1 = team1.getMembers();
					players2 = team2.getMembers();
					found = playersMatch(players1, players2);
					break;
				}
			}
			if (!found) {
				return false;
			}
		}
		
		return true;
	}
	
	@Test (expected = LoadImpossibleException.class)
	public void testLoadFail1() throws IOException, LoadImpossibleException {
		FileWriter file = new FileWriter(filename);
		file.write("This is a file impossible to load.\n");
		try {
			controller.load(filename);
		} finally {
			file.close();
		}
	}
	
	@Test
	public void testLoadComa() throws PlayerAlreadyExistsException, GameAlreadyExistsException, SaveImpossibleException, LoadImpossibleException {
		Controller miniC = new Controller();
		
		Game game = new Game("a;game;with;some;semicolon");
		Player player = new Player("a;player;with;some;semicolon");
		Team team = new Team("a;team;with;some;semicolon");
		
		miniC.addGame(game);
		miniC.addPlayer(player);
		miniC.addTeam(team);
		
		miniC.save(filename);
		miniC.load(filename);
		
		Set<Game> games2 = miniC.getGames();
		Set<Player> players2 = miniC.getPlayers();
		Set<Team> teams2 = miniC.getTeams();
		
		for (Game game2 : games2) {
			assertEquals("Incorrect translation of the game.", "a,game,with,some,semicolon", game2.getName());
		}
		
		for (Player player2 : players2) {
			assertEquals("Incorrect translation of the player.", "a,player,with,some,semicolon", player2.getName());
		}
		
		for (Team team2 : teams2) {
			assertEquals("Incorrect translation of the team.", "a,team,with,some,semicolon", team2.getName());
		}
	}
}
