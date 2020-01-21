package simulation;

import network.logging.strategy.LoggingStrategyType;

class SimulationResult {
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
        return new StringBuilder().append(loggingStrategyType).append(",").append(numAttacker).append(",").append(falsePositiveRate).append(",").append(totalAttackPacket).append(",").append(successfulAttackPacket).append(",").append(averagePathLength).toString();
    }
}
