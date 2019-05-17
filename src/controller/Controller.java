package controller;
import java.util.HashSet;
import java.util.Set;
import tournament.Game;
import tournament.Participant;
import tournament.Tournament;

public class Controller {
	
	private Set<Tournament> tournaments;
	private Set<Game> games;
	private Set<Participant> participants;
	
	public Controller() {
		this.tournaments = new HashSet<Tournament>();
		this.games = new HashSet<Game>();
		this.participants = new HashSet<Participant>();
	}
	
	public void addTournament(Tournament tournament) {
		this.tournaments.add(tournament);
	}
	
	public void removeTournament(Tournament tournament) {
		this.tournaments.remove(tournament);
	}
	
	public Set<Tournament> getTournaments() {
		return this.tournaments;
	}
	
	public void addGame(Game game) {
		this.games.add(game);
	}
	
	public void removeGame(Game game) {
		this.games.remove(game);
	}
	
	public Set<Game> getGames() {
		return this.games;
	}
	
	public void addParticipant(Participant participant) {
		this.participants.add(participant);
	}
	
	public void removeParticipant(Participant participant) {
		this.participants.remove(participant);
	}
	
	public Set<Participant> getParticipants() {
		return this.participants;
	}
}
