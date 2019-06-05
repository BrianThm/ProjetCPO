package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import controller.exceptions.GameAlreadyExistsException;
import controller.exceptions.GameUsedException;
import controller.exceptions.PlayerAlreadyExistsException;
import controller.exceptions.TeamAlreadyExistsException;
import tournament.Game;
import tournament.Player;
import tournament.Team;
import tournament.Tournament;

/**
 * Test of Controller
 * @author Group
 * @version 1.0
 */
public class ControllerTest extends SetupTest {
	
	private Set<Game> expectedListGames;
	private Set<Player> expectedListPlayers;
	private Set<Tournament> expectedListTournaments;
	private Set<Team> expectedListTeams;
	
	@Before 
	public void setUp() throws Exception {
		super.setUp();
		expectedListTournaments = new HashSet<Tournament>();
		expectedListGames = new HashSet<Game>();
		expectedListPlayers = new HashSet<Player>();
		expectedListTeams = new HashSet<Team>();
	}
	
	/**
	 * Test method for {@link controller.Controller#Controller()}.
	 */
	@Test
	public void testController() {
		assertEquals("The list of Tournaments has not been correctly created !", expectedListTournaments, controller1.getTournaments());
		assertEquals("The list of Games has not been correctly created !", expectedListGames, controller1.getGames());
		assertEquals("The list of PLayers has not been correctly created !", expectedListPlayers, controller1.getPlayers());
		assertEquals("The list of Teams has not been correctly created !", expectedListTeams, controller1.getTeams());
	}
	
	/**
	 * Test method for {@link controller.Controller#addTournament(tournament.Tournament)}.
	 */
	@Test
	public void testAddTournament() {
		controller.addTournament(tournament);
		assertEquals("The number of tournament is not good !", 1, controller.getNbTournaments());
		
		tournaments.remove(tournament1);
		assertEquals("The tournament has not been correctly added in the controller", tournaments, controller.getTournaments());
		
		controller.addTournament(tournament2);
		tournaments.add(tournament2);
		
		assertEquals("The number of tournament is not good !", 2, controller.getNbTournaments());
		assertEquals("The tournament has not been correctly added in the controller", tournaments, controller.getTournaments());
	}
	
	/**
	 * Test method for {@link controller.Controller#removeTournament(tournament.Tournament)}.
	 */
	@Test
	public void testRemoveTournament() {
		controller.addTournament(tournament);
		controller.addTournament(tournament1);
		controller.addTournament(tournament2);
		controller.removeTournament(tournament2);
		assertEquals("The number of tournaments is not good !", 2, controller.getNbTournaments());
		assertEquals("The tournament has not been correctly added in the controller", tournaments, controller.getTournaments());
		
		controller.removeTournament(tournament);
		controller.removeTournament(tournament1);
		tournaments = new HashSet<Tournament>();
		assertEquals("The number of tournament is not good !", 0, controller.getNbTournaments());
		assertEquals("The tournaments have not been correctly removed in the controller", tournaments, controller.getTournaments());
	}
	
	/**
	 * Test method for {@link controller.Controller#getTournaments()}.
	 */
	@Test
	public void testGetTournaments() {
		tournaments = new HashSet<Tournament>();
		assertEquals("The List of Tournament has not been correctly returned !", tournaments, controller.getTournaments());
		assertEquals("The List of Tournament has not been correctly returned !", tournaments, controller1.getTournaments());
		
		tournaments.add(tournament3);
		controller.addTournament(tournament3);
		assertEquals("The list returned is not the correct one !", tournaments, controller.getTournaments());
	}
	
	/**
	 * Test method for {@link controller.Controller#getNbTournaments()}.
	 */
	@Test
	public void testGetNbTournaments() {
		assertEquals("The number of tournaments for controller is not good !", 0, controller.getNbTournaments());
		assertEquals("The number of tournaments for controller1 is not good !", 0, controller1.getNbTournaments());
		
		controller.addTournament(tournament);
		controller1.addTournament(tournament);
		controller1.addTournament(tournament2);
		assertEquals("The number of tournaments for controller1 is not good !", 1, controller.getNbTournaments());
		assertEquals("The number of tournaments for controller1 is not good !", 2, controller1.getNbTournaments());
		
	}
	
