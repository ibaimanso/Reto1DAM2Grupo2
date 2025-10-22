package controllers;

import java.util.List;

import controllers.exceptinos.NameIsAlreadyUsedException;
import controllers.exceptinos.PKException;
import entities.User;
import firebase.ManagerFactory;
import firebase.exceptions.DBException;
import xml.ManagerFactoryXML;
import xml.exceptions.XMLException;

public class UserController {
	
	private static UserController instance = null;
	
	public UserController() {}
	
	public static UserController getInstance() {
		if (null == instance) {
			instance = new UserController();
		}
		return instance;
	}
	
	private int nextId() throws PKException {
		List<User> users = null;

		try {
			users = ManagerFactory.getInstance().getUserManager().selectAll();
		} catch (DBException ex) {
			// #TODO: Se debe hacer que la consulta la realice en local.
		}
		
		if (null == users) {
			throw new PKException();
		}
		
		int highestId = 1;
		
		for (User user: users) {
			if (user.getId() > highestId) {
				highestId = user.getId();
			}
		}
		
		return highestId;
	}
	
	private boolean existsThisFname(String userFname) throws DBException {
		List<User> users = null;

		try {
			users = ManagerFactory.getInstance().getUserManager().selectAll();
		} catch (DBException ex) {
			// #TODO: hacer conecxión a local.
		}
		
		if (null == users) {
			throw new DBException();
		}
		
		for (User user: users) {
			if (user.getFname().equals(userFname)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void insert(User user) throws DBException, PKException, NameIsAlreadyUsedException {
		user.setId(this.nextId());

		if (this.existsThisFname(user.getFname())) {
			throw new NameIsAlreadyUsedException();
		}

		ManagerFactory.getInstance().getUserManager().insert(user);
		
		// #TODO: Se debe hacer que lo inserte también en la base de datos.
	}
	
	public void update(User user) throws DBException {
		ManagerFactory.getInstance().getUserManager().update(user);
	}
	
	public void delete(User user) throws XMLException{
		try {
			ManagerFactory.getInstance().getUserManager().delete(user);
		} catch (DBException ex) {}
		ManagerFactoryXML.getInstance().getUserManager().delete(user);
		ControllerFactory.getInstance().getUserWorkoutLineController().deleteByUserId(user.getId());
		ControllerFactory.getInstance().getUserExerciseLineController().deleteByUserId(user.getId());
		ControllerFactory.getInstance().getUserSerieLineController().deleteByUserId(user.getId());
	}
}
