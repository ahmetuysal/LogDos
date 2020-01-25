package simulation;

import network.logdos.strategy.LogDosStrategyType;

public class TimeBasedSimulationResult extends SimulationResult {

    public TimeBasedSimulationResult(int successfulAttackPacket, double averagePathLength) {
        super(successfulAttackPacket, averagePathLength);
    }

    public TimeBasedSimulationResult(LogDosStrategyType logDosStrategyType, double falsePositiveRate, int numAttacker, int totalAttackPacket, int successfulAttackPacket, double averagePathLength) {
        super(logDosStrategyType, falsePositiveRate, numAttacker, totalAttackPacket, successfulAttackPacket, averagePathLength);
    }
}
