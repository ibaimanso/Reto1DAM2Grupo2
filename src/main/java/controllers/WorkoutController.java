package controllers;

public class WorkoutController {
	
	private static WorkoutController instance = null;
	
	public WorkoutController() {}
	
	public static WorkoutController getInstance() {
		if (null == instance) {
			instance = new WorkoutController();
		}
		return instance;
	}
}
