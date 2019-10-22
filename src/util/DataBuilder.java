package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import exception.NoSuchRoutableTypeException;
import network.Client;
import network.Domain;
import network.NetworkTopology;
import network.Routable;
import network.Route;
import network.Router;

public class DataBuilder {
	
	public static void writeNetworkTopology(NetworkTopology networkTopology) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter("src/dataTexts/topology-" + LocalDateTime.now() + ".txt"));
			String domainLine = "" + networkTopology.getDomainList().size();
			for (Domain d : networkTopology.getDomainList()) {
				domainLine += " " + d.getRoutableList().size();
			}
			bf.write(domainLine + "\n");
			for (Domain d: networkTopology.getDomainList()) {
				for (Routable r:  d.getRoutableList()) {
					bf.write(r.getUUID().toString() + " " + convertRoutableTypeToString(r) + "\n");
				}
			}
			for (Route r : networkTopology.getRouteList()) {
				bf.write(r.getOrigin().getUUID().toString() +  " " + r.getDestination().getUUID().toString() + "\n");
			}
			bf.close();
		} catch (IOException | NoSuchRoutableTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static String convertRoutableTypeToString(Routable r) throws NoSuchRoutableTypeException{
		if(r instanceof Router) {
			return "r";
		} else if (r instanceof Client) {
			return "c";
		} else {
			throw new NoSuchRoutableTypeException(r.getClass().getName());
		}
	}
}
