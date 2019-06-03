package controller;

import java.util.HashSet;
import java.util.Set;

import controller.exceptions.*;
import tournament.*;
import tournament.exceptions.*;

/**
 * A controller of tournaments.
 * @author Group
 * @version 1.0
 */
public class Controller {
	
	private Set<Tournament> tournaments;
	private Set<Game> games;
	private Set<Player> players;
	private Set<Team> teams;
	
	/**
	 * Create a new controller of tournaments.
	 */
	public Controller() {
		this.tournaments = new HashSet<Tournament>();
		this.games = new HashSet<Game>();
		this.players = new HashSet<Player>();
		this.teams = new HashSet<Team>();
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
		
		for (Player player : this.players) {
			player.removeTournament(tournament);
		}
		for (Team team : this.teams) {
			team.removeTournament(tournament);
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
	 * Get the number of tournaments.
	 * @return The number of tournaments.
	 */
	public int getNbTournaments() {
		return this.tournaments.size();
	}
	
	/**
	 * Add a game in order to save it and use it later.
	 * @param game The game saved.
	 * @throws GameAlreadyExistsException If the game already exists
	 */
	public void addGame(Game game) throws GameAlreadyExistsException {
		assert game != null;
		
		for (Game g : this.games) {
			if (game.equals(g)) {
				throw new GameAlreadyExistsException();
			}
		}
		
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
		
		for (Player player : this.players) {
			try {
				player.removeGame(game);
			} catch (GamePlayedException e) {
				// This can't happen because there is no tournament with
				// this game.
			}
		}
		for (Team team : this.teams) {
			try {
				team.removeGame(game);
			} catch (GamePlayedException e) {
				// This can't happen because there is no tournament with
				// this game.
			}
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
	 * Get the number of games.
	 * @return The number of games.
	 */
	public int getNbGames() {
		return this.games.size();
	}
	
	/**
	 * Tells if a game already exists in the controller after modifying it.
	 * @param oldG The old game.
	 * @param newG The new game.
	 * @return true if the game exists, else false.
	 */
	public boolean gameExists(Game oldG, Game newG) {
		if (oldG.equals(newG)) {
			return false;
		}
		
		for (Game game : this.games) {
			if (newG.equals(game)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Add a player in order to save it and use it later.
	 * @param player The player to save.
	 * @throws PlayerAlreadyExistsException If the player already exists.
	 */
	public void addPlayer(Player player) throws PlayerAlreadyExistsException {
		assert player != null;
		
		for (Player p : this.players) {
			if (player.equals(p)) {
				throw new PlayerAlreadyExistsException();
			}
		}
		
		this.players.add(player);
	}
	
	/**
	 * Remove a player from the players saved. 
	 * The player is also removed from each of the tournaments he played in.
	 * @param player The player to remove.
	 */
	public void removePlayer(Player player) {
		assert player != null;
		
		for (Tournament tournament : this.tournaments) {
			tournament.removeParticipant(player);
		}
		
		this.players.remove(player);
	}
	
	/**
	 * Get the players.
	 * @return A set of players.
	 */
	public Set<Player> getPlayers() {
		return this.players;
	}
	
	/**
	 * Get the number of players.
	 * @return The number of players.
	 */
	public int getNbPlayers() {
		return this.players.size();
	}
	
	/**
	 * Tells if a player already exists in the controller after modifying it.
	 * @param oldP The old player.
	 * @param newP The new player.
	 * @return true if the player exists, else false.
	 */
	public boolean playerExists(Player oldP, Player newP) {
		if (oldP.equals(newP)) {
			return false;
		}
		
		for (Player player : this.players) {
			if (newP.equals(player)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Add a team in order to save it and use it later.
	 * @param team The team to save.
	 * @throws TeamAlreadyExistsException If the team already exists.
	 */
	public void addTeam(Team team) throws TeamAlreadyExistsException {
		assert team != null;
		
		for (Team t : this.teams) {
			if (team.equals(t))
				throw new TeamAlreadyExistsException();
		}
		
		this.teams.add(team);
	}
	
	/**
	 * Remove a team from the teams saved. 
	 * The team is also removed from each of the tournaments he played in.
	 * @param team The team to remove.
	 */
	public void removeTeam(Team team) {
		assert team != null;
		
		for (Tournament tournament : this.tournaments) {
			tournament.removeParticipant(team);
		}
		
		this.teams.remove(team);
	}
	
	/**
	 * Get the teams.
	 * @return A set of teams.
	 */
	public Set<Team> getTeams() {
		return this.teams;
	}
	
	/**
	 * Get the number of teams.
	 * @return The number of team.
	 */
	public int getNbTeams() {
		return this.teams.size();
	}
	
	/**
	 * Tells if a team already exists in the controller after modifying it.
	 * @param oldT The old team.
	 * @param newT The new team.
	 * @return true if the team exists, else false.
	 */
	public boolean teamExists(Team oldT, Team newT) {
		if (oldT.equals(newT)) {
			return false;
		}
		
		for (Team team : this.teams) {
			if (newT.equals(team)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Save all informations in a file.
	 * @param filename The filename to save (path included).
	 * @throws SaveImpossibleException If the save is impossible.
	 */
	public void save(String filename) throws SaveImpossibleException {
		FileOperation.save(filename, tournaments, games, players, teams);
	}
	
	/**
	 * Load all informations from a file. 
	 * This emtpies the tournaments, games and participants before loading the file.
	 * @param filename The filename used to load (path included).
	 * @throws LoadImpossibleException If the save is impossible.
	 */
	public void load(String filename) throws LoadImpossibleException {
		FileOperation.load(filename, tournaments, games, players, teams);
	}
}
