package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import tournament.Participant;
import tournament.exceptions.MatchDrawException;

/**
 * Class which test methods of class Match
 * @author Group
 * @version 1.0
 */
public class MatchTest extends SetupTest {

	/**
	 * Test method for {@link tournament.Match#Match(tournament.Participant, tournament.Participant, tournament.Game)}.
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	@Test
	public void testMatch() throws NoSuchFieldException, SecurityException {
		assertNotEquals("The match is not correctly created!", null, match);
		assertEquals("The first Participant is incorrect!", (Participant)player, match.getParticipant1()); 
		assertEquals("The second Participant is incorrect!", (Participant)player3, match.getParticipant2()); 
		assertEquals("The winner is incorrect", null, match.getWinner());
		assertEquals("The draw is not correctly assigned!", false, match.isDraw());
		
		assertNotEquals("The match is not correctly created!", null, match1);
		assertEquals("the first Participant is incorrect!", (Participant)team1, match1.getParticipant1()); 
		assertEquals("the second Participant is incorrect!", (Participant)team2, match1.getParticipant2()); 
		assertEquals("The winner is incorrect", null, match1.getWinner());
		assertEquals("The draw is not correctly assigned!", false, match1.isDraw());
	}

	/**
	 * Test method for {@link tournament.Match#getParticipant1()}.
	 */
	@Test
	public void testGetParticipant1() {
		assertEquals("The first Participant is incorrect!", (Participant)player, match.getParticipant1()); 
		assertEquals("the first Participant is incorrect!", (Participant)team1, match1.getParticipant1()); 
	}

	/**
	 * Test method for {@link tournament.Match#setParticipant1(tournament.Participant)}.
	 */
	@Test
	public void testSetParticipant1() {
		try {
			match.setParticipant1(team);
			fail("Cannot play a match between a team and a player");
		} catch (AssertionError e) {
			assertEquals("The first Participant is incorrect!", (Participant)player, match.getParticipant1());
			assertEquals("The second Participant is incorrect!", (Participant)player3, match.getParticipant2());
		}
		
		match.setParticipant1(player2);
		assertEquals("The first Participant is incorrect!", (Participant)player2, match.getParticipant1());
		assertEquals("The second Participant is incorrect!", (Participant)player3, match.getParticipant2());
		
		try {
			match1.setParticipant1(team2);
			fail("A participant cannot play a match against itself");
		} catch (AssertionError e) {
			assertEquals("The first Participant is incorrect!", (Participant)team1, match1.getParticipant1());
			assertEquals("The second Participant is incorrect!", (Participant)team2, match1.getParticipant2());
		}
	
		match1.setParticipant1(team);
		assertEquals("The first Participant is incorrect!", (Participant)team, match1.getParticipant1());
		assertEquals("The second Participant is incorrect!", (Participant)team2, match1.getParticipant2());
	}

	/**
	 * Test method for {@link tournament.Match#getParticipant2()}.
	 */
	@Test
	public void testGetParticipant2() {
		assertEquals("The second Participant is incorrect!", (Participant)player3, match.getParticipant2()); 
		assertEquals("the second Participant is incorrect!", (Participant)team2, match1.getParticipant2()); 
	}

	/**
	 * Test method for {@link tournament.Match#setParticipant2(tournament.Participant)}.
	 */
	@Test
	public void testSetParticipant2() {
		try {
			match.setParticipant2(player);
			fail("A participant cannot play a match against itself");
		} catch (AssertionError e) {
			assertEquals("The first Participant is incorrect!", (Participant)player, match.getParticipant1());
			assertEquals("The second Participant is incorrect!", (Participant)player3, match.getParticipant2());
		}
		
		try {
			match.setParticipant2(team2);
			fail("Cannot play a match between a team and a player");
		} catch (AssertionError e) {
			assertEquals("The first Participant is incorrect!", (Participant)player, match.getParticipant1());
			assertEquals("The second Participant is incorrect!", (Participant)player3, match.getParticipant2());
		}
		
		match1.setParticipant2(team);
		assertEquals("The first Participant is incorrect!", (Participant)team1, match1.getParticipant1());
		assertEquals("The second Participant is incorrect!", (Participant)team, match1.getParticipant2());
	}

	/**
	 * Test method for {@link tournament.Match#getScore()}.
	 * @throws MatchDrawException 
	 */
	@Test
	public void testGetScore() throws MatchDrawException {
		int[] expectedScore = new int[2];
		expectedScore[0] = -1;
		expectedScore[1] = -1;
		assertEquals("The score has not been correctly initialize!", match.getScore()[0], expectedScore[0]);
		assertEquals("The score has not been correctly initialize!", match.getScore()[1], expectedScore[1]);
		assertEquals("The score has not been correctly initialize!", match1.getScore()[0], expectedScore[0]);
		assertEquals("The score has not been correctly initialize!", match1.getScore()[1], expectedScore[1]);
		
		expectedScore[0]=1;
		expectedScore[1]=2;
		match.setScore(1,2);
		assertEquals("The score has not been correctly initialize!", match.getScore()[0], expectedScore[0]);
		assertEquals("The score has not been correctly initialize!", match.getScore()[1], expectedScore[1]);
		
		expectedScore[0]=3;
		match1.setScore(3,2);
		assertEquals("The score has not been correctly initialize!", match1.getScore()[0], expectedScore[0]);
		assertEquals("The score has not been correctly initialize!", match1.getScore()[1], expectedScore[1]);
	}

