package test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import tournament.Game;
import tournament.Player;

/**
 * Class which test the methods of the class Team
 * @author Group
 * @version 1.0
 */
public class TeamTest extends SetupTest {

	/**
	 * Map of games that we will expect during the tests
	 */
	private Map<Game,Integer> expectedGames;
	
	/**
	 * Set of players that we will expect during tests
	 */
	private Set<Player> listPlayers;
	
	/**
	 * Initialization of the set and the map. 
	 * Initialization of all variables defined in SetupTest
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
		expectedGames = new HashMap<Game,Integer>();
		listPlayers = new HashSet<Player>();
	}
	
	/**
	 * Test method for {@link tournament.Team#plays(tournament.Game)}.
	 */
	@Test
	public void testPlays() {
		team.plays(game1);
		team.plays(game1);
		
		expectedGames.put(game1, 2);
		assertEquals("The plays have not been correctly added !", expectedGames, team.getGames()); 
		for(Player p : team.getMembers()) {
			assertEquals("The plays have not been correctly added to player !", expectedGames, p.getGames()); 
		}
		
		expectedGames.remove(game1);
		
		team2.plays(game);
		team2.plays(game3);
		team2.plays(game3);
		expectedGames.put(game,1);
		expectedGames.put(game2, 0);
		expectedGames.put(game3, 2);
		assertEquals("The plays have not been correctly added !", expectedGames, team2.getGames());
		expectedGames.remove(game2);
		for(Player p : team2.getMembers()) {
			assertEquals("The plays have not been correctly added to player !", expectedGames, p.getGames()); 
		}
		
		expectedGames.remove(game);
		expectedGames.remove(game2);
		expectedGames.replace(game3, 1);
		team3.plays(game3);
		assertEquals("The plays have not been correctly added !", expectedGames, team3.getGames()); 
		for(Player p : team3.getMembers()) {
			
			assertEquals("The plays have not been correctly added to player !", expectedGames, p.getGames()); 
		}
	}

	/**
	 * Test method for {@link tournament.Team#addTournament(tournament.Tournament)}.
	 */
	@Test
	public void testAddTournament() {
		
		team.addTournament(tournament3);
		team.addTournament(tournament1);
		team1.addTournament(tournament);
		
		tournaments.removeAll(tournaments);
		tournaments.add(tournament1);
		tournaments.add(tournament3);
		
		assertEquals("The tournaments have not been correctly added !", tournaments, team.getTournaments()); 
		for(Player p : team.getMembers()) {
			assertEquals("The tournaments have not been correctly added to player !", tournaments, p.getTournaments()); 
		}
		
		tournaments.removeAll(tournaments);
		tournaments.add(tournament);
		assertEquals("The tournament has not been correctly added !", tournaments, team1.getTournaments()); 
		for(Player p : team1.getMembers()) {
			assertEquals("The tournament has not been correctly added to player !", tournaments, p.getTournaments()); 
		}
		
	}

	/**
	 * Test method for {@link tournament.Team#removeTournament(tournament.Tournament)}.
	 */
	@Test
	public void testRemoveTournament() {
		team.addTournament(tournament3);
		team.addTournament(tournament1);
		team1.addTournament(tournament);

		tournaments.removeAll(tournaments);
		tournaments.add(tournament1);

		team.removeTournament(tournament3);

		assertEquals("The tournament has not bee correctly removed !", tournaments, team.getTournaments());
		for(Player p : team.getMembers()) {
			assertEquals("The tournament has not been correctly removed to player !", tournaments, p.getTournaments()); 
		}

		team.removeTournament(tournament1);
		tournaments.remove(tournament1);

		assertEquals("The tournament has not bee correctly removed !", tournaments, team.getTournaments()); 
		for(Player p : team.getMembers()) {
			assertEquals("The tournament has not been correctly removed to player !", tournaments, p.getTournaments()); 
		}

		team1.removeTournament(tournament);

		assertEquals("The tournament has not bee correctly removed !", tournaments, team1.getTournaments()); 
		for(Player p : team1.getMembers()) {
			assertEquals("The tournament has not been correctly removed to player !", tournaments, p.getTournaments()); 
		}
	}

