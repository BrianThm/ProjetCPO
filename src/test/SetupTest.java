/**
 * 
 */
package test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;

import org.junit.Before;

import controller.Controller;
import controller.exceptions.GameAlreadyExistsException;
import controller.exceptions.PlayerAlreadyExistsException;
import tournament.DoubleElimination;
import tournament.Game;
import tournament.Participant;
import tournament.Player;
import tournament.SimpleElimination;
import tournament.Team;
import tournament.Tournament;


/**
 * @author Group
 * @version 1.4
 * Class that permit to gathered initialization of 
 * many variables for class tests 
 *
 */
public abstract class SetupTest {
	
	protected Player player;
	protected Player player1;
	protected Player player2;
	protected Player player3;
	protected Player player4;
	protected Player player5;
	protected Player player6;
	protected Player player7;
	
	protected String pseudo;
	protected String pseudo1;
	protected String pseudo2;
	protected String pseudo3;
	protected String pseudo4; 
	protected String pseudo5;
	protected String pseudo6;
	protected String pseudo7;
	
	protected String name;
	protected String name1;
	protected String name2;
	protected String name3;
	
	protected String firstname;
	protected String firstname1;
	protected String firstname2;
	protected String firstname3;
	
	protected String nomTeam; 
	protected String nomTeam1; 
	protected String nomTeam2; 
	protected String nomTeam3; 
	
	protected Game game;
	protected Game game1;
	protected Game game2;
	protected Game game3;

	protected Team team;
	protected Team team1;
	protected Team team2;
	protected Team team3;
	
	protected Tournament tournament;
	protected Tournament tournament1;
	protected Tournament tournament2;
	protected Tournament tournament3;
	protected Tournament tournament4;
	protected Tournament tournament5;
	
	protected Controller controller;
	protected Controller controller1;
	
	protected String location; 
	protected String location1; 
	protected String location2;
	protected String location3;
	
	protected ArrayList<Participant> participants;
	protected Set<Tournament> tournaments;
	
	/**
	 * Class SetUp that initialize variables for all the test classes.
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		controller = new Controller();
		controller1 = new Controller();
		
		game = new Game("Overwatch");
		game1 = new Game("CS:GO",new ImageIcon("Image test.jpg"));
		game2 = new Game("PUBG");
		game3 = new Game("Smash");
		
		nomTeam = "El Mexico";
		nomTeam1 = "Sunny";
		nomTeam2 = "TheJeans"; 
		nomTeam3 = "CampingTeam";
		
		team = new Team(nomTeam);
		team1 = new Team(nomTeam1);
		team2 = new Team(nomTeam2,game2);
		team3 = new Team(nomTeam3,game3);
		
		location = "Paris"; 
		location1 = "New York"; 
		location2 = "Toulouse"; 
		location3 = "Nantes";
		
		tournament = new SimpleElimination(game);
		tournament1 = new SimpleElimination(game1);
		tournament2 = new SimpleElimination(game2, location2); 
		tournament3 = new SimpleElimination(game3,location3);
		tournament4 = new DoubleElimination(game);
		tournament5 = new DoubleElimination(game1, location3);
		
		
		participants = new ArrayList<Participant>();
		
		pseudo = "nono23";
		pseudo1 = "Elareron";
		pseudo2 = "Laxul";
		pseudo3 = "ElMojito";
		pseudo4 = "Sanchez";
		pseudo5 = "Miguel";
		pseudo6 = "Francis";
		pseudo7 = "Patrick";
		
		name = "Martin";
		name1 = "Delaunay";
		name2 = "Dupont";
		name3 = "Dupont";
		
		firstname = "Bruno";
		firstname1 = "Gabriel";
		firstname2 = "Renaud";
		firstname3 = "Renaud";
		
		player = new Player(pseudo);
		player1 = new Player(pseudo1,game1);
		player2 = new Player(firstname2, name2 ,pseudo2);
		player3 = new Player(firstname3, name3, pseudo3, game3);
		player4 = new Player(pseudo4);
		player5 = new Player(pseudo5);
		player6 = new Player(pseudo6);
		player7 = new Player(pseudo7);
		
		participants.add(player);
		participants.add(player1);
		participants.add(player2);
		participants.add(player3);
		participants.add(player4);
		participants.add(player5);
		participants.add(player6);
		participants.add(player7);	
		participants.add(team);
		participants.add(team1);
		participants.add(team2);
		participants.add(team3);
		
		team.addMember(player);
		team.addMember(player1);
		
		team1.addMember(player2);
		team1.addMember(player3);
		
		team2.addMember(player4);
		team2.addMember(player5);
		
		team3.addMember(player6);
		team3.addMember(player7);
		
		tournaments = new HashSet<Tournament>();
		tournaments.add(tournament); 
		tournaments.add(tournament1);
		
		controller.addGame(game);
		controller.addGame(game1);
		controller.addGame(game2);
		
		controller.addPlayer(player);
		controller.addPlayer(player1);
		controller.addPlayer(player2);
		controller.addPlayer(player3);
		
		controller.addTeam(team1);
		controller.addTeam(team2);
		controller.addTeam(team);
		
	}

}
