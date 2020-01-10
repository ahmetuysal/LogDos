package network.logging.strategy;

import config.NetworkConfiguration;
import network.Packet;
import util.TickProvider;

import java.util.Random;

public class PeriodicLoggingStrategy extends LoggingStrategy implements TimeBasedLoggingStrategy {

    private double loggingInterval;
    private double initialTime;
    private int attackOccurrencesDuringInterval;
    private TickProvider tickProvider;

    public PeriodicLoggingStrategy(double falsePositiveRate) {
        super(falsePositiveRate);
        this.initialTime = 0;
        this.loggingInterval = NetworkConfiguration.getInstance().getInitialLoggingInterval();
        this.attackOccurrencesDuringInterval = 0;
    }

    public PeriodicLoggingStrategy() {
        super();
        this.initialTime = 0;
        this.loggingInterval = NetworkConfiguration.getInstance().getInitialLoggingInterval();
        this.attackOccurrencesDuringInterval = 0;
    }

    public double getLoggingInterval() {
        return loggingInterval;
    }

    public void setLoggingInterval(double loggingInterval) {
        this.loggingInterval = loggingInterval;
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

        int currentTick = tickProvider.getCurrentTick();
        if (initialTime < currentTick && currentTick < initialTime + loggingInterval) {
            super.bloomFilter.put(packet);
        }
        updateRouterState(currentTick);
    }

    @Override
    public boolean checkPacket(Packet packet) {
        int currentTick = tickProvider.getCurrentTick();

        if (initialTime + NetworkConfiguration.getInstance().getRoundTripDelay() < currentTick &&
                currentTick < initialTime + loggingInterval + NetworkConfiguration.getInstance().getRoundTripDelay()) {
            if (super.bloomFilter.mightContain(packet)) {
                return true;
            } else {
                this.attackOccurrencesDuringInterval++;
                return false;
            }
        }

        updateRouterState(currentTick);

        return true;
    }

    private void updateRouterState(int currentTick) {
        if (currentTick > initialTime + loggingInterval + NetworkConfiguration.getInstance().getRoundTripDelay()) {
            if (attackOccurrencesDuringInterval > NetworkConfiguration.getInstance().getAttackThreshold()) {
                loggingInterval += NetworkConfiguration.getInstance().getInitialLoggingInterval();
                attackOccurrencesDuringInterval = 0;
            } else {
                initialTime += loggingInterval + NetworkConfiguration.getInstance().getSilentPeriod();
                loggingInterval = NetworkConfiguration.getInstance().getInitialLoggingInterval();
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
        this.initialTime = NetworkConfiguration.getInstance().getInitialLoggingInterval() * new Random().nextDouble();
        this.loggingInterval = NetworkConfiguration.getInstance().getInitialLoggingInterval();
        this.attackOccurrencesDuringInterval = 0;
    }
}