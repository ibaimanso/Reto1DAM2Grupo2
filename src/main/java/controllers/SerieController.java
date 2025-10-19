package controllers;

public class SerieController {
	
	private static SerieController instance = null;

	public SerieController() {}
	
	public static SerieController getInstance() {
		if (null == instance) {
			instance = new SerieController();
		}
		return instance;
	}
}
