package controllers;

import java.util.ArrayList;
import java.util.List;

import entities.Exercise;
import entities.Workout;
import firebase.ManagerFactory;
import firebase.exceptions.DBException;

public class ExerciseController {
	
	private static ExerciseController instance = null;

	public ExerciseController() {}
	
	public static ExerciseController getInstance() {
		if (null == instance) {
			instance = new ExerciseController();
		}
		return instance;
	}
	
	public List<Exercise> getExercisesByWorkout(Workout workout)
	throws DBException {
		List<Exercise> ret = new ArrayList<Exercise>();

		List<Exercise> exercises = ManagerFactory.getInstance().getExerciseManager().selectAll();
		
		for (int i = 0; i < exercises.size(); i++) {

			if (exercises.get(i).getWorkoutId() == workout.getId()) {
				ret.add(exercises.get(i));
			}
		}
	
		return ret;
	}
	
}
