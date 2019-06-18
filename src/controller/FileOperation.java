package controller;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import controller.exceptions.LoadImpossibleException;
import controller.exceptions.SaveImpossibleException;
import tournament.Game;
import tournament.Match;
import tournament.Participant;
import tournament.Player;
import tournament.SimpleElimination;
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
	 * @param players The players to save.
	 * @param teams The teams to save.
	 * @throws SaveImpossibleException If the save is impossible, with the reason in the getMessage().
	 */
	public static void save(String filename, Set<Tournament> tournaments, Set<Game> games, Set<Player> players, Set<Team> teams) throws SaveImpossibleException {
		List<Game> idGames = new ArrayList<Game>();
		List<Player> idPlayers = new ArrayList<Player>();
		List<Team> idTeams = new ArrayList<Team>();
		List<SimpleElimination> idSimples = new ArrayList<SimpleElimination>();
		File basicFile = null;
		FileWriter file = null;
		String name;
		Path path;
		
		for (Game game : games) {
			idGames.add(game);
		}
		for (Player player : players) {
			idPlayers.add(player);
		}
		for (Team team : teams) {
			idTeams.add(team);
		}
		for (Tournament tournament : tournaments) {
			if (tournament instanceof SimpleElimination) {
				idSimples.add((SimpleElimination) tournament);
			}
		}
		
		try {
			basicFile = new File(filename);
			file = new FileWriter(basicFile);
			name = basicFile.getName();
			name = name.substring(0, name.lastIndexOf("."));
			path = Paths.get(filename);
			
			saveGames(file, idGames, path.getParent().toString(), name);
			savePlayers(file, idPlayers, idGames);
			saveTeams(file, idTeams, idPlayers, idGames);
			saveSimpleEliminations(file, idSimples, idTeams, idPlayers, idGames);
		} catch (IOException e) {
			throw new SaveImpossibleException(e.getMessage());
		} finally {
			if (file != null) {
				try {
					file.close();
					// No need to close basicFile, this closes it as well
				} catch (IOException e) {
					throw new SaveImpossibleException(e.getMessage());
				}
			}
		}
	}
	
	private static void saveGames(FileWriter file, List<Game> games, String path, String name) throws IOException {
		String line, game, image;
		file.write("# games #\n");
		// name of game [; image name]
		
		for (int i = 0; i < games.size(); i++) {
			game = games.get(i).getName().replace(';', ',');
			image = saveImage(path, name, games.get(i), i);
			line = game + image + "\n";
			file.write(line);
		}
	}
	
	private static String saveImage(String path, String name, Game game, int i) throws IOException {
		if (!game.hasImage()) {
			return "";
		}
		
		// Creating folder if needed
		String folderPath = path + File.separator + name;
		File folder = new File(folderPath);
		folder.mkdirs();
		// The image file
		File imgFile = new File(folderPath + File.separator + i + ".png");
		
		// ImageIO can only write a rendered image
		Image image = game.getImage().getImage();
		BufferedImage buff = toBufferedImage(image);
		
		// Wrtiting the image
		ImageIO.write(buff, "png", imgFile);
		
		return ";" + i + ".png";
	}
	
	private static BufferedImage toBufferedImage(Image img) {
		// This is obviously from stackoverflow, i'm not gonna deny it
		int width = img.getWidth(null);
		int height = img.getHeight(null);
		
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.getGraphics();
		g.drawImage(img, 0,0, null);
		g.dispose();
		
		return bi;
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
	
	private static void saveSimpleEliminations(FileWriter file, List<SimpleElimination> simples, List<Team> teams, List<Player> players, List<Game> games) throws IOException {
		String line;
		SimpleElimination simple;
		Match[] matchs;
		int game, p1, p2, s1, s2;
		boolean isPlayer;
		Participant participant;
		file.write("# simple eliminations #");
		// location ; date ; index of game ; [ p(layer) | t(eam) ] ; [ participant 1, participant 2, score 1, score 2 ]
		
		for (int i = 0; i < simples.size(); i++) {
			simple = simples.get(i);
			isPlayer = simple.getParticipants().get(0) instanceof Player;
			game = games.indexOf(simple.getGame());
			matchs = simple.getMatchs();
			line = simple.getLocation() + ";" + simple.getDate().getTime() + ";" + game + ";";
			if (isPlayer) {
				line += "p" + ";";
			} else {
				line += "t" + ";";
			}
			for (Match match : matchs) {
				participant = match.getParticipant1();
				if (isPlayer) {
					p1 = players.indexOf(participant);
				} else {
					p1 = teams.indexOf(participant);
				}
				
				participant = match.getParticipant2();
				if (participant instanceof Player) {
					p2 = players.indexOf(participant);
				} else {
					p2 = teams.indexOf(participant);
				}
				
				s1 = match.getScore()[0];
				s2 = match.getScore()[1];
				
				line += ";" + p1 + "," + p2 + "," + s1 + "," + s2;
			}
			file.write(line);
		}
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
		List<SimpleElimination> idSimples = new ArrayList<SimpleElimination>();
		File basicFile = null;
		FileReader file = null;
		BufferedReader buffer = null;
		String name;
		Path path;
		
		try {
			basicFile = new File(filename);
			file = new FileReader(basicFile);
			name = basicFile.getName();
			name = name.substring(0, name.lastIndexOf("."));
			path = Paths.get(filename);

			buffer = new BufferedReader(file);
			loadGames(buffer, idGames, path.getParent().toString(), name);
			loadPlayers(buffer, idPlayers, idGames);
			loadTeams(buffer, idTeams, idPlayers, idGames);
			loadSimplesEliminations(buffer, idSimples, idTeams, idPlayers, idGames);
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
		tournaments.addAll(idSimples);
	}

	private static void loadGames(BufferedReader buffer, List<Game> games, String path, String name) throws IOException, LoadImpossibleException {
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
			Game game = new Game(args[0]);
			games.add(game);
			if (args.length == 2) {
				loadImage(game, path, name, args[1]);
			}
		}
	}
	
	private static void loadImage(Game game, String path, String strFolder, String name) throws IOException {
		// Getting the image
		String imgPath = path + File.separator + strFolder + File.separator + name;
		File image = new File(imgPath);
		
		BufferedImage buff = ImageIO.read(image);
		ImageIcon icon = new ImageIcon(buff);
		game.setImage(icon);
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
		
		while ((line = buffer.readLine()) != null && !line.equals("# simple eliminations #")) {
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

	private static void loadSimplesEliminations(BufferedReader buffer, List<SimpleElimination> idSimples, List<Team> idTeams, List<Player> idPlayers, List<Game> idGames) throws IOException, LoadImpossibleException, ParseException {
		String line, args[], location, date, match[];
		Match[] matchs;
		Participant par1, par2;
		Game game;
		int g, p1, p2, s1, s2;
		boolean isPlayer;
		
		while((line = buffer.readLine()) != null) {
			args = line.split(";");
			location = args[0];
			date = args[1];
			g = Integer.parseInt(args[2]);
			game = idGames.get(g);
			isPlayer = args[3].equals("p");
			
			matchs = new Match[args.length - 3];
			for (int i = 4; i < args.length; i++) {
				match = args[i].split(",");
				p1 = Integer.parseInt(match[0]);
				p2 = Integer.parseInt(match[1]);
				s1 = Integer.parseInt(match[2]);
				s2 = Integer.parseInt(match[3]);
				
				if (isPlayer) {
					par1 = idPlayers.get(p1);
					par2 = idPlayers.get(p2);
				} else {
					par1 = idTeams.get(p1);
					par2 = idTeams.get(p2);
				}
				
				matchs[i - 4] = new Match(par1, par2, game);
				matchs[i - 4].setScore(s1, s2);
			}
			
			idSimples.add(new SimpleElimination(new Date(Long.parseLong(date)), game, location));
		}
	}
}
