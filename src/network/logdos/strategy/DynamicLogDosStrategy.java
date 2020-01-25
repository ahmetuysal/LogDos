package network.logdos.strategy;

import config.NetworkConfiguration;
import network.Packet;
import util.TickProvider;

import java.util.Random;

public class DynamicLogDosStrategy extends LogDosStrategy implements TimeBasedLogDosStrategy {

    private double loggingPeriod;
    private double initialTime;
    private int attackOccurrencesDuringPeriod;
    private TickProvider tickProvider;

    public DynamicLogDosStrategy(double falsePositiveRate) {
        super(falsePositiveRate);
        this.initialTime = 0;
        this.loggingPeriod = NetworkConfiguration.getInstance().getInitialLoggingPeriod();
        this.attackOccurrencesDuringPeriod = 0;
    }

    public DynamicLogDosStrategy() {
        super();
        this.initialTime = 0;
        this.loggingPeriod = NetworkConfiguration.getInstance().getInitialLoggingPeriod();
        this.attackOccurrencesDuringPeriod = 0;
    }

    public double getLoggingPeriod() {
        return loggingPeriod;
    }

    public void setLoggingPeriod(double loggingPeriod) {
        this.loggingPeriod = loggingPeriod;
    }

    public double getInitialTime() {
        return initialTime;
    }

    public void setInitialTime(int initialTime) {
        this.initialTime = initialTime;
    }

    @Override
    public void logPacket(Packet packet, boolean isForced) {
        if (isForced) {
            super.bloomFilter.put(packet);
            return;
        }

        double currentTick = tickProvider.getCurrentTick();
        if (initialTime < currentTick && currentTick < initialTime + loggingPeriod) {
            super.bloomFilter.put(packet);
        }
        updateRouterState(currentTick);
    }

    @Override
    public boolean checkPacket(Packet packet) {
        double currentTick = tickProvider.getCurrentTick();

        if (initialTime + NetworkConfiguration.getInstance().getRoundTripDelay() < currentTick &&
                currentTick < initialTime + loggingPeriod + NetworkConfiguration.getInstance().getRoundTripDelay()) {
            if (super.bloomFilter.mightContain(packet)) {
                return true;
            } else {
                this.attackOccurrencesDuringPeriod++;
                return false;
            }
        }

        updateRouterState(currentTick);

        return true;
    }

    private void updateRouterState(double currentTick) {
        if (currentTick > initialTime + loggingPeriod + NetworkConfiguration.getInstance().getRoundTripDelay()) {
            if (attackOccurrencesDuringPeriod > NetworkConfiguration.getInstance().getAttackThreshold()) {
                loggingPeriod += NetworkConfiguration.getInstance().getInitialLoggingPeriod();
                attackOccurrencesDuringPeriod = 0;
            } else {
                initialTime += loggingPeriod + NetworkConfiguration.getInstance().getSilentPeriod();
                loggingPeriod = NetworkConfiguration.getInstance().getInitialLoggingPeriod();
            }
        }
    }

    @Override
    public TickProvider getTickProvider() {
        return this.tickProvider;
    }

    @Override
    public void setTickProvider(TickProvider tickProvider) {
        this.tickProvider = tickProvider;
        this.initialTime = NetworkConfiguration.getInstance().getInitialLoggingPeriod() * new Random().nextDouble();
        this.loggingPeriod = NetworkConfiguration.getInstance().getInitialLoggingPeriod();
        this.attackOccurrencesDuringPeriod = 0;
    }
}