package network.logging.strategy;

import network.Packet;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import config.NetworkConfiguration;

public abstract class LoggingStrategy {

    protected BloomFilter<Packet> bloomFilter;

    public LoggingStrategy() {
        this(NetworkConfiguration.DEFAULT_FALSE_POSITIVE_RATE);
    }

    public LoggingStrategy(double falsePositiveRate) {
        this.bloomFilter = BloomFilter.create((Funnel<Packet>) (packet, primitiveSink) -> {
            primitiveSink.putUnencodedChars(packet.getSid().toString());
            packet.getPidStack().forEach(integer -> primitiveSink.putInt(integer));
        }, NetworkConfiguration.BLOOM_FILTER_EXPECTED_INSERTIONS, falsePositiveRate);
    }

    public final void logPacket(Packet packet) {
        logPacket(packet, false);
    }

    public abstract void logPacket(Packet packet, boolean isForced);

    public abstract boolean checkPacket(Packet packet);

}
