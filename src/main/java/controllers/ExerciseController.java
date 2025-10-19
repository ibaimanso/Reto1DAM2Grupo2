package controllers;

public class ExerciseController {
	
	private static ExerciseController instance = null;

	public ExerciseController() {}
	
	public static ExerciseController getInstance() {
		if (null == instance) {
			instance = new ExerciseController();
		}
		return instance;
	}
}
