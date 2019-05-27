package test;

import java.util.HashSet;
import java.util.Set;
import org.junit.*;
import tournament.*;

/**
 * Class which test, with unit test, methods of the 
 * class SimpleElimination
 * @author Romain Tixier
 */
public class TestSimpleElimination {

	private static Game game;
	private Tournament tournament;
	private Set<Participant> participants;
	
	private Team team1;
	private Team team2;
	private Team team3;
	private Team team4;
	
	private Player player1;
	private Player player2;
	private Player player3;
	private Player player4;
	private Player player5;
	private Player player6;
	private Player player7;
	private Player player8;
	
	@Before
	public void setUp() {
		game = new Game("Overwatch");
		tournament = new SimpleElimination(game);
		participants = new HashSet<Participant>();
		
		player1 = new Player("Sanchez");
		player2 = new Player("Miguel");
		player3 = new Player("Bakari");
		player4 = new Player("Bamacko");
		player5 = new Player("Jean-Mi");
		player6 = new Player("Jean-Jean");
		player7 = new Player("Francis");
		player8 = new Player("Patrick");
		
		participants.add(player1);
		participants.add(player2);
		participants.add(player3);
		participants.add(player4);
		participants.add(player5);
		participants.add(player6);
		participants.add(player7);
		participants.add(player8);
		
		team1 = new Team("El Mexico");
		team1.addMember(player1);
		team1.addMember(player2);
		
		team2 = new Team("Sunny");
		team2.addMember(player3);
		team2.addMember(player4);
		
		team3 = new Team("TheJeans");
		team3.addMember(player5);
		team3.addMember(player6);
		
		team4 = new Team("CampingTeam");
		team4.addMember(player7);
		team4.addMember(player8);
	}
	
	@Test
	public void testInit() {
		tournament.initializeMatchs(participants);
	}
}
