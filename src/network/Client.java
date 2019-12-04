package network;

import exception.CouldNotSetRouteListException;

import java.util.ArrayList;

public class Client extends Routable {

    public Client(int id) {
        super(id);
    }

    public Client() {
        super();
    }

    /**
     * @param _routeList A list of <code>Route</code> objects that are to be in this <code>NetworkTopology</code>.
     * @throws CouldNotSetRouteListException
     */
    @Override
    public void setRouteList(ArrayList<Route> _routeList) {
        if (_routeList.size() <= 1) {
            super.setRouteList(_routeList);
        } else {
            throw new CouldNotSetRouteListException();
        }

    }
}
