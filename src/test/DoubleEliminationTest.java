package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import tournament.Match;
import tournament.Player;
import tournament.Team;
import tournament.exceptions.MatchDrawException;
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
	public void testDrawMatch() throws NotEnoughParticipantsException {
		tournament4.initializeMatchs();
		matchs = tournament4.getMatchs();
		try {
			matchs[4].setScore(4, 3);
			matchs[5].setScore(1, 1);
			fail("Failed to throw MatchDraw exception.");
		} catch (MatchDrawException e) {}
		
		matchs = tournament4.getMatchs();
		assertEquals("nono23", matchs[4].getWinner().getName());
		assertEquals(true, matchs[4].isPlayed());
		assertEquals(null, matchs[5].getWinner());
		assertEquals(false, matchs[5].isDraw());
		assertEquals(false, matchs[5].isPlayed());
		assertEquals("nono23", matchs[2].getParticipant1().getName());
		assertEquals("?", matchs[2].getParticipant2().getName());
		
		matchs[5].setScore(0, 2);
		matchs = tournament4.getMatchs();

		assertEquals("nono23", matchs[2].getParticipant1().getName());
		assertEquals("ElMojito", matchs[2].getParticipant2().getName());
		assertEquals("Elareron", matchs[8].getParticipant1().getName());
		assertEquals("Laxul", matchs[8].getParticipant2().getName());
		try {
			matchs[8].setScore(2, 2);
			fail("Failed to throw MatchDraw exception.");
		} catch (MatchDrawException e) {}
		
		matchs = tournament4.getMatchs();
		assertEquals(null, matchs[8].getWinner());
		assertEquals(false, matchs[8].isPlayed());
		assertEquals(false, matchs[8].isDraw());
		
		matchs[8].setScore(2, 4);
		matchs = tournament4.getMatchs();
		assertEquals("Laxul", matchs[8].getWinner().getName());
		assertEquals("Elareron", matchs[8].getLooser().getName());
		assertEquals(true, matchs[8].isPlayed());
		assertEquals(false, matchs[8].isDraw());
		assertEquals("Laxul", matchs[10].getParticipant1().getName());
		assertEquals("?", matchs[10].getParticipant2().getName());
	}
	
	@Test
	public void testUpdate1() throws NotEnoughParticipantsException {
		tournament4.initializeMatchs();
		matchs = tournament4.getMatchs();
		matchs[4].setScore(1,0);
		matchs[5].setScore(0,2);
		matchs[6].setScore(3,2);
		matchs[7].setScore(4,1);
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
		matchs[8].setScore(0,6);
		matchs[9].setScore(3,6);
		matchs = tournament4.getMatchs();
		assertEquals("Laxul", matchs[10].getParticipant1().getName());
		assertEquals("Patrick", matchs[11].getParticipant1().getName());
		
		// Second matchs in the winner's bracket
		matchs[2].setScore(2,0);
		matchs[3].setScore(5,7);
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
		matchs[10].setScore(0,9);
		matchs[11].setScore(1,0);
		matchs = tournament4.getMatchs();
		// Match to know who will meet the looser of the winner's bracket final
		assertEquals("ElMojito", matchs[12].getParticipant1().getName());
		assertEquals("Patrick", matchs[12].getParticipant2().getName());
		
		matchs[12].setScore(1, 4);
		matchs[1].setScore(5, 0);
		matchs = tournament4.getMatchs();
		assertEquals("Patrick", matchs[13].getParticipant1().getName());
		assertEquals("Francis", matchs[13].getParticipant2().getName());
		
		matchs[13].setScore(2, 1);
		matchs = tournament4.getMatchs();
		assertEquals("Patrick", matchs[14].getParticipant1().getName());
		assertEquals("nono23", matchs[14].getParticipant2().getName());
	}
	
	@Test
	public void testFinale_1() throws NotEnoughParticipantsException {
		testUpdate3();
		matchs = tournament4.getMatchs();
		// The winner of the winner's bracket win the finale
		matchs[14].setScore(2, 5);
		
		matchs = tournament4.getMatchs();
		assertEquals("nono23", matchs[14].getWinner().getName());
		assertEquals("Patrick", matchs[14].getLooser().getName());
		assertEquals("nono23", tournament4.getWinner().getName());
		assertEquals(null, matchs[15]);

	}
	
	@Test
	public void testFinale_2() throws NotEnoughParticipantsException {
		testUpdate3();
		matchs = tournament4.getMatchs();
		// The winner of the looser's bracket win the finale
		// => The match have to be replay again
		matchs[14].setScore(3, 1);
		matchs = tournament4.getMatchs();
		assertEquals("Patrick", matchs[14].getWinner().getName());
		assertEquals("nono23", matchs[14].getLooser().getName());
		assertEquals("Patrick", matchs[15].getParticipant1().getName());
		assertEquals("nono23", matchs[15].getParticipant2().getName());
		
		matchs[15].setScore(1, 2);
		matchs = tournament4.getMatchs();
		assertEquals("nono23", matchs[15].getWinner().getName());
		assertEquals("nono23", tournament4.getWinner().getName());
	}
	
	@Test
	public void fullTournamentWithTeams() throws NotEnoughParticipantsException {
		testInitWithTeams();
		matchs = tournament5.getMatchs();
		
		matchs[2].setScore(2, 0);
		matchs[3].setScore(4, 1);
		matchs = tournament5.getMatchs();
		assertEquals("El Mexico", matchs[1].getParticipant1().getName());
		assertEquals("TheJeans", matchs[1].getParticipant2().getName());
		assertEquals("Sunny", matchs[4].getParticipant1().getName());
		assertEquals("CampingTeam", matchs[4].getParticipant2().getName());
		
		matchs[4].setScore(7, 1);
		matchs = tournament5.getMatchs();
		assertEquals("Sunny", matchs[5].getParticipant1().getName());
		assertEquals("?", matchs[5].getParticipant2().getName());
		
		matchs[1].setScore(2, 3);
		matchs = tournament5.getMatchs();
		assertEquals("Sunny", matchs[5].getParticipant1().getName());
		assertEquals("El Mexico", matchs[5].getParticipant2().getName());
		assertEquals("TheJeans", matchs[6].getParticipant2().getName());
		assertEquals("?", matchs[6].getParticipant1().getName());
		
		matchs[5].setScore(1, 0);
		matchs = tournament5.getMatchs();
		assertEquals("Sunny", matchs[6].getParticipant1().getName());
		
		matchs[6].setScore(4, 3);
		matchs = tournament5.getMatchs();
		// The winner of winner's bracket have lost in finale
		// => We replay the match again.
		assertEquals("Sunny", matchs[7].getParticipant1().getName());
		assertEquals("TheJeans", matchs[7].getParticipant2().getName());
		assertEquals(null, tournament5.getWinner());
		
		matchs[7].setScore(0, 11);
		assertEquals("TheJeans", tournament5.getWinner().getName());
	}
}
