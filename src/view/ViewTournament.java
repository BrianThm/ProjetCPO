/**
 * 
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.CompoundBorder;

import controller.Controller;
import tournament.Championship;
import tournament.DoubleElimination;
import tournament.Game;
import tournament.Participant;
import tournament.SimpleElimination;
import tournament.Tournament;

/**
 * @author group 
 * @version 1.0 
 * 
 * Class that permit to display all informations 
 * of a tournament in a panel.
 *
 */
@SuppressWarnings("serial")
public class ViewTournament extends JPanel {

	private Controller controller;
	private JPanel content, panelParticipants;
	private JTextField textLocation;
	private JTextField textDate;
	private JLabel title;
	private JTextField textTypeTournament;
	private JTextField textGame;
	private JList<Participant> listParticipant;

	/**
	 * Constructor to create the view with the lsit of games and the form to add a game.
	 * @param controller The controller.
	 */
	public ViewTournament(Controller controller, Tournament t) {
		super();
		/* Initialization of the attributes */
		
		this.setLayout(new BorderLayout());
		this.content = new JPanel();
		this.content.setLayout(new BoxLayout(this.content, BoxLayout.Y_AXIS));
		
		
		this.textLocation = new JTextField(20);
		this.textLocation.setText(t.getLocation());
		this.textDate = new JTextField(20); 
		this.textLocation.setText(""+t.getDate());
		SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
		formater.format(t.getDate());
		this.textDate.setText(""+t.getDate());
		this.textTypeTournament = new JTextField(20);
		if (SimpleElimination.class.isInstance(t)) {
			textTypeTournament.setText("Simple Elimination");
		} else if (DoubleElimination.class.isInstance(t)) {
			textTypeTournament.setText("Double Elimination");
		} else if (Championship.class.isInstance(t)) {
			textTypeTournament.setText("Championship");
		}
		this.textGame= new JTextField(20);
		this.textLocation.setText(""+t.getGame());
		
		
		this.panelParticipants = new JPanel(new FlowLayout());
		this.controller = controller;
		// Grid layout with two columns, one for each view
		this.setLayout(new GridLayout(0, 2));

		/* Initialization of the components */
		JPanel panelName = new JPanel(new FlowLayout());
		JLabel locationTournament = new JLabel("Location ");
		JLabel participants = new JLabel("Participants");
		JPanel panelType = new JPanel(new FlowLayout());
		JLabel typeTournament = new JLabel("Type ");
		JPanel panelGame = new JPanel(new FlowLayout()); 
		JLabel gameTournament = new JLabel("Game ");
		List<Participant> listP = new ArrayList<Participant>(this.controller.getSortedPlayers());
		listP.addAll(new ArrayList<Participant>(t.getParticipants()));
		Participant[] participantArray = listP.toArray(new Participant[listP.size()]);
		
		this.title = new JLabel("Tournament information");
		title.setFont(new Font("defaultFont", Font.BOLD, 15));
		title.setAlignmentX(CENTER_ALIGNMENT);
		title.setBorder(new CompoundBorder(
				BorderFactory.createEmptyBorder(15, 0, 15, 0),
				BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray)));
		this.listParticipant = new JList<Participant>(participantArray);
		this.listParticipant.setVisibleRowCount(5);
		this.listParticipant.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		panelName.add(locationTournament);
		panelName.add(textLocation);
		panelType.add(typeTournament);
		panelType.add(textTypeTournament);
		panelGame.add(gameTournament);
		panelGame.add(textGame);
		panelParticipants.add(participants);
		panelParticipants.add(new JScrollPane(this.listParticipant));
		content.add(title);
		content.add(panelName);
		content.add(panelType); 
		content.add(panelGame);
		
		/* All is centered */
		panelName.setAlignmentX(CENTER_ALIGNMENT);
		panelType.setAlignmentX(CENTER_ALIGNMENT);
		panelGame.setAlignmentX(CENTER_ALIGNMENT);
		panelParticipants.setAlignmentX(CENTER_ALIGNMENT);
		
		this.add(content, BorderLayout.CENTER);
		
		this.displayTournament();
	}
	
	private void displayTournament() {
		clear();
		title.setText("Tournament informations");
		content.remove(panelParticipants);
		content.add(panelParticipants);
		refreshPanel();
	}
	
	private void clear() {
		textLocation.setText("");
		listParticipant.clearSelection();
	}

	/**
	 * Refresh the panel when the display changes.
	 */
	private void refreshPanel() {
		this.repaint();
		this.revalidate();
	}
}
