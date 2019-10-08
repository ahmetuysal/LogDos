package test;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import graph.NetworkToGraphConverter;
import network.NetworkTopology;
import network.Route;
import network.Router;

public class Playground {

	public static void main(String[] args) {
		//Playground method for quick testing.
		NetworkTopology nt = new NetworkTopology();
		Stack<Router> routers = new Stack<>();
		ArrayList<Route> routes = new ArrayList<>();
		
		for(int i = 0; i<2000; i++) {
			routers.add(new Router());
		}
		nt.setRoutableList(new ArrayList<>(routers));
		while(routers.size()>2) {
			Route route = new Route();
			route.setOrigin(routers.pop());
			route.setDestination(routers.get(new Random().nextInt(routers.size()-1)));
			routes.add(route);
		}
		nt.setRouteList(routes);
		/*
		for(int i = 0; i<1000; i++) {
			Route route = new Route();
			Router origin = new Router();
			Router dest = new Router();
			route.setOrigin(origin);
			route.setDestination(dest);
			nt.addRoute(route);
		}*/
		NetworkToGraphConverter.convertNetwork(nt).display();
	}
	
}