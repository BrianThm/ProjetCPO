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

@SuppressWarnings("serial")
public class ViewAddGame extends JPanel {

	private Controller controller;
	private JLabel labelImage;
	private JPanel content, editCancel, panelSave;
	private ImageIcon imgGame;
	private JTextField textGame;
	private boolean hasChecked, isEditing;
	private ViewListGame viewList;
	private JCheckBox hasImage;
	private JButton btnAddImg, btnSave;
	private Game gameToEdit;

	public ViewAddGame(Controller controller, ViewListGame viewList) {
		this(controller);
		this.viewList = viewList;
	}

	public ViewAddGame(Controller controller) {
		super();
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

		labelAdd.setAlignmentX(Component.CENTER_ALIGNMENT);
		nameText.setAlignmentX(Component.CENTER_ALIGNMENT);
		checkBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		labelImage.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSave.setAlignmentX(Component.CENTER_ALIGNMENT);

		content.add(labelAdd);
		content.add(Box.createRigidArea(new Dimension(0, 20)));
		content.add(nameText);
		content.add(checkBtn);
		content.add(labelImage);
		panelSave.add(btnSave);
		btnAddImg.setEnabled(false);
		content.setBorder(BorderFactory.createEmptyBorder(10, 5, 15, 5));

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

		btnAddImg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addImage();
			}
		});

		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (checkFields())
					addGame(imgGame, textGame.getText());
			}
		});

		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				editGame();
			}
		});

		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				displayAddGame();
			}
		});
		
		this.add(content, BorderLayout.CENTER);
		this.add(panelSave, BorderLayout.SOUTH);
		this.setBorder(new CompoundBorder(
				BorderFactory.createEmptyBorder(20, 20, 20, 20),
				BorderFactory.createMatteBorder(2, 2, 2, 2, Color.gray)));
	}

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
	
	void gameDeleted(Game game) {
		if (isEditing && gameToEdit == game) {
			displayAddGame();
		}
	}

	private void displayAddGame() {
		this.isEditing = false;
		clear();
		imgGame = null;
		this.remove(editCancel);
		this.add(panelSave, BorderLayout.SOUTH);
		refreshPanel();
	}

	private void editGame() {
		if (checkFields()) {
			gameToEdit.setName(textGame.getText());

			if (imgGame != null) {
				gameToEdit.setImage(imgGame);
			} else if (gameToEdit.hasImage() && imgGame == null) {
				gameToEdit.removeImage();
			}

			JOptionPane.showMessageDialog(this, "The game " + gameToEdit.getName() + " has successfully been updated!", "Game edited", JOptionPane.INFORMATION_MESSAGE);
			isEditing = false;
			
			if (viewList != null)
				viewList.makeList();
		}
	}

	private void addImage() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
		fileChooser.showOpenDialog(this);
		File fileImg = fileChooser.getSelectedFile();
		BufferedImage bimg = null;
		try {
			bimg = ImageIO.read(fileImg);
			int width = bimg.getWidth();
			int height = bimg.getHeight();
			int newHeight = (500 * height) / width;
			ImageIcon img = new ImageIcon(bimg);
			Image imgResize = img.getImage().getScaledInstance(500, newHeight, Image.SCALE_SMOOTH);
			imgGame = new ImageIcon(imgResize);
			labelImage.setIcon(imgGame);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "You can only choose an image file!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

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

	private void addGame(ImageIcon image, String name) {
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

	private void clear() {
		textGame.setText("");
		hasImage.setSelected(false);
	}

	private void refreshPanel() {
		this.repaint();
		this.revalidate();
	}
}
