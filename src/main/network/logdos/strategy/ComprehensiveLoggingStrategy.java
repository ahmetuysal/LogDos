package network.logdos.strategy;

import network.Packet;

public class ComprehensiveLoggingStrategy extends LogDosStrategy {

    public ComprehensiveLoggingStrategy() {
        super();
    }

    public ComprehensiveLoggingStrategy(double falsePositiveRate) {
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