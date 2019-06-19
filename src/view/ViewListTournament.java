package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;

import controller.Controller;
import tournament.Championship;
import tournament.DoubleElimination;
import tournament.Game;
import tournament.SimpleElimination;
import tournament.Tournament;

@SuppressWarnings("serial")
/**
 * View which displays the list of all the tournaments.
 * A tournament cannot be modified.
 * @author Group
 */
public class ViewListTournament extends JPanel {

	private Controller controller;
	private ImageIcon imgSeeInfo, imgDelete;
	private boolean deleteTournament;
	private JLabel title;
	private ViewAdd<Tournament> viewAdd;
	private ViewMain fenetre;
	
	public ViewListTournament(Controller controller, boolean deleteTournament, ViewMain fenetre) {
		super();
		this.controller = controller;
		this.deleteTournament = deleteTournament;
		this.fenetre = fenetre;
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		title = new JLabel("List of tournaments");
		title.setFont(new Font("defaultFont", Font.BOLD, 15));
		title.setAlignmentX(CENTER_ALIGNMENT);
		title.setBorder(new CompoundBorder(
				BorderFactory.createEmptyBorder(15, 0, 15, 0),
				BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray)));
		
		/* Creation of the image button to display the information about the tournament */
		imgSeeInfo = new ImageIcon(getClass().getResource("/res/view_info.png"));
		Image imageInfos = imgSeeInfo.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
		imgSeeInfo = new ImageIcon(imageInfos);
		
		/* Creation of the delete image button */
		imgDelete = new ImageIcon(getClass().getResource("/res/delete.png"));
		Image image = imgDelete.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
		imgDelete = new ImageIcon(image);
		
		/* Creates and displays the list of games */
		makeList();

		/* Empty border for the outside (kind of margin) and gray border for the inside */
		this.setBorder(new CompoundBorder(
				BorderFactory.createEmptyBorder(20, 20, 20, 20),
				BorderFactory.createMatteBorder(2, 2, 2, 2, Color.gray)));
	}
	
	
	private void makeList() {
		Set<Tournament> tournaments = controller.getTournaments();
		this.removeAll();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(title);
		
		for (Tournament t : tournaments) {
			this.add(getPanel(t));
		}
		
		// If there is any tournament created, the panel displays a message
		if (controller.getNbTournaments() == 0) {
			noTournament();
		}
		
		refreshList();
	}
	
	
	private JPanel getPanel(Tournament tournament) {
		JPanel line = new JPanel(new BorderLayout());
		JPanel infosTournament = new JPanel(new GridLayout(0, 2));
		JLabel labelImgSeeInfo = new JLabel(imgSeeInfo);
		JLabel labelImgDel = new JLabel(imgDelete);
		String labelTextInfo, labelTextGame = "";
		SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
		
		int nbParts = tournament.getParticipants().size();
		Game game = tournament.getGame();
		Date date = tournament.getDate();
		String location = tournament.getLocation();
		String type = "Not specified";
		if (SimpleElimination.class.isInstance(tournament)) {
			type = "Simple Elimination";
		} else if (DoubleElimination.class.isInstance(tournament)) {
			type = "Double Elimination";
		} else if (Championship.class.isInstance(tournament)) {
			type = "Championship";
		}
		
		labelTextInfo = "<html>Type: " + type + "<br/>";
		if (location != null) {
			labelTextInfo += "Location: " + location + "<br/>";
		}
		labelTextInfo += "Date: " + formater.format(date) + "<br/>";
		labelTextInfo += "Number of participants: " + nbParts + "<br/></html>";
		
		if (game != null) {
			labelTextGame = "<html>Game: " + game.getName() + "<br/></html>";
		}
		
		JLabel textInfo = new JLabel(labelTextInfo);
		JLabel textGame = new JLabel(labelTextGame);
		infosTournament.add(textInfo);
		infosTournament.add(textGame);
		textInfo.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 10));
		textGame.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 40));
		
		line.add(infosTournament, BorderLayout.CENTER);
		
		labelImgSeeInfo.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 5));
		labelImgDel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 5));
		
		//TODO: Implement the listener for the seeInfo button
		
		labelImgSeeInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changeView(tournament);
			}
		});
		
		/* Listener for the delete button */
		labelImgDel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deleteTournament(tournament, line);
			}
		});
		
		JPanel panelImg = new JPanel(new GridLayout(0, 2));
		
		if (deleteTournament) {
			line.remove(panelImg);
			panelImg.add(labelImgDel);
			line.add(panelImg, BorderLayout.EAST);
		} else {
			panelImg.add(labelImgSeeInfo);
			line.add(panelImg, BorderLayout.EAST);
		}
		
		line.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
		return line;
	}
	
	/**
	 * Display a message if the is'nt any tournament already created.
	 */
	private void noTournament() {
		this.removeAll();
		this.setLayout(new GridBagLayout());
		JLabel empty = new JLabel("There isn't any tournament for now."
				+ "You can add one via the Tournament menu.");
		this.add(empty);
		refreshList();
	}
	
	/**
	 * Refresh the list when a displays changes.
	 */
	private void refreshList() {
		this.repaint();
		this.revalidate();
	}
	
	/**
	 * Delete a tournament of the tournament's list.
	 * @param tournament The tournament to delete.
	 * @param line The panel the delete.
	 */
	private void deleteTournament(Tournament tournament, JPanel line) {
		int answer = JOptionPane.showConfirmDialog(this,
				"This tournament will be removed from the list of tournaments. Are you sure?",
				"Delete tournament", JOptionPane.YES_NO_OPTION);
		if (answer == 0) {
			this.controller.removeTournament(tournament);
			this.remove(line);

			if (controller.getNbTeams() == 0)
				noTournament();

			refreshList();

			// Signal to the viewAddTournament that the tournament has been deleted
			if (viewAdd != null)
				viewAdd.deleted(tournament);
		}
	}
	
	private void changeView(Tournament t) {
		fenetre.changeView(new ViewTournament(controller, t, fenetre));
	}
}
