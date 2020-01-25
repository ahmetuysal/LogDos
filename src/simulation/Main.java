package simulation;

import config.NetworkConfiguration;
import network.AutonomousSystem;
import network.AutonomousSystemTopology;
import network.AutonomousSystemType;
import network.Packet;
import network.logdos.strategy.LogDosStrategyType;
import util.DataReader;
import util.TickProvider;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static Field _table;

    // Make HashMap.table accessible to get random value faster.
    static {
        try {
            _table = HashMap.class.getDeclaredField("table");
            _table.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        ArrayList<Double> falsePositiveRates = new ArrayList<>(Arrays.asList(0.0001, 0.001, 0.01, 0.05));
        int[] numAttackers = {100, 500, 1000, 1500, 2000};
        int[] totalAttackPackets = {1000000, 2000000, 3000000};

        ArrayList<Integer> attackThresholds = new ArrayList<>(Arrays.asList(0, 10, 20, 30, 40, 50));
        ArrayList<Double> initialLoggingPeriods = new ArrayList<>(Collections.singletonList(1D));
        ArrayList<Double> silentLoggingPeriods = new ArrayList<>(Arrays.asList(5D, 6D, 7D, 8D, 9D, 10D));

        List<TimelessSimulationResult> timelessSimulationResults = simulateTimelessLogDosSchemes(
                new ArrayList<>(Arrays.asList(LogDosStrategyType.COMPREHENSIVE, LogDosStrategyType.ODD, LogDosStrategyType.EVEN)),
                falsePositiveRates, numAttackers, totalAttackPackets);

        List<TimeBasedSimulationResult> periodicSimulationResults = simulateLoggingSchemesWithTime(
                new ArrayList<>(Collections.singletonList(LogDosStrategyType.DYNAMIC)),
                falsePositiveRates, numAttackers, totalAttackPackets, attackThresholds, initialLoggingPeriods, silentLoggingPeriods, 0D, 500D);

        for (TimeBasedSimulationResult periodicSimulationResult : periodicSimulationResults) {
            writeTimeBasedSimulationResultToDATFile(periodicSimulationResult, periodicSimulationResult.logDosStrategyType.toString() + "-FP" + periodicSimulationResult.falsePositiveRate + ".dat");
        }

        List<SimulationResult> allSimulationResults = Stream.concat(timelessSimulationResults.stream(), periodicSimulationResults.stream())
                .collect(Collectors.toList());

        writeSimulationResultsToCSVFile(allSimulationResults, "results/comp_odd_even_periodic.csv");

        long endTime = System.currentTimeMillis();

        System.out.println((endTime - startTime) / 1000 + "s");
    }


    private static List<TimelessSimulationResult> simulateTimelessLogDosSchemes(ArrayList<LogDosStrategyType> logDosStrategyTypes,
                                                                                ArrayList<Double> falsePositiveRates,
                                                                                int[] numAttackers,
                                                                                int[] totalAttackPackets) {
        List<TimelessSimulationResult> timelessSimulationResults = Collections.synchronizedList(new ArrayList<>());

        logDosStrategyTypes.parallelStream()
                .forEach(logDosStrategyType -> {
                    falsePositiveRates.parallelStream()
                            .forEach(falsePositiveRate -> {
                                AutonomousSystemTopology ast = DataReader.readAutonomousSystemTopologyFromFile("src/dataTexts/AS-topology.txt", "src/dataTexts/transAs.txt", logDosStrategyType, falsePositiveRate);
                                System.out.println(logDosStrategyType.toString() + " fpRate: " + falsePositiveRate);
                                fillBloomFiltersWithRandomPackets(ast, NetworkConfiguration.getInstance().getBloomFilterExpectedInsertions());
                                for (int numAttacker : numAttackers) {
                                    for (int totalAttackPacket : totalAttackPackets) {
                                        for (int i = 0; i < 5; i++) {
                                            var victim = selectRandomNonTransientASFromTopology(ast);
                                            TimelessSimulationResult timelessSimulationResult = simulateAttackTrafficToAS(ast, victim, numAttacker, totalAttackPacket / numAttacker);
                                            timelessSimulationResult.logDosStrategyType = logDosStrategyType;
                                            timelessSimulationResult.falsePositiveRate = falsePositiveRate;
                                            timelessSimulationResult.numAttacker = numAttacker;
                                            timelessSimulationResult.totalAttackPacket = totalAttackPacket;
                                            timelessSimulationResults.add(timelessSimulationResult);
                                            System.out.println(timelessSimulationResults.size());
                                        }
                                    }
                                }
                            });
                });

        return timelessSimulationResults;
    }

    private static List<TimeBasedSimulationResult> simulateLoggingSchemesWithTime(ArrayList<LogDosStrategyType> logDosStrategyTypes,
                                                                                  ArrayList<Double> falsePositiveRates,
                                                                                  int[] numAttackers,
                                                                                  int[] totalAttackPackets,
                                                                                  ArrayList<Integer> attackThresholds,
                                                                                  ArrayList<Double> initialLoggingPeriods,
                                                                                  ArrayList<Double> silentLoggingPeriods,
                                                                                  double startTick,
                                                                                  double endTick) {
        List<TimeBasedSimulationResult> simulationResults = Collections.synchronizedList(new ArrayList<>());

// TODO: experiment with different dynamic LogDos strategy parameters
//        for (int attackThreshold : attackThresholds) {
//            for (double initialLoggingPeriod : initialLoggingPeriods) {
//                for (double silentLoggingPeriod : silentLoggingPeriods) {
//                    NetworkConfiguration networkConfiguration = NetworkConfiguration.getInstance();
//                    networkConfiguration.setAttackThreshold(attackThreshold);
//                    networkConfiguration.setInitialLoggingPeriod(initialLoggingPeriod);
//                    networkConfiguration.setSilentPeriod(silentLoggingPeriod);
//                }
//            }
//        }


        logDosStrategyTypes.parallelStream()
                .forEach(logDosStrategyType -> {
                    falsePositiveRates.parallelStream()
                            .forEach(falsePositiveRate -> {
                                AutonomousSystemTopology ast = DataReader.readAutonomousSystemTopologyFromFile("src/dataTexts/AS-topology.txt", "src/dataTexts/transAs.txt", logDosStrategyType, falsePositiveRate);
                                System.out.println(logDosStrategyType.toString() + " fpRate: " + falsePositiveRate);
                                fillBloomFiltersWithRandomPackets(ast, NetworkConfiguration.getInstance().getBloomFilterExpectedInsertions());
                                for (int numAttacker : numAttackers) {
                                    for (int totalAttackPacket : totalAttackPackets) {
                                            var victim = selectRandomNonTransientASFromTopology(ast);
                                            TimeBasedSimulationResult timeBasedSimulationResult = simulateAttackTrafficWithTimeToAS(ast, victim, numAttacker, totalAttackPacket / numAttacker, startTick, endTick);
                                            timeBasedSimulationResult.logDosStrategyType = logDosStrategyType;
                                            timeBasedSimulationResult.falsePositiveRate = falsePositiveRate;
                                            timeBasedSimulationResult.numAttacker = numAttacker;
                                            timeBasedSimulationResult.totalAttackPacket = totalAttackPacket;
                                            simulationResults.add(timeBasedSimulationResult);
                                            System.out.println(simulationResults.size());
                                    }
                                }
                            });
                });

        return simulationResults;
    }

    private static void writeTimeBasedSimulationResultToDATFile(TimeBasedSimulationResult timeBasedSimulationResult, String fileName) {
        try {
            FileWriter datWriter = new FileWriter(fileName);
            datWriter.append("time(seconds)\taggregate Attack(Mbps)\n");

            for (AbstractMap.SimpleEntry<Double, Integer> dataPoint : timeBasedSimulationResult.getAggregateAttackOverTime()) {
                datWriter.append(dataPoint.getKey().toString())
                        .append("\t")
                        .append(dataPoint.getValue().toString())
                        .append("\n");
            }
            datWriter.flush();
            datWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeSimulationResultsToCSVFile(List<SimulationResult> simulationResults, String fileName) {
        try {
            FileWriter csvWriter = new FileWriter(fileName);
            csvWriter.append("LogDos Type,Num Attackers,FP Rate,Total Attack Packet,Successful Attack Packet,Average Path Length\n");
            for (SimulationResult result : simulationResults) {
                csvWriter.append(result.toString());
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    /**
     * Simulates Attack Traffic from random non-transient attackers of AutonomousSystemTopology to given Autonomous System.
     *
     * @param ast               Autonomous System Topology that the target belongs.
     * @param target            victim of the attack
     * @param attackerCount     number of randomly selected attackers
     * @param packetPerAttacker packet per attacker
     * @return Average attack path length (excluding both victim and attacker)
     */
    private static TimelessSimulationResult simulateAttackTrafficToAS(AutonomousSystemTopology ast, AutonomousSystem target,
                                                                      int attackerCount, int packetPerAttacker) {
        long totalPathLength = 0;
        int successfulAttackPackets = 0;
        for (int i = 0; i < attackerCount; i++) {
            AutonomousSystem start = selectRandomNonTransientASFromTopology(ast);
            while (target.equals(start)) {
                start = selectRandomNonTransientASFromTopology(ast);
            }
            var path = ast.findPathBetweenAutonomousSystemsBFS(start, target);
            if (path != null) {
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
                }
            } else {
                i--;
            }
        }

        return new TimelessSimulationResult(successfulAttackPackets, totalPathLength / (double) attackerCount);
    }


    /**
     * Simulates Attack Traffic from random non-transient attackers of AutonomousSystemTopology to given Autonomous System.
     *
     * @param ast               Autonomous System Topology that the target belongs.
     * @param target            victim of the attack
     * @param attackerCount     number of randomly selected attackers
     * @param packetPerAttacker packet per attacker
     * @param startTick         startTick (inclusive)
     * @param endTick           endTick (exclusive)
     * @return Average attack path length (excluding both victim and attacker)
     */
    private static TimeBasedSimulationResult simulateAttackTrafficWithTimeToAS(AutonomousSystemTopology ast,
                                                                               AutonomousSystem target,
                                                                               int attackerCount,
                                                                               int packetPerAttacker,
                                                                               double startTick,
                                                                               double endTick) {
        long totalPathLength = 0;
        int successfulAttackPackets = 0;

        Random random = new Random();

        ArrayList<Map.Entry<Double, AttackPacketInfo>> attackPacketsWithTicks = new ArrayList<>();

        for (int i = 0; i < attackerCount; i++) {
            AutonomousSystem start = selectRandomNonTransientASFromTopology(ast);
            while (target.equals(start)) {
                start = selectRandomNonTransientASFromTopology(ast);
            }
            var path = ast.findPathBetweenAutonomousSystemsBFS(start, target);
            if (path != null) {
                // remove the attacker from the path
                path.remove(0);
                Collections.reverse(path);
                var attackPath = new Stack<Integer>();
                attackPath.addAll(path.stream().map(AutonomousSystem::getId).collect(Collectors.toList()));
                totalPathLength += attackPath.size();
                for (int j = 0; j < packetPerAttacker; j++) {
                    double attackTick = startTick + random.nextDouble() * (endTick - startTick);
                    attackPacketsWithTicks.add(new AbstractMap.SimpleEntry<>(attackTick, new AttackPacketInfo(start, attackPath)));
                }
            } else {
                i--;
            }
        }

        TickProvider tickProvider = new TickProvider();
        ast.setTickProvidersForAllTimeBasedLogDosStrategies(tickProvider);

        attackPacketsWithTicks.sort(Map.Entry.comparingByKey());

        List<AbstractMap.SimpleEntry<Double, Integer>> aggregateAttackOverTime = new ArrayList<>();

        for (Map.Entry<Double, AttackPacketInfo> attackPacketWithTick : attackPacketsWithTicks) {
            Stack<Integer> attackPathCopy = (Stack<Integer>) attackPacketWithTick.getValue().attackPath.clone();
            var attackPacket = new Packet(UUID.randomUUID(), attackPathCopy);
            double attackPacketTick = attackPacketWithTick.getKey();
            tickProvider.setCurrentTick(attackPacketTick);
            boolean isSuccessful = attackPacketWithTick.getValue().attacker.sendResponsePacket(attackPacket, ast, true);
            if (isSuccessful) {
                successfulAttackPackets++;

                aggregateAttackOverTime.add(new AbstractMap.SimpleEntry<Double, Integer>(attackPacketTick, successfulAttackPackets));
            }
        }

        TimeBasedSimulationResult simulationResult = new TimeBasedSimulationResult(successfulAttackPackets, totalPathLength / (double) attackerCount);
        simulationResult.setAggregateAttackOverTime(aggregateAttackOverTime);
        return simulationResult;
    }

}
