package network;

import java.util.UUID;

/**
 * An object that describes a route between two <code>{@link AutonomousSystem}</code> objects.
 *
 * @author Ahmet Uysal @ahmetuysal, Ceren Kocaogullar @ckocaogullar15, Kaan Yıldırım @kyildirim
 * @see AutonomousSystem
 */
public class Route {

    /**
     * Universal unique identifier of this <code>Route</code>.
     */
    private UUID pid;

    /**
     * <code>{@link AutonomousSystem}</code> object that is the origin of this <code>Route</code>.
     */
    private AutonomousSystem origin;

    /**
     * <code>{@link AutonomousSystem}</code> object that is the destination of this <code>Route</code>.
     */
    private AutonomousSystem destination;

    public Route() {
        this.pid = UUID.randomUUID();
    }

    public Route(AutonomousSystem origin, AutonomousSystem destination) {
        this.pid = UUID.randomUUID();
        this.origin = origin;
        this.destination = destination;
    }

    /**
     * @return the pid
     */
    public UUID getPid() {
        return this.pid;
    }

    /**
     * @param pid the pid to set
     */
    public void setPid(UUID pid) {
        this.pid = pid;
    }

    /**
     * @return the <code>{@link AutonomousSystem}</code> object that is the origin of this <code>Route</code>.
     */
    public AutonomousSystem getOrigin() {
        return this.origin;
    }

    /**
     * @param origin the <code>{@link AutonomousSystem}</code> object to set as the origin of this <code>Route</code>.
     */
    public void setOrigin(AutonomousSystem origin) {
        this.origin = origin;
    }

    /**
     * @return the <code>{@link AutonomousSystem}</code> object that is the destination of this <code>Route</code>.
     */
    public AutonomousSystem getDestination() {
        return this.destination;
    }

    /**
     * @param destination <code>{@link AutonomousSystem}</code> object to set as the destination of this <code>Route</code>.
     */
    public void setDestination(AutonomousSystem destination) {
        this.destination = destination;
    }


    @Override
    public String toString() {
        return "Route{" +
                "pid=" + pid +
                ", originId=" + origin.getId() +
                ", destinationId=" + destination.getId() +
                '}';
    }
}
