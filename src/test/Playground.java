package test;

import network.AutonomousSystemTopology;
import util.DataReader;

public class Playground {

	public static void main(String[] args) {
//		NetworkTopology nt = new NetworkTopology.Builder().domainCount(10).routableCount(400).setClientRatio(0.4).build();
//		NetworkToGraphConverter.convertNetwork(nt).display();

		AutonomousSystemTopology ast = DataReader.readAutonomousSystemTopologyFromFile("src/dataTexts/AS-topology.txt", "src/dataTexts/transAs.txt");
		System.out.println("Num routes: " + ast.getRouteSet().size());
		System.out.println("Num ASs: " + ast.getAutonomousSystems().size());
		ast.findPathBetweenAutonomousSystemsBFS(24971, 262589);
		// var graph = NetworkToGraphConverter.convertAutonomousSystemTopology(ast);
		// System.out.println("Num node: " + graph.getNodeSet().size());
		// System.out.println("Num edge: " + graph.getEdgeCount());
		// graph.display();

	}
	
}