	/**
	 * Test method for {@link controller.Controller#addGame(tournament.Game)}.
	 * @throws GameAlreadyExistsException
	 */
	@Test
	public void testAddGame() throws GameAlreadyExistsException {
		expectedListGames.add(game);
		expectedListGames.add(game3);
		controller1.addGame(game);
		controller1.addGame(game3);
		assertEquals("The number of Games is incorrect !", 2, controller1.getNbGames());
		assertEquals("The List of games has not been updated !", expectedListGames, controller1.getGames());
		
		controller1.addGame(game1);
		expectedListGames.add(game1);
		assertEquals("The number of Games is incorrect !", 3, controller1.getNbGames());
		assertEquals("The List of games has not been updated !", expectedListGames, controller1.getGames());
		
	}
	
	/**
	 * Test method for {@link controller.Controller#removeGame(tournament.Game)}.
	 * @throws GameAlreadyExistsException
	 * @throws GameUsedException
	 */
	@Test
	public void testRemoveGame() throws GameAlreadyExistsException, GameUsedException {
		expectedListGames.add(game3);
		expectedListGames.add(game1);
		
		controller1.addGame(game);
		controller1.addGame(game1);
		controller1.addGame(game3);
		
		controller1.removeGame(game);
		
		assertEquals("The number of Games is incorrect !", 2, controller1.getNbGames());
		assertEquals("The List of games has not been updated !", expectedListGames, controller1.getGames());
		
		controller1.removeGame(game1);
		controller1.removeGame(game3);
		expectedListGames = new HashSet<Game>();
		
		assertEquals("The number of Games is incorrect !", 0, controller1.getNbGames());
		assertEquals("The List of games has not been updated !", expectedListGames, controller1.getGames());
		
	}
	
	/**
	 * Test method for {@link controller.Controller#getGames()}.
	 * @throws GameAlreadyExistsException 
	 */
	@Test
	public void testGetGames() throws GameAlreadyExistsException {
		assertEquals("The List of Games has not been correctly returned !", expectedListGames, controller1.getGames());
		
		expectedListGames.add(game);
		expectedListGames.add(game1);
		expectedListGames.add(game2);
		expectedListGames.add(game3);
		assertEquals("The List of Games has not been correctly returned !", expectedListGames, controller.getGames());
		
		expectedListGames.remove(game);
		expectedListGames.remove(game1);
		expectedListGames.remove(game2);
		controller1.addGame(game3);
		assertEquals("The list returned is not the correct one !", expectedListGames, controller1.getGames());
	}
	
	/**
	 * Test method for {@link controller.Controller#getNbGames()}.
	 */
	@Test
	public void testGetNbGames() {
		assertEquals("The number of tournaments for controller is not good !", 0, controller.getNbTournaments());
		assertEquals("The number of tournaments for controller1 is not good !", 0, controller1.getNbTournaments());

		controller.addTournament(tournament);
		controller1.addTournament(tournament);
		controller1.addTournament(tournament2);
		assertEquals("The number of tournaments for controller1 is not good !", 1, controller.getNbTournaments());
		assertEquals("The number of tournaments for controller1 is not good !", 2, controller1.getNbTournaments());
	}
	
	/**
	 * Test method for {@link controller.Controller#gameExists(tournament.Game, tournament.Game)}.
	 */
	@Test
	public void testGameExists() {
		assertEquals("The test must return false !", false, controller.gameExists(game1, game1));
		assertEquals("The test must return true !", true, controller.gameExists(game1, game2));
		assertEquals("The test must return false !", false, controller1.gameExists(game3, game3));
		Game game1old = game1;
		game1.setName("Disc Jam");
		assertEquals("The test must return false !", false, controller.gameExists(game1old, game1));
	}
	
	/**
	 * Test method for {@link controller.Controller#addPlayer(tournament.Player)}.
	 * @throws PlayerAlreadyExistsException
	 */
	@Test
	public void testAddPlayer() throws PlayerAlreadyExistsException {
		expectedListPlayers.add(player1);
		expectedListPlayers.add(player3);
		controller1.addPlayer(player1);
		controller1.addPlayer(player3);
		assertEquals("The number of PLayers is incorrect !", 2, controller1.getNbPlayers());
		assertEquals("The list of players has not been updated !", expectedListPlayers, controller1.getPlayers());
		
		controller1.addPlayer(player);
		expectedListPlayers.add(player);
		assertEquals("The number of Games is incorrect !", 3, controller1.getNbPlayers());
		assertEquals("The List of players has not been updated !", expectedListPlayers, controller1.getPlayers());
	}
	
