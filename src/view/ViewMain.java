package view;
import javax.swing.JFrame;

public class ViewMain extends JFrame {
	
	public ViewMain(Controller controller, String title) {
		super(title);
		
		
		
		// this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		this.setVisible(true);
	}
}
