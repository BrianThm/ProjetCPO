package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;

import controller.Controller;

/**
 * The ViewAddTeam is the view wich allow to create an edit a team.
 * @author Group
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ViewAddTeam extends JPanel {

	private Controller controller;
	private JPanel content, panelSave, editCancel;
	private ViewListTeam viewList;
	private JTextField textTeam;
	private JLabel title;
	private boolean isEditing;
	
	public ViewAddTeam(Controller controller) {
		super();
		/* Initialization of the attributes */
		this.controller = controller;
		this.viewList = null;
		this.setLayout(new BorderLayout());
		this.content = new JPanel();
		this.content.setLayout(new BoxLayout(this.content, BoxLayout.Y_AXIS));
		this.textTeam = new JTextField(20);
		this.panelSave = new JPanel(new FlowLayout());
		this.isEditing = false;
		
		/* Initialization of the components */
		JPanel panelName = new JPanel(new FlowLayout());
		JLabel nameTeam = new JLabel("Name: ");
		JPanel panelSave = new JPanel(new FlowLayout());
		JButton btnSave = new CustomButton("Save the team");
		JButton btnEdit = new CustomButton("Edit the team");
		JButton btnCancel = new CustomButton("Cancel");
		this.title = new JLabel("Add a team");
		this.title.setFont(new Font("defaultFont", Font.BOLD, 15));
		this.title.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
		this.editCancel = new JPanel(new FlowLayout());
		panelName.add(nameTeam);
		panelName.add(textTeam);
		editCancel.add(btnEdit);
		editCancel.add(btnCancel);
		panelSave.add(btnSave);
		
		/* All is centered */
		// TODO
		
		/* Adding all the components to the main panel */
		this.add(content, BorderLayout.CENTER);
		this.add(panelSave, BorderLayout.SOUTH);
		
		/* Empty border outside, gray border inside */
		this.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20),
				BorderFactory.createMatteBorder(2, 2, 2, 2, Color.gray)));
	}
}
