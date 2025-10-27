package controllers;

import java.util.ArrayList;
import java.util.List;

import entities.User;
import entities.Workout;
import firebase.ManagerFactory;
import firebase.exceptions.DBException;

public class WorkoutController {
	
	private static WorkoutController instance = null;
	
	public WorkoutController() {}
	
	public static WorkoutController getInstance() {
		if (null == instance) {
			instance = new WorkoutController();
		}
		return instance;
	}
	
	public List<Workout> getWorkoutsByLevel(User user)
	throws DBException {
		List<Workout> ret = new ArrayList<Workout>();

		List<Workout> workouts = ManagerFactory.getInstance().getWorkoutManager().selectAll();
		
		for (int i = 0; i < workouts.size(); i++) {

			if (workouts.get(i).getLevel() <= user.getLevel()) {
				ret.add(workouts.get(i));
			}
		}
	
		return ret;
	}
	
}
