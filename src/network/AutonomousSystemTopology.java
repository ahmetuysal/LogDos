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

    public List<Route> findPathBetweenAutonomousSystemsBFS(int as1Id, int as2Id) {
       AutonomousSystem startPoint =  this.getAutonomousSystemById(as1Id);
       AutonomousSystem targetAS = this.getAutonomousSystemById(as2Id);
       List<AutonomousSystem> visitedAS = new ArrayList<>();
       Queue<AutonomousSystem> autonomousSystemQueue = new ArrayDeque<>();

       autonomousSystemQueue.add(startPoint);

       boolean flag = false;
       while (!autonomousSystemQueue.isEmpty()) {
           AutonomousSystem currentSystem = autonomousSystemQueue.poll();
           if(paths.containsKey(currentSystem) && paths.get(currentSystem).containsKey(targetAS)) {
        	   visitedAS.addAll(paths.get(currentSystem).get(targetAS));
        	   //System.out.println(visitedAS.toString());
        	   flag = true;
        	   break;
           }
           visitedAS.add(currentSystem);
           if (currentSystem.equals(targetAS)) {
               //System.out.println(visitedAS.toString());
               flag=true;
               break;
           }
           for(AutonomousSystem autonomousSystem : (Set<AutonomousSystem>)currentSystem.getConnectedAutonomousSystems()) {
        	   if (visitedAS.contains(autonomousSystem)) continue;
               autonomousSystemQueue.add((AutonomousSystem)autonomousSystem);
           }
       }
       if(flag) {
    	   visitedAS = visitedAS.subList(1, visitedAS.size());
    	   while(!visitedAS.isEmpty()) {
    		   if(paths.containsKey(startPoint)) {
    			   if(!paths.get(startPoint).containsKey(targetAS)) {
    				   paths.get(startPoint).put(targetAS, visitedAS);
    			   }
    		   } else {
    			   paths.put(startPoint, new HashMap<AutonomousSystem, List<AutonomousSystem>>());
    			   paths.get(startPoint).put(targetAS, visitedAS);
    		   }
    		   visitedAS = visitedAS.subList(1, visitedAS.size());
    	   }
       }
       

       return null;
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
