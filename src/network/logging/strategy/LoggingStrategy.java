package network.logging.strategy;

import network.Packet;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import network.NetworkConfigurationConstants;

public abstract class LoggingStrategy {

    protected BloomFilter<Packet> bloomFilter;

    public LoggingStrategy() {
        this(NetworkConfigurationConstants.DEFAULT_FALSE_POSITIVE_RATE);
    }

    public LoggingStrategy(double falsePositiveRate) {
        this.bloomFilter = BloomFilter.create((Funnel<Packet>) (packet, primitiveSink) -> {
            primitiveSink.putUnencodedChars(packet.getSid().toString());
            packet.getPidStack().forEach(integer -> primitiveSink.putInt(integer));
        }, NetworkConfigurationConstants.BLOOM_FILTER_EXPECTED_INSERTIONS, falsePositiveRate);
    }

    public abstract void logPacket(Packet packet);

    public abstract boolean checkPacket(Packet packet);

}
