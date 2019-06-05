/**
 * 
 */
package test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import tournament.Participant;

/**
 * @author Group
 * @version 1.0 
 * Class that test the interface Tournament
 *
 */
public class TournamentTest extends SetupTest {

	/**
	 * Test method for {@link tournament.Tournament#Tournament(tournament.Game)}.
	 */
	@Test
	public void testTournamentGame() {
		assertEquals("The game is incorrect for tournament!", game, tournament.getGame());
		assertEquals("The location is incorrect for tournament !","",tournament.getLocation()); 
		assertEquals("The location is incorrect for tournament1 !","",tournament1.getLocation()); 
		assertEquals("The game is incorrect for tournament1!", game1, tournament1.getGame());
	}

	/**
	 * Test method for {@link tournament.Tournament#Tournament(tournament.Game, java.lang.String)}.
	 */
	@Test
	public void testTournamentGameString() {
		assertEquals("The game is incorrect for tournament3 ! ", game3, tournament3.getGame());
		assertEquals("The location is incorrect for tournament3 !",location3,tournament3.getLocation()); 
		assertEquals("The game is incorrect for tournament2 ! ", game2, tournament2.getGame());
		assertEquals("The location is incorrect for tournament2 !",location2,tournament2.getLocation()); 		
	}

	/**
	 * Test method for {@link tournament.Tournament#getGame()}.
	 */
	@Test
	public void testGetGame() {
		assertEquals("The game is incorrect for tournament!", game, tournament.getGame());
		assertEquals("The game is incorrect for tournament1!", game1, tournament1.getGame());
		assertEquals("The game is incorrect for tournament2!", game2, tournament2.getGame());
		assertEquals("The game is incorrect for tournament3!", game3, tournament3.getGame());
	}

	/**
	 * Test method for {@link tournament.Tournament#setGame(tournament.Game)}.
	 */
	@Test
	public void testSetGame() {
		tournament.setGame(game2);
		tournament1.setGame(game);
		tournament2.setGame(game2);
		tournament3.setGame(game1);
		assertEquals("The game is incorrect for tournament!", game2, tournament.getGame());
		assertEquals("The game is incorrect for tournament1!", game, tournament1.getGame());
		assertEquals("The game is incorrect for tournament2!", game2, tournament2.getGame());
		assertEquals("The game is incorrect for tournament3!", game1, tournament3.getGame());
	}

	/**
	 * Test method for {@link tournament.Tournament#getLocation()}.
	 */
	@Test
	public void testGetLocation() {
		assertEquals("The location is incorrect for tournament !","", tournament.getLocation()); 
		assertEquals("The location is incorrect for tournament1 !","", tournament1.getLocation()); 
		assertEquals("The location is incorrect for tournament2 !", location2, tournament2.getLocation()); 
		assertEquals("The location is incorrect for tournament3 !", location3, tournament3.getLocation()); 
	}

	/**
	 * Test method for {@link tournament.Tournament#setLocation(java.lang.String)}.
	 */
	@Test
	public void testSetLocation() {
		tournament.setLocation(location);
		tournament1.setLocation(location1);
		tournament2.setLocation(location3);
		tournament3.setLocation(location3);
		assertEquals("The location is incorrect for tournament !",location, tournament.getLocation()); 
		assertEquals("The location is incorrect for tournament1 !",location1, tournament1.getLocation()); 
		assertEquals("The location is incorrect for tournament2 !", location3, tournament2.getLocation()); 
		assertEquals("The location is incorrect for tournament3 !", location3, tournament3.getLocation()); 
	}

	/**
	 * Test method for {@link tournament.Tournament#addParticipant(tournament.Participant)}.
	 */
	@Test
	public void testAddParticipant() {
		Set<Participant> participantsTournament = new HashSet<Participant>();
		assertEquals("Participants for tournament1 have not been add ! ", 0, tournament1.getParticipants().size());
		assertEquals("Participants for tournament1 have not been correctly initialize! ", participantsTournament, tournament1.getParticipants());
		tournament.addParticipant(participants.get(0));
		tournament.addParticipant(participants.get(3));
		tournament.addParticipant(participants.get(8)); 
		assertEquals("Participants for tournament have not been add ! ", 3, tournament.getParticipants().size()); 
		participantsTournament.add(participants.get(0)); 
		participantsTournament.add(participants.get(3)); 
		participantsTournament.add(participants.get(8)); 
		assertEquals("Participants for tournament have not been correctly initialize! ", participantsTournament, tournament.getParticipants());
	}

	/**
	 * Test method for {@link tournament.Tournament#removeParticipant(tournament.Participant)}.
	 */
	@Test
	public void testRemoveParticipant() {
		tournament.addParticipant(participants.get(0));
		tournament.addParticipant(participants.get(3));
		tournament.addParticipant(participants.get(8)); 
		Set<Participant> participantsTournament = new HashSet<Participant>();
		participantsTournament.add(participants.get(0)); 
		participantsTournament.add(participants.get(3)); 
		
		tournament.removeParticipant(participants.get(8));
		assertEquals("Participants for tournament have not been remove! ", 2, tournament.getParticipants().size()); 
		assertEquals("Participants for tournament are not good! ", participantsTournament, tournament.getParticipants());
		
		tournament.removeParticipant(participants.get(0));
		participantsTournament.remove(participants.get(0)); 
		assertEquals("Participants for tournament have not been remove! ", 1, tournament.getParticipants().size()); 
		assertEquals("Participants for tournament are not good! ", participantsTournament, tournament.getParticipants());
		
		tournament.removeParticipant(participants.get(3));
		participantsTournament.remove(participants.get(3)); 
		assertEquals("Participants for tournament have not been remove! ", 0, tournament.getParticipants().size()); 
		assertEquals("Participants for tournament are not good! ", participantsTournament, tournament.getParticipants());
		
	}

	/**
	 * Test method for {@link tournament.Tournament#getParticipants()}.
	 */
	@Test
	public void testGetParticipants() {
		assertEquals("Participants for tournament have not been initialize correctly!",new HashSet<Participant>(),tournament.getParticipants());
		Set<Participant> participantsTournament1 = new HashSet<Participant>();
		participantsTournament1.add(participants.get(8)); 
		participantsTournament1.add(participants.get(10)); 
		
		tournament1.addParticipant(participants.get(8));
		tournament1.addParticipant(participants.get(10));
		assertEquals("Participants for tournament have not been initialize correctly!",participantsTournament1,tournament1.getParticipants());
	}

}
