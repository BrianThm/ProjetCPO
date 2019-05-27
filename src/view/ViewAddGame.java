package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Controller;
import controller.GameAlreadyExistsException;
import tournament.Game;

/**
 * The ViewAddGame is the view which allows to create and edit a game.
 * @author Group
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ViewAddGame extends JPanel {

	private Controller controller;
	private JLabel labelImage;
	private JPanel content, editCancel, panelSave;
	private ImageIcon imgGame;
	private JTextField textGame;
	private ViewListGame viewList;
	private JCheckBox hasImage;
	private JButton btnAddImg, btnSave;
	private Game gameToEdit;
	private boolean hasChecked, isEditing;

	/**
	 * Constructor for the view when it is associated to the view which displays the list of games.
	 * @param controller The controller.
	 * @param viewList The view which displays the games and allow the user to edit a game.
	 */
	public ViewAddGame(Controller controller, ViewListGame viewList) {
		this(controller);
		this.viewList = viewList;
	}

	/**
	 * Constructor for the view when it's displayed alone.
	 * @param controller The controller.
	 */
	public ViewAddGame(Controller controller) {
		super();
		/* Initialization of the attributes */
		this.controller = controller;
		this.viewList = null;
		this.setLayout(new BorderLayout());
		this.content = new JPanel();
		this.content.setLayout(new BoxLayout(this.content, BoxLayout.Y_AXIS));
		this.textGame = new JTextField(20);
		this.imgGame = null;
		this.hasChecked = false;
		this.panelSave = new JPanel(new FlowLayout());
		this.isEditing = false;
		
		/* Initialization of the components */
		JLabel labelAdd = new JLabel("Add a game");
		JLabel nameGame = new JLabel("Name: ");
		JPanel nameText = new JPanel(new FlowLayout());
		JPanel checkBtn = new JPanel(new FlowLayout());
		JButton btnEdit = new JButton("Edit the game");
		JButton btnCancel = new JButton("Cancel");

		editCancel = new JPanel(new FlowLayout());
		btnSave = new JButton("Save the game");
		btnAddImg = new JButton("Add an image");
		hasImage = new JCheckBox("Image associated to the game");
		labelImage = new JLabel();
		labelAdd.setFont(new Font("defaultFont", Font.BOLD, 15));
		labelAdd.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
		nameText.add(nameGame);
		nameText.add(textGame);
		checkBtn.add(hasImage);
		checkBtn.add(btnAddImg);
		editCancel.add(btnEdit);
		editCancel.add(btnCancel);
		
		/* All is centered */
		labelAdd.setAlignmentX(Component.CENTER_ALIGNMENT);
		nameText.setAlignmentX(Component.CENTER_ALIGNMENT);
		checkBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		labelImage.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSave.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		/* Adding the components to the main panel (panel without buttons save/edit) */
		content.add(labelAdd);
		content.add(Box.createRigidArea(new Dimension(0, 20)));
		content.add(nameText);
		content.add(checkBtn);
		content.add(labelImage);
		panelSave.add(btnSave);
		btnAddImg.setEnabled(false);
		content.setBorder(BorderFactory.createEmptyBorder(10, 5, 15, 5));
		
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
		
		/* When the button to save a game is clicked */
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (checkFields())
					addGame(imgGame, textGame.getText());
			}
		});

		/* When the button to edit a game is clicked */
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				editGame();
			}
		});
		
		/* When the button to cancel an editing is clicked */
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				displayAddGame();
			}
		});
		
		/* Adding all the components to the main panel */
		this.add(content, BorderLayout.CENTER);
		this.add(panelSave, BorderLayout.SOUTH);
		
		/* Empty border outside, gray border inside */
		this.setBorder(new CompoundBorder(
				BorderFactory.createEmptyBorder(20, 20, 20, 20),
				BorderFactory.createMatteBorder(2, 2, 2, 2, Color.gray)));
	}
	
	/**
	 * Displays the edit and cancel buttons and fill the fields with the game to edit.
	 * @param game The game to edit.
	 */
	void displayEditGame(Game game) {
		gameToEdit = game;
		isEditing = true;
		this.remove(panelSave);
		this.add(editCancel, BorderLayout.SOUTH);

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
	 * Check if the game deleted is displayed in the edit panel. If it is, displays the add panel.
	 * @param game The game which has been deleted.
	 */
	void gameDeleted(Game game) {
		if (isEditing && gameToEdit == game) {
			displayAddGame();
		}
	}
	
	/**
	 * Display the view to add a game, remove the edit and cancel buttons and displays the save button.
	 */
	private void displayAddGame() {
		this.isEditing = false;
		clear();
		imgGame = null;
		this.remove(editCancel);
		this.add(panelSave, BorderLayout.SOUTH);
		refreshPanel();
	}
	
	/**
	 * Edits the game whith the filled fields.
	 */
	private void editGame() {
		if (!checkFields()) // Check all the fields
			return;
		
		// Check if the game the user is trying to edit already exists
		Game game = new Game(textGame.getText());
		if (controller.gameExists(gameToEdit, game)) {
			JOptionPane.showMessageDialog(this, "The game " + textGame.getText() + " already exists!", "Editing not possible", JOptionPane.ERROR_MESSAGE);
			return;
		}

		gameToEdit.setName(textGame.getText());

		if (imgGame != null) {
			gameToEdit.setImage(imgGame);
		} else if (gameToEdit.hasImage() && imgGame == null) {
			gameToEdit.removeImage();
		}

		JOptionPane.showMessageDialog(this, "The game " + gameToEdit.getName() + " has successfully been updated!", "Game edited", JOptionPane.INFORMATION_MESSAGE);
		isEditing = false;
		displayAddGame();

		if (viewList != null)
			viewList.makeList();
	}

	/**
	 * Method which is executed when the user clicks on the add image button.
	 * Opens a file chooser and let the user choose an image. He can't choose something else.
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
			JOptionPane.showMessageDialog(this, "You can only choose an image file!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Check the fields when the user wants to save or edit a game.
	 * @return False if all the fields aren't well filled, true if all is ok.
	 */
	private boolean checkFields() {
		String name = textGame.getText();
		if (name.length() == 0) {
			JOptionPane.showMessageDialog(this, "The game must have a name!", "Empty game name", JOptionPane.WARNING_MESSAGE);
			return false;
		} else if (name.length() > 50) {
			JOptionPane.showMessageDialog(this, "The name of the game can't exceed 50 characters!", "Name too long", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (hasChecked && imgGame == null) {
			JOptionPane.showMessageDialog(this,
					"The game is supposed to have an associated image! Uncheck the checkbox if it hasn't.",
					"No image", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	/**
	 * Add a game with the user inputs.
	 * @param image The image of the game (can be null).
	 * @param name The name of the game.
	 */
	private void addGame(ImageIcon image, String name) {
		// Create a game with an image if it isn't null.
		Game game = (image == null) ? new Game(name) : new Game(name, image);
		try {
			controller.addGame(game);
			JOptionPane.showMessageDialog(this, "The game " + name + " has been successfully added!", "Game added", JOptionPane.INFORMATION_MESSAGE);

			if (viewList != null) 
				viewList.makeList();

			clear();
		} catch (GameAlreadyExistsException e) {
			JOptionPane.showMessageDialog(this, "This game already exists, you can't add it twice!", "Existing game", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Clear all the fields.
	 */
	private void clear() {
		textGame.setText("");
		hasImage.setSelected(false);
	}

	/**
	 * Refresh the panel when the display changes.
	 */
	private void refreshPanel() {
		this.repaint();
		this.revalidate();
	}
}
