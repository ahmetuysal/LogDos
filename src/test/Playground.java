package test;

import network.AutonomousSystem;
import network.AutonomousSystemTopology;
import network.Packet;
import util.DataReader;
import util.TickProvider;

import java.util.*;
import java.util.stream.Collectors;

public class Playground {

    public static void main(String[] args) {
//		NetworkTopology nt = new NetworkTopology.Builder().domainCount(10).routableCount(400).setClientRatio(0.4).build();
//		NetworkToGraphConverter.convertNetwork(nt).display();

        AutonomousSystemTopology ast = DataReader.readAutonomousSystemTopologyFromFile("src/dataTexts/AS-topology.txt", "src/dataTexts/transAs.txt");
        System.out.println("Num routes: " + ast.getRouteSet().size());
        System.out.println("Num ASs: " + ast.getAutonomousSystems().size());

        long startTime = System.currentTimeMillis();

        simulateLegitimateTraffic(ast, 3000);
        simulateAttackTraffic(ast, 1000);

//		for (int numAttacker : numAttackers ) {
//			for (double totalAttack : totalAttacks) {
//				for (double falsePositiveRate : falsePositiveRates) {
//					double reachingAttack = 0;
//					double attack = totalAttack / numAttacker;
//
//					for(int i = 0; i < numAttacker; i++) {
//						int start = availableNodes.get(rg.nextInt(range));
//						int target = availableNodes.get(rg.nextInt(range));
//						// System.out.println(start + "->" + target);
//						var path = ast.findPathBetweenAutonomousSystemsBFS(start, target);
//
//						if (path != null) {
//							var startAS = path.remove(0);
//							var packet = new Packet(UUID.randomUUID(), start, new Stack<>());
//							startAS.sendInterestPacket(packet, path);
//						}
//						else {
//							System.out.println("Cannot go to node " + target + " from node " + start);
//						}
//
//						int length =  path == null ? Integer.MAX_VALUE : path.size();
//
//
//						// System.out.println("Distance: " + length);
//						reachingAttack += (attack * Math.pow(falsePositiveRate, Math.floor(length/2)));
//						// System.out.println(i);
//					}
//
//					System.out.println("# Attacker: " + numAttacker + " totalAttack: " + totalAttack + " fpRate: " + falsePositiveRate + " reaching: " + reachingAttack);
//				}
//			}
//		}

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

    private static void simulateLegitimateTraffic(AutonomousSystemTopology ast, int packetCount) {
        List<Integer> availableNodes = new ArrayList<>(ast.autonomousSystemMap.keySet());
        Random rg = new Random();
        int range = availableNodes.size();

        for (int i = 0; i < packetCount; i++) {
            int start = availableNodes.get(rg.nextInt(range));
            int target = availableNodes.get(rg.nextInt(range));

            var path = ast.findPathBetweenAutonomousSystemsBFS(start, target);
            if (path != null) {
                var startAS = path.remove(0);
                var packet = new Packet(UUID.randomUUID(), start, new Stack<>());
                startAS.sendInterestPacket(packet, path);
                int randomTickAmount = (int)(rg.nextDouble() * 2+ 0.1);
                TickProvider.getInstance().tick(randomTickAmount);
            } else {
                i--;
                System.out.println("Cannot go to node " + target + " from node " + start);
            }
        }
    }


    private static void simulateAttackTraffic(AutonomousSystemTopology ast, int packetCount) {
        List<Integer> availableNodes = new ArrayList<>(ast.autonomousSystemMap.keySet());
        Random rg = new Random();
        int range = availableNodes.size();

        for (int i = 0 ; i < packetCount; i++) {
            int start = availableNodes.get(rg.nextInt(range));
            int target = availableNodes.get(rg.nextInt(range));

            var path = ast.findPathBetweenAutonomousSystemsBFS(start, target);
            if (path != null) {
                    var attackPath = new Stack<Integer>();
                    attackPath.addAll(path.stream().map(AutonomousSystem::getId).collect(Collectors.toList()));
                    var attackPacket = new Packet(UUID.randomUUID(), start, attackPath);
                    var lastAS = path.get(path.size() - 1);
                    lastAS.sendResponsePacket(attackPacket);
                int randomTickAmount = (int)(rg.nextDouble() * 2+ 0.1);
                TickProvider.getInstance().tick(randomTickAmount);
            } else {
                i--;
                System.out.println("Cannot go to node " + target + " from node " + start);
            }
        }

    }


}
