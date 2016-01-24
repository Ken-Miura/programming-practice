/* Copyright (C) 2016 Ken Miura */
package interpret;

/**
 * @author Ken Miura
 *
 */
public final class InvalidInputException extends Exception {

	/**
	 * Ver 1.0
	 */
	private static final long serialVersionUID = 4712138885723481345L;

	public InvalidInputException () {
	}
	
	public InvalidInputException (String message) {
		super(message);
	}
}
