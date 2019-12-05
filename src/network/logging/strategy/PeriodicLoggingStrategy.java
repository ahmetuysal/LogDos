package network.logging.strategy;

import network.Packet;
import util.TickProvider;

public class PeriodicLoggingStrategy extends LoggingStrategy {

    private static final int INITIAL_LOGGING_INTERVAL = 20;
    private static final int SILENT_PERIOD = 40;
    private static final int ROUND_TRIP_DELAY = 0;
    private static final int ATTACK_THRESHOLD = 0;
    int loggingInterval;
    int initialTime;
    int attackOccurrencesDuringInterval;


    public PeriodicLoggingStrategy() {
        super();
        this.initialTime = TickProvider.getInstance().getCurrentTick();
        this.loggingInterval = INITIAL_LOGGING_INTERVAL;
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

        if (initialTime + ROUND_TRIP_DELAY < currentTick && currentTick < initialTime + loggingInterval + ROUND_TRIP_DELAY) {
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
        if (currentTick > initialTime + loggingInterval + ROUND_TRIP_DELAY) {
            if (attackOccurrencesDuringInterval > ATTACK_THRESHOLD) {
                loggingInterval += INITIAL_LOGGING_INTERVAL;
                attackOccurrencesDuringInterval = 0;
            } else {
                initialTime += loggingInterval + SILENT_PERIOD;
                loggingInterval = INITIAL_LOGGING_INTERVAL;
            }
        }
    }
}
