package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import network.AutonomousSystemTopology;
import util.DataReader;

public class Playground {

	public static void main(String[] args) {
//		NetworkTopology nt = new NetworkTopology.Builder().domainCount(10).routableCount(400).setClientRatio(0.4).build();
//		NetworkToGraphConverter.convertNetwork(nt).display();

		AutonomousSystemTopology ast = DataReader.readAutonomousSystemTopologyFromFile("src/dataTexts/AS-topology.txt", "src/dataTexts/transAs.txt");
		System.out.println("Num routes: " + ast.getRouteSet().size());
		System.out.println("Num ASs: " + ast.getAutonomousSystems().size());
		
		
		List<Integer> availableNodes = new ArrayList<Integer>(ast.autonomousSystemMap.keySet());
		Random rg = new Random();
		int range = availableNodes.size();
		long startTime = System.currentTimeMillis();
		double attack = 3000000; //3Gbps in kbps
		double reachingAttack = 0;
		for(int i = 0; i<10; i++) {
			int start = availableNodes.get(rg.nextInt(range));
			int target = availableNodes.get(rg.nextInt(range));
			System.out.println(start + "->" + target);
			int lenght = ast.findPathBetweenAutonomousSystemsBFS(start, target).size();
			System.out.println("Distance: "+lenght);
			reachingAttack += (attack * Math.pow(0.05, lenght));
			System.out.println(i);
		}
		long endTime = System.currentTimeMillis(); 
        System.out.println((endTime - startTime) / 1000 + "s");
        System.out.println("Total Attack Rate = " + (attack * 10) + "kbps");
        System.out.println("Reaching Attack Rate = " + reachingAttack + "kbps");
		
		/*
		ExecutorService bfsPool = Executors.newFixedThreadPool(100);
		for(int i = 0; i<2000; i++) {
			bfsPool.execute(new Runnable() {
				@Override
				public void run() {
					ast.findPathBetweenAutonomousSystemsBFS(24971, 262589);
				}
			});
		}
		bfsPool.shutdown();
		try {
			bfsPool.awaitTermination(2, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		*/
		// var graph = NetworkToGraphConverter.convertAutonomousSystemTopology(ast);
		// System.out.println("Num node: " + graph.getNodeSet().size());
		// System.out.println("Num edge: " + graph.getEdgeCount());
		// graph.display();

	}
	
}
