package test;

import org.junit.*;

import tournament.Player;
import tournament.Team;
import tournament.exceptions.NotEnoughParticipantsException;

public class DoubleEliminationTest extends SetupTest {

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
	}

}
