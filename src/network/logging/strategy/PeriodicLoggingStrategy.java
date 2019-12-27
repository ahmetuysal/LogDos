package network.logging.strategy;

import config.NetworkConfiguration;
import network.Packet;
import util.TickProvider;

public class PeriodicLoggingStrategy extends LoggingStrategy {

    int loggingInterval;
    int initialTime;
    int attackOccurrencesDuringInterval;


    public PeriodicLoggingStrategy(double falsePositiveRate) {
        super(falsePositiveRate);
        this.initialTime = TickProvider.getInstance().getCurrentTick();
        this.loggingInterval = NetworkConfiguration.INITIAL_LOGGING_INTERVAL;
        this.attackOccurrencesDuringInterval = 0;
    }

    public PeriodicLoggingStrategy() {
        super();
        this.initialTime = TickProvider.getInstance().getCurrentTick();
        this.loggingInterval = NetworkConfiguration.INITIAL_LOGGING_INTERVAL;
        this.attackOccurrencesDuringInterval = 0;
    }

    @Override
    public void logPacket(Packet packet, boolean isForced) {
        int currentTick = TickProvider.getInstance().getCurrentTick();
        if (isForced || (initialTime < currentTick && currentTick < initialTime + loggingInterval)) {
            super.bloomFilter.put(packet);
        }
        updateRouterState(currentTick);
    }

    @Override
    public boolean checkPacket(Packet packet) {
        int currentTick = TickProvider.getInstance().getCurrentTick();

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
}