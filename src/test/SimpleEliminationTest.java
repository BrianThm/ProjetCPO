package test;

import java.util.HashSet;

import org.junit.*;


import tournament.*;
import tournament.exceptions.*;

/**
 * Class which test, with unit test, methods of the 
 * class SimpleElimination
 * @author Romain Tixier
 */
public class SimpleEliminationTest extends SetupTest{

	HashSet<Participant> setPlayer;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		for (int i = 0; i<8; i++) {
			setPlayer.add(participants.get(i)); 
		}
		
	}
	
	@Test
	public void testInit() throws NotEnoughParticipantsException {
		tournament.initializeMatchs(setPlayer);
	}
}
