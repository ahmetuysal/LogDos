package network.logging.strategy;

import network.Packet;

public class ComprehensiveLoggingStrategy extends  LoggingStrategy {
    @Override
    void logPacket(Packet packet) {
        super.bloomFilter.put(packet);
    }
}
