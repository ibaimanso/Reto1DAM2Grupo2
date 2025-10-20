package controllers.exceptinos;

public class NameIsAlreadyUsedException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public NameIsAlreadyUsedException() {}

	public NameIsAlreadyUsedException(String message) {
		super(message);
	}

}
