package exception;

public class CouldNotReadRoutableTypeException extends RuntimeException {

	/**
	 * Exception constructor.
	 */
	public CouldNotReadRoutableTypeException(String s) {
		super("Invalid Router type" + s + " in the data file. Router type can only take the values: r, c, rm");
	}
}
