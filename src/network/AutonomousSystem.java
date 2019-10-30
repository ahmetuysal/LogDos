package network;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AutonomousSystem extends Routable {


    private List<Route> routes = new ArrayList<>();
    private Set<AutonomousSystem> connectedAutonomousSystems = new HashSet<>();
    private AutonomousSystemType type;

    public AutonomousSystem(int id) {
        super(id);
        this.type = AutonomousSystemType.CORE;
    }

    public AutonomousSystem(int id, AutonomousSystemType type) {
        super(id);
        this.type = type;
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

    public List<Route> getRoutes() {
        return routes;
    }

    public Set<AutonomousSystem> getConnectedAutonomousSystems() {
        return connectedAutonomousSystems;
    }

}
