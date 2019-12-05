package exception;

public class CouldNotSetRouteListException extends RuntimeException {

    /**
     * Auto-generated serial version UUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Exception constructor.
     */
    public CouldNotSetRouteListException() {
        super("Cannot add more than one Route objects to the Client object.");
    }
}
