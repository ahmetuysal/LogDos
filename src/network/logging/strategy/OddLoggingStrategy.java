package network.logging.strategy;

import network.Packet;

public class OddLoggingStrategy extends LoggingStrategy {
    @Override
    void logPacket(Packet packet) {
        if (packet.getPidStack().size() % 2 == 1) {
            super.bloomFilter.put(packet);
        }
    }
}
