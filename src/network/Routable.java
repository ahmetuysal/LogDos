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
	
	/**
	 * Domain of this <code>Routable</code>.
	 */
	private Domain domain;
	
	/**
	 * A list of <code>Route</code> objects that describe routes originated from this <code>Routable</code>.
	 */
	private ArrayList<Route> routeList;

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
	
	
}
