package network;

import java.util.ArrayList;

/**
 * The abstract class that describes any object that can be routed to.
 *
 * @author Kaan Yıldırım @kyildirim, Ahmet Uysal @ahmetuysal, Ceren Kocaoğullar @ckocaogullar15
 * @see Client
 * @see Router
 */
public abstract class Routable {

    /**
     * Identifier of this <code>Routable</code>.
     */
    private int id;
    /**
     * Domain of this <code>Routable</code>.
     */
    private Domain domain;
    /**
     * A list of <code>Route</code> objects that describe routes originated from this <code>Routable</code>.
     */
    private ArrayList<Route> routeList = new ArrayList<>();

    public Routable() {
        // TODO: how to assure uniqueness
        this.id = (int) (Math.random() * Integer.MAX_VALUE);
    }

    public Routable(int id) {
        this.id = id;
    }

    /**
     * @return the <code>id</code> of this <code>Routable</code>
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return the <code>Domain</code> of this <code>Routable</code>
     */
    public Domain getDomain() {
        return this.domain;
    }

    /**
     * @param domain the <code>Domain</code> of this <code>Routable</code>
     */
    public void setDomain(Domain domain) {
        this.domain = domain;
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
