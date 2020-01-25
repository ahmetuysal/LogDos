package simulation;

import network.logdos.strategy.LogDosStrategyType;

import java.util.ArrayList;
import java.util.List;

public class TimeBasedSimulationResult extends SimulationResult {

    private List<Double> successfulAttackPacketTicks;

    public TimeBasedSimulationResult(int successfulAttackPacket, double averagePathLength) {
        super(successfulAttackPacket, averagePathLength);
        this.successfulAttackPacketTicks = new ArrayList<>();
    }

    public TimeBasedSimulationResult(LogDosStrategyType logDosStrategyType, double falsePositiveRate, int numAttacker, int totalAttackPacket, int successfulAttackPacket, double averagePathLength) {
        super(logDosStrategyType, falsePositiveRate, numAttacker, totalAttackPacket, successfulAttackPacket, averagePathLength);
        this.successfulAttackPacketTicks = new ArrayList<>();
    }

    public List<Double> getSuccessfulAttackPacketTicks() {
        return successfulAttackPacketTicks;
    }

    public void setSuccessfulAttackPacketTicks(List<Double> successfulAttackPacketTicks) {
        this.successfulAttackPacketTicks = successfulAttackPacketTicks;
    }
}
