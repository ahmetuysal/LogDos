package network;

import java.util.ArrayList;

import exception.CouldNotSetRouteListException;

public class Client extends Routable {

	public Client() {
		super();
	}
	/**
	 * @param _routeList A list of <code>Route</code> objects that are to be in this <code>NetworkTopology</code>.
	 * @throws CouldNotSetRouteListException 
	 */
	@Override
	public void setRouteList(ArrayList<Route> _routeList) {
		if(_routeList.size() <= 1) {
			super.setRouteList(_routeList);
		} else {
			throw new CouldNotSetRouteListException();
		}
		
	}
}
