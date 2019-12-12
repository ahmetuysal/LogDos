package network.logging.strategy;

import network.Packet;

public class EvenLoggingStrategy extends LoggingStrategy {
    @Override
    public void logPacket(Packet packet) {
        if (packet.getPidStack().size() % 2 == 0) {
            super.bloomFilter.put(packet);
        }
    }

    @Override
    public boolean checkPacket(Packet packet) {
        if (packet.getPidStack().size() % 2 == 0) {
            return super.bloomFilter.mightContain(packet);
        } else {
            return true;
        }
    }
}