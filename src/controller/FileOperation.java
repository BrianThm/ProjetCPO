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
	public static void save(String filename, Set<Tournament> tournaments, Set<Game> games, Set<Participant> participants) throws SaveImpossibleException {
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
		
		for (int i = 0; i < games.size(); i++) {
			game = games.get(i).getName().replace(',', ';');
			line = i + "," + game + "\n";
			file.write(line);
		}
	}
	
	/**
	 * Load from a file informations about tournaments.
	 * @param filename The filename to load from (path included).
	 * @param tournaments The tournaments to be saved (will be emptied).
	 * @param games The games to be saved (will be emptied).
	 * @param participants The participants to be save (will be emptied).
	 * @throws LoadImpossibleException If the load is impossible.
	 */
	public static void load(String filename, Set<Tournament> tournaments, Set<Game> games, Set<Participant> participants) throws LoadImpossibleException {
		tournaments = new HashSet<Tournament>();
		games = new HashSet<Game>();
		participants = new HashSet<Participant>();
		
		
	}
}
