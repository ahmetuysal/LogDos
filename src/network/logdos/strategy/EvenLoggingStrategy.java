package network.logdos.strategy;

import network.Packet;

public class EvenLoggingStrategy extends LogDosStrategy {

    public EvenLoggingStrategy() {
        super();
    }

    public EvenLoggingStrategy(double falsePositiveRate) {
        super(falsePositiveRate);
    }

    @Override
    public void logPacket(Packet packet, boolean isForced) {
        if (isForced || packet.getPidStack().size() % 2 == 0) {
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