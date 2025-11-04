package controllers;

import java.util.ArrayList;
import java.util.List;

import entities.User;
import entities.UserWorkoutLine;
import entities.Workout;
import xml.ManagerFactoryXML;
import xml.exceptions.XMLException;
import firebase.ManagerFactory;
import firebase.exceptions.DBException;

public class UserWorkoutLineController {
	
	private static UserWorkoutLineController instance = null;
	
	public UserWorkoutLineController() {}
	
	public static UserWorkoutLineController getInstance() {
		if (null == instance) {
			instance = new UserWorkoutLineController();
		}
		return instance;
	}
	
	public void deleteByUserId(int userId) throws XMLException{
		List<UserWorkoutLine> uwls = null;

		try {
			uwls = ManagerFactory.getInstance().getUserWorkoutLineManager().selectAll();
			for (UserWorkoutLine uwl: uwls) {
				if (uwl.getUserId() == userId) {
					ManagerFactory.getInstance().getUserWorkoutLineManager().delete(uwl);
				}
			}
		} catch (DBException ex) {}
		uwls = ManagerFactoryXML.getInstance().getUserWorkoutLineManager().selectAll();
		for (UserWorkoutLine uwl : uwls) {
			if (uwl.getUserId() == userId) {
				ManagerFactoryXML.getInstance().getUserWorkoutLineManager().delete(uwl);
			}
		}
	}
	
	/**
	 * Registra que un usuario ha completado un workout
	 */
	public void registerWorkoutCompletion(User user, Workout workout, String doneDate) throws DBException {
		UserWorkoutLine uwl = new UserWorkoutLine(user.getId(), workout.getId(), doneDate);
		ManagerFactory.getInstance().getUserWorkoutLineManager().insert(uwl);
	}
	
	/**
	 * Obtiene todos los workouts completados por un usuario
	 */
	public List<UserWorkoutLine> getWorkoutsByUser(User user) throws DBException {
		List<UserWorkoutLine> result = new ArrayList<>();
		List<UserWorkoutLine> all = ManagerFactory.getInstance().getUserWorkoutLineManager().selectAll();
		
		for (UserWorkoutLine uwl : all) {
			if (uwl.getUserId() == user.getId()) {
				result.add(uwl);
			}
		}
		
		return result;
	}
}