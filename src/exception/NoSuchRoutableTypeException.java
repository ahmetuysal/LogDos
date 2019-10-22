package exception;

public class NoSuchRoutableTypeException extends Exception {

	/**
	 * Exception constructor.
	 */
	public NoSuchRoutableTypeException(String s) {
		super("Invalid Router type " + s + " in the data file. Router type can only take the values: r, c, rm");
	}
}
