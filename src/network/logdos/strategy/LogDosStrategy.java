package network.logdos.strategy;

import network.Packet;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import config.NetworkConfiguration;

public abstract class LogDosStrategy {

    protected BloomFilter<Packet> bloomFilter;

    public LogDosStrategy() {
        this(NetworkConfiguration.getInstance().getDefaultFalsePositiveRate());
    }

    public LogDosStrategy(double falsePositiveRate) {
        this.bloomFilter = BloomFilter.create((Funnel<Packet>) (packet, primitiveSink) -> {
            primitiveSink.putUnencodedChars(packet.getSid().toString());
            packet.getPidStack().forEach(integer -> primitiveSink.putInt(integer));
        }, NetworkConfiguration.getInstance().getBloomFilterExpectedInsertions(), falsePositiveRate);
    }

    public final void logPacket(Packet packet) {
        logPacket(packet, false);
    }

    public abstract void logPacket(Packet packet, boolean isForced);

    public abstract boolean checkPacket(Packet packet);

}
