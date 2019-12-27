package test;

import config.NetworkConfiguration;
import network.*;
import network.logging.strategy.LoggingStrategyType;
import util.DataReader;

import java.io.FileWriter;
import java.io.IOException;
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

        ArrayList<Double> falsePositiveRates = new ArrayList<>(Arrays.asList(0.0001, 0.001, 0.01, 0.05));
        int[] numAttackers = {100, 500, 1000, 1500, 2000};
        int[] totalAttackPackets = {1000000, 2000000, 3000000};
        ArrayList<LoggingStrategyType> loggingStrategyTypes = new ArrayList<>(Arrays.asList(LoggingStrategyType.COMPREHENSIVE, LoggingStrategyType.ODD, LoggingStrategyType.EVEN));
        long startTime = System.currentTimeMillis();
        long endTime;

        List<Playground.SimulationResult> simulationResults = Collections.synchronizedList(new ArrayList<>());

        loggingStrategyTypes.parallelStream()
                .forEach(loggingStrategyType -> {
                    falsePositiveRates.parallelStream()
                            .forEach(falsePositiveRate -> {
                                AutonomousSystemTopology ast = DataReader.readAutonomousSystemTopologyFromFile("src/dataTexts/AS-topology.txt", "src/dataTexts/transAs.txt", loggingStrategyType, falsePositiveRate);
                                System.out.println(loggingStrategyType.toString() + " fpRate: " + falsePositiveRate);
                                fillBloomFiltersWithRandomPackets(ast, NetworkConfiguration.BLOOM_FILTER_EXPECTED_INSERTIONS);
                                for (int numAttacker : numAttackers) {
                                    for (int totalAttackPacket : totalAttackPackets) {
                                        for (int i = 0; i < 5; i++) {
                                            var victim = selectRandomNonTransientASFromTopology(ast);
                                            Playground.SimulationResult simulationResult = simulateAttackTrafficToAS(ast, victim, numAttacker, totalAttackPacket / numAttacker);
                                            simulationResult.loggingStrategyType = loggingStrategyType;
                                            simulationResult.falsePositiveRate = falsePositiveRate;
                                            simulationResult.numAttacker = numAttacker;
                                            simulationResult.totalAttackPacket = totalAttackPacket;
                                            simulationResults.add(simulationResult);
                                            System.out.println(simulationResults.size());
                                        }
                                    }
                                }
                            });
                });

        try {
            FileWriter csvWriter = new FileWriter("results/comp_odd_even.csv");
            csvWriter.append("Logging Type,Num Attackers,FP Rate,Total Attack Packet,Successful Attack Packet,Average Path Length\n");
            for (SimulationResult result : simulationResults) {
                csvWriter.append(result.toString());
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        endTime = System.currentTimeMillis();

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

    private static AutonomousSystem selectRandomNonTransientASFromTopology(AutonomousSystemTopology ast) {
        Map.Entry<Integer, AutonomousSystem>[] entries = new Map.Entry[0];
        try {
            entries = (Map.Entry<Integer, AutonomousSystem>[]) _table.get(ast.autonomousSystemMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int start = new Random().nextInt(entries.length);
        for (int i = 0; i < entries.length; i++) {
            int idx = (start + i) % entries.length;
            Map.Entry<Integer, AutonomousSystem> entry = entries[idx];
            if (entry != null && entry.getValue().getType() != AutonomousSystemType.TRANSIENT) return entry.getValue();
        }
        return null;
    }

    private static void fillBloomFiltersWithRandomPackets(AutonomousSystemTopology ast, int packetPerFilter) {
        for (AutonomousSystem as : ast.getAutonomousSystems()) {
            for (int i = 0; i < packetPerFilter; i++) {
                as.logPacket(new Packet(UUID.randomUUID(), new Stack<>()), true);
            }
        }
    }

    private static void simulateLegitimateTrafficToAS(AutonomousSystemTopology ast, AutonomousSystem target,
                                                      int peerCount, int packetPerPeer) {
        // Random rg = new Random();

        for (int i = 0; i < peerCount; i++) {
            AutonomousSystem start = selectRandomASFromTopology(ast);
            var path = ast.findPathBetweenAutonomousSystemsBFS(start, target);
            if (path != null) {
                var startAS = path.remove(0);
                for (int j = 0; j < packetPerPeer; j++) {
                    var packet = new Packet(UUID.randomUUID(), new Stack<>());
                    packet.getPidStack().add(startAS.getId());
                    startAS.sendInterestPacket(packet, path, ast);
                    // int randomTickAmount = (int) (rg.nextDouble() * 2 + 0.1);
                    // TickProvider.getInstance().tick(randomTickAmount);
                }
            } else {
                i--;
            }
        }
    }


    /**
     * Simulates Attack Traffic from random non-transient attackers of AutonomousSystemTopology to given Autonomous System.
     *
     * @param ast               Autonomous System Topology that the target belongs.
     * @param target            victim of the attack
     * @param attackerCount     number of randomly selected attackers
     * @param packetPerAttacker packet per attacker
     * @return Average attack path length (excluding both victim and attacker)
     */
    private static SimulationResult simulateAttackTrafficToAS(AutonomousSystemTopology ast, AutonomousSystem target,
                                                              int attackerCount, int packetPerAttacker) {
        // Random rg = new Random();

        long totalPathLength = 0;
        int successfulAttackPackets = 0;
        for (int i = 0; i < attackerCount; i++) {
            AutonomousSystem start = selectRandomNonTransientASFromTopology(ast);
            while (target.equals(start)) {
                start = selectRandomNonTransientASFromTopology(ast);
            }
            var path = ast.findPathBetweenAutonomousSystemsBFS(start, target);
            if (path != null) {
                // System.out.println("Attacker: " + start.toString() + ", victim: " + target.toString() +", path: "+ path.toString());
                var attacker = path.remove(0);
                Collections.reverse(path);
                var attackPath = new Stack<Integer>();
                attackPath.addAll(path.stream().map(AutonomousSystem::getId).collect(Collectors.toList()));
                totalPathLength += attackPath.size();
                for (int j = 0; j < packetPerAttacker; j++) {
                    Stack<Integer> attackPathCopy = (Stack<Integer>) attackPath.clone();
                    var attackPacket = new Packet(UUID.randomUUID(), attackPathCopy);
                    boolean isSuccessful = attacker.sendResponsePacket(attackPacket, ast, true);
                    if (isSuccessful) {
                        successfulAttackPackets++;
                    }
                    // int randomTickAmount = (int) (rg.nextDouble() * 2 + 0.1);
                    // TickProvider.getInstance().tick(randomTickAmount);
                }
            } else {
                i--;
            }
        }

        return new SimulationResult(successfulAttackPackets, totalPathLength / (double) attackerCount);
    }

    private static class SimulationResult {
        LoggingStrategyType loggingStrategyType;
        double falsePositiveRate;
        int numAttacker;
        int totalAttackPacket;
        int successfulAttackPacket;
        double averagePathLength;

        public SimulationResult(int successfulAttackPacket, double averagePathLength) {
            this.successfulAttackPacket = successfulAttackPacket;
            this.averagePathLength = averagePathLength;
        }

        public SimulationResult(LoggingStrategyType loggingStrategyType, double falsePositiveRate, int numAttacker,
                                int totalAttackPacket, int successfulAttackPacket, double averagePathLength) {
            this.loggingStrategyType = loggingStrategyType;
            this.falsePositiveRate = falsePositiveRate;
            this.numAttacker = numAttacker;
            this.totalAttackPacket = totalAttackPacket;
            this.successfulAttackPacket = successfulAttackPacket;
            this.averagePathLength = averagePathLength;
        }

        @Override
        public String toString() {
            return loggingStrategyType + "," + numAttacker + "," + falsePositiveRate + "," + totalAttackPacket + "," + successfulAttackPacket + "," + averagePathLength;
        }
    }

}
