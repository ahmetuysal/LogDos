package simulation;

import network.logdos.strategy.LogDosStrategyType;

public abstract class SimulationResult {
    LogDosStrategyType logDosStrategyType;
    double falsePositiveRate;
    int numAttacker;
    int totalAttackPacket;
    int successfulAttackPacket;
    double averagePathLength;

    public SimulationResult(int successfulAttackPacket, double averagePathLength) {
        this.successfulAttackPacket = successfulAttackPacket;
        this.averagePathLength = averagePathLength;
    }

    public SimulationResult(LogDosStrategyType logDosStrategyType, double falsePositiveRate, int numAttacker,
                            int totalAttackPacket, int successfulAttackPacket, double averagePathLength) {
        this.logDosStrategyType = logDosStrategyType;
        this.falsePositiveRate = falsePositiveRate;
        this.numAttacker = numAttacker;
        this.totalAttackPacket = totalAttackPacket;
        this.successfulAttackPacket = successfulAttackPacket;
        this.averagePathLength = averagePathLength;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(logDosStrategyType).append(",").append(numAttacker).append(",").append(falsePositiveRate).append(",").append(totalAttackPacket).append(",").append(successfulAttackPacket).append(",").append(averagePathLength).toString();
    }
}
