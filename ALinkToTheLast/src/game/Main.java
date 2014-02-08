package game;
import javax.swing.*;
import java.awt.event.*;

public class Main {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame window = new JFrame("Last");
		final GWindow panel = new GWindow(800, 600);
		window.setContentPane(panel);
		window.pack();
		window.setVisible(true);
	}

}
