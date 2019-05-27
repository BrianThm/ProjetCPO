package view;

import java.awt.Color;

import javax.swing.JButton;

/**
 * Custom buttons to have stylish buttons.
 * @author Group
 */
@SuppressWarnings("serial")
public class CustomButton extends JButton {
	
	/**
	 * Creates a gray button with the text in white.
	 */
	public CustomButton(String title) {
		super(title);
		this.setBackground(Color.darkGray);
		this.setOpaque(true);
		this.setForeground(Color.white);
	}
}
