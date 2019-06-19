package view;

import java.awt.GridLayout;

import javax.swing.JPanel;

import controller.Controller;
import tournament.Team;

/**
 * ViewTeam which displays the ViewListTeam next to the ViewAddTeam.
 * @author Group
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ViewTeam extends JPanel {
	private Controller controller;

	/**
	 * Constructor to create the view with the lsit of teams and the form to add a team.
	 * @param controller The controller.
	 */
	public ViewTeam(Controller controller) {
		this.controller = controller;
		// Grid layout with two columns, one for each view
		this.setLayout(new GridLayout(0, 2));

		// The two views need to be linked by each other when there is a modification in one of them.
		ViewList<Team> listTeam = new ViewListTeam(this.controller, true);
		ViewAdd<Team> addTeam = new ViewAddTeam(this.controller, listTeam);
		listTeam.setViewAdd(addTeam);

		this.add(listTeam);
		this.add(addTeam);
	}
}
