package network;

import java.util.ArrayList;
import java.util.UUID;

/**
 * 
 * The abstract class that describes any object that can be routed to.
 * 
 * @author Kaan Yıldırım @kyildirim, Ahmet Uysal @ahmetuysal, Ceren Kocaoğullar @ckocaogullar15
 * @see Client
 * @see Router
 *
 */
public abstract class Routable {

	/**
	 * Universal unique identifier of this <code>Routable</code>.
	 */
	private UUID uuid;
	
	
	//TODO: Review and fix. 
	/**
	 * @return the uuid
	 */
	public UUID getUUID() {
		return this.uuid;
	}
	/**
	 * @param uuid the uuid to set
	 */
	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	/**
	 * Domain of this <code>Routable</code>.
	 */
	private Domain domain;
	
	/**
	 * A list of <code>Route</code> objects that describe routes originated from this <code>Routable</code>.
	 */
	private ArrayList<Route> routeList = new ArrayList<>();
	
	public Routable() {
		this.uuid = UUID.randomUUID();
	}

	/**
	 * @return the <code>Domain</code> of this <code>Routable</code>
	 */
	public Domain getDomain() {
		return this.domain;
	}

	/**
	 * @param <code>Domain</code> of this <code>Routable</code>
	 */
	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	/**
	 * @return the <code>UUID</code> of this <code>Routable</code>
	 */
	public UUID getUuid() {
		return this.uuid;
	}

	/**
	 * @return the <code>ArrayList</code> of list of <code>Route</code> objects this <code>Routable</code>
	 */
	public ArrayList<Route> getRouteList() {
		return this.routeList;
	}
	/**
	 * @param _routeList A list of <code>Route</code> objects that are to be in this <code>NetworkTopology</code>.
	 */
	public void setRouteList(ArrayList<Route> _routeList) {
		this.routeList = _routeList;
	}
	
	
}
