package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;

import controller.Controller;
import controller.exceptions.PlayerAlreadyExistsException;
import tournament.Game;
import tournament.Player;

@SuppressWarnings("serial")
public class ViewAddPlayer extends JPanel {

	private Controller controller;
	private ViewListPlayer viewList;
	private Player playerToEdit;
	private JPanel content, panelSave, panelEditCancel, panelNN, panelFN, panelLN, panelCB;
	private JTextField nickname, fname, lname;
	private JComboBox<Game> comboBox;
	private JLabel labelAdd;
	private Game preferredGame;
	private boolean isEditing;

	public ViewAddPlayer(Controller controller, ViewListPlayer viewList) {
		this(controller);
		this.viewList = viewList;
	}

	public ViewAddPlayer(Controller controller) {
		super();
		/* Initialization of the attributes */
		this.controller = controller;
		this.viewList = null;
		this.setLayout(new BorderLayout());
		this.content = new JPanel();
		this.content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		this.isEditing = false;
		this.nickname = new JTextField(20);
		this.fname = new JTextField(20);
		this.lname = new JTextField(20);
		this.preferredGame = null;

		/* Initialization of the components */
		//JPanel formStr = new JPanel(new GridLayout(3, 2));
		JLabel labelN = new JLabel("Nickname* ");
		JLabel labelF = new JLabel("Firstname ");
		JLabel labelL = new JLabel("Lastname ");
		JLabel labelGame = new JLabel("Preferred game ");
		JButton save = new CustomButton("Save the player");
		JButton edit = new CustomButton("Edit the player");
		JButton cancel = new CustomButton("Cancel");

		labelAdd = new JLabel("Add a player");
		labelAdd.setFont(new Font("defaultFont", Font.BOLD, 15));

		panelNN = new JPanel(new FlowLayout());
		panelFN = new JPanel(new FlowLayout());
		panelLN = new JPanel(new FlowLayout());
		panelCB = new JPanel(new FlowLayout());
		panelSave = new JPanel(new FlowLayout());
		panelEditCancel = new JPanel(new FlowLayout());
		comboBox = new JComboBox<Game>();

		panelNN.add(labelN);
		panelNN.add(nickname);
		panelFN.add(labelF);
		panelFN.add(fname);
		panelLN.add(labelL);
		panelLN.add(lname);
		panelCB.add(labelGame);
		panelCB.add(comboBox);
		panelSave.add(save);
		panelEditCancel.add(edit);
		panelEditCancel.add(cancel);
		panelSave.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
		panelEditCancel.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));

		content.add(labelAdd);
		content.add(Box.createRigidArea(new Dimension(0, 20)));
		content.add(panelNN);
		content.add(panelFN);
		content.add(panelLN);
		this.add(content, BorderLayout.CENTER);
		this.labelAdd.setBorder(new CompoundBorder(
				BorderFactory.createEmptyBorder(15, 0, 15, 0),
				BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray)));

		labelAdd.setAlignmentX(CENTER_ALIGNMENT);
		panelNN.setAlignmentX(CENTER_ALIGNMENT);
		panelFN.setAlignmentX(CENTER_ALIGNMENT);
		panelLN.setAlignmentX(CENTER_ALIGNMENT);
		panelCB.setAlignmentX(CENTER_ALIGNMENT);
		panelSave.setAlignmentX(CENTER_ALIGNMENT);
		panelEditCancel.setAlignmentX(CENTER_ALIGNMENT);

		displayAddPlayer();

		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					preferredGame = (Game) event.getItem();
				} else {
					preferredGame = null;
				}
			}
		});

		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (checkFields())
					addPlayer(nickname.getText(), fname.getText(), lname.getText(), preferredGame);
			}
		});

		/* When the button to cancel an editing is clicked */
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				displayAddPlayer();
			}
		});

		/* When the button to edit a player is clicked */
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				editPlayer();
			}
		});

		/* Empty border outside, gray border inside */
		this.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20),
				BorderFactory.createMatteBorder(2, 2, 2, 2, Color.gray)));
	}

	void displayEditPlayer(Player player) {
		this.labelAdd.setText("Edit a player");
		content.remove(panelCB);
		this.remove(panelSave);
		this.isEditing = true;
		this.playerToEdit = player;

		nickname.setText(playerToEdit.getName());
		fname.setText(playerToEdit.getFName());
		lname.setText(playerToEdit.getLName());

		// TODO add the combobox with checkbox for the games, finish getGamesPlayer

		this.add(panelEditCancel, BorderLayout.SOUTH);
		refreshPanel();
	}

	public void playerDeleted(Player player) {
		if (isEditing && playerToEdit == player) {
			displayAddPlayer();
		}
	}

	private void displayAddPlayer() {
		clear();
		this.isEditing = false;
		this.labelAdd.setText("Add a player");
		this.remove(panelEditCancel);
		Set<Game> games = this.controller.getGames();
		comboBox.removeAllItems();
		comboBox.addItem(null);
		for (Game g : games) {
			comboBox.addItem(g);
		}
		content.add(panelCB);
		this.add(panelSave, BorderLayout.SOUTH);
		refreshPanel();
	}

	private void editPlayer() {
		if (!checkFields()) // Check all the fields
			return;

		Player player = new Player(fname.getText(), lname.getText(), nickname.getText());
		if (controller.playerExists(playerToEdit, player)) {
			JOptionPane.showMessageDialog(this, "The player " + nickname.getText() + " already exists!",
					"Editing not possible", JOptionPane.ERROR_MESSAGE);
			return;
		}

		playerToEdit.setName(nickname.getText());
		playerToEdit.setFName(fname.getText());
		playerToEdit.setLName(lname.getText());

		JOptionPane.showMessageDialog(this, "The player " + playerToEdit.getName() + " has successfully been updated!",
				"Player edited", JOptionPane.INFORMATION_MESSAGE);
		isEditing = false;
		displayAddPlayer();

		if (viewList != null)
			viewList.makeList();
	}

	private boolean checkFields() {
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

	private void addPlayer(String nn, String fn, String ln, Game game) {
		Player player = null;

		if (fn.isEmpty() && ln.isEmpty() && game == null)
			player = new Player(nn);
		else if (!fn.isEmpty() && !ln.isEmpty() && game == null)
			player = new Player(fn, ln, nn);
		else if (fn.isEmpty() && ln.isEmpty() && game != null)
			player = new Player(nn, game);
		else if (!fn.isEmpty() && !ln.isEmpty() && game != null)
			player = new Player(fn, ln, nn, game);

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

	// TODO
	private JScrollPane getGamesPlayer(Player p) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		Set<Game> gamesPlayer = p.getGames().keySet();


		return new JScrollPane(panel);
	}

	private void clear() {
		nickname.setText("");
		fname.setText("");
		lname.setText("");
		comboBox.setSelectedItem(null);
	}

	/**
	 * Refresh the panel when the display changes.
	 */
	private void refreshPanel() {
		this.repaint();
		this.revalidate();
	}
}
