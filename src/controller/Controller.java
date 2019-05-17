package controller;

import java.util.HashSet;
import java.util.Set;

import tournament.Game;
import tournament.Participant;
import tournament.Tournament;

/**
 * A controller of tournaments.
 * @author Group
 * @version 1.0
 */
public class Controller {
	
	private Set<Tournament> tournaments;
	private Set<Game> games;
	private Set<Participant> participants;
	
	/**
	 * Create a new controller of tournaments.
	 */
	public Controller() {
		this.tournaments = new HashSet<Tournament>();
		this.games = new HashSet<Game>();
		this.participants = new HashSet<Participant>();
	}
	
	/**
	 * Add a tournament in order to save it and use it later.
	 * @param tournament The tournament saved.
	 */
	public void addTournament(Tournament tournament) {
		assert tournament != null;
		
		this.tournaments.add(tournament);
	}
	
	/**
	 * Remove a tournament from the tournaments saved. 
	 * The tournament is also removed from the tournaments of each participant.
	 * @param tournament The tournament to remove.
	 */
	public void removeTournament(Tournament tournament) {
		assert tournament != null;
		
		for (Participant participant : this.participants) {
			participant.removeTournament(tournament);
		}
		
		this.tournaments.remove(tournament);
	}
	
	/**
	 * Get the tournaments.
	 * @return A set of tournaments.
	 */
	public Set<Tournament> getTournaments() {
		return this.tournaments;
	}
	
	/**
	 * Add a game in order to save it and use it later.
	 * @param game The game saved.
	 */
	public void addGame(Game game) {
		assert game != null;
		
		this.games.add(game);
	}
	
	/**
	 * Remove a game from the games saved. 
	 * A game used in a tournament can't be removed. 
	 * The game is also removed from the games of each participant.
	 * @param game The game to remove.
	 * @throws GameUsedException If the game is used in a tournament.
	 */
	public void removeGame(Game game) throws GameUsedException {
		assert game != null;
		
		for (Tournament tournament : this.tournaments) {
			if (tournament.getGame() == game) {
				throw new GameUsedException();
			}
		}
		
		for (Participant participant : this.participants) {
			participant.removeGame(game);
		}
		
		this.games.remove(game);
	}
	
	/**
	 * Get the games.
	 * @return A set of games.
	 */
	public Set<Game> getGames() {
		return this.games;
	}
	
	/**
	 * Add a participant in order to save it and use it later.
	 * @param participant The participant to save.
	 */
	public void addParticipant(Participant participant) {
		assert participant != null;
		
		this.participants.add(participant);
	}
	
	/**
	 * Remove a participant from the participants saved. 
	 * The participant is also removed from each of the tournaments he played in.
	 * @param participant The participant to remove.
	 */
	public void removeParticipant(Participant participant) {
		assert participant != null;
		
		for (Tournament tournament : this.tournaments) {
			tournament.removeParticipant(participant);
		}
		
		this.participants.remove(participant);
	}
	
	/**
	 * Get the participants.
	 * @return A set of participants.
	 */
	public Set<Participant> getParticipants() {
		return this.participants;
	}
}
