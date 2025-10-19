package controllers;

public class UserExerciseLineController {
	
	private static UserExerciseLineController instance = null;
	
	public UserExerciseLineController() {}
	
	public static UserExerciseLineController getInstance() {
		if (null == instance) {
			instance = new UserExerciseLineController();
		}
		return instance;
	}
}
