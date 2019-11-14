package network;

import java.util.*;

public class AutonomousSystemTopology {

    private HashMap<AutonomousSystem, HashMap<AutonomousSystem, List<AutonomousSystem>>> paths = new HashMap<>(); //Memoization for fast search.

    public HashMap<Integer, AutonomousSystem> autonomousSystemMap = new HashMap<>();

    private HashSet<Route> routeSet = new HashSet<>();

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

        final List<AutonomousSystem> visitedAS = new ArrayList<>();
        Queue<AutonomousSystem> autonomousSystemQueue = new ArrayDeque<>();
        autonomousSystemQueue.add(startPoint);

        Map<AutonomousSystem, AutonomousSystem> addedByWhom = new HashMap<>();

        boolean didFindPath = false;

        while (!autonomousSystemQueue.isEmpty()) {
            AutonomousSystem currentSystem = autonomousSystemQueue.poll();

            if (paths.containsKey(currentSystem) && paths.get(currentSystem).containsKey(targetAS)) {
                visitedAS.addAll(paths.get(currentSystem).get(targetAS));
                didFindPath = true;
                break;
            }

            visitedAS.add(currentSystem);

            if (currentSystem.equals(targetAS)) {
                didFindPath = true;
                break;
            }

            currentSystem.getConnectedAutonomousSystems().forEach(autonomousSystem -> {
                if (!visitedAS.contains(autonomousSystem) && !autonomousSystemQueue.contains(autonomousSystem)) {
                    autonomousSystemQueue.add(autonomousSystem);
                    addedByWhom.put(autonomousSystem, currentSystem);
                }
            });
        }

        if (didFindPath) {
            System.out.println(visitedAS.toString());
            List<AutonomousSystem> result = new ArrayList<AutonomousSystem>();
            result.addAll(visitedAS);
            visitedAS.remove(0);
            while (!visitedAS.isEmpty()) {
                if (paths.containsKey(startPoint)) {
                    if (!paths.get(startPoint).containsKey(targetAS)) {
                        paths.get(startPoint).put(targetAS, visitedAS);
                    }
                } else {
                    paths.put(startPoint, new HashMap<>());
                    paths.get(startPoint).put(targetAS, visitedAS);
                }
                visitedAS.remove(0);
            }
            return result;
        }

        return null;
    }

    public List<AutonomousSystem> findPathBetweenAutonomousSystemsBFS2(int as1Id, int as2Id) {
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
