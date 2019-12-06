package network.logging.strategy;

import network.NetworkConfigurationConstants;
import network.Packet;
import util.TickProvider;

public class PeriodicLoggingStrategy extends LoggingStrategy {

    int loggingInterval;
    int initialTime;
    int attackOccurrencesDuringInterval;


    public PeriodicLoggingStrategy() {
        super();
        this.initialTime = TickProvider.getInstance().getCurrentTick();
        this.loggingInterval = NetworkConfigurationConstants.INITIAL_LOGGING_INTERVAL;
        this.attackOccurrencesDuringInterval = 0;
    }

    @Override
    public void logPacket(Packet packet) {
        int currentTick = TickProvider.getInstance().getCurrentTick();
        if (initialTime < currentTick && currentTick < initialTime + loggingInterval) {
            super.bloomFilter.put(packet);
        }

        updateRouterState(currentTick);
    }

    @Override
    public boolean checkPacket(Packet packet) {
        int currentTick = TickProvider.getInstance().getCurrentTick();

        if (initialTime + NetworkConfigurationConstants.ROUND_TRIP_DELAY < currentTick &&
                currentTick < initialTime + loggingInterval + NetworkConfigurationConstants.ROUND_TRIP_DELAY) {
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
        if (currentTick > initialTime + loggingInterval + NetworkConfigurationConstants.ROUND_TRIP_DELAY) {
            if (attackOccurrencesDuringInterval > NetworkConfigurationConstants.ATTACK_THRESHOLD) {
                loggingInterval += NetworkConfigurationConstants.INITIAL_LOGGING_INTERVAL;
                attackOccurrencesDuringInterval = 0;
            } else {
                initialTime += loggingInterval + NetworkConfigurationConstants.SILENT_PERIOD;
                loggingInterval = NetworkConfigurationConstants.INITIAL_LOGGING_INTERVAL;
            }
        }
    }
}
