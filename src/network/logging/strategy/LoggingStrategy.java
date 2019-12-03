package network.logging.strategy;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import network.Packet;

public abstract class LoggingStrategy {

    protected BloomFilter<Packet> bloomFilter;

    public LoggingStrategy() {
        this.bloomFilter = BloomFilter.create((Funnel<Packet>) (packet, primitiveSink) -> {
            primitiveSink.putInt(packet.getSourceId())
                    .putUnencodedChars(packet.getSid().toString());

            packet.getPidStack().forEach(integer -> primitiveSink.putInt(integer));
        }, 2000);
    }

    abstract void logPacket(Packet packet);

    boolean checkPacket(Packet packet) {
        return this.bloomFilter.mightContain(packet);
    }

}
