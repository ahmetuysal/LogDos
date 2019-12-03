package network.logging.strategy;

import network.Packet;

public class OddLoggingStrategy extends LoggingStrategy {
    @Override
    public void logPacket(Packet packet) {
        if (packet.getPidStack().size() % 2 == 1) {
            super.bloomFilter.put(packet);
        }
    }

    @Override
    public boolean checkPacket(Packet packet) {
        if (packet.getPidStack().size() % 2 == 1) {
            return super.bloomFilter.mightContain(packet);
        }
        else {
            return true;
        }
    }
}
