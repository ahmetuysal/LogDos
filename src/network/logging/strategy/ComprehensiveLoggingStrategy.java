package network.logging.strategy;

import network.NetworkConfigurationConstants;
import network.Packet;

import java.util.Random;

public class ComprehensiveLoggingStrategy extends LoggingStrategy {

    public static int falseResultCount = 0;
    public static int checkCount = 0;

    @Override
    public void logPacket(Packet packet) {
        super.loggedPackages.add(packet);
    }

    @Override
    public boolean checkPacket(Packet packet) {
        ComprehensiveLoggingStrategy.checkCount++;
        if (super.loggedPackages.contains(packet)) {
            return true;
        } else {
            ComprehensiveLoggingStrategy.falseResultCount++;
            return new Random().nextDouble() < NetworkConfigurationConstants.FALSE_POSITIVE_RATE;
        }
    }
}
