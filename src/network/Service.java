/**
 *
 */
package network;

import exception.CouldNotGenerateRandomUUIDException;

import java.util.UUID;

/**
 * @author Ceren Kocaoğullar @ckocaogullar15
 *
 */
public class Service {

    /**
     * Universally unique identifier of this <code>Service</code>.
     */
    private UUID sid;


    /**
     * Creates a new <code>Service</code> object with a randomly generated <code>UUID</code>.
     * @throws Exception
     */
    public Service() throws Exception {

        UUID randomUUID = UUID.randomUUID();
        if (randomUUID != null) {
            this.sid = randomUUID;
        } else {
            throw new CouldNotGenerateRandomUUIDException();
        }

    }


    /**
     * @return the universally unique identifier of this <code>Service</code>.
     */
    public UUID getSid() {
        return this.sid;
    }


}