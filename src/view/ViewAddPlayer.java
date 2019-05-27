package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;

import controller.Controller;
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

		/* Initialization of the components */
		//JPanel formStr = new JPanel(new GridLayout(3, 2));
		JLabel labelAdd = new JLabel("Add a player");
		JLabel labelN = new JLabel("Nickname: *");
		JLabel labelF = new JLabel("Firstname: ");
		JLabel labelL = new JLabel("Lastname: ");
		JLabel labelGame = new JLabel("Preferred game: ");
		JButton save = new CustomButton("Save the player");
		JButton edit = new CustomButton("Edit the player");
		JButton cancel = new CustomButton("Cancel");

		comboBox = new JComboBox<Game>();
		labelAdd.setFont(new Font("defaultFont", Font.BOLD, 15));

		panelNN = new JPanel(new FlowLayout());
		panelFN = new JPanel(new FlowLayout());
		panelLN = new JPanel(new FlowLayout());
		panelCB = new JPanel(new FlowLayout());
		panelSave = new JPanel(new FlowLayout());
		panelEditCancel = new JPanel(new FlowLayout());

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
		
		content.add(labelAdd);
		content.add(Box.createRigidArea(new Dimension(0, 20)));
		content.add(panelNN);
		content.add(panelFN);
		content.add(panelLN);
		this.add(content, BorderLayout.CENTER);
		
		displayAddPlayer();

		/* Empty border outside, gray border inside */
		this.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20),
				BorderFactory.createMatteBorder(2, 2, 2, 2, Color.gray)));
	}

	void displayEditPlayer(Player player) {
		content.remove(comboBox);
		this.remove(panelSave);
		
		// TODO add the combobox with checkbox for the games
		
		this.add(panelEditCancel, BorderLayout.SOUTH);
	}

	public void playerDeleted(Player player) {
		// TODO
	}

	private void displayAddPlayer() {
		this.remove(panelEditCancel);
		Set<Game> games = this.controller.getGames();
		comboBox.addItem(null);
		for (Game g : games) {
			comboBox.addItem(g);
		}
		content.add(panelCB);
		this.add(panelSave, BorderLayout.SOUTH);
		// TODO
	}

	private void editPlayer() {
		// TODO
	}

	private boolean checkFields() {
		// TODO
		return true;
	}

	private void addPlayer() {
		// TODO
	}

	private void clear() {
		// TODO
	}

	/**
	 * Refresh the panel when the display changes.
	 */
	private void refreshPanel() {
		this.repaint();
		this.revalidate();
	}
}
