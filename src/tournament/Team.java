package tournament;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Groupe
 * @version 1.0 
 * A team is composed of players.
 * A team can participate to a tournament.
 */
public class Team extends Participant {
	
	private Set<Player> members;
	
	/**
	 * Create a team with a name.
	 * @param name The name of the team.
	 */
	public Team(String name) {
		super(name);
		this.members = new HashSet<Player>();
	}
	
	/**
	 * Create a team with a name and a base game.
	 * @param name The name of the team.
	 * @param game The base game of the team.
	 */
	public Team(String name, Game game) {
		super(name, game);
		this.members = new HashSet<Player>();
	}
	
	/**
	 * Add a player to the team.
	 * @param player The new player.
	 */
	public void addMember(Player player) {
		assert player != null;
		
		this.members.add(player);
	}
	
	/**
	 * Remove a player of the team.
	 * @param member The player to remove.
	 */
	public void removeMember(Player member) {
		assert member != null;
		
		this.members.remove(member);
	}
	
	/**
	 * Get the members of the team.
	 * @return A set of the members.
	 */
	public Set<Player> getMembers() {
		return this.members;
	}
	
	@Override
	/**
	 * When a team plays a game, each member plays the game too.
	 */
	public void plays(Game game) {
		super.plays(game);
		for (Player member : this.members) {
			member.plays(game);
		}
	}
	
	@Override
	/**
	 * When a team plays a tournament, each member plays the tournament too.
	 */
	public void addTournament(Tournament tournament) {
		super.addTournament(tournament);
		for (Player member : this.members) {
			member.addTournament(tournament);
		}
	}
	
	@Override
	/**
	 * When a team no longer plays a tournament, each member no longer plays the tournament.
	 */
	public void removeTournament(Tournament tournament) {
		super.removeTournament(tournament);
		for (Player member : this.members) {
			member.removeTournament(tournament);
		}
	}
}
