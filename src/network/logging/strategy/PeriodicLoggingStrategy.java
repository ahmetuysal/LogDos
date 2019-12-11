package network.logging.strategy;

import network.NetworkConfigurationConstants;
import network.Packet;
import util.TickProvider;

import java.util.Random;

public class PeriodicLoggingStrategy extends LoggingStrategy {

    private int loggingInterval;
    private int initialTime;
    private int attackOccurrencesDuringInterval;

    public PeriodicLoggingStrategy() {
        super();
        this.initialTime = TickProvider.getInstance().getCurrentTick() - (NetworkConfigurationConstants.INITIAL_LOGGING_INTERVAL / 2)
                + new Random().nextInt(NetworkConfigurationConstants.INITIAL_LOGGING_INTERVAL);
        this.loggingInterval = NetworkConfigurationConstants.INITIAL_LOGGING_INTERVAL;
        this.attackOccurrencesDuringInterval = 0;
    }

    @Override
    public void logPacket(Packet packet) {
        int currentTick = TickProvider.getInstance().getCurrentTick();

        if (initialTime < currentTick && currentTick < initialTime + loggingInterval) {
            super.loggedPackages.add(packet);
        }

        updateRouterState(currentTick);
    }

    @Override
    public boolean checkPacket(Packet packet) {
        int currentTick = TickProvider.getInstance().getCurrentTick();

        if (initialTime + NetworkConfigurationConstants.ROUND_TRIP_DELAY < currentTick &&
                currentTick < initialTime + loggingInterval + NetworkConfigurationConstants.ROUND_TRIP_DELAY) {
            if (super.loggedPackages.contains(packet)) {
                return true;
            } else {
                boolean isFalsePositive = NetworkConfigurationConstants.FALSE_POSITIVE_RATE - new Random().nextDouble()  > 0;
                if (!isFalsePositive) {
                    this.attackOccurrencesDuringInterval++;
                }
                return isFalsePositive;
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
