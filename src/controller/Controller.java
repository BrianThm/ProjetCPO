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
	
	public Set<Tournament> getTournaments() {
		return this.tournaments;
	}
	
	public Set<Game> getGames() {
		return this.games;
	}
	
	public Set<Participant> getParticipants() {
		return this.participants;
	}
}
