package tournament;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import tournament.exceptions.NotEnoughParticipantsException;

@SuppressWarnings("deprecation")
/**
 * Class Tournament, will be specified by each type of tournament.
 * @author Group
 * @version 1.0
 */
public abstract class Tournament implements Observer {
	
	private Game game;
	private String location;
	private Date date; 
	protected List<Participant> participants;
	protected Participant winner;
	protected Match[] matchs;
	
	/**
	 * Constructor of an empty tournament, composed of a game.
	 * @param date The date of the tournament.
	 * @param game The game of the tournament.
	 */
	public Tournament(Date date, Game game) {
		this(date, game, ""); 
	}
	
	/**
	 * Constructor of a tournament with a location and a game.
	 * @param date The date of the tournament.
	 * @param game The game of the tournament.
	 * @param location The location of the tournament.
	 */
	public Tournament(Date date, Game game, String location) {
		assert game != null;
		assert location != null;
		assert date != null;
		
		this.date = date;
		this.game = game;
		this.location = location;
		participants = new ArrayList<Participant>();
	}
	
	/**
	 * Get the game of the tournament.
	 * @return The game of the tournament.
	 */
	public Game getGame() {
		return this.game;
	}
	
	/**
	 * Change the game of the tournament.
	 * @param game The new game of the tournament.
	 */
	public void setGame(Game game) {
		assert game != null;
		
		this.game = game;
	}
	
	/**
	 * Get the date of the tournament.
	 * @return the date of the tournament.
	 */
	public Date getDate() {
		return this.date;
	}
	
	/**
	 * Change the date of the tournament.
	 * @param date The new date of the tournament.
	 */
	public void setDate(Date date) {
		assert date != null;
		
		this.date = date; 
	}
	
	/**
	 * Get the location of the tournament.
	 * @return The location of the tournament.
	 */
	public String getLocation() {
		return this.location;
	}
	
	/**
	 * Set the location of the tournament.
	 * @param location The new location.
	 */
	public void setLocation(String location) {
		assert location != null;
		
		this.location = location;
	}
	
	/**
	 * Add a participant to a tournament.
	 * @param participant The new participant.
	 */
	public void addParticipant(Participant participant) {
		assert participant != null;
		
		participants.add(participant);
		participant.addTournament(this);
	}
	
	/**
	 * Remove a participant to a tournament.
	 * @param participant The participant to remove.
	 */
	public void removeParticipant(Participant participant) {
		assert participant != null;
		
		participants.remove(participant);
		participant.removeTournament(this);
	}
	
	/**
	 * Get the participants of this tournament.
	 * @return A set of the participants.
	 */
	public List<Participant> getParticipants() {
		return participants;
	}
	
	/**
	 * Random draw of tournament participants.
	 */
	public void randomDraw() {
		Collections.shuffle(participants);
	}
	
	/**
	 * Get the winner of this tournament.
	 * @return The winner of the tournament.
	 */
	public Participant getWinner() {
		return this.winner;
	}
	
	/**
	 * Set the winner of this tournament.
	 * @param winner The winner of the tournament.
	 */
	public void setWinner(Participant winner) {
		this.winner = winner;
	}
	
	/**
	 * Get the matchs of this tournament.
	 * @return A tree of matchs.
	 */
	public final Match[] getMatchs() {
		return this.matchs;
	}
	
	/**
	 * Set an array of matchs to the tournament.
	 * @param matchs The new matchs.
	 */
	public void setMatchs(Match[] matchs) {
		this.matchs = matchs;
	}
	
	/**
	 * Initialize the tournament tree.
	 * @param partipants The parcipants of the tournament.
	 * @throws NotEnoughParticipantsException If there is not enough participants to start the tournament.
	 */
	public abstract void initializeMatchs() throws NotEnoughParticipantsException;
	
	/**
	 * Update the tournament tree at the end of each match.
	 */
	public abstract void updateMatchs(Match m);
	
	
	@Override
	public void update(Observable arg0, Object arg1) {
		Match m = null;
		if (arg0 instanceof Match) {
			m = (Match) arg0;
		}
		this.updateMatchs(m);
	}
}
