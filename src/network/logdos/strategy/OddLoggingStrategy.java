package network.logdos.strategy;

import network.Packet;

public class OddLoggingStrategy extends LogDosStrategy {

    public OddLoggingStrategy() {
        super();
    }

    public OddLoggingStrategy(double falsePositiveRate) {
        super(falsePositiveRate);
    }

    @Override
    public void logPacket(Packet packet, boolean isForced) {
        if (isForced || packet.getPidStack().size() % 2 == 1) {
            super.bloomFilter.put(packet);
        }
    }

    @Override
    public boolean checkPacket(Packet packet) {
        if (packet.getPidStack().size() % 2 == 1) {
            return super.bloomFilter.mightContain(packet);
        } else {
            return true;
        }
    }
}