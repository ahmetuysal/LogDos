package network.logdos.strategy;

import network.Packet;

public class ComprehensiveLogDosStrategy extends LogDosStrategy {

    public ComprehensiveLogDosStrategy() {
        super();
    }

    public ComprehensiveLogDosStrategy(double falsePositiveRate) {
        super(falsePositiveRate);
    }

    @Override
    public void logPacket(Packet packet, boolean isForced) {
        super.bloomFilter.put(packet);
    }

    @Override
    public boolean checkPacket(Packet packet) {
        return super.bloomFilter.mightContain(packet);
    }
}