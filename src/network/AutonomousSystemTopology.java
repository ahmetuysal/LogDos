package network;

import network.logging.strategy.TimeBasedLoggingStrategy;
import util.TickProvider;

import java.util.*;

public class AutonomousSystemTopology implements Cloneable {

    public HashMap<Integer, AutonomousSystem> autonomousSystemMap;
    private HashMap<AutonomousSystem, HashMap<AutonomousSystem, List<AutonomousSystem>>> paths; //Memoization for fast search.
    private HashSet<Route> routeSet;

    public AutonomousSystemTopology() {
        this.autonomousSystemMap = new HashMap<>();
        this.paths = new HashMap<>();
        this.routeSet = new HashSet<>();
    }

    /**
     * Adds a <code>Route</code> object to this <code>AutonomousSystemTopology<code>.
     *
     * @param route <code>Route</code> object to add to this <code>AutonomousSystemTopology<code>.
     * @return <code>true</code> if given <code>Route</code> object is added, <code>false</code> otherwise.
     */
    public boolean addRoute(Route route) {
        return this.routeSet.add(route);
    }

    /**
     * Removes a <code>Route</code> object from this <code>AutonomousSystemTopology<code>.
     *
     * @param route <code>Route</code> object to remove from this <code>AutonomousSystemTopology<code>.
     * @return <code>true</code> if given <code>Route</code> object is removed, <code>false</code> otherwise.
     */
    public boolean removeRoute(Route route) {
        return this.routeSet.remove(route);
    }

    public boolean addAutonomousSystem(AutonomousSystem autonomousSystem) {
        return this.autonomousSystemMap.put(autonomousSystem.getId(), autonomousSystem) == null;
    }

    public boolean removeAutonomousSystem(AutonomousSystem autonomousSystem) {
        return this.autonomousSystemMap.remove(autonomousSystem.getId(), autonomousSystem);
    }

    public void setTickProvidersForAllTimeBasedLoggingStrategies(TickProvider tickProvider) {
        this.autonomousSystemMap.values()
                .forEach(as -> {
                    if (as.getLoggingStrategy() instanceof TimeBasedLoggingStrategy)
                        ((TimeBasedLoggingStrategy) as.getLoggingStrategy()).setTickProvider(tickProvider);
                });
    }


    public List<AutonomousSystem> findPathBetweenAutonomousSystemsBFS(AutonomousSystem as1, AutonomousSystem as2) {
        HashMap<AutonomousSystem, AutonomousSystem> parentMap = new HashMap<>();
        Queue<AutonomousSystem> queue = new ArrayDeque<>();
        queue.add(as1);

        while (!queue.isEmpty()) {
            AutonomousSystem node = queue.remove();
            if (node.equals(as2)) {
                return backtracePath(parentMap, as1, node);
            }
            node.getConnectedAutonomousSystems().forEach(adjacentAutonomousSystem -> {
                if (!parentMap.containsKey(adjacentAutonomousSystem)) {
                    queue.add(adjacentAutonomousSystem);
                    parentMap.put(adjacentAutonomousSystem, node);
                }
            });
        }

        return null;
    }

    public List<AutonomousSystem> findPathBetweenAutonomousSystemsBFS(int as1Id, int as2Id) {
        AutonomousSystem startPoint = this.getAutonomousSystemById(as1Id);
        AutonomousSystem targetAS = this.getAutonomousSystemById(as2Id);
        return findPathBetweenAutonomousSystemsBFS(startPoint, targetAS);
    }

    public List<AutonomousSystem> findPathBetweenAutonomousSystemsBFS(AutonomousSystem as1, int as2Id) {
        AutonomousSystem targetAS = this.getAutonomousSystemById(as2Id);
        return findPathBetweenAutonomousSystemsBFS(as1, targetAS);
    }

    public List<AutonomousSystem> findPathBetweenAutonomousSystemsBFS(int as1Id, AutonomousSystem as2) {
        AutonomousSystem startPoint = this.getAutonomousSystemById(as1Id);
        return findPathBetweenAutonomousSystemsBFS(startPoint, as2);
    }

    private List<AutonomousSystem> backtracePath(HashMap<AutonomousSystem, AutonomousSystem> parentMap, AutonomousSystem start, AutonomousSystem end) {
        AutonomousSystem current = end;
        List<AutonomousSystem> path = new ArrayList<>();
        path.add(current);
        while (!current.equals(start)) {
            current = parentMap.get(current);
            path.add(current);
        }

        Collections.reverse(path);
        return path;
    }

    public boolean hasAutonomousSystemById(int id) {
        return this.autonomousSystemMap.containsKey(id);
    }

    public AutonomousSystem getAutonomousSystemById(int id) {
        return this.autonomousSystemMap.get(id);
    }

    public HashSet<Route> getRouteSet() {
        return routeSet;
    }

    public Collection<AutonomousSystem> getAutonomousSystems() {
        return autonomousSystemMap.values();
    }

}
