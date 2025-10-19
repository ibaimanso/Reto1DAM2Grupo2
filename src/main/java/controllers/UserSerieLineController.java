package controllers;

public class UserSerieLineController {

	private static UserSerieLineController instance = null;
	
	public UserSerieLineController() {}
	
	public static UserSerieLineController getInstance() {
		if (null == instance) {
			instance = new UserSerieLineController();
		}
		return instance;
	}
}
