package network.logging.strategy;

import config.NetworkConfiguration;
import network.Packet;
import util.TickProvider;

public class PeriodicLoggingStrategy extends LoggingStrategy implements TimeBasedLoggingStrategy {

    private int loggingInterval;
    private int initialTime;
    private int attackOccurrencesDuringInterval;
    private TickProvider tickProvider;

    public PeriodicLoggingStrategy(double falsePositiveRate) {
        super(falsePositiveRate);
        this.initialTime = 0;
        this.loggingInterval = NetworkConfiguration.INITIAL_LOGGING_INTERVAL;
        this.attackOccurrencesDuringInterval = 0;
    }

    public PeriodicLoggingStrategy() {
        super();
        this.initialTime = 0;
        this.loggingInterval = NetworkConfiguration.INITIAL_LOGGING_INTERVAL;
        this.attackOccurrencesDuringInterval = 0;
    }

    public int getLoggingInterval() {
        return loggingInterval;
    }

    public void setLoggingInterval(int loggingInterval) {
        this.loggingInterval = loggingInterval;
    }

    public int getInitialTime() {
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

        if (initialTime + NetworkConfiguration.ROUND_TRIP_DELAY < currentTick &&
                currentTick < initialTime + loggingInterval + NetworkConfiguration.ROUND_TRIP_DELAY) {
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
        if (currentTick > initialTime + loggingInterval + NetworkConfiguration.ROUND_TRIP_DELAY) {
            if (attackOccurrencesDuringInterval > NetworkConfiguration.ATTACK_THRESHOLD) {
                loggingInterval += NetworkConfiguration.INITIAL_LOGGING_INTERVAL;
                attackOccurrencesDuringInterval = 0;
            } else {
                initialTime += loggingInterval + NetworkConfiguration.SILENT_PERIOD;
                loggingInterval = NetworkConfiguration.INITIAL_LOGGING_INTERVAL;
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
        this.initialTime = 0;
        this.loggingInterval = NetworkConfiguration.INITIAL_LOGGING_INTERVAL;
        this.attackOccurrencesDuringInterval = 0;
    }
}