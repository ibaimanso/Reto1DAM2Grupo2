package ui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import entities.User;
import ui.panels.*;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;

	public static int EXERCISE_PANEL = 0;
	public static int HISTORY_PANEL  = 1;
	public static int LOGIN_PANEL    = 2;
	public static int REGISTER_PANEL = 3;
	public static int WORKOUT_PANEL  = 4;
	public static int WORKOUT_DETAIL_PANEL = 5;

	public JPanel[] panels = {
		new ExercisePanel(this),
		new HistoryPanel(this),
		new LoginPanel(this),
		new RegisterPanel(this),
		new WorkoutPanel(this),
	};
	
	private User userLogin = null;

	public Window() {
		/*
		for (JPanel panel: this.panels) {
			this.add(panel);
		}
		*/
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Elorrieta GYM");
		//this.setSize(600,500);
		//this.setResizable(false);
		this.setVisible(true);
		this.showPanel(Window.LOGIN_PANEL);
	}
	
	public void showPanel(int panelToShow) {
		this.setContentPane(this.panels[panelToShow]);
		this.setSize(600, 500);
		this.setResizable(false);
	}
	
	public void start() {
		
	}
	
	public User getUserLogin() {
		return this.userLogin;
	}
	
	public void setUserLogin(User userLogin) {
		this.userLogin = userLogin;
	}

	public ExercisePanel getExercisePanel() {
		return (ExercisePanel) this.panels[Window.EXERCISE_PANEL];
	}
	
	public HistoryPanel getHistoryPanel() {
		return (HistoryPanel) this.panels[Window.HISTORY_PANEL];
	}
	
	public WorkoutPanel getWorkoutPanel() {
		return (WorkoutPanel) this.panels[Window.WORKOUT_PANEL];
	}
}