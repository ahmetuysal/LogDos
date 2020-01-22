/**
 *
 */
package exception;

/**
 * @author Ahmet Uysal @ahmetuysal, Ceren Kocaogullar @ckocaogullar15, Kaan Yıldırım @kyildirim
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
