package network;

import java.util.UUID;

/**
 * An object that describes a route between two <code>{@link Routable}</code> objects.
 *
 * @author Kaan Yıldırım @kyildirim
 * @see Routable
 */
public class Route {

    /**
     * Universal unique identifier of this <code>Route</code>.
     */
    private UUID pid;


    //TODO: Review and fix below.
    /**
     * <code>{@link Routable}</code> object that is the origin of this <code>Route</code>.
     */
    private Routable origin;
    /**
     * <code>{@link Routable}</code> object that is the destination of this <code>Route</code>.
     */
    private Routable destination;
    /**
     * Throughput that is through this <code>Route</code>.
     */
    private double throughput;

    public Route() {
        this.pid = UUID.randomUUID();
    }

    public Route(Routable _origin, Routable _destination) {
        this.pid = UUID.randomUUID();
        this.origin = _origin;
        this.destination = _destination;
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
     * @return the <code>{@link Routable}</code> object that is the origin of this <code>Route</code>.
     */
    public Routable getOrigin() {
        return this.origin;
    }

    /**
     * @param _origin the <code>{@link Routable}</code> object to set as the origin of this <code>Route</code>.
     */
    public void setOrigin(Routable _origin) {
        this.origin = _origin;
    }

    /**
     * @return the <code>{@link Routable}</code> object that is the destination of this <code>Route</code>.
     */
    public Routable getDestination() {
        return this.destination;
    }

    /**
     * @param _destination <code>{@link Routable}</code> object to set as the destination of this <code>Route</code>.
     */
    public void setDestination(Routable _destination) {
        this.destination = _destination;
    }

    /**
     * @return the throughput through this <code>Route</code>.
     */
    public double getThroughput() {
        return this.throughput;
    }

    /**
     * @param _throughput The throughput value to set.
     */
    public void setThroughput(double _throughput) {
        this.throughput = _throughput;
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
