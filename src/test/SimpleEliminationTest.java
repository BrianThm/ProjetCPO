package test;

import static org.junit.Assert.*;

import org.junit.*;
import tournament.*;
import tournament.exceptions.*;

/**
 * Class which test, with unit test, methods of the 
 * class SimpleElimination
 * @author Romain Tixier
 */
public class SimpleEliminationTest extends SetupTest{

	Match[] matchs;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		for (int i=0; i<participants.size(); i++) {
			if (participants.get(i) instanceof Player) {
				tournament.addParticipant(participants.get(i));
			} else if (participants.get(i) instanceof Team) {
				tournament1.addParticipant(participants.get(i));
			}
		}
		
		team.addMember(player);
		team.addMember(player1);
		team1.addMember(player2);
		team1.addMember(player3);
		team2.addMember(player4);
		team2.addMember(player5);
		team3.addMember(player6);
		team3.addMember(player7);
	}
	
	@Test
	public void testInitWithPlayers() throws NotEnoughParticipantsException {
		tournament.initializeMatchs();
		matchs = tournament.getMatchs();
		
		assertEquals(8, tournament.getParticipants().size());
		for (int i=0; i<4; i++) {
			assertEquals(null, matchs[i]);
		}
		for (int i=4; i<8; i++) {
			assertNotEquals(null, matchs[i]);
		}
		
		assertEquals("nono23", matchs[4].getParticipant1().getName());
		assertEquals("Elareron", matchs[4].getParticipant2().getName());
		assertEquals("Laxul", matchs[5].getParticipant1().getName());
		assertEquals("ElMojito", matchs[5].getParticipant2().getName());
		assertEquals("Sanchez", matchs[6].getParticipant1().getName());
		assertEquals("Miguel", matchs[6].getParticipant2().getName());
		assertEquals("Francis", matchs[7].getParticipant1().getName());
		assertEquals("Patrick", matchs[7].getParticipant2().getName());
	}
	
	@Test
	public void testInitWithTeams() throws NotEnoughParticipantsException {
		tournament1.initializeMatchs();
		matchs = tournament1.getMatchs();
		
		assertEquals(4, tournament1.getParticipants().size());
		for (int i=0; i<2; i++) {
			assertEquals(null, matchs[i]);
		}
		for (int i=2; i<4; i++) {
			assertNotEquals(null, matchs[i]);
		}
		
		assertEquals("El Mexico", matchs[2].getParticipant1().getName());
		assertEquals("Sunny", matchs[2].getParticipant2().getName());
		assertEquals("TheJeans", matchs[3].getParticipant1().getName());
		assertEquals("CampingTeam", matchs[3].getParticipant2().getName());
	}
	
	@Test
	public void testWithoutEnoughtPlayers() {
		tournament.removeParticipant(tournament.getParticipants().remove(0));
		try {
			tournament.initializeMatchs();
			fail("FAILED to throw NotEnoughtParticipants exception.");
		} catch (NotEnoughParticipantsException e) {}
	}
	
	@Test
	public void testWithoutEnoughtTeams() {
		tournament.addParticipant(new Player("j4cK"));
		try {
			tournament.initializeMatchs();
			fail("FAILED to throw NotEnoughtParticipants exception.");
		} catch (NotEnoughParticipantsException e) {}
	}
	
	@Test
	public void testUpdateTree() throws NotEnoughParticipantsException {
		tournament.initializeMatchs();
		matchs = tournament.getMatchs();
		
		matchs[4].endGame(matchs[4].getParticipant1());
		matchs[7].endGame(matchs[7].getParticipant2());
		assertEquals("nono23", matchs[2].getParticipant1().getName());
		assertEquals("?", matchs[2].getParticipant2().getName());
		assertEquals("?", matchs[3].getParticipant1().getName());
		assertEquals("Patrick", matchs[3].getParticipant2().getName());
		
		matchs[5].endGame(matchs[5].getParticipant2());
		matchs[6].endGame(matchs[6].getParticipant1());
		assertEquals("nono23", matchs[2].getParticipant1().getName());
		assertEquals("ElMojito", matchs[2].getParticipant2().getName());
		assertEquals("Sanchez", matchs[3].getParticipant1().getName());
		assertEquals("Patrick", matchs[3].getParticipant2().getName());
		
		matchs[2].endGame(matchs[2].getParticipant2());
		matchs[3].endGame(matchs[3].getParticipant1());
		assertEquals("ElMojito", matchs[1].getParticipant1().getName());
		assertEquals("Sanchez", matchs[1].getParticipant2().getName());
	}
	
	@Test
	public void testFinale() throws NotEnoughParticipantsException {
		testUpdateTree();
		matchs = tournament.getMatchs();
		
		matchs[1].endGame(matchs[1].getParticipant1());
		assertEquals(null, matchs[0]);
		assertEquals("ElMojito", tournament.getWinner().getName());
	}
}
