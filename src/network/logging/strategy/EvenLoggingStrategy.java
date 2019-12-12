package network.logging.strategy;

import network.NetworkConfigurationConstants;
import network.Packet;

import java.util.Random;

public class EvenLoggingStrategy extends LoggingStrategy {
    @Override
    public void logPacket(Packet packet) {
        if (packet.getPidStack().size() % 2 == 0) {
            super.loggedPackages.add(packet);
        }
    }

    @Override
    public boolean checkPacket(Packet packet) {
        if (packet.getPidStack().size() % 2 == 0) {
            if (super.loggedPackages.contains(packet)) {
                return true;
            } else {
                return new Random().nextDouble() < NetworkConfigurationConstants.FALSE_POSITIVE_RATE;
            }
        } else {
            return true;
        }
    }
}
