package network.logging.strategy;

import network.Packet;

public class EvenLoggingStrategy extends LoggingStrategy {
    @Override
    void logPacket(Packet packet) {
        if (packet.getPidStack().size() % 2 == 0) {
            super.bloomFilter.put(packet);
        }
    }
}