	/**
	 * Test method for {@link tournament.Match#setScore(int, int)}.
	 * @throws MatchDrawException 
	 */
	@Test
	public void testSetScore() throws MatchDrawException {
		int[] expectedScore = new int[2];
		expectedScore[0]=1;
		expectedScore[1]=2;
		match.setScore(1,2);
		assertEquals("The score has not been correctly initialize!", match.getScore()[0], expectedScore[0]);
		assertEquals("The score has not been correctly initialize!", match.getScore()[1], expectedScore[1]);
		assertEquals("The winner has not been correctly set!", player3, match.getWinner());
		
		expectedScore[0]=3;
		expectedScore[1]=0;
		match.setScore(3,0);
		assertEquals("The score has not been correctly initialize!", match.getScore()[0], expectedScore[0]);
		assertEquals("The score has not been correctly initialize!", match.getScore()[1], expectedScore[1]);
		assertEquals("The winner has not been correctly set!", player, match.getWinner());
	}

	/**
	 * Test method for {@link tournament.Match#isPlayed()}.
	 * @throws MatchDrawException 
	 */
	@Test
	public void testIsPlayed() throws MatchDrawException {
		assertEquals("The match has not been played !", false, match.isPlayed());
		assertEquals("The match has not been played !", false, match1.isPlayed());
		
		match.setScore(2, 1);
		assertEquals("The match has not been played !", true, match.isPlayed());
		
		match1.setScore(1, 0);
		assertEquals("The match has not been played !", true, match1.isPlayed());
	}

	/**
	 * Test method for {@link tournament.Match#getWinner()}.
	 * @throws MatchDrawException 
	 */
	@Test
	public void testGetWinner() throws MatchDrawException {
		assertEquals("The winner has not been correctly returned!", null, match.getWinner());
		
		match.setScore(3, 1);
		assertEquals("The winner has not been correctly returned!", player, match.getWinner());
		
		match1.setScore(0, 1);
		assertEquals("The winner has not been correctly returned!", team2, match1.getWinner());
		
		match1.setScore(2, 1);
		assertEquals("The winner has not been correctly returned!", team1, match1.getWinner());
	}

	/**
	 * Test method for {@link tournament.Match#getLooser()}.
	 * @throws MatchDrawException 
	 */
	@Test
	public void testGetLooser() throws MatchDrawException {
		assertEquals("The looser has not been correctly returned!", null, match.getLooser());
		
		match.setScore(3, 1);
		assertEquals("The looser has not been correctly returned!", player3, match.getLooser());
		
		match1.setScore(0, 1);
		assertEquals("The looser has not been correctly returned!", team1, match1.getLooser());
		
		match1.setScore(2, 1);
		assertEquals("The looser has not been correctly returned!", team2, match1.getLooser());
	}

	/**
	 * Test method for {@link tournament.Match#isDraw()}.
	 * @throws MatchDrawException 
	 */
	@Test
	public void testIsDraw() throws MatchDrawException {
		match.setScore(3, 1);
		assertEquals("The Match is not be draw!", false, match.isDraw());
		
		try {
			match.setScore(0, 0);
			fail("MatchDrawException was not thrown");
		} catch (MatchDrawException e) {}
		
		match1.setScore(0, 1);
		assertEquals("The match isn't draw! ", false, match1.isDraw());
		
		try {
			match1.setScore(2, 2);
			fail("MatchDrawException was not thrown");
		} catch (MatchDrawException e) {}
	}

	/**
	 * Test method for {@link tournament.Match#toString()}.
	 * @throws MatchDrawException 
	 */
	@Test
	public void testToString() throws MatchDrawException {
		assertEquals("the match has not been correctly transformed into String!", ""+player.getName()+": -1\n"+player3.getName()+": -1\n", match.toString());
		assertEquals("the match has not been correctly transformed into String!", ""+team1.getName()+": -1\n"+team2.getName()+": -1\n", match1.toString());
		match.setScore(3, 1);
		match1.setScore(1, 5);
		assertEquals("the match has not been correctly transformed into String!", ""+player.getName()+": 3\n"+player3.getName()+": 1\n", match.toString());
		assertEquals("the match has not been correctly transformed into String!", ""+team1.getName()+": 1\n"+team2.getName()+": 5\n", match1.toString());
	}
}
