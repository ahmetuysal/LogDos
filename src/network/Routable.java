package network;

import java.util.ArrayList;

/**
 * The abstract class that describes any object that can be routed to.
 *
 * @author Kaan Yıldırım @kyildirim, Ahmet Uysal @ahmetuysal, Ceren Kocaoğullar @ckocaogullar15
 */
public abstract class Routable {

    /**
     * Identifier of this <code>Routable</code>.
     */
    private int id;

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
