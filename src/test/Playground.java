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
		double[] totalAttacks = { 1000000, 2000000, 3000000};
		int[] numAttackers = {100, 500, 1000, 1500, 2000};
		double[] falsePositiveRates = {0.0001D, 0.001D, 0.01D, 0.05D };

		for (int numAttacker : numAttackers ) {
			for (double totalAttack : totalAttacks) {
				for (double falsePositiveRate : falsePositiveRates) {
					double reachingAttack = 0;
					double attack = totalAttack / numAttacker;

					for(int i = 0; i < numAttacker; i++) {
						int start = availableNodes.get(rg.nextInt(range));
						int target = availableNodes.get(rg.nextInt(range));
						// System.out.println(start + "->" + target);
						var path = ast.findPathBetweenAutonomousSystemsBFS2(start, target);
						int length =  path == null ? Integer.MAX_VALUE : path.size();
						// System.out.println("Distance: " + length);
						reachingAttack += (attack * Math.pow(falsePositiveRate, Math.floor(length/2)));
						// System.out.println(i);
					}

					System.out.println("# Attacker: " + numAttacker + " totalAttack: " + totalAttack + " fpRate: " + falsePositiveRate + " reaching: " + reachingAttack);
				}
			}
		}

		long endTime = System.currentTimeMillis(); 
        System.out.println((endTime - startTime) / 1000 + "s");
		
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
