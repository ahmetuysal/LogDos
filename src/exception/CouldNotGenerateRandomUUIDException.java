/**
 * 
 */
package exception;

/**
 * @author Ceren Kocaoğullar @ckocaogullar15
 *
 */
public class CouldNotGenerateRandomUUIDException extends Exception {
	
	/**
	 * Auto-generated serial version UUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Exception constructor.
	 */
	public CouldNotGenerateRandomUUIDException() {
		super("Random generation of UUID object failed.");
	}
}
