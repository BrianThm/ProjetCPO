package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;

import controller.Controller;
import tournament.Championship;
import tournament.DoubleElimination;
import tournament.Match;
import tournament.Participant;
import tournament.SimpleElimination;
import tournament.Tournament;
import tournament.exceptions.MatchDrawException;

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

	private JPanel content; 
	private JPanel subContent; 
	private JPanel panelInformations; 
	private JPanel panelParticipants;
	private JPanel panelMatchs;
	private JTextField textLocation;
	private JTextField textDate;
	private JLabel title;
	private JTextField textTypeTournament;
	private JTextField textGame;
	private JTextField textWinner;
	private JList<Participant> listParticipant;
	private JButton tree;
	private JButton ret;
	private Tournament tournament;
	private ViewMain fenetre;
	private Match[] matchs;
	private ArrayList<Match> matchsEditable;
	private JPanel panelName;
	private JPanel panelType;
	private JPanel panelGame;
	private JPanel panelButtonTree;
	private JPanel panelButtonReturn;
	private JPanel panelDate;
	private JPanel panelWinner; 

	/**
	 * Constructor to create the view with the lsit of games and the form to add a game.
	 * @param controller TODO
	 */
	public ViewTournament(Controller controller, Tournament t, ViewMain fenetre) {
		super();
		
		/* Initialization of the attributes */
		this.tournament = t;
		this.fenetre=fenetre;
		this.setLayout(new BorderLayout());
		this.matchs = tournament.getMatchs();
		this.content = new JPanel();
		
		
		this.content.setLayout(new BoxLayout(this.content, BoxLayout.Y_AXIS));
		
		this.subContent = new JPanel(new FlowLayout());
		
		this.panelInformations = new JPanel(); 
		this.panelInformations.setLayout(new BoxLayout(panelInformations, BoxLayout.Y_AXIS));
		
		this.panelMatchs = new JPanel(); 
		this.panelMatchs.setLayout(new BoxLayout(panelMatchs, BoxLayout.Y_AXIS));
		
		this.textLocation = new JTextField(15);
		this.textLocation.setEditable(false);
		
		this.textDate = new JTextField(15); 		
		this.textDate.setEditable(false);
		
		this.textTypeTournament = new JTextField(15);
		this.textTypeTournament.setEditable(false);
		
		
		this.textGame= new JTextField(15);
		this.textGame.setEditable(false);
		
		this.panelParticipants = new JPanel(new FlowLayout());
		
		this.tree = new JButton("Tree of the tournament !");

		this.tree.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new popup(new PanelTreeTournament(matchs));
			}
		});
		
		this.ret = new JButton("Return");
		
		this.ret.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fenetre.changeView(new ViewListTournament(controller, false, fenetre));
				
			}
			
		});
		
		this.textWinner = new JTextField(15);
		this.textWinner.setEditable(false);
		 
		/* Initialization of the components */
	    panelName = new JPanel(new FlowLayout());
		JLabel locationTournament = new JLabel("Location ");
		
		JLabel participants = new JLabel("Participants");
		
		panelType = new JPanel(new FlowLayout());
		JLabel typeTournament = new JLabel("Type ");
		
		panelGame = new JPanel(new FlowLayout()); 
		JLabel gameTournament = new JLabel("Game ");
		
		panelDate = new JPanel(new FlowLayout()); 
		JLabel dateTournament = new JLabel("Date ");
		
		panelButtonTree = new JPanel(new FlowLayout()); 
		
		panelButtonReturn = new JPanel(new FlowLayout());
		
		panelWinner = new JPanel(new FlowLayout());
		JLabel winnerTournament = new JLabel("Winner ");
		
		
		
		
		this.title = new JLabel("Tournament information");
		title.setFont(new Font("defaultFont", Font.BOLD, 15));
		title.setAlignmentX(CENTER_ALIGNMENT);
		this.title.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
		
		List<Participant> listP = new ArrayList<Participant>(tournament.getParticipants());
		Participant[] participantArray = listP.toArray(new Participant[listP.size()]);		
		this.listParticipant = new JList<Participant>(participantArray);
		int nbPartDisplayed = listP.size()>8 ? 8 : listP.size();
		this.listParticipant.setVisibleRowCount(nbPartDisplayed);
		
		panelName.add(locationTournament);
		panelName.add(textLocation);
		panelType.add(typeTournament);
		panelType.add(textTypeTournament);
		panelGame.add(gameTournament);
		panelGame.add(textGame);
		panelDate.add(dateTournament);
		panelDate.add(textDate); 
		panelButtonTree.add(tree); 
		panelButtonReturn.add(ret);
		
		panelParticipants.add(participants);
		panelParticipants.add(new JScrollPane(this.listParticipant));
		
		panelWinner.add(winnerTournament); 
		panelWinner.add(textWinner);
				
		matchsEditable = new ArrayList<Match>();
		
		subContent.setAlignmentX(CENTER_ALIGNMENT);
		
		
		this.add(content, BorderLayout.CENTER);
		this.add(panelButtonReturn,BorderLayout.SOUTH);
		this.setAutoscrolls(true);
		this.displayTournament();
		this.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20),
				BorderFactory.createMatteBorder(2, 2, 2, 2, Color.gray)));
	}
	
	private class popup extends JDialog {
		public popup(JPanel jp) {
			super(fenetre , "Tree Tournament"); 
			super.setAlwaysOnTop(true);
			super.setLocationRelativeTo(null);
			super.add(jp);
			super.pack();
			super.setVisible(true);
		}
	}
	
	private void getMatchsEditable(ArrayList<Match> matchsEditable, Match[] matchList, int indice) {
		Match m = matchList[indice];
		if (m != null){
			if (m.getParticipant1().getName() != "?" ) {
				if (m.getParticipant2().getName() != "?") {
					if (m.getWinner()==null && !m.isDraw()) {
						matchsEditable.add(m);
					}
				}
				else {
					getMatchsEditable(matchsEditable ,matchList ,indice*2+1);
				}
			}
			else {
				getMatchsEditable(matchsEditable ,matchList ,indice*2);
			}
		}
		else {
			getMatchsEditable(matchsEditable, matchList, indice*2);
			getMatchsEditable(matchsEditable, matchList, indice*2+1);
		}
	}
	
	private void displayTournament() {
		clear();
		title.setText("Tournament informations");
		textLocation.setText(tournament.getLocation());
		
		if (SimpleElimination.class.isInstance(tournament)) {
			textTypeTournament.setText("Simple Elimination");
		} else if (DoubleElimination.class.isInstance(tournament)) {
			textTypeTournament.setText("Double Elimination");
		} else if (Championship.class.isInstance(tournament)) {
			textTypeTournament.setText("Championship");
		}
		
		textGame.setText(tournament.getGame().getName());
		
		SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
		formater.format(tournament.getDate());
		this.textDate.setText(""+formater);
		
		if (tournament.getWinner()!=null) {
			this.textWinner.setText(tournament.getWinner().getName());
		} else {
			this.textWinner.setText("?");
		}
		
		content.add(title);
		content.setBorder(BorderFactory.createEmptyBorder(15, 5, 15, 5));
		content.add(Box.createRigidArea(new Dimension(0, 40)));
		content.add(subContent); 
		subContent.add(panelInformations); 
		subContent.add(panelMatchs); 
		
		
		
		panelInformations.add(panelName);
		panelInformations.add(Box.createRigidArea(new Dimension(0, 20)));
		panelInformations.add(panelType); 
		panelInformations.add(Box.createRigidArea(new Dimension(0, 20)));
		panelInformations.add(panelGame);
		panelInformations.add(Box.createRigidArea(new Dimension(0, 20)));
		panelInformations.add(panelParticipants);
		panelMatchs.add(panelButtonTree);
		panelMatchs.add(Box.createRigidArea(new Dimension(0, 20)));
		panelMatchs.add(panelWinner);
		panelMatchs.add(Box.createRigidArea(new Dimension(0, 20)));
		matchs = tournament.getMatchs();
		getMatchsEditable(matchsEditable,matchs,1);
		AdditionMatchsEditable();
		refreshPanel();
	}
	
	private void clear() {
		content.removeAll();
		subContent.removeAll();
		panelMatchs.removeAll();
		panelInformations.removeAll();
		matchsEditable.clear();
		title.setText("");
		textLocation.setText("");
		textDate.setText("");
		textGame.setText("");
		textWinner.setText("");
		textTypeTournament.setText("");
		
		listParticipant.clearSelection();
	}
	
	private void AdditionMatchsEditable() {
		for (Match m : matchsEditable) {
			JPanel Match = new JPanel(new FlowLayout()); 
			JLabel P1 = new JLabel();
			JLabel P2 = new JLabel();
			JTextField scorep1 = new JTextField(5);
			scorep1.setText("0");
			JTextField scorep2 = new JTextField(5);
			scorep2.setText("0");
			JButton setScore = new JButton("Submit Score");
			setScore.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						int scoreP1 = Integer.parseInt(scorep1.getText());
						int scoreP2 = Integer.parseInt(scorep2.getText());
						if (scoreP1 <0 || scoreP2 <0) {
							JOptionPane.showMessageDialog(content, "The score must be positive !",
									"Score is negative !", JOptionPane.ERROR_MESSAGE);
						} else {
							m.setScore(scoreP1, scoreP2);
						}
					}catch(NumberFormatException exc) {
						JOptionPane.showMessageDialog(content, "You must put an number in the score !",
								"Score is not a number !", JOptionPane.ERROR_MESSAGE);
					} catch(MatchDrawException exc1) {
						JOptionPane.showMessageDialog(content, exc1.getMessage(),
								"Match Draw !", JOptionPane.ERROR_MESSAGE);
					}
					finally {
						clear();
						displayTournament();
						refreshPanel();
					}
				}
			});
			
			P1.setText(m.getParticipant1().getName());
			P2.setText(m.getParticipant2().getName());
			Match.add(P1); 
			Match.add(scorep1); 
			Match.add(P2);
			Match.add(scorep2); 
			Match.add(setScore);
			panelMatchs.add(Match);
			panelMatchs.add(Box.createRigidArea(new Dimension(0, 20)));
			panelMatchs.setAlignmentX(RIGHT_ALIGNMENT);
		}
	}

	/**
	 * Refresh the panel when the display changes.
	 */
	private void refreshPanel() {
		this.repaint();
		this.revalidate();
	}
}
