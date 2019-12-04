package network;

import java.util.*;

public class AutonomousSystemTopology {

    private static AutonomousSystemTopology instance;
    public HashMap<Integer, AutonomousSystem> autonomousSystemMap = new HashMap<>();
    private HashMap<AutonomousSystem, HashMap<AutonomousSystem, List<AutonomousSystem>>> paths = new HashMap<>(); //Memoization for fast search.
    private HashSet<Route> routeSet = new HashSet<>();

    private AutonomousSystemTopology() {
    }

    public static synchronized AutonomousSystemTopology getInstance() {
        if (instance == null) {
            instance = new AutonomousSystemTopology();
        }
        return instance;
    }

    /**
     * Adds a <code>Route</code> object to this <code>AutonomousSystemTopology<code>.
     *
     * @param _route <code>Route</code> object to add to this <code>AutonomousSystemTopology<code>.
     * @return <code>true</code> if given <code>Route</code> object is added, <code>false</code> otherwise.
     */
    public boolean addRoute(Route _route) {
        return this.routeSet.add(_route);
    }

    /**
     * Removes a <code>Route</code> object from this <code>AutonomousSystemTopology<code>.
     *
     * @param _route <code>Route</code> object to remove from this <code>AutonomousSystemTopology<code>.
     * @return <code>true</code> if given <code>Route</code> object is removed, <code>false</code> otherwise.
     */
    public boolean removeRoute(Route _route) {
        return this.routeSet.remove(_route);
    }

    public boolean addAutonomousSystem(AutonomousSystem _autonomousSystem) {
        return this.autonomousSystemMap.put(_autonomousSystem.getId(), _autonomousSystem) == null;
    }

    public boolean removeAutonomousSystem(AutonomousSystem _autonomousSystem) {
        return this.autonomousSystemMap.remove(_autonomousSystem.getId(), _autonomousSystem);
    }

    public List<AutonomousSystem> findPathBetweenAutonomousSystemsBFS(int as1Id, int as2Id) {
        AutonomousSystem startPoint = this.getAutonomousSystemById(as1Id);
        AutonomousSystem targetAS = this.getAutonomousSystemById(as2Id);

        HashMap<AutonomousSystem, AutonomousSystem> parentMap = new HashMap<>();
        Queue<AutonomousSystem> queue = new ArrayDeque<>();
        queue.add(startPoint);

        while (!queue.isEmpty()) {
            AutonomousSystem node = queue.remove();
            if (node.equals(targetAS)) {
                return backtracePath(parentMap, startPoint, node);
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

    public boolean hasAutonomousSystemById(int _id) {
        return this.autonomousSystemMap.containsKey(_id);
    }

    public AutonomousSystem getAutonomousSystemById(int _id) {
        return this.autonomousSystemMap.get(_id);
    }

    public HashSet<Route> getRouteSet() {
        return routeSet;
    }

    public Collection<AutonomousSystem> getAutonomousSystems() {
        return autonomousSystemMap.values();
    }
}
