package network.logging.strategy;

import network.Packet;

public class ComprehensiveLoggingStrategy extends  LoggingStrategy {
    @Override
    public void logPacket(Packet packet) {
        super.bloomFilter.put(packet);
    }

    @Override
    public boolean checkPacket(Packet packet) {
        return super.bloomFilter.mightContain(packet);
    }
}
