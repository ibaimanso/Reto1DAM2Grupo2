package controllers;

import java.util.List;

import controllers.exceptions.NameIsAlreadyUsedException;
import controllers.exceptions.PKException;
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
	
	private int nextId() throws PKException, DBException {
		List<User> users = null;
		
		users = ManagerFactory.getInstance().getUserManager().selectAll();
			
		if (null == users) {
			throw new PKException();
		}
		
		int highestId = 0;
		
		for (User user: users) {
			if (user.getId() > highestId) {
				highestId = user.getId();
			}
		}
		
		return highestId + 1;
	}
	
	private boolean existsThisFname(String userFname) throws DBException {
		List<User> users = null;

		users = ManagerFactory.getInstance().getUserManager().selectAll();
		
		for (User user: users) {
			if (user.getFname().equals(userFname)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void insert(User user) throws DBException, PKException, NameIsAlreadyUsedException, XMLException {
		user.setId(this.nextId());

		if (this.existsThisFname(user.getFname())) {
			throw new NameIsAlreadyUsedException();
		}
		
		ManagerFactoryXML.getInstance().getUserManager().insert(user);
		
		try {
			ManagerFactory.getInstance().getUserManager().insert(user);
		} catch (Exception e) {}
		
	}
	
	public void update(User user) throws DBException, XMLException {
		ManagerFactoryXML.getInstance().getUserManager().update(user);
		
		try {
			ManagerFactory.getInstance().getUserManager().update(user);
		} catch (Exception e) {}
		
	}
	
	public void delete(User user) throws XMLException{
		ManagerFactoryXML.getInstance().getUserManager().delete(user);
		
		try {
			ManagerFactory.getInstance().getUserManager().delete(user);
		} catch (DBException ex) {}
		ControllerFactory.getInstance().getUserWorkoutLineController().deleteByUserId(user.getId());
		ControllerFactory.getInstance().getUserExerciseLineController().deleteByUserId(user.getId());
		ControllerFactory.getInstance().getUserSerieLineController().deleteByUserId(user.getId());
	}
	
	public void signUp(User user) throws DBException, NameIsAlreadyUsedException, PKException {
		
		boolean userExists = this.existsThisFname(user.getFname());
		
		if (userExists) {
			throw new NameIsAlreadyUsedException();
		}
		
		user.setId(this.nextId());
		ManagerFactory.getInstance().getUserManager().insert(user);
		// #TODO Llamar al proceso de backup para descargar toda la base de datos en local
	}
	
	public boolean existLogin(User user) throws XMLException{
		List<User> users = ManagerFactoryXML.getInstance().getUserManager().selectAll();
		
		for (User user2 : users) {
			if (!user2.getFname().equals(user.getFname())) {
				continue;
			}

			return user2.getPw().equals(user.getPw());
		}
		return false;
	}
	
	public User selectByFname(User userToFind)
	throws DBException {
		User user = null;
		List<User> users = ManagerFactory.getInstance().getUserManager().selectAll();
		
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getFname().equals(userToFind.getFname())) {
				user = users.get(i);
				break;
			}
		}
		
		return user;
	}
	
}
