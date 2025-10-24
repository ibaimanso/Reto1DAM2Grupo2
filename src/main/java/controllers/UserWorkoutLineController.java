package controllers;

import java.util.List;

import entities.UserWorkoutLine;
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
}
