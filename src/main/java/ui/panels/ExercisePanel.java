package ui.panels;

import javax.swing.JPanel;

import ui.Window;

public class ExercisePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Window window = null;

	public ExercisePanel(Window window) {
		this.window = window;
		this.setVisible(true);
		this.setSize(600, 500);
		
	}
	
}
