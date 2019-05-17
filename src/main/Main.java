package main;

import controller.Controller;
import view.ViewMain;

public class Main {
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Controller controller = new Controller();
		ViewMain view = new ViewMain(controller, "Esport tournament management");
	}
}
