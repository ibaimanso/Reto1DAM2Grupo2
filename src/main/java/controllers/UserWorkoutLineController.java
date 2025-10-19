package controllers;

public class UserWorkoutLineController {
	
	private static UserWorkoutLineController instance = null;
	
	public UserWorkoutLineController() {}
	
	public static UserWorkoutLineController getInstance() {
		if (null == instance) {
			instance = new UserWorkoutLineController();
		}
		return instance;
	}
}
