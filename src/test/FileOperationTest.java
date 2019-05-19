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
		
		Game cs = new Game("CSGO");
		Game tm = new Game("Trackmania");
		Game lol = new Game("League of Legends");
		
		Player mo = new Player("Morgane", "Cadeau", "Enaxom", cs);
		Player wi = new Player("William", "Mateille", "Wyzedix", cs);
		Player vi = new Player("Ravioli");
		Player ep = new Player("Epsilon");
		
		Team famille = new Team("Famille");
		famille.addMember(mo);
		famille.addMember(wi);
		famille.addMember(vi);
		famille.addMember(ep);
		
		controller.addGame(cs);
		controller.addGame(tm);
		controller.addGame(lol);
		
		controller.addPlayer(mo);
		controller.addPlayer(wi);
		controller.addPlayer(vi);
		controller.addPlayer(ep);
		
		controller.addTeam(famille);
	}

	@Test
	public void testSimpleSave() {
		try {
			controller.save("/tmp/save_test1.txt");
		} catch (SaveImpossibleException e) {
			fail(e.getMessage());
		}
	}

}
