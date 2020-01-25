package simulation;

import network.logdos.strategy.LogDosStrategyType;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class TimeBasedSimulationResult extends SimulationResult {

    private List<AbstractMap.SimpleEntry<Double, Integer>> aggregateAttackOverTime;

    public TimeBasedSimulationResult(int successfulAttackPacket, double averagePathLength) {
        super(successfulAttackPacket, averagePathLength);
        this.aggregateAttackOverTime = new ArrayList<>();
    }

    public TimeBasedSimulationResult(LogDosStrategyType logDosStrategyType, double falsePositiveRate, int numAttacker, int totalAttackPacket, int successfulAttackPacket, double averagePathLength) {
        super(logDosStrategyType, falsePositiveRate, numAttacker, totalAttackPacket, successfulAttackPacket, averagePathLength);
        this.aggregateAttackOverTime = new ArrayList<>();
    }

    public List<AbstractMap.SimpleEntry<Double, Integer>> getAggregateAttackOverTime() {
        return aggregateAttackOverTime;
    }

    public void setAggregateAttackOverTime(List<AbstractMap.SimpleEntry<Double, Integer>> aggregateAttackOverTime) {
        this.aggregateAttackOverTime = aggregateAttackOverTime;
    }
}
