package test;

import network.AutonomousSystem;
import network.AutonomousSystemTopology;
import network.Packet;
import util.DataReader;
import util.TickProvider;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class Playground {

    private static Field _table;

    static {
        try {
            _table = HashMap.class.getDeclaredField("table");
            _table.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AutonomousSystemTopology ast = DataReader.readAutonomousSystemTopologyFromFile("src/dataTexts/AS-topology.txt", "src/dataTexts/transAs.txt");
        System.out.println("Num routes: " + ast.getRouteSet().size());
        System.out.println("Num ASs: " + ast.getAutonomousSystems().size());

        long startTime = System.currentTimeMillis();

        var victim = selectRandomASFromTopology(ast);

        simulateLegitimateTrafficToAS(ast, victim, 1000, 10000);
        simulateAttackTrafficToAS(ast, victim, 20, 5000);

        long endTime = System.currentTimeMillis();
        System.out.println("Packages caught: " + AutonomousSystem.packagesCaught);
        System.out.println((endTime - startTime) / 1000 + "s");
    }


    private static AutonomousSystem selectRandomASFromTopology(AutonomousSystemTopology ast) {
        Random rand = new Random();
        Map.Entry<Integer, AutonomousSystem>[] entries = new Map.Entry[0];
        try {
            entries = (Map.Entry<Integer, AutonomousSystem>[]) _table.get(ast.autonomousSystemMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int start = rand.nextInt(entries.length);
        for (int i = 0; i < entries.length; i++) {
            int idx = (start + i) % entries.length;
            Map.Entry<Integer, AutonomousSystem> entry = entries[idx];
            if (entry != null) return entry.getValue();
        }
        return null;
    }

    private static void simulateLegitimateTrafficToAS(AutonomousSystemTopology ast, AutonomousSystem target,
                                                      int peerCount, int packetPerPeer) {
        Random rg = new Random();

        for (int i = 0; i < peerCount; i++) {
            AutonomousSystem start = selectRandomASFromTopology(ast);
            var path = ast.findPathBetweenAutonomousSystemsBFS(start, target);
            if (path != null) {
                var startAS = path.remove(0);
                for (int j = 0; j < packetPerPeer; j++) {
                    var packet = new Packet(UUID.randomUUID(), start.getId(), new Stack<>());
                    startAS.sendInterestPacket(packet, path);
                    int randomTickAmount = (int) (rg.nextDouble() * 2 + 0.1);
                    TickProvider.getInstance().tick(randomTickAmount);
                }
            } else {
                i--;
                System.out.println("Cannot go to node " + target + " from node " + start);
            }
        }
    }


    private static void simulateAttackTrafficToAS(AutonomousSystemTopology ast, AutonomousSystem target,
                                                  int attackerCount, int packetPerAttacker) {
        Random rg = new Random();

        for (int i = 0; i < attackerCount; i++) {
            AutonomousSystem start = selectRandomASFromTopology(ast);

            var path = ast.findPathBetweenAutonomousSystemsBFS(start, target);
            if (path != null) {
                var attackPath = new Stack<Integer>();
                attackPath.addAll(path.stream().map(AutonomousSystem::getId).collect(Collectors.toList()));
                for (int j = 0; j < packetPerAttacker; j++) {
                    var attackPacket = new Packet(UUID.randomUUID(), start.getId(), attackPath);
                    var lastAS = path.get(path.size() - 1);
                    lastAS.sendResponsePacket(attackPacket);
                    int randomTickAmount = (int) (rg.nextDouble() * 2 + 0.1);
                    TickProvider.getInstance().tick(randomTickAmount);
                }
            } else {
                i--;
                System.out.println("Cannot go to node " + target + " from node " + start);
            }
        }

    }

}
