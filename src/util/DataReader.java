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

import exception.CouldNotReadRoutableTypeException;
import network.Client;
import network.Domain;
import network.NetworkTopology;
import network.ResourceManager;
import network.Routable;
import network.Route;
import network.Router;

public class DataReader {

	public static NetworkTopology readNetworkTopologyFromFile(String fileName) throws CouldNotReadRoutableTypeException{
		NetworkTopology networkTopology = new NetworkTopology();
		try {
			BufferedReader bf = new BufferedReader(new FileReader(fileName));
			String l = bf.readLine();
			// Parse node counts for each domain to integer
			List<Integer> domainNodeCounts = Arrays.asList(l.split(" ")).stream().map(s -> Integer.parseInt(s))
					.collect(Collectors.toList());
			ArrayList<Domain> domains = new ArrayList<>();
			ArrayList<Routable> routables = new ArrayList<>();
			// Create domains and add routables to them
			for (int i = 1; i < domainNodeCounts.size(); i++) {
				domains.add(new Domain());
				for (int j = 0; j < domainNodeCounts.get(i); j++) {
					l = bf.readLine();
					Routable r;
					if (l.split(" ")[1] == "r") {
						r = new Router(UUID.fromString(l.split(" ")[0]));
						
					} else if (l.split(" ")[1] == "c") {
						r = new Client(UUID.fromString(l.split(" ")[0]));
					} else {
						throw new CouldNotReadRoutableTypeException(l.split(" ")[1]);
					}
					routables.add(r);
					r.setDomain(domains.get(i - 1));
					domains.get(i - 1).addRoutable(r);
				}
			}
			networkTopology.setDomainList(domains);
			networkTopology.setRoutableList(routables);
			// Create routes and and add them to domains
			ArrayList<Route> routes = new ArrayList<>();
			for (int i = 1; i < domainNodeCounts.size(); i++) {
				for (int j = 0; j < domainNodeCounts.get(i); j++) {
					l = bf.readLine();
					Routable src = networkTopology.getRoutablebyID(UUID.fromString(l.split(" ")[0]));
					Routable dest = networkTopology.getRoutablebyID(UUID.fromString(l.split(" ")[1]));
					routes.add(new Route(src, dest));
				}
			}
			networkTopology.setRouteList(routes);	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return networkTopology;
	}

}
