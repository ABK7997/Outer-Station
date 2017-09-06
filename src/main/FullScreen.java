package main;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.JFrame;

public class FullScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FullScreen() {
		setUndecorated(true);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GraphicsEnvironment graphEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle maximumWindowBounds = graphEnv.getMaximumWindowBounds();
		setBounds(maximumWindowBounds);
	}

}
