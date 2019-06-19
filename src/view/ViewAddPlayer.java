package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.Controller;
import controller.exceptions.PlayerAlreadyExistsException;
import tournament.Game;
import tournament.Player;

@SuppressWarnings("serial")
public class ViewAddPlayer extends ViewAdd<Player> {

	private JPanel panelNN, panelFN, panelLN, panelCB;
	private JTextField nickname, fname, lname;
	private JComboBox<Game> comboBox;
	private Game preferredGame;

	public ViewAddPlayer(Controller controller, ViewList<Player> viewList) {
		this(controller);
		this.viewList = viewList;
	}

	public ViewAddPlayer(Controller controller) {
		super(controller, "player");
		/* Initialization of the attributes */
		this.preferredGame = null;

		/* Initialization of the components */
		//JPanel formStr = new JPanel(new GridLayout(3, 2));
		JLabel labelN = new JLabel("Nickname* ");
		JLabel labelF = new JLabel("Firstname ");
		JLabel labelL = new JLabel("Lastname ");
		JLabel labelGame = new JLabel("Preferred game ");

		nickname = new JTextField(20);
		fname = new JTextField(20);
		lname = new JTextField(20);
		comboBox = new JComboBox<Game>();
		panelCB = new JPanel(new FlowLayout());
		panelNN = new JPanel(new FlowLayout());
		panelFN = new JPanel(new FlowLayout());
		panelLN = new JPanel(new FlowLayout());

		panelNN.add(labelN);
		panelNN.add(nickname);
		panelFN.add(labelF);
		panelFN.add(fname);
		panelLN.add(labelL);
		panelLN.add(lname);
		panelCB.add(labelGame);
		panelCB.add(comboBox);

		content.add(panelNN);
		content.add(panelFN);
		content.add(panelLN);

		panelNN.setAlignmentX(CENTER_ALIGNMENT);
		panelFN.setAlignmentX(CENTER_ALIGNMENT);
		panelLN.setAlignmentX(CENTER_ALIGNMENT);
		panelCB.setAlignmentX(CENTER_ALIGNMENT);
		
		displaySave();

		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					preferredGame = (Game) event.getItem();
				} else {
					preferredGame = null;
				}
			}
		});
	}

	@Override
	protected void displayEdit(Player player) {
		super.displayEdit(player);
		
		content.remove(panelCB);
		nickname.setText(toEdit.getName());
		fname.setText(toEdit.getFName());
		lname.setText(toEdit.getLName());

		// TODO add the combobox with checkbox for the games

		this.add(editCancel, BorderLayout.SOUTH);
		refreshPanel();
	}

	@Override
	protected void displaySave() {
		super.displaySave();
		
		ArrayList<Game> games = (ArrayList<Game>) this.controller.getSortedGames();
		comboBox.removeAllItems();
		comboBox.addItem(null);
		
		for (Game g : games) {
			comboBox.addItem(g);
		}
		
		content.add(panelCB);
		refreshPanel();
	}

	@Override
	protected void edit() {
		if (!checkFields()) // Check all the fields
			return;

		Player player = new Player(fname.getText(), lname.getText(), nickname.getText());
		if (controller.playerExists(toEdit, player)) {
			JOptionPane.showMessageDialog(this, "The player " + nickname.getText() + " already exists!",
					"Editing not possible", JOptionPane.ERROR_MESSAGE);
			return;
		}

		toEdit.setName(nickname.getText());
		toEdit.setFName(fname.getText());
		toEdit.setLName(lname.getText());

		JOptionPane.showMessageDialog(this, "The player " + toEdit.getName() + " has successfully been updated!",
				"Player edited", JOptionPane.INFORMATION_MESSAGE);
		isEditing = false;

		if (viewList != null)
			viewList.makeList();
		
		displaySave();
	}

	@Override
	protected boolean checkFields() {
		int lengthNN = nickname.getText().length();
		int lengthFN = fname.getText().length();
		int lengthLN = lname.getText().length();

		if (lengthNN == 0) {
			JOptionPane.showMessageDialog(this, "The player must have a nickname!", "No nickname", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (lengthNN > 50) {
			JOptionPane.showMessageDialog(this, "The nickname of the player can't exceed 50 characters!", "Nickname too long", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (lengthFN > 0 && lengthLN == 0) {
			JOptionPane.showMessageDialog(this, "A player with a firstname must have a lastname!", "No lastname", JOptionPane.WARNING_MESSAGE);
			return false;
		} else if (lengthFN == 0 && lengthLN > 0) {
			JOptionPane.showMessageDialog(this, "A player with a lastname must have a firstname!", "No firstname", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if (lengthFN > 50) {
			JOptionPane.showMessageDialog(this, "The firstname of the player can't exceed 50 characters!", "Firstname too long", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (lengthLN > 50) {
			JOptionPane.showMessageDialog(this, "The lastname of the player can't exceed 50 characters!", "Lastname too long", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	@Override
	protected void save() {
		String nn = nickname.getText();
		String fn = fname.getText();
		String ln = lname.getText();
		Player player = null;

		if (fn.isEmpty() && ln.isEmpty() && preferredGame == null)
			player = new Player(nn);
		else if (!fn.isEmpty() && !ln.isEmpty() && preferredGame == null)
			player = new Player(fn, ln, nn);
		else if (fn.isEmpty() && ln.isEmpty() && preferredGame != null)
			player = new Player(nn, preferredGame);
		else if (!fn.isEmpty() && !ln.isEmpty() && preferredGame != null)
			player = new Player(fn, ln, nn, preferredGame);

		try {
			this.controller.addPlayer(player);
			JOptionPane.showMessageDialog(this, "The player " + nn + " has been successfully added!", "Player " + nn + " added", JOptionPane.INFORMATION_MESSAGE);

			if (viewList != null)
				viewList.makeList();

			clear();
		} catch (PlayerAlreadyExistsException e) {
			JOptionPane.showMessageDialog(this, "The player " + nn + " already exists, you can't add it twice!", "Existing player", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	protected void clear() {
		nickname.setText("");
		fname.setText("");
		lname.setText("");
		comboBox.setSelectedItem(null);
	}
}
