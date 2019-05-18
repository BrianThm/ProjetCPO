package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tournament.Game;
import tournament.Participant;
import tournament.Player;
import tournament.Team;
import tournament.Tournament;

/**
 * This class permits to save & load a list of tournaments, games and participant.
 * @author Group
 * @version 1.0
 */
public class FileOperation {
	
	/**
	 * Save in a file all the informations saved by a controller.
	 * @param filename The filename to save (path included).
	 * @param tournaments The tournaments to save.
	 * @param games The games to save.
	 * @param participants The participants to save.
	 * @throws SaveImpossibleException If the save is impossible, with the reason in the getMessage().
	 */
	public static void save(String filename, Set<Tournament> tournaments, Set<Game> games, Set<Player> players, Set<Team> teams) throws SaveImpossibleException {
		List<Game> idGames = new ArrayList<Game>();
		List<Player> idPlayers = new ArrayList<Player>();
		List<Team> idTeams = new ArrayList<Team>();
		FileWriter file = null;
		
		for (Game game : games) {
			idGames.add(game);
		}
		
		for (Participant participant : participants) {
			if (participant instanceof Player) {
				idPlayers.add((Player) participant);
			} else if (participant instanceof Team) {
				idTeams.add((Team) participant);
			} // Nobody should end up in the else
		}
		
		try {
			file = new FileWriter(filename);
			saveGames(file, idGames);
			savePlayers(file, idPlayers, idGames);
			saveTeams(file, idTeams, idPlayers, idGames);
		} catch (IOException e) {
			throw new SaveImpossibleException(e.getMessage());
		} finally {
			if (file != null) {
				try {
					file.close();
				} catch (IOException e) {
					throw new SaveImpossibleException(e.getMessage());
				}
			}
		}
	}
	
	private static void saveGames(FileWriter file, List<Game> games) throws IOException {
		String line, game;
		file.write("# games #\n");
		// index of game ; name
		
		for (int i = 0; i < games.size(); i++) {
			game = games.get(i).getName().replace(';', ',');
			line = i + ";" + game + "\n";
			file.write(line);
		}
	}
	
	private static void savePlayers(FileWriter file, List<Player> players, List<Game> games) throws IOException {
		String line, fname, name, lname;
		int game;
		Player player;
		file.write("# players #\n");
		// index of player ; fname ; name ; lname ; index of game (if there is one played 0 time)
		
		for (int i = 0; i < players.size(); i++) {
			player = players.get(i);
			game = getIndexBaseGame(player, games);
			fname = player.getFName().replace(',', ';');
			name = player.getName().replace(',', ';');
			lname = player.getLName().replace(',', ';');
			line = i + ";" + fname + ";" + name + ";" + lname + ";" + game + "\n";
			file.write(line);
		}
	}
	
	private static void saveTeams(FileWriter file, List<Team> teams, List<Player> players, List<Game> games) throws IOException {
		String line, name;
	}
	
	private static int getIndexBaseGame(Participant participant, List<Game> games) {
		for (Game g : participant.getGames().keySet()) {
			if (participant.getGames().get(g) == 0) {
				return games.indexOf(g);
			}
		}
		
		return -1;
	}
	
	/**
	 * Load from a file informations about tournaments.
	 * @param filename The filename to load from (path included).
	 * @param tournaments The tournaments to be saved (will be emptied).
	 * @param games The games to be saved (will be emptied).
	 * @param participants The participants to be save (will be emptied).
	 * @throws LoadImpossibleException If the load is impossible.
	 */
	public static void load(String filename, Set<Tournament> tournaments, Set<Game> games, Set<Player> players, Set<Team> teams) throws LoadImpossibleException {
		tournaments.clear();
		games.clear();
		players.clear();
		teams.clear();
	}
}
