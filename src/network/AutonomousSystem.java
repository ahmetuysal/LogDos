package network;

import network.logging.strategy.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AutonomousSystem extends Routable {

    public static int packagesCaught = 0;
    public static int packagesReached = 0;
    private List<Route> routes = new ArrayList<>();
    private Set<AutonomousSystem> connectedAutonomousSystems = new HashSet<>();
    private AutonomousSystemType type;
    private LoggingStrategy loggingStrategy;

    public AutonomousSystem(int id) {
        this(id, AutonomousSystemType.CORE);
    }

    public AutonomousSystem(int id, AutonomousSystemType type) {
        this(id, type, LoggingStrategyType.COMPREHENSIVE);
    }

    public AutonomousSystem(int id, AutonomousSystemType type, LoggingStrategyType loggingStrategyType) {
        this(id, type, loggingStrategyType, NetworkConfigurationConstants.DEFAULT_FALSE_POSITIVE_RATE);
    }

    public AutonomousSystem(int id, AutonomousSystemType type, LoggingStrategyType loggingStrategyType, double falsePositiveRate) {
        super(id);
        this.type = type;
        this.loggingStrategy = getLoggingStrategy(loggingStrategyType, falsePositiveRate);
    }

    private static LoggingStrategy getLoggingStrategy(LoggingStrategyType loggingStrategyType, double falsePositiveRate) {
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

    public void sendInterestPacket(Packet packet, List<AutonomousSystem> path) {
        if (path.isEmpty()) {
            sendResponsePacket(packet, true);
        } else {
            this.loggingStrategy.logPacket(packet);
            packet.getPidStack().add(this.getId());
            var nextAs = path.remove(0);
            nextAs.sendInterestPacket(packet, path);
        }
    }

    public void sendResponsePacket(Packet packet) {
        sendResponsePacket(packet, false);
    }

    public void sendResponsePacket(Packet packet, boolean firstTime) {
        if (!firstTime && !this.loggingStrategy.checkPacket(packet)) {
            AutonomousSystem.packagesCaught++;
            // System.out.println("Caught attack packet " + packet.toString() + " at node " + this.getId());
        } else {
            if (packet.getPidStack().isEmpty()) {
                AutonomousSystem.packagesReached++;
                // System.out.println("Packet " + packet.toString() + " reached the target: "+ this.getId());
            } else {
                var nextASId = packet.getPidStack().pop();
                var nextAS = AutonomousSystemTopology.getInstance().getAutonomousSystemById(nextASId);
                nextAS.sendResponsePacket(packet);
            }
        }
    }


    public AutonomousSystemType getType() {
        return type;
    }

    public void setType(AutonomousSystemType _type) {
        this.type = _type;
    }

    public void addRoute(Route route) {
        this.routes.add(route);
        if (route.getOrigin().equals(this)) {
            this.connectedAutonomousSystems.add((AutonomousSystem) route.getDestination());
        } else {
            this.connectedAutonomousSystems.add((AutonomousSystem) route.getOrigin());
        }
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

        System.out.println(getId() + " " + autonomousSystem.getId() + " " + autonomousSystem.getConnectedAutonomousSystems().contains(this));
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
