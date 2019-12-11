package network.logging.strategy;

import network.Packet;

import java.util.HashSet;

public abstract class LoggingStrategy {

    protected HashSet<Packet> loggedPackages;

    public LoggingStrategy() {
        this.loggedPackages = new HashSet<>();
    }

    public abstract void logPacket(Packet packet);

    public abstract boolean checkPacket(Packet packet);

}
