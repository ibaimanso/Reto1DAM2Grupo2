package controllers;

import java.util.List;

import entities.UserExerciseLine;
import firebase.ManagerFactory;
import firebase.exceptions.DBException;

public class UserExerciseLineController {
	
	private static UserExerciseLineController instance = null;
	
	public UserExerciseLineController() {}
	
	public static UserExerciseLineController getInstance() {
		if (null == instance) {
			instance = new UserExerciseLineController();
		}
		return instance;
	}
	
	public void deleteByUserId(int userId) {
		List<UserExerciseLine> uels = null;

		try {
			uels = ManagerFactory.getInstance().getUserExerciseLineManager().selectAll();
			for (UserExerciseLine uel: uels) {
				if (uel.getUserId() == userId) {
					ManagerFactory.getInstance().getUserExerciseLineManager().delete(uel);
				}
			}
		} catch (DBException ex) {
			// #TODO: hay que tratar de acceder a base de datos local.
		}
	}
}
