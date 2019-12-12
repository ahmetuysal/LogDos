package network;

import network.logging.strategy.ComprehensiveLoggingStrategy;
import network.logging.strategy.LoggingStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AutonomousSystem extends Routable {

    private List<Route> routes = new ArrayList<>();
    private Set<AutonomousSystem> connectedAutonomousSystems = new HashSet<>();
    private AutonomousSystemType type;
    private LoggingStrategy loggingStrategy;
    public static int packagesCaught = 0;
    public static int packagesReached = 0;

    public AutonomousSystem(int id) {
        super(id);
        this.type = AutonomousSystemType.CORE;
        this.loggingStrategy = new ComprehensiveLoggingStrategy();
    }

    public AutonomousSystem(int id, AutonomousSystemType type) {
        super(id);
        this.type = type;
        this.loggingStrategy = new ComprehensiveLoggingStrategy();
    }

    public AutonomousSystem(int id, AutonomousSystemType type, LoggingStrategy loggingStrategy) {
        super(id);
        this.type = type;
        this.loggingStrategy = loggingStrategy;
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
        if (!packet.getPidStack().isEmpty()) {
            if (!firstTime && !this.loggingStrategy.checkPacket(packet)) {
                AutonomousSystem.packagesCaught++;
                // System.out.println("Caught attack packet " + packet.toString() + " at node " + this.getId());
            } else {
                var nextASId = packet.getPidStack().pop();
                var nextAS = AutonomousSystemTopology.getInstance().getAutonomousSystemById(nextASId);
                nextAS.sendResponsePacket(packet);
            }
        }
        else {
            AutonomousSystem.packagesReached++;
            // System.out.println("Packet " + packet.toString() + " reached the target: "+ this.getId());
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
