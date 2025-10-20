package controllers;

import java.util.List;

import entities.UserSerieLine;
import firebase.ManagerFactory;
import firebase.exceptions.DBException;

public class UserSerieLineController {

	private static UserSerieLineController instance = null;
	
	public UserSerieLineController() {}
	
	public static UserSerieLineController getInstance() {
		if (null == instance) {
			instance = new UserSerieLineController();
		}
		return instance;
	}
	
	public void deleteByUserId(int userId) {
		List<UserSerieLine> usls = null;

		try {
			usls = ManagerFactory.getInstance().getUserSerieLineManager().selectAll();
			for (UserSerieLine usl: usls) {
				if (usl.getUserId() == userId) {
					ManagerFactory.getInstance().getUserSerieLineManager().delete(usl);
				}
			}
		} catch (DBException ex) {
			// #TODO: hay que tratar de acceder a base de datos local.
		}
	}
}
