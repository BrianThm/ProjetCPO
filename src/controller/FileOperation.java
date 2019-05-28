package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import controller.exceptions.*;
import tournament.*;

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
	 * @param players The players to save.
	 * @param teams The teams to save.
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
		for (Player player : players) {
			idPlayers.add(player);
		}
		for(Team team : teams) {
			idTeams.add(team);
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
		// name of game
		
		for (int i = 0; i < games.size(); i++) {
			game = games.get(i).getName().replace(';', ',');
			line = game + "\n";
			file.write(line);
		}
	}
	
	private static void savePlayers(FileWriter file, List<Player> players, List<Game> games) throws IOException {
		String line, fname, name, lname;
		Player player;
		int game;
		file.write("# players #\n");
		// fname ; name ; lname ; index of game (if there is one played 0 time)
		
		for (int i = 0; i < players.size(); i++) {
			player = players.get(i);
			game = getIndexBaseGame(player, games);
			fname = player.getFName().replace(';', ',');
			name = player.getName().replace(';', ',');
			lname = player.getLName().replace(';', ',');
			line = fname + ";" + name + ";" + lname + ";" + game + "\n";
			file.write(line);
		}
	}
	
	private static void saveTeams(FileWriter file, List<Team> teams, List<Player> players, List<Game> games) throws IOException {
		String line, name, tPlayers;
		Team team;
		int game;
		file.write("# teams #\n");
		// name ; index of game (if there is one played 0 time) [; indexes of players member]
		
		for (int i = 0; i < teams.size(); i++) {
			team = teams.get(i);
			game = getIndexBaseGame(team, games);
			name = team.getName().replace(';', ',');
			tPlayers = "";
			for (Player member : team.getMembers()) {
				tPlayers += ";" + players.indexOf(member);
			}
			line = name + ";" + game + tPlayers + "\n";
			file.write(line);
		}
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
	 * @param players The players to be save (will be emptied).
	 * @param teams The teams to be save (will be emptied).
	 * @throws LoadImpossibleException If the load is impossible.
	 */
	public static void load(String filename, Set<Tournament> tournaments, Set<Game> games, Set<Player> players, Set<Team> teams) throws LoadImpossibleException {
		games.clear();
		players.clear();
		teams.clear();
		tournaments.clear();
		
		List<Game> idGames = new ArrayList<Game>();
		List<Player> idPlayers = new ArrayList<Player>();
		List<Team> idTeams = new ArrayList<Team>();
		FileReader file = null;
		BufferedReader buffer = null;
		
		try {
			file = new FileReader(filename);
			buffer = new BufferedReader(file);
			loadGames(buffer, idGames);
			loadPlayers(buffer, idPlayers, idGames);
			loadTeams(buffer, idTeams, idPlayers, idGames);
		} catch (IOException e) {
			throw new LoadImpossibleException(e.getMessage());
		} catch (Throwable e) {
			throw new LoadImpossibleException("File format incorrect.");
		} finally {
			if (file != null) {
				try {
					file.close();
				} catch (IOException e) {
					throw new LoadImpossibleException(e.getMessage());
				}
			}
		}
		
		games.addAll(idGames);
		players.addAll(idPlayers);
		teams.addAll(idTeams);
	}

	private static void loadGames(BufferedReader buffer, List<Game> games) throws IOException, LoadImpossibleException {
		String[] args;
		String line = buffer.readLine();
		if (!line.equals("# games #")) {
			throw new LoadImpossibleException("File format incorrect, delimiter '# games #' not found.");
		}
		
		while (!(line = buffer.readLine()).equals("# players #")) {
			if (line == null || line.startsWith("#")) {
				throw new LoadImpossibleException("File format incorrect, delimiter '# players #' not found.");
			}
			
			args = line.split(";");
			games.add(new Game(args[0]));
		}
	}

	private static void loadPlayers(BufferedReader buffer, List<Player> idPlayers, List<Game> idGames) throws IOException, LoadImpossibleException {
		String[] args;
		String line, fname, name, lname;
		Game game;
		Player player;
		
		while (!(line = buffer.readLine()).equals("# teams #")) {
			if (line == null || line.startsWith("#")) {
				throw new LoadImpossibleException("File format incorecct, delimiter '# teams #' not found");
			}
			
			args = line.split(";");
			fname = args[0];
			name = args[1];
			lname = args[2];
			if (!args[3].equals("-1")) {
				game = idGames.get(Integer.parseInt(args[3]));
				player = new Player(fname, lname, name, game);
			} else {
				player = new Player(fname, lname, name);
			}
			
			idPlayers.add(player);
		}
	}

	private static void loadTeams(BufferedReader buffer, List<Team> idTeams, List<Player> idPlayers,
			List<Game> idGames) throws IOException, LoadImpossibleException {
		String[] args;
		String line, name;
		Player player;
		Game game;
		Team team;
		
		while ((line = buffer.readLine()) != null && !line.equals("")) {
			args = line.split(";");
			name = args[0];
			if (!args[1].equals("-1")) {
				game = idGames.get(Integer.parseInt(args[1]));
				team = new Team(name, game);
			} else {
				team = new Team(name);
			}
			
			for (int i = 2; i < args.length; i++) {
				player = idPlayers.get(Integer.parseInt(args[i]));
				team.addMember(player);
			}
			
			idTeams.add(team);
		}
	}
}