	/**
	 * Test method for {@link controller.Controller#removePlayer(tournament.Player)}.
	 * @throws PlayerAlreadyExistsException
	 */
	@Test
	public void testRemovePlayer() throws PlayerAlreadyExistsException {
		expectedListPlayers.add(player3);
		expectedListPlayers.add(player1);
		
		controller1.addPlayer(player);
		controller1.addPlayer(player1);
		controller1.addPlayer(player3);
		
		controller1.removePlayer(player);
		
		assertEquals("The number of Players is incorrect !", 2, controller1.getNbPlayers());
		assertEquals("The List of players has not been updated !", expectedListPlayers, controller1.getPlayers());
		
		controller1.removePlayer(player1);
		controller1.removePlayer(player3);
		expectedListPlayers = new HashSet<Player>();
		
		assertEquals("The number of Players is incorrect !", 0, controller1.getNbPlayers());
		assertEquals("The List of players has not been updated !", expectedListPlayers, controller1.getPlayers());
	}
	
	/**
	 * Test method for {@link controller.Controller#getPlayers()}.
	 * @throws PlayerAlreadyExistsException
	 */
	@Test
	public void testGetPlayers() throws PlayerAlreadyExistsException {
		assertEquals("The List of Players has not been correctly returned !", expectedListPlayers, controller1.getPlayers());
		
		expectedListPlayers.add(player);
		expectedListPlayers.add(player1);
		expectedListPlayers.add(player2);
		expectedListPlayers.add(player3);
		expectedListPlayers.add(player4);
		expectedListPlayers.add(player5);
		expectedListPlayers.add(player6);
		expectedListPlayers.add(player7);
		
		assertEquals("The List of Players has not been correctly returned !", expectedListPlayers, controller.getPlayers());

		expectedListPlayers.remove(player);
		expectedListPlayers.remove(player1);
		expectedListPlayers.remove(player2);
		expectedListPlayers.remove(player4);
		expectedListPlayers.remove(player5);
		expectedListPlayers.remove(player6);
		expectedListPlayers.remove(player7);
		
		controller1.addPlayer(player3);
		assertEquals("The list returned is not the correct one !", expectedListPlayers, controller1.getPlayers());
	}
	
	/**
	 * Test method for {@link controller.Controller#getNbPlayers()}.
	 * @throws PlayerAlreadyExistsException
	 */
	@Test
	public void testGetNbPlayers() throws PlayerAlreadyExistsException {
		assertEquals("The number of Players for controller is not good !", 8, controller.getNbPlayers());
		assertEquals("The number of Players for controller1 is not good !", 0, controller1.getNbPlayers());
		
		controller.removePlayer(player);
		controller1.addPlayer(player);
		controller1.addPlayer(player2);
		assertEquals("The number of players for controller is not good !", 7, controller.getNbPlayers());
		assertEquals("The number of players for controller1 is not good !", 2, controller1.getNbPlayers());
	}
	
	/**
	 * Test method for {@link controller.Controller#playerExists(tournament.Player, tournament.Player)}.
	 */
	@Test
	public void testPlayerExists() {
		assertEquals("The test must return false ! ", false, controller1.playerExists(player1, player1));
		assertEquals("The test must return false ! ", false, controller.playerExists(player1, player1));
		assertEquals("The test must return true ! ", true, controller.playerExists(player1, player2));
		Player playerold = player;
		player.setName("Gota");
		assertEquals("The test must return false ! ", false, controller.playerExists(playerold, player));
	}
	
	/**
	 * Test method for {@link controller.Controller#addTeam(tournament.Team)}.
	 * @throws TeamAlreadyExistsException
	 */
	@Test
	public void testAddTeam() throws TeamAlreadyExistsException {
		expectedListTeams.add(team1);
		expectedListTeams.add(team3);
		controller1.addTeam(team1);
		controller1.addTeam(team3);
		assertEquals("The number of PLayers is incorrect !", 2, controller1.getNbTeams());
		assertEquals("The list of teams has not been updated !", expectedListTeams, controller1.getTeams());
		
		controller1.addTeam(team);
		expectedListTeams.add(team);
		assertEquals("The number of Games is incorrect !", 3, controller1.getNbTeams());
		assertEquals("The List of teams has not been updated !", expectedListTeams, controller1.getTeams());
	}
	
