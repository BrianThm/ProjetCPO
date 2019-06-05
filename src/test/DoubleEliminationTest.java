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

}
