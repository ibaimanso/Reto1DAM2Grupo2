package controllers;

public class UserController {
	
	private static UserController instance = null;
	
	public UserController() {}
	
	public static UserController getInstance() {
		if (null == instance) {
			instance = new UserController();
		}
		return instance;
	}
}
