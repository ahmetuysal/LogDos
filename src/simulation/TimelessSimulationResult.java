package simulation;

import network.logdos.strategy.LogDosStrategyType;

class TimelessSimulationResult extends SimulationResult {

    public TimelessSimulationResult(int successfulAttackPacket, double averagePathLength) {
        super(successfulAttackPacket, averagePathLength);
    }

    public TimelessSimulationResult(LogDosStrategyType logDosStrategyType, double falsePositiveRate, int numAttacker, int totalAttackPacket, int successfulAttackPacket, double averagePathLength) {
        super(logDosStrategyType, falsePositiveRate, numAttacker, totalAttackPacket, successfulAttackPacket, averagePathLength);
    }

}
