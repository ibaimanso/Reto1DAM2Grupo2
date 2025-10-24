package controllers;

import java.util.List;

import entities.UserExerciseLine;
import firebase.ManagerFactory;
import firebase.exceptions.DBException;
import xml.ManagerFactoryXML;
import xml.exceptions.XMLException;

public class UserExerciseLineController {
	
	private static UserExerciseLineController instance = null;
	
	public UserExerciseLineController() {}
	
	public static UserExerciseLineController getInstance() {
		if (null == instance) {
			instance = new UserExerciseLineController();
		}
		return instance;
	}
	
	public void deleteByUserId(int userId) throws XMLException{
		List<UserExerciseLine> uels = null;

		try {
			uels = ManagerFactory.getInstance().getUserExerciseLineManager().selectAll();
			for (UserExerciseLine uel: uels) {
				if (uel.getUserId() == userId) {
					ManagerFactory.getInstance().getUserExerciseLineManager().delete(uel);
				}
			}
		} catch (DBException ex) {}
		uels = ManagerFactoryXML.getInstance().getUserExerciseLineManager().selectAll();
		for (UserExerciseLine uel: uels) {
			if (uel.getUserId() == userId) {
				ManagerFactoryXML.getInstance().getUserExerciseLineManager().delete(uel);
			}
		}
	}
}
