package main;

import controller.Controller;
import view.ViewMain;

public class Main {
	
	public static void main(String[] args) {
		Controller controller = new Controller();
		new ViewMain(controller, "Esport tournament management");
	}
}
