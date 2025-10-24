package controllers.exceptions;

public class PKException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public PKException() {}

	public PKException(String message) {
		super(message);
	}
}
