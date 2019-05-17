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
	
	// TODO write javadoc
	
	public Controller() {
		this.tournaments = new HashSet<Tournament>();
		this.games = new HashSet<Game>();
		this.participants = new HashSet<Participant>();
	}
	
	public void addTournament(Tournament tournament) {
		assert tournament != null;
		
		this.tournaments.add(tournament);
	}
	
	public void removeTournament(Tournament tournament) {
		assert tournament != null;
		
		this.tournaments.remove(tournament);
	}
	
	public Set<Tournament> getTournaments() {
		return this.tournaments;
	}
	
	public void addGame(Game game) {
		assert game != null;
		
		this.games.add(game);
	}
	
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
	
	public Set<Game> getGames() {
		return this.games;
	}
	
	public void addParticipant(Participant participant) {
		assert participant != null;
		
		this.participants.add(participant);
	}
	
	public void removeParticipant(Participant participant) {
		assert participant != null;
		
		this.participants.remove(participant);
	}
	
	public Set<Participant> getParticipants() {
		return this.participants;
	}
}
