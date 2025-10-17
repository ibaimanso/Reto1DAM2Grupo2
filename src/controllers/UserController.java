package controllers;

import java.util.List;

import entities.User;
import firebase.ManagerFactory;

public class UserController {
	
	private ManagerFactory managerFactory = null;
	
	public UserController() {
		this.managerFactory = ManagerFactory.getInstance();
	}

	public boolean existsLogin(User userToCompare) {
		List<User> users = this.managerFactory.getUserManager().selectAll();
		
		for (User user: users) {
			if (user.getName().equals(userToCompare.getName())) {
				
			}
		}
		return false;
	}
}
