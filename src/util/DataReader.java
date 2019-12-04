package util;

import exception.NoSuchRoutableTypeException;
import network.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataReader {

    public static AutonomousSystemTopology readAutonomousSystemTopologyFromFile(String topologyFileName, String transientAutonomousSystemsFileName) {
        AutonomousSystemTopology autonomousSystemTopology = AutonomousSystemTopology.getInstance();
        try {
            BufferedReader bf = new BufferedReader(new FileReader(topologyFileName));
            String line = bf.readLine();
            while (line != null) {
                String[] ASIds = line.split(" ");
                int ASId1 = Integer.parseInt(ASIds[0]);
                int ASId2 = Integer.parseInt(ASIds[1]);

                if (!autonomousSystemTopology.hasAutonomousSystemById(ASId1)) {
                    AutonomousSystem as1 = new AutonomousSystem(ASId1);
                    autonomousSystemTopology.addAutonomousSystem(as1);
                }

                if (!autonomousSystemTopology.hasAutonomousSystemById(ASId2)) {
                    AutonomousSystem as2 = new AutonomousSystem(ASId2);
                    autonomousSystemTopology.addAutonomousSystem(as2);
                }

                AutonomousSystem as1 = autonomousSystemTopology.getAutonomousSystemById(ASId1);
                AutonomousSystem as2 = autonomousSystemTopology.getAutonomousSystemById(ASId2);
                Route route = new Route(as1, as2);

                as1.addRoute(route);
                as2.addRoute(route);
                autonomousSystemTopology.addRoute(route);
                line = bf.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader bf = new BufferedReader(new FileReader(transientAutonomousSystemsFileName));
            String line = bf.readLine();
            while (line != null) {
                int asId = Integer.parseInt(line.trim());
                AutonomousSystem transientAS = autonomousSystemTopology.getAutonomousSystemById(asId);
                if (transientAS == null) {
                    transientAS = new AutonomousSystem(asId, AutonomousSystemType.TRANSIENT);
                    autonomousSystemTopology.addAutonomousSystem(transientAS);
                } else {
                    transientAS.setType(AutonomousSystemType.TRANSIENT);
                }
                line = bf.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return autonomousSystemTopology;
    }

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
