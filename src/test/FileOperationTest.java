package test;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import controller.SaveImpossibleException;
import tournament.Game;
import tournament.Player;
import tournament.Team;

public class FileOperationTest {
	
	Controller controller;
	
	@Before
	public void setup() {
		controller = new Controller();
		Game j1 = new Game("jeux 1");
		Player j = new Player("joueur 1", j1);
		Team t = new Team("team 1", j1);
		t.addMember(j);
		controller.addGame(j1);
		controller.addPlayer(j);
		controller.addTeam(t);
	}

	@Test
	public void test() {
		try {
			controller.save("/tmp/save1.txt");
		} catch (SaveImpossibleException e) {
			fail(e.getMessage());
		}
	}

}
