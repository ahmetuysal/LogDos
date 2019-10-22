package test;

import exception.NoSuchRoutableTypeException;
import graph.NetworkToGraphConverter;
import network.NetworkTopology;
import util.DataBuilder;
import util.DataReader;

public class Playground {

	public static void main(String[] args) {
		//Playground method for quick testing.
		
		
		
		try {
			// DataBuilder.writeNetworkTopology(nt);
			NetworkTopology nt = DataReader.readNetworkTopologyFromFile("src/dataTexts/topology-2019-10-22T15:22:52.879086.txt");
			NetworkToGraphConverter.convertNetwork(nt).display();
		} catch (NoSuchRoutableTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
//		NetworkTopology nt = new NetworkTopology.Builder().domainCount(10).routableCount(400).setClientRatio(0.4).build();
//		DataBuilder.writeNetworkTopology(nt);
//		NetworkToGraphConverter.convertNetwork(nt).display();
		/*
		Stack<Router> routers = new Stack<>();
		ArrayList<Route> routes = new ArrayList<>();
		ArrayList<Domain> domains = new ArrayList<>();
		
		for(int i = 0; i<5; i++) {
			domains.add(new Domain());
		}
		nt.setDomainList(domains);
		for(int i = 0; i<2000; i++) {
			Router router = new Router();
			router.setDomain(domains.get(new Random().nextInt(5)));
			routers.add(router);
		}
		nt.setRoutableList(new ArrayList<>(routers));
		while(routers.size()>2) {
			Route route = new Route();
			route.setOrigin(routers.pop());
			route.setDestination(routers.get(new Random().nextInt(routers.size()-1)));
			routes.add(route);
		}
		nt.setRouteList(routes);
		//
		for(int i = 0; i<1000; i++) {
			Route route = new Route();
			Router origin = new Router();
			Router dest = new Router();
			route.setOrigin(origin);
			route.setDestination(dest);
			nt.addRoute(route);
		}
		NetworkToGraphConverter.convertNetwork(nt).display();*/
	}
	
}
