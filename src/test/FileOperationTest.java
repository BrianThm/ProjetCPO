package test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import controller.GameAlreadyExistsException;
import controller.LoadImpossibleException;
import controller.PlayerAlreadyExistsException;
import controller.SaveImpossibleException;
import tournament.Game;
import tournament.Player;
import tournament.Team;

public class FileOperationTest {
	
	Controller controller;
	
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
		
		controller.addGame(cs);
		controller.addGame(tm);
		controller.addGame(lol);
		
		controller.addPlayer(mo);
		controller.addPlayer(wi);
		controller.addPlayer(vi);
		controller.addPlayer(ep);
		
		controller.addTeam(famille);
	}
	
	@Test
	public void testSimpleSave() {
		String filename = "/tmp/testSave1.txt";
		try {
			controller.save(filename);
		} catch (SaveImpossibleException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testSimpleLoad() {
		String filename = "/tmp/testLoad1.txt";
		try {
			controller.save(filename);
			controller.load(filename);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testLoadInchanged1() throws SaveImpossibleException, LoadImpossibleException {
		String filename = "/tmp/testLoad2.txt";
		
		Set<Game> games = controller.getGames();
		Set<Player> players = controller.getPlayers();
		Set<Team> teams = controller.getTeams();
		
		controller.save(filename);
		controller.load(filename);
		
		Set<Game> gamesAfter = controller.getGames();
		Set<Player> playersAfter = controller.getPlayers();
		Set<Team> teamsAfter = controller.getTeams();
		
		
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
	
	@Test (expected = LoadImpossibleException.class)
	public void testLoadFail1() throws IOException, LoadImpossibleException {
		String filename = "/tmp/TestLoadFail.txt";
		FileWriter file = new FileWriter(filename);
		file.write("This is a file impossible to load.\n");
		controller.load(filename);
	}
}
