package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;

import controller.Controller;

@SuppressWarnings("serial")
public abstract class ViewList<T> extends JPanel {

	protected Controller controller;
	protected ImageIcon imgDelete, imgEdit;
	protected JLabel title;
	protected boolean delete, edit;
	protected ViewAdd<T> viewAdd;
	protected String name;
	
	public ViewList(Controller controller, boolean delete, String name) {
		this.controller = controller;
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.delete = delete;
		this.name = name;
		this.edit = false;
		
		title = new JLabel("List of " + name);
		title.setFont(new Font("defaultFont", Font.BOLD, 15));
		title.setAlignmentX(CENTER_ALIGNMENT);

		/* Creation of the edit image button */
		imgEdit = new ImageIcon(getClass().getResource("/res/edit.png"));
		Image imageEdit = imgEdit.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
		imgEdit = new ImageIcon(imageEdit);

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
	
	void makeList() {
		this.removeAll();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(title);

		/* Empty border for the outside (kind of margin) and gray border for the inside */
		title.setBorder(new CompoundBorder(
				BorderFactory.createEmptyBorder(15, 0, 15, 0),
				BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray)));
	}
	
	void setViewAdd(ViewAdd<T> viewAdd) {
		this.viewAdd = viewAdd;
		this.edit = true;
		makeList();
	}
	
	/**
	 * Refresh the list when a displays changes.
	 */
	void refreshList() {
		this.repaint();
		this.revalidate();
	}
	
	void noElement() {
		this.removeAll();
		this.setLayout(new GridBagLayout());
		JLabel empty = new JLabel("There isn't any " + name + ". You can add one via the " + name.substring(0, 1).toUpperCase() + name.substring(1) + " menu.");
		this.add(empty);
		refreshList();
	}
	
	abstract void delete(T t, JPanel line);
}
