package controllers;

public class ControllerFactory {

	private static ControllerFactory instance = null;
	
	public ControllerFactory() {}
	
	public static ControllerFactory getInstance() {
		if (null == instance) {
			instance = new ControllerFactory();
		}
		return instance;
	}
	
	public ExerciseController getExerciseController() {
		return ExerciseController.getInstance();
	}
	
	public SerieController getSerieController() {
		return SerieController.getInstance();
	}
	
	public UserController getUserController() {
		return UserController.getInstance();
	}
	
	public UserExerciseLineController getUserExerciseLineController() {
		return UserExerciseLineController.getInstance();
	}
	
	public UserSerieLineController getUserSerieLineController() {
		return UserSerieLineController.getInstance();
	}
	
	public UserWorkoutLineController getUserWorkoutLineController() {
		return UserWorkoutLineController.getInstance();
	}
	
	public WorkoutController getWorkoutController() {
		return WorkoutController.getInstance();
	}
}
