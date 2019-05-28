package test;


import org.junit.*;

import tournament.*;
import tournament.exceptions.*;

/**
 * Class which test, with unit test, methods of the 
 * class SimpleElimination
 * @author Romain Tixier
 */
public class SimpleEliminationTest extends TestSetup{


	@Before
	public void setUp() throws Exception {
		super.setUp();
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
	public void testInit() throws NotEnoughParticipantsException {
		tournament.initializeMatchs(participants);
	}
}
