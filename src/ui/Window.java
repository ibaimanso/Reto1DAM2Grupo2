package ui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ui.panels.*;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;

	public static int EXERCISE_PANEL = 0;
	public static int HISTORY_PANEL  = 1;
	public static int LOGIN_PANEL    = 2;
	public static int REGISTER_PANEL = 3;
	public static int WORKOUT_PANEL  = 4;

	public JPanel[] panels = {
		new ExercisePanel(),
		new HistoryPanel(),
		new LoginPanel(),
		new RegisterPanel(),
		new WorkoutPanel()
	};

	public Window() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("GYM App");
		this.setVisible(true);
		this.showPanel(Window.LOGIN_PANEL);
	}
	
	public void showPanel(int panelToShow) {
		for (JPanel panel: this.panels) {
			panel.setVisible(false);
		} 
		panels[panelToShow].setVisible(true);
		this.pack();
	}
	
	public void start() {
		
	}
}
