package network.logging.strategy;

import network.NetworkConfigurationConstants;
import network.Packet;

import java.util.Random;

public class OddLoggingStrategy extends LoggingStrategy {
    @Override
    public void logPacket(Packet packet) {
        if (packet.getPidStack().size() % 2 == 1) {
            super.loggedPackages.add(packet);
        }
    }

    @Override
    public boolean checkPacket(Packet packet) {
        if (packet.getPidStack().size() % 2 == 1) {
            if (super.loggedPackages.contains(packet)) {
                return true;
            } else {
                return NetworkConfigurationConstants.FALSE_POSITIVE_RATE - new Random().nextDouble()  > 0;
            }
        } else {
            return true;
        }
    }
}
