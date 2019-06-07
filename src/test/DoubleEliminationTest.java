package test;

import static org.junit.Assert.*;
import org.junit.*;

import tournament.Match;
import tournament.Player;
import tournament.Team;
import tournament.exceptions.NotEnoughParticipantsException;

public class DoubleEliminationTest extends SetupTest {
	
	Match[] matchs;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		// Player to tournament4 and teams to tournament5
		for (int i=0; i<participants.size(); i++) {
			if (participants.get(i) instanceof Player) {
				tournament4.addParticipant(participants.get(i));
			} else if (participants.get(i) instanceof Team) {
				tournament5.addParticipant(participants.get(i));
			}
		}
	}
	
	@Test
	public void testInitWithPlayers() throws NotEnoughParticipantsException {
		tournament4.initializeMatchs();
		matchs = tournament4.getMatchs();
		
		assertEquals(16, matchs.length);
		assertEquals(8, tournament4.getParticipants().size());
		// Winner's bracket
		for (int i=0; i<4; i++) {
			assertEquals(null, matchs[i]);
		}
		for (int i=4; i<8; i++) {
			assertNotEquals(null, matchs[i]);
		}
		// Looser's bracket
		for (int i=8; i<16; i++) {
			assertEquals(null, matchs[i]);
		}
	}
	
	@Test
	public void testInitWithTeams() throws NotEnoughParticipantsException {
		tournament5.initializeMatchs();
		matchs = tournament5.getMatchs();
		
		assertEquals(8, matchs.length);
		assertEquals(4, tournament5.getParticipants().size());
		// Winner's bracket
		for (int i=0; i<2; i++) {
			assertEquals(null, matchs[i]);
		}
		for (int i=2; i<4; i++) {
			assertNotEquals(null, matchs[i]);
		}
		// Looser's bracket
		for (int i=4; i<8; i++) {
			assertEquals(null, matchs[i]);
		}
	}

	@Test
	public void testWithoutEnoughtPlayers() {
		tournament4.removeParticipant(tournament4.getParticipants().remove(0));
		try {
			tournament4.initializeMatchs();
			fail("FAILED to throw NotEnoughtParticipants exception.");
		} catch (NotEnoughParticipantsException e) {}
	}
	
	@Test
	public void testWithoutEnoughtTeams() {
		tournament5.addParticipant(new Team("LosPollos"));
		try {
			tournament5.initializeMatchs();
			fail("FAILED to throw NotEnoughtParticipants exception.");
		} catch (NotEnoughParticipantsException e) {}
	}
	
	@Test
	public void testUpdate1() throws NotEnoughParticipantsException {
		tournament4.initializeMatchs();
		matchs = tournament4.getMatchs();
		matchs[4].endGame(matchs[4].getParticipant1());
		matchs[5].endGame(matchs[5].getParticipant2());
		matchs[6].endGame(matchs[6].getParticipant1());
		matchs[7].endGame(matchs[7].getParticipant1());
		matchs = tournament4.getMatchs();
		// Winners
		assertEquals("nono23", matchs[2].getParticipant1().getName());
		assertEquals("ElMojito", matchs[2].getParticipant2().getName());
		assertEquals("Sanchez", matchs[3].getParticipant1().getName());
		assertEquals("Francis", matchs[3].getParticipant2().getName());
		// Loosers
		assertEquals("Elareron", matchs[8].getParticipant1().getName());
		assertEquals("Laxul", matchs[8].getParticipant2().getName());
		assertEquals("Miguel", matchs[9].getParticipant1().getName());
		assertEquals("Patrick", matchs[9].getParticipant2().getName());
	}
	
	@Test
	public void testUpdate2() throws NotEnoughParticipantsException {
		testUpdate1();
		matchs = tournament4.getMatchs();
		// First matchs between the loosers
		matchs[8].endGame(matchs[8].getParticipant2());
		matchs[9].endGame(matchs[9].getParticipant2());
		matchs = tournament4.getMatchs();
		assertEquals("Laxul", matchs[10].getParticipant1().getName());
		assertEquals("Patrick", matchs[11].getParticipant1().getName());
		
		// Second matchs in the winner's bracket
		matchs[2].endGame(matchs[2].getParticipant1());
		matchs[3].endGame(matchs[3].getParticipant2());
		matchs = tournament4.getMatchs();
			// Final of winner's bracket
		assertEquals("nono23", matchs[1].getParticipant1().getName());
		assertEquals("Francis", matchs[1].getParticipant2().getName());
			// 2nd turn of looser's bracket
			// winners of looser's bracket vs loosers of winner's bracket
		assertEquals("Laxul", matchs[10].getParticipant1().getName());
		assertEquals("ElMojito", matchs[10].getParticipant2().getName());
		assertEquals("Patrick", matchs[11].getParticipant1().getName());
		assertEquals("Sanchez", matchs[11].getParticipant2().getName());
	}
	
	@Test
	public void testUpdate3() throws NotEnoughParticipantsException {
		testUpdate2();
		matchs = tournament4.getMatchs();
		// Ending the 2nd turn of looser's bracket
		matchs[10].endGame(matchs[10].getParticipant2());
		matchs[11].endGame(matchs[11].getParticipant1());
		matchs = tournament4.getMatchs();
		// Match to know who will meet the looser of the winner's bracket final
		assertEquals("ElMojito", matchs[12].getParticipant1().getName());
		assertEquals("Patrick", matchs[12].getParticipant2().getName());
		
		// NOT IMPLEMENTED YET
	}
}
