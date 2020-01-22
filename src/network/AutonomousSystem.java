package network;

import config.NetworkConfiguration;
import network.logging.strategy.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AutonomousSystem {

    private final int id;
    private List<Route> routes;
    private Set<AutonomousSystem> connectedAutonomousSystems;
    private AutonomousSystemType type;
    private LoggingStrategy loggingStrategy;

    public AutonomousSystem(int id) {
        this(id, AutonomousSystemType.CORE);
    }

    public AutonomousSystem(int id, AutonomousSystemType type) {
        this(id, type, LoggingStrategyType.COMPREHENSIVE);
    }

    public AutonomousSystem(int id, AutonomousSystemType type, LoggingStrategyType loggingStrategyType) {
        this(id, type, loggingStrategyType, NetworkConfiguration.getInstance().getDefaultFalsePositiveRate());
    }

    public AutonomousSystem(int id, AutonomousSystemType type, LoggingStrategyType loggingStrategyType, double falsePositiveRate) {
        this.id = id;
        this.type = type;
        this.routes = new ArrayList<>();
        this.connectedAutonomousSystems = new HashSet<>();
        this.loggingStrategy = getLoggingStrategyForTypeAndFPRate(loggingStrategyType, falsePositiveRate);
    }

    private static LoggingStrategy getLoggingStrategyForTypeAndFPRate(LoggingStrategyType loggingStrategyType, double falsePositiveRate) {
        switch (loggingStrategyType) {
            case ODD:
                return new OddLoggingStrategy(falsePositiveRate);
            case EVEN:
                return new EvenLoggingStrategy(falsePositiveRate);
            case PERIODIC:
                return new PeriodicLoggingStrategy(falsePositiveRate);
            case COMPREHENSIVE:
            default:
                return new ComprehensiveLoggingStrategy(falsePositiveRate);
        }
    }

    public int getId() {
        return id;
    }

    public LoggingStrategy getLoggingStrategy() {
        return loggingStrategy;
    }

    public void sendInterestPacket(Packet packet, List<AutonomousSystem> path, AutonomousSystemTopology ast) {
        if (path.isEmpty()) {
            sendResponsePacket(packet, ast, true);
        } else {
            this.loggingStrategy.logPacket(packet);
            packet.getPidStack().add(this.getId());
            var nextAs = path.remove(0);
            nextAs.sendInterestPacket(packet, path, ast);
        }
    }

    /**
     * Returns <code>true</code> if packet has reached its final destination, false otherwise (packet is discarded as attack packet)
     *
     * @param packet Response packet to send.
     * @return <code>true</code> if packet has reached its final destination, false otherwise
     */
    public boolean sendResponsePacket(Packet packet, AutonomousSystemTopology ast) {
        return sendResponsePacket(packet, ast, false);
    }

    /**
     * Returns <code>true</code> if packet has reached its final destination, false otherwise (packet is discarded as attack packet)
     *
     * @param packet    Response packet to send.
     * @param firstTime A flag to indicate this is the sender AS and no check should be done.
     * @return <code>true</code> if packet has reached its final destination, false otherwise
     */
    public boolean sendResponsePacket(Packet packet, AutonomousSystemTopology ast, boolean firstTime) {
        if (!firstTime && !this.loggingStrategy.checkPacket(packet)) {
            return false;
        } else {
            if (packet.getPidStack().isEmpty()) {
                return true;
            } else {
                var nextASId = packet.getPidStack().pop();
                var nextAS = ast.getAutonomousSystemById(nextASId);
                return nextAS.sendResponsePacket(packet, ast);
            }
        }
    }


    public AutonomousSystemType getType() {
        return type;
    }

    public void setType(AutonomousSystemType type) {
        this.type = type;
    }

    public void addRoute(Route route) {
        this.routes.add(route);
        if (route.getOrigin().equals(this)) {
            this.connectedAutonomousSystems.add(route.getDestination());
        } else {
            this.connectedAutonomousSystems.add(route.getOrigin());
        }
    }

    public void logPacket(Packet packet, boolean isForced) {
        this.loggingStrategy.logPacket(packet, isForced);
    }

    public void logPacket(Packet packet) {
        this.loggingStrategy.logPacket(packet);
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public Set<AutonomousSystem> getConnectedAutonomousSystems() {
        return connectedAutonomousSystems;
    }


    public Route getRouteToAutonomousSystem(AutonomousSystem autonomousSystem) {
        for (Route route : this.routes) {
            if (route.getOrigin().getId() == autonomousSystem.getId() || route.getDestination().getId() == autonomousSystem.getId()) {
                return route;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "AutonomousSystem{" +
                "id=" + this.getId() +
                ", type=" + type +
                '}';
    }
}
