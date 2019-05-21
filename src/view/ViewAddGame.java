package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Controller;

@SuppressWarnings("serial")
public class ViewAddGame extends JPanel {
	
	private Controller controller;
	private JLabel labelImage;
	private JPanel content;
	
	public ViewAddGame(Controller controller) {
		this.controller = controller;
		this.setLayout(new BorderLayout());
		
		content = new JPanel();
		JLabel labelAdd = new JLabel("Add a game");
		JLabel nameGame = new JLabel("Name : ");
		JTextField textGame = new JTextField(20);
		JPanel nameText = new JPanel(new FlowLayout());
		JCheckBox hasImage = new JCheckBox("Image associated to the game");
		JButton btnAddImg = new JButton("Add an image");
		JPanel checkBtn = new JPanel(new FlowLayout());
		JButton btnSave = new JButton("Save the game");
		labelImage = new JLabel();
		
		labelAdd.setFont(new Font("defaultFont", Font.BOLD, 15));
		labelAdd.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
		nameText.add(nameGame);
		nameText.add(textGame);
		checkBtn.add(hasImage);
		checkBtn.add(btnAddImg);
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		nameText.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
		
		content.add(labelAdd);
		content.add(nameText);
		content.add(checkBtn);
		content.add(labelImage);
		content.add(btnSave);
		btnAddImg.setEnabled(false);
		content.setBorder(new CompoundBorder(
				BorderFactory.createMatteBorder(2, 2, 2, 2, Color.gray),
				BorderFactory.createEmptyBorder(10, 5, 50, 80)));
		
		hasImage.addItemListener(new ItemListener() {
			@Override
		    public void itemStateChanged(ItemEvent e) {
		    	btnAddImg.setEnabled(e.getStateChange() == ItemEvent.SELECTED);
			}
		});
		
		btnAddImg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addImage();
			}
		});
		
		this.add(content, BorderLayout.CENTER);
		this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	}
	
	private void addImage() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
		fileChooser.showOpenDialog(this);
		File fileImg = fileChooser.getSelectedFile();
		ImageIcon img = new ImageIcon(fileImg.getPath());
		labelImage = new JLabel(img);
		// TO FIX
		refreshPanel();
	}
	
	private void refreshPanel() {
		this.repaint();
		this.revalidate();
		this.updateUI();
	}
}
