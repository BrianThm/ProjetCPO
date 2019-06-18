package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;

import controller.Controller;

@SuppressWarnings("serial")
public abstract class ViewAdd<T> extends JPanel {

	protected ViewList<T> viewList;
	protected Controller controller;
	protected JPanel content, panelSave, editCancel;
	protected JLabel title;
	protected String name;
	protected T toEdit;
	protected boolean isEditing;
	
	public ViewAdd(Controller controller, ViewList<T> viewList, String name) {
		this(controller, name);
		this.viewList = viewList;
	}
	
	public ViewAdd(Controller controller, String name) {
		super();
		/* Initialization of the attributes */
		this.controller = controller;
		this.viewList = null;
		this.name = name;
		this.setLayout(new BorderLayout());
		this.content = new JPanel();
		this.content.setLayout(new BoxLayout(this.content, BoxLayout.Y_AXIS));
		this.panelSave = new JPanel(new FlowLayout());
		this.editCancel = new JPanel(new FlowLayout());
		this.isEditing = false;
		
		this.title = new JLabel("Add a " + name);
		this.title.setFont(new Font("defaultFont", Font.BOLD, 15));
		this.title.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
		
		JButton btnSave = new CustomButton("Save the " + name);
		JButton btnEdit = new CustomButton("Edit the " + name);
		JButton btnCancel = new CustomButton("Cancel");
		
		editCancel.add(btnEdit);
		editCancel.add(btnCancel);
		panelSave.add(btnSave);
		content.add(title);
		content.add(Box.createRigidArea(new Dimension(0, 20)));
		content.setBorder(BorderFactory.createEmptyBorder(15, 5, 15, 5));
		panelSave.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
		editCancel.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));

		/* All is centered */
		title.setAlignmentX(CENTER_ALIGNMENT);
		editCancel.setAlignmentX(CENTER_ALIGNMENT);
		panelSave.setAlignmentX(CENTER_ALIGNMENT);
		
		/* Adding all the components to the main panel */
		this.add(content, BorderLayout.CENTER);
		this.add(panelSave, BorderLayout.SOUTH);
		this.displaySave();
		
		/* When the button to cancel an editing is clicked */
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				displaySave();
			}
		});

		/* When the button to edit a player is clicked */
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				edit();
			}
		});

		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (checkFields())
					save();
			}
		});

		/* Empty border outside, gray border inside */
		this.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20),
				BorderFactory.createMatteBorder(2, 2, 2, 2, Color.gray)));
	}
	
	protected void displaySave() {
		clear();
		this.isEditing = false;
		title.setText("Add a " + name.toLowerCase());
		this.remove(editCancel);
	}
	
	protected void displayEdit(T t) {
		this.title.setText("Edit a " + name);
		this.remove(panelSave);
		this.isEditing = true;
		this.toEdit = t;
		this.add(editCancel, BorderLayout.SOUTH);
	}
	
	protected void deleted(T t) {
		if (isEditing && toEdit == t)
			displaySave();
	}
	
	protected void edit() {
		if (!checkFields()) // Check all the fields
			return;
	}
	
	/**
	 * Refresh the panel when the display changes.
	 */
	protected void refreshPanel() {
		this.repaint();
		this.revalidate();
	}
	
	protected abstract void save();
	
	protected abstract boolean checkFields();
	
	protected abstract void clear();
}
