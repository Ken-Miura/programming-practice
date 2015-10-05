package ch01.ex01_16;

public class BadDataSetException extends Exception {
	
	/**
	 * BadDataSetException V1.0
	 */
	private static final long serialVersionUID = 1L;
	private final String dateSetName;
	private final Throwable ioException;
	
	public BadDataSetException(String dateSetName, Throwable ioException) {
		this.dateSetName = dateSetName;
		this.ioException = ioException;
	}

	public String getDateSetName() {
		return dateSetName;
	}

	public Throwable getIoException() {
		return ioException;
	}
}