	/**
	 * Test method for {@link controller.Controller#removeTeam(tournament.Team)}.
	 */
	@Test
	public void testRemoveTeam() {
		expectedListTeams.add(team);
		expectedListTeams.add(team1);
		
		controller.removeTeam(team2);
		controller.removeTeam(team3);
		
		assertEquals("The number of Teams is incorrect !", 2, controller.getNbTeams());
		assertEquals("The List of teams has not been updated !", expectedListTeams, controller.getTeams());
		
		controller.removeTeam(team1);
		controller.removeTeam(team);
		expectedListTeams = new HashSet<Team>();
		
		assertEquals("The number of Teams is incorrect !", 0, controller.getNbTeams());
		assertEquals("The List of teams has not been updated !", expectedListTeams, controller.getTeams());
	}
	
	/**
	 * Test method for {@link controller.Controller#getTeams()}.
	 * @throws TeamAlreadyExistsException
	 */
	@Test
	public void testGetTeams() throws TeamAlreadyExistsException {
		assertEquals("The List of Teams has not been correctly returned !", expectedListTeams, controller1.getTeams());

		expectedListTeams.add(team);
		expectedListTeams.add(team1);
		expectedListTeams.add(team2);
		expectedListTeams.add(team3);
		
		assertEquals("The List of Teams has not been correctly returned !", expectedListTeams, controller.getTeams());

		expectedListTeams.remove(team);
		expectedListTeams.remove(team1);
		expectedListTeams.remove(team2);
		controller1.addTeam(team3);
		assertEquals("The list returned is not the correct one !", expectedListTeams, controller1.getTeams());
	}
	
	/**
	 * Test method for {@link controller.Controller#getNbTeams()}.
	 * @throws TeamAlreadyExistsException
	 */
	@Test
	public void testGetNbTeams() throws TeamAlreadyExistsException {
		assertEquals("The number of Teams for controller is not good !", 4, controller.getNbTeams());
		assertEquals("The number of Teams for controller1 is not good !", 0, controller1.getNbTeams());

		controller.removeTeam(team);
		controller1.addTeam(team1);
		assertEquals("The number of teams for controller is not good !", 3, controller.getNbTeams());
		assertEquals("The number of teams for controller1 is not good !", 1, controller1.getNbTeams());
	}
	
	/**
	 * Test method for {@link controller.Controller#getSortedPlayers()}.
	 */
	@Test
	public void testGetSortedPlayers() {
		ArrayList<Player> expectedSortedPlayers = new ArrayList<Player>();
		assertEquals("The list of sortedPLayers returned is not correct !", expectedSortedPlayers, controller1.getSortedPlayers());
		
		expectedSortedPlayers.add(player1); 
		expectedSortedPlayers.add(player3); 
		expectedSortedPlayers.add(player6); 
		expectedSortedPlayers.add(player2); 
		expectedSortedPlayers.add(player5); 
		expectedSortedPlayers.add(player); 
		expectedSortedPlayers.add(player7); 
		expectedSortedPlayers.add(player4); 
		
		assertEquals("The list of sortedPLayers returned is not correct !", expectedSortedPlayers, controller.getSortedPlayers());
	}
	
	/**
	 * Test method for {@link controller.Controller#getSortedTeam()}.
	 */
	@Test
	public void testGetSortedTeam() {
		ArrayList<Team> expectedSortedTeams = new ArrayList<Team>();
		assertEquals("The list of sortedPLayers returned is not correct !", expectedSortedTeams, controller1.getSortedTeams());
		
		expectedSortedTeams.add(team3); 
		expectedSortedTeams.add(team); 
		expectedSortedTeams.add(team1); 
		expectedSortedTeams.add(team2); 
		
		assertEquals("The list of sortedPLayers returned is not correct !", expectedSortedTeams, controller.getSortedTeams());
	
	}	
	
	/**
	 * Test method for {@link controller.Controller#getSortedGames()}.
	 */
	@Test
	public void testGetSortedGame() {
		ArrayList<Game> expectedSortedGames = new ArrayList<Game>();
		assertEquals("The list of sortedPLayers returned is not correct !", expectedSortedGames, controller1.getSortedGames());
		
		expectedSortedGames.add(game1); 
		expectedSortedGames.add(game); 
		expectedSortedGames.add(game2); 
		expectedSortedGames.add(game3); 
		
		assertEquals("The list of sortedPLayers returned is not correct !", expectedSortedGames, controller.getSortedGames());
	
	}	
	
	/**
	 * Test method for {@link controller.Controller#save(java.lang.String)}.
	 */
	//@Test
	//public void testSave() {
		//fail("Not yet implemented");
	//}
	
	/**
	 * Test method for {@link controller.Controller#load(java.lang.String)}.
	 */
	//@Test
	//public void testLoad() {
		//fail("Not yet implemented");
	//}
	
}
