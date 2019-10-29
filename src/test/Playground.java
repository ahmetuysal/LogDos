package test;

import exception.NoSuchRoutableTypeException;
import graph.NetworkToGraphConverter;
import network.NetworkTopology;
import util.DataBuilder;
import util.DataReader;

public class Playground {

	public static void main(String[] args) {
		NetworkTopology nt = new NetworkTopology.Builder().domainCount(10).routableCount(400).setClientRatio(0.4).build();
		NetworkToGraphConverter.convertNetwork(nt).display();
	}
	
}
