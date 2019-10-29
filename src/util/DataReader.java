package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import exception.NoSuchRoutableTypeException;
import network.Client;
import network.Domain;
import network.NetworkTopology;
import network.Routable;
import network.Route;
import network.Router;

public class DataReader {

	public static NetworkTopology readNetworkTopologyFromFile(String fileName) throws NoSuchRoutableTypeException {
		NetworkTopology networkTopology = new NetworkTopology();
//		try {
//			BufferedReader bf = new BufferedReader(new FileReader(fileName));
//			String l = bf.readLine();
//			// Parse node counts for each domain to integer
//			List<Integer> domainNodeCounts = Arrays.asList(l.split(" ")).stream().map(s -> Integer.parseInt(s))
//					.collect(Collectors.toList());
//			ArrayList<Domain> domains = new ArrayList<>();
//			ArrayList<Routable> routables = new ArrayList<>();
//			// Create domains and add routables to them
//			for (int i = 1; i < domainNodeCounts.size(); i++) {
//				domains.add(new Domain());
//				for (int j = 0; j < domainNodeCounts.get(i); j++) {
//					l = bf.readLine();
//					Routable r;
//					if (l.split(" ")[1].equals("r")) {
//						r = new Router(UUID.fromString(l.split(" ")[0]));
//
//					} else if (l.split(" ")[1].equals("c")) {
//						r = new Client(UUID.fromString(l.split(" ")[0]));
//					} else {
//						throw new NoSuchRoutableTypeException(l.split(" ")[1]);
//					}
//					routables.add(r);
//					r.setDomain(domains.get(i - 1));
//					domains.get(i - 1).addRoutable(r);
//				}
//			}
//			networkTopology.setDomainList(domains);
//			networkTopology.setRoutableList(routables);
//			// Create routes and and add them to domains
//			ArrayList<Route> routes = new ArrayList<>();
//			l = bf.readLine();
//			do {
//				Routable src = networkTopology.getRoutableById(UUID.fromString(l.split(" ")[0]));
//				Routable dest = networkTopology.getRoutableById(UUID.fromString(l.split(" ")[1]));
//				routes.add(new Route(src, dest));
//				l = bf.readLine();
//			} while (l != null);
//			networkTopology.setRouteList(routes);
//			bf.close();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return networkTopology;
	}

}
