package main;

import controller.Controller;
import view.ViewMain;

/**
 * Main class.
 * @author Group
 * @version 1.0
 */
public class Main {
	
	public static void main(String[] args) {
		// Run the application
		Controller controller = new Controller();
		new ViewMain(controller, "Esport tournament management");
	}
}
