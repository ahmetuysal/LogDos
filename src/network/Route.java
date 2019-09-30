package network;

/**
 * An object that describes a route between two <code>{@link Routable}</code> objects.
 * 
 * @author Kaan Yıldırım @kyildirim
 * @see Routable
 */
public class Route {

	/**
	 * <code>{@link Routable}</code> object that is the origin of this route.
	 */
	private Routable origin;
	/**
	 * <code>{@link Routable}</code> object that is the destination of this route.
	 */
	private Routable destination;
	/**
	 * Throughput that is through this route.
	 */
	private double throughput;
	/**
	 * @return the <code>{@link Routable}</code> object that is the origin of this route.
	 */
	public Routable getOrigin() {
		return this.origin;
	}
	/**
	 * @param _origin the <code>{@link Routable}</code> object to set as the origin of this route.
	 */
	public void setOrigin(Routable _origin) {
		this.origin = _origin;
	}
	/**
	 * @return the <code>{@link Routable}</code> object that is the destination of this route.
	 */
	public Routable getDestination() {
		return this.destination;
	}
	/**
	 * @param _destination <code>{@link Routable}</code> object to set as the destination of this route.
	 */
	public void setDestination(Routable _destination) {
		this.destination = _destination;
	}
	/**
	 * @return the throughput through this route.
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
	
}
