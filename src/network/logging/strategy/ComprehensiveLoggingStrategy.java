package network.logging.strategy;

import network.NetworkConfigurationConstants;
import network.Packet;

import java.util.Random;

public class ComprehensiveLoggingStrategy extends LoggingStrategy {
    @Override
    public void logPacket(Packet packet) {
        super.loggedPackages.add(packet);
    }

    @Override
    public boolean checkPacket(Packet packet) {
        if (super.loggedPackages.contains(packet)) {
            return true;
        } else {
            return NetworkConfigurationConstants.FALSE_POSITIVE_RATE - new Random().nextDouble() > 0;
        }
    }
}
