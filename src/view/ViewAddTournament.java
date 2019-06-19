package view;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import javax.swing.JFormattedTextField.AbstractFormatter;
import java.util.Calendar;

import controller.Controller;
import tournament.DoubleElimination;
import tournament.Game;
import tournament.Participant;
import tournament.SimpleElimination;
import tournament.Tournament;
import tournament.exceptions.NotEnoughParticipantsException;

/**
 * The ViewAddTournament is the view wich allow to create tournament.
 * @author Group
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ViewAddTournament extends ViewAdd<Tournament> {

	private ViewMain fenetre;
	private JPanel panelDate, panelType, panelCB, panelParticipants;
	private UtilDateModel dateModel;
	private JDatePanelImpl datePanel;
	private JDatePickerImpl datePicker;
	private Date selectedDate;
	private JTextField textLocation;
	private JComboBox<String> cbTournament;
	private JComboBox<Game> comboBox;
	private String type;
	private Game game;
	private JList<Participant> listParticipant;
	private boolean isPlayerSelected;
	private boolean randomDraw;

	/**
	 * Constructor of the ViewAddTournament when there is a viewList<Tournament> associated to it.
	 * @param controller The controller.
	 * @param viewList The viewList displayed next to ViewAddTournament.
	 */
	public ViewAddTournament(Controller controller, ViewList<Tournament> viewList) {
		this(controller);
		this.viewList = viewList;
	}

	/**
	 * Constructor of the ViewAddTournament when there isn't the viewList displayed next.
	 * @param controller The controller.
	 */
	public ViewAddTournament(Controller controller) {
		super(controller, "tournament");

		Properties properties = new Properties();
		properties.put("text.today", "Today");
		properties.put("text.mouth", "Month");
		properties.put("text.year", "Year");

		/* Initialization of the attributes */
		this.textLocation = new JTextField(20);
		this.panelDate = new JPanel(new FlowLayout());
		this.dateModel = new UtilDateModel();
		this.datePanel = new JDatePanelImpl(dateModel, properties);
		this.datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		this.panelType = new JPanel(new FlowLayout());
		this.panelCB = new JPanel(new FlowLayout());
		this.panelParticipants = new JPanel(new FlowLayout());
		this.randomDraw = false;

		/* Initialization of the components */
		JPanel panelName = new JPanel(new FlowLayout());
		JPanel panelRadio = new JPanel(new GridLayout(2, 0));
		JPanel panelRadListAdd = new JPanel(new FlowLayout());
		JPanel panelShuffle = new JPanel();
		panelShuffle.setLayout(new BoxLayout(panelShuffle, BoxLayout.Y_AXIS));;
		JLabel locationTournament = new JLabel("Location ");
		JLabel dateTournament = new JLabel("Date ");
		JLabel typeTournament = new JLabel("Type ");
		JLabel labelGame = new JLabel("Game of the tournament");
		JRadioButton rbtnPlayers = new JRadioButton("Players");
		JRadioButton rbtnTeams = new JRadioButton("Teams");
		JButton addParticipant = new CustomButton("Add");
		JCheckBox btnShuffleParts = new JCheckBox("Random draw");
		ButtonGroup btnGrp = new ButtonGroup();

		/* By default, players are displayed */
		isPlayerSelected = true;
		List<Participant> listP = new ArrayList<Participant>(this.controller.getSortedPlayers());
		Participant[] playersArray = listP.toArray(new Participant[listP.size()]);

		this.cbTournament = new JComboBox<String>();
		this.comboBox = new JComboBox<Game>();
		this.listParticipant = new JList<Participant>(playersArray);
		this.listParticipant.setVisibleRowCount(5);
		this.listParticipant.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		rbtnPlayers.setSelected(true);
		dateModel.setSelected(true);
		selectedDate = (Date) datePicker.getModel().getValue();	

		panelName.add(locationTournament);
		panelName.add(textLocation);
		panelDate.add(dateTournament);
		panelDate.add(datePicker);
		panelType.add(typeTournament);
		panelType.add(cbTournament);
		panelCB.add(labelGame);
		panelCB.add(comboBox);
		btnGrp.add(rbtnPlayers);	// Group the two radio buttons
		btnGrp.add(rbtnTeams);
		panelRadio.add(rbtnPlayers);
		panelRadio.add(rbtnTeams);
		panelRadListAdd.add(panelRadio);
		panelRadListAdd.add(new JScrollPane(this.listParticipant));
		panelRadListAdd.add(addParticipant);
		panelShuffle.add(panelRadListAdd);
		panelShuffle.add(btnShuffleParts);
		panelParticipants.add(panelShuffle);
		content.add(panelName);
		
		/* All is centered */
		panelName.setAlignmentX(CENTER_ALIGNMENT);
		datePicker.setAlignmentX(CENTER_ALIGNMENT);
		panelType.setAlignmentX(CENTER_ALIGNMENT);
		panelCB.setAlignmentX(CENTER_ALIGNMENT);
		panelParticipants.setAlignmentX(CENTER_ALIGNMENT);

		/* Adding all the components to the main panel */
		this.displaySave();

		datePicker.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selectedDate = (Date) datePicker.getModel().getValue();				
			}
		});

		/* Check the selected type of tournament */
		cbTournament.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED){
					type = (String) arg0.getItem();
				} else {
					type = null;
				}
			}
		});

		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					game = (Game) event.getItem();
				} else {
					game = null;
				}
			}
		});

		/* When a radio button is selected to display players
		 * or teams in the list of participants */
		rbtnPlayers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				isPlayerSelected = true;
				List<Participant> listP = new ArrayList<Participant>(controller.getSortedPlayers());
				Participant[] playersArray = listP.toArray(new Participant[listP.size()]);
				listParticipant.setListData(playersArray);
				refreshPanel();
			}
		});

		rbtnTeams.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				isPlayerSelected = false;
				List<Participant> listP = new ArrayList<Participant>(controller.getSortedTeams());
				Participant[] teamsArray = listP.toArray(new Participant[listP.size()]);
				listParticipant.setListData(teamsArray);
				refreshPanel();
			}
		});

		/* When the button add participant is clicked, a popup will appear 
		 * to add players or teams.
		 * When the popup is closed by the user, listParticpant is updated. 
		 * */
		addParticipant.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (isPlayerSelected) {
					JDialog popup = new popup(new ViewAddPlayer(controller));
					popup.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent e) {
							rbtnPlayers.doClick();
						}
					});
				} else {
					JDialog popup = new popup(new ViewAddTeam(controller));
					popup.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent e) {
							rbtnTeams.doClick();
						}
					});
				}
			}
		});

		/* If the user want to shuffle the participants into the tournament */
		btnShuffleParts.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (btnShuffleParts.isSelected()) {
					randomDraw = true;
				} else {
					randomDraw = false;
				}
			}
		});
	}
	
	@Override
	protected boolean checkFields() {
		if (type == null) {
			JOptionPane.showMessageDialog(content, "You must select a type of tournament !",
					"Any type selected !", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (game == null) {
			JOptionPane.showMessageDialog(content, "You must select a game for the tournament !",
					"Any game selected !", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	@Override
	protected void displaySave() {
		super.displaySave();
		
		ArrayList<Game> games = (ArrayList<Game>) this.controller.getSortedGames();

		cbTournament.removeAllItems();
		cbTournament.addItem(null);
		cbTournament.addItem("Simple Elimination");
		//cbTournament.addItem("Double Elimination");		

		comboBox.removeAllItems();
		comboBox.addItem(null);
		
		for (Game g : games) {
			comboBox.addItem(g);
		}
		
		content.add(panelDate);
		content.add(panelType);
		content.add(panelCB);
		content.remove(panelParticipants);
		content.add(panelParticipants);

		refreshPanel();
	}

	@Override
	protected void displayEdit(Tournament tournament) {
		super.displayEdit(tournament);

		textLocation.setText(tournament.getLocation());

		List<Participant> tournamentParticipants = tournament.getParticipants();
		List<Integer> selectedParticipants = new ArrayList<Integer>();
		for (int i = 0; i < listParticipant.getModel().getSize(); i++) {
			if (tournamentParticipants.contains(listParticipant.getModel().getElementAt(i)))
				selectedParticipants.add(i);
		}
		int[] arraySelected = new int[selectedParticipants.size()];

		for (int i = 0; i < arraySelected.length; i++)
			arraySelected[i] = selectedParticipants.get(i);

		listParticipant.setSelectedIndices(arraySelected);

		refreshPanel();
	}

	@Override
	protected void edit() {
		if (!checkFields()) // Check all the fields
			return;
		
		List<Participant> newParticipants = listParticipant.getSelectedValuesList();
		Tournament tournament = new SimpleElimination(selectedDate, toEdit.getGame());

		for (Participant p : newParticipants)
			tournament.addParticipant(p);
		
		toEdit.setLocation(textLocation.getText());
		List<Participant> oldParticipants = toEdit.getParticipants();

		for (Participant p : new ArrayList<Participant>(oldParticipants)) {
			if (!newParticipants.contains(p))
				toEdit.removeParticipant(p);
		}

		for (Participant p : new ArrayList<Participant>(newParticipants)) {
			if (!oldParticipants.contains(p))
				toEdit.addParticipant(p);
		}

		JOptionPane.showMessageDialog(this, "The tournament on " + toEdit.getGame() + " has successfully been updated!",
				"Team edited", JOptionPane.INFORMATION_MESSAGE);
		isEditing = false;
		displaySave();

		if (viewList != null)
			viewList.makeList();
	}

	@Override
	protected void save() {
		String location = textLocation.getText();
		Tournament tournament;
		
		if (type == "Simple Elimination") {
			tournament = (location == null || location.length() == 0) 
					? new SimpleElimination(selectedDate, game) : new SimpleElimination(selectedDate, game, location);
		} else {
			tournament = (location == null || location.length() == 0) 
					? new DoubleElimination(selectedDate, game) : new DoubleElimination(selectedDate, game, location);
		}
		
		List<Participant> parts =  listParticipant.getSelectedValuesList();

		for (Participant p : parts)
			tournament.addParticipant(p);

		if (randomDraw) {
			tournament.randomDraw();
		}

		try {
			tournament.initializeMatchs();
			this.controller.addTournament(tournament);
			JOptionPane.showMessageDialog(this, "The tournament on "+ game + " has been successfully added!", "Tournament " + game + " added", JOptionPane.INFORMATION_MESSAGE);
		} catch (NotEnoughParticipantsException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Wrong number of participants !", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (viewList != null)
			viewList.makeList();

		clear();
	}

	private class popup extends JDialog {
		public popup(JPanel jp) {
			super(fenetre, "Add");
			super.setAlwaysOnTop(true);
			super.setLocationRelativeTo(null);
			super.add(jp);
			super.pack();
			super.setVisible(true);
		}
	}

	@Override
	protected void clear() {
		textLocation.setText("");
		listParticipant.clearSelection();
		comboBox.setSelectedItem(null);
	}

	/*
	 * Private class which permit to format the date
	 */
	private class DateLabelFormatter extends AbstractFormatter {

		private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

		@Override
		public Object stringToValue(String text) throws ParseException {
			return dateFormatter.parseObject(text);
		}

		@Override
		public String valueToString(Object value) throws ParseException {
			if (value != null) {
				Calendar cal = (Calendar) value;
				return dateFormatter.format(cal.getTime());
			}

			return "";
		}

	}
}
