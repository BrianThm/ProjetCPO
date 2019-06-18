package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Controller;
import controller.exceptions.GameAlreadyExistsException;
import tournament.Game;

/**
 * The ViewAddGame is the view which allows to create and edit a game.
 * @author Group
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ViewAddGame extends ViewAdd<Game> {

	private JLabel labelImage;
	private ImageIcon imgGame;
	private JTextField textGame;
	private JCheckBox hasImage;
	private JButton btnAddImg;
	private boolean hasChecked;

	/**
	 * Constructor for the view when it is associated to the view which displays the 
	 * list of games.
	 * @param controller The controller.
	 * @param viewList The view which displays the games and allow the user to edit a game.
	 */
	public ViewAddGame(Controller controller, ViewList<Game> viewList) {
		this(controller);
		this.viewList = viewList;
	}

	/**
	 * Constructor for the view when it's displayed alone.
	 * @param controller The controller.
	 */
	public ViewAddGame(Controller controller) {
		super(controller, "game");
		this.imgGame = null;
		this.hasChecked = false;

		/* Initialization of the components */
		JLabel nameGame = new JLabel("Name: ");
		JPanel nameText = new JPanel(new FlowLayout());
		JPanel checkBtn = new JPanel(new FlowLayout());

		editCancel = new JPanel(new FlowLayout());
		btnAddImg = new CustomButton("Add an image");
		hasImage = new JCheckBox("Image associated to the game");
		labelImage = new JLabel();
		nameText.add(nameGame);
		nameText.add(textGame);
		checkBtn.add(hasImage);
		checkBtn.add(btnAddImg);

		/* All is centered */
		nameText.setAlignmentX(Component.CENTER_ALIGNMENT);
		checkBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		labelImage.setAlignmentX(Component.CENTER_ALIGNMENT);

		/* Adding the components to the main panel (panel without buttons save/edit) */
		content.add(Box.createRigidArea(new Dimension(0, 20)));
		content.add(nameText);
		content.add(checkBtn);
		content.add(labelImage);
		btnAddImg.setEnabled(false);

		/* Listeners */

		/* When the checkbox to associate an image is clicked */
		hasImage.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				hasChecked = (e.getStateChange() == ItemEvent.SELECTED);
				if (!hasChecked) {
					imgGame = null;
					labelImage.setIcon(imgGame);
				}
				btnAddImg.setEnabled(hasChecked);
			}
		});

		/* When the button to add an image is clicked */
		btnAddImg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addImage();
			}
		});
	}

	/**
	 * Displays the edit and cancel buttons and fill the fields with the game to edit.
	 * @param game The game to edit.
	 */
	protected void displayEdit(Game game) {
		super.displayEdit(game);
		textGame.setText(game.getName());

		if (game.hasImage()) {
			hasImage.setSelected(true);
			imgGame = game.getImage();
			labelImage.setIcon(imgGame);
		} else {
			hasImage.setSelected(false);
			imgGame = null;
			labelImage.setIcon(imgGame);
		}

		refreshPanel();
	}

	/**
	 * Display the view to add a game, remove the edit and cancel buttons and 
	 * displays the save button.
	 */
	protected void displaySave() {
		super.displaySave();
		imgGame = null;
		this.add(panelSave, BorderLayout.SOUTH);
		refreshPanel();
	}

	/**
	 * Edits the game whith the filled fields.
	 */
	protected void edit() {
		super.edit();

		// Check if the game the user is trying to edit already exists
		Game game = new Game(textGame.getText());
		if (controller.gameExists(toEdit, game)) {
			JOptionPane.showMessageDialog(this, "The game " + textGame.getText() + " already exists!",
					"Editing not possible", JOptionPane.ERROR_MESSAGE);
			return;
		}

		toEdit.setName(textGame.getText());

		if (imgGame != null) {
			toEdit.setImage(imgGame);
		} else if (toEdit.hasImage() && imgGame == null) {
			toEdit.removeImage();
		}

		JOptionPane.showMessageDialog(this, "The game " + toEdit.getName() + " has successfully been updated!",
				"Game edited", JOptionPane.INFORMATION_MESSAGE);
		isEditing = false;
		displaySave();

		if (viewList != null)
			viewList.makeList();
	}

	/**
	 * Method which is executed when the user clicks on the add image button. Opens 
	 * a file chooser and let the user choose an image. He can't choose something 
	 * else.
	 */
	private void addImage() {
		/* Opens a file chooser for image files */
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
		fileChooser.showOpenDialog(this);
		File fileImg = fileChooser.getSelectedFile();

		/* If the cancel button is pressed */
		if (fileImg == null)
			return;

		/* Traitment of the image */
		BufferedImage bimg = null;
		try {
			bimg = ImageIO.read(fileImg);
			int width = bimg.getWidth();
			int height = bimg.getHeight();
			int newHeight = 300, newWidth = 500;

			if (height > width) {
				newWidth = (newHeight * width) / height;
			} else {
				newHeight = (newWidth * height) / width;
			}

			// Resized image
			ImageIcon img = new ImageIcon(bimg);
			Image imgResize = img.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

			// Save the image
			imgGame = new ImageIcon(imgResize);
			labelImage.setIcon(imgGame);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "You can only choose an image file!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	protected void save() {
		String name = textGame.getText();
		// Create a game with an image if it isn't null.
		Game game = (imgGame == null) ? new Game(name) : new Game(name, imgGame);
		try {
			controller.addGame(game);
			JOptionPane.showMessageDialog(this, "The game " + name + " has been successfully added!", "Game added",
					JOptionPane.INFORMATION_MESSAGE);

			if (viewList != null)
				viewList.makeList();

			clear();
		} catch (GameAlreadyExistsException e) {
			JOptionPane.showMessageDialog(this, "This game already exists, you can't add it twice!", "Existing game",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	protected boolean checkFields() {
		String name = textGame.getText();
		if (name.length() == 0) {
			JOptionPane.showMessageDialog(this, "The game must have a name!", "Empty game name",
					JOptionPane.WARNING_MESSAGE);
			return false;
		} else if (name.length() > 50) {
			JOptionPane.showMessageDialog(this, "The name of the game can't exceed 50 characters!", "Name too long",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (hasChecked && imgGame == null) {
			JOptionPane.showMessageDialog(this,
					"The game is supposed to have an associated image! Uncheck the checkbox if it hasn't.", "No image",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	@Override
	protected void clear() {
		if (this.textGame == null)
			this.textGame = new JTextField(20);
		textGame.setText("");
		hasImage.setSelected(false);
	}
}