	/**
	 * Test method for {@link tournament.Team#Team(java.lang.String)}.
	 */
	@Test
	public void testTeamString() {
		assertEquals("The name of Team is not correct !", nomTeam, team.getName()); 
		assertEquals("The name of Team1 is not correct !", nomTeam1, team1.getName());

		assertEquals("The list of games for Team is incorrect !", expectedGames, team.getGames());
		assertEquals("The list of games for Team1 is incorrect !", expectedGames, team1.getGames());

		assertEquals("The preferred game for Team is incorrect !", null, team.getPreferredGame());
		assertEquals("The preferred game for Team1 is incorrect !", null, team.getPreferredGame());
	}

	/**
	 * Test method for {@link tournament.Team#Team(java.lang.String, tournament.Game)}.
	 */
	@Test
	public void testTeamStringGame() {
		assertEquals("The name of Team2 is not correct !", nomTeam2, team2.getName());
		assertEquals("The name of Team3 is not correct !", nomTeam3, team3.getName());
		
		expectedGames.put(game2, 0);
		assertEquals("The list of games for Team2 is incorrect !", expectedGames, team2.getGames());
		
		expectedGames.remove(game2);
		expectedGames.put(game3, 0);
		assertEquals("The list of games for Team3 is incorrect !", expectedGames, team3.getGames());
		
		assertEquals("The preferred game for Team2 is incorrect !", null, team.getPreferredGame());
		assertEquals("The preferred game for Team3 is incorrect !", null, team.getPreferredGame());
	}

	/**
	 * Test method for {@link tournament.Team#addMember(tournament.Player)}.
	 */
	@Test
	public void testAddMember() {
		team.removeMember(player);
		team.removeMember(player1);
		listPlayers.add(player3);
		team.addMember(player3);
		assertEquals("The player has not been added !", listPlayers, team.getMembers()); 
		
		team3.addMember(player); 
		listPlayers.remove(player3);
		listPlayers.add(player);
		listPlayers.add(player6);
		listPlayers.add(player7);
		
		assertEquals("The player has not been added !", listPlayers, team3.getMembers()); 
	}

	/**
	 * Test method for {@link tournament.Team#removeMember(tournament.Player)}.
	 */
	@Test
	public void testRemoveMember() {
		team.removeMember(player);
		listPlayers.add(player1);
		assertEquals("The player has not been removed !", listPlayers, team.getMembers()); 
		
		team3.addMember(player1); 
		team3.addMember(player5);
		listPlayers.add(player7);
		
		team3.removeMember(player6);
		team3.removeMember(player5);
		assertEquals("The players have not been removed !", listPlayers, team3.getMembers()); 
		
	}

	/**
	 * Test method for {@link tournament.Team#getMembers()}.
	 */
	@Test
	public void testGetMembers() {
		team.removeMember(player);
		team.removeMember(player1);
		assertEquals("The list returned is not correct !", listPlayers, team.getMembers()); 
		
		listPlayers.add(player4); 
		listPlayers.add(player5);

		assertEquals("The list returned is not correct !", listPlayers, team2.getMembers()); 
		
	}

	/**
	 * Test method for {@link tournament.Team#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		assertEquals("The teams must not be equals", false, team.equals(team1)); 
		
		team1.setName(nomTeam);
		
		assertEquals("The teams must not be equals", false, team.equals(team1)); 
		
		team1.removeMember(player2);
		team1.removeMember(player3);
		
		assertEquals("The teams must not be equals", false, team.equals(team1)); 
		
		team1.addMember(player);
		team1.addMember(player1);
		
		assertEquals("The teams must be equals", true, team.equals(team1)); 
	}
}
