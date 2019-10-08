package network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * An object that represents a network topology.
 * 
 * @author Kaan Yıldırım @kyildirim
 * @see NetworkTopologyManager
 */
public class NetworkTopology {
	
	public static class Builder {
		
		/**
		 * A list of <code>Domain</code> objects that are in this <code>NetworkTopology</code>.
		 */
		private int domainCount = 1;
		
		private int routableCount = 100;
		/**
		 * A list of <code>Route</code> objects that are in this <code>NetworkTopology</code>.
		 */
		private ArrayList<Route> routeList = new ArrayList<>();
		/**
		 * A list of <code>Routable</code> objects that are in this <code>NetworkTopology</code>.
		 */
		private ArrayList<Routable> routableList;
		/**
		 * A table showing associations between <code>Routable</code> objects and the <code>Domain</code> objects they are contained within. 
		 */
		private HashMap<Routable, Domain> domainAssociationTable; 

		public Builder() {
			
		}
		
		public Builder domainCount(int domainCount) {
			this.domainCount = domainCount;
			return this;
		}
		
		public Builder routableCount(int routableCount) {
			this.routableCount = routableCount;
			return this;	
		}
		
		public NetworkTopology build() {
			HashMap<Routable, Domain> domainAssociationTable = new HashMap<>();
			ArrayList<Route> routeList = new ArrayList<>();
			NetworkTopology nt = new NetworkTopology();
			// Filling domainList
			nt.domainList = new ArrayList<>();
			for(int i = 0; i<domainCount; i++) {
				nt.domainList.add(new Domain());
			}
			
			for(int i = 0; i<routableCount; i++) {
				Routable routable = new Random().nextDouble() < 0.2 ? new Client() : new Router(); 
				nt.routableList.add(routable);
				Domain domain = nt.domainList.get(new Random().nextInt(nt.domainList.size()));
				domain.addRoutable(routable);
				routable.setDomain(domain);
				domainAssociationTable.put(routable, domain);
			}
			
			for(Domain d : nt.domainList) {
				ArrayList<Client> clientList = new ArrayList<>();
				ArrayList<Router> routerList = new ArrayList<>();
				for(Routable r : nt.getRoutableList()) {
					if(r instanceof Client) {
						clientList.add((Client)r);
					} else {
						routerList.add((Router)r);
					}
				}
				
				for(Client c : clientList) {
					Route route = new Route();
					route.setOrigin(c);
					route.setDestination(routerList.get(new Random().nextInt(routerList.size()-1)));
					routeList.add(route);
				}
				
				for (int i = 0; i < routerList.size(); i++) {
					Router router = routerList.get(i);
					int routeCount = 1 + new Random().nextInt(routerList.size() - 2 - i);
					// Generates a set of min(count, maxValue) distinct random ints 
					//  from [0, maxValue - 1] range.
					Set<Integer> was = new HashSet<>();
					int maxValue = routerList.size() - i;
				    for (int j = Math.max(0, maxValue - routeCount); j < maxValue; j++) {
				        int curr = i + j == 0 ? 0 : new Random().nextInt(j);
				        if (was.contains(curr))
				            curr = i + j;
				        was.add(curr);
				    }
				    
				    
				    for (Integer index : was) {
				    	Route route = new Route();
						route.setOrigin(router);
						route.setDestination(routerList.get(index));
						routeList.add(route);
				    }
				}

			}
			nt.setDomainAssociationTable(domainAssociationTable);
			nt.setRouteList(routeList);
			return nt;
		}
		

	}
	
	private NetworkTopology() {
		
	}

	/**
	 * A list of <code>Domain</code> objects that are in this <code>NetworkTopology</code>.
	 */
	private ArrayList<Domain> domainList = new ArrayList<>();
	/**
	 * A list of <code>Route</code> objects that are in this <code>NetworkTopology</code>.
	 */
	private ArrayList<Route> routeList = new ArrayList<>();
	/**
	 * A list of <code>Routable</code> objects that are in this <code>NetworkTopology</code>.
	 */
	private ArrayList<Routable> routableList;
	/**
	 * A table showing associations between <code>Routable</code> objects and the <code>Domain</code> objects they are contained within. 
	 */
	private HashMap<Routable, Domain> domainAssociationTable;
	/**
	 * @return A list of <code>Domain</code> objects that are in this <code>NetworkTopology</code>.
	 */
	public ArrayList<Domain> getDomainList() {
		return this.domainList;
	}
	/**
	 * @param _domainList A list of <code>Domain</code> objects that are to be in this <code>NetworkTopology</code>.
	 */
	public void setDomainList(ArrayList<Domain> _domainList) {
		this.domainList = _domainList;
	}
	/**
	 * @return A list of <code>Route</code> objects that are in this <code>NetworkTopology</code>.
	 */
	public ArrayList<Route> getRouteList() {
		return this.routeList;
	}
	/**
	 * @param _routeList A list of <code>Route</code> objects that are to be in this <code>NetworkTopology</code>.
	 */
	public void setRouteList(ArrayList<Route> _routeList) {
		this.routeList = _routeList;
	}
	/**
	 * @return A list of <code>Routable</code> objects that are in this <code>NetworkTopology</code>.
	 */
	public ArrayList<Routable> getRoutableList() {
		return this.routableList;
	}
	/**
	 * @param _routableList A list of <code>Routable</code> objects that are to be in this <code>NetworkTopology</code>.
	 */
	public void setRoutableList(ArrayList<Routable> _routableList) {
		this.routableList = _routableList;
	}
	/**
	 * @return A table showing associations between code>Routable</code> objects and the <code>Domain</code> objects they are contained within.
	 */
	public HashMap<Routable, Domain> getDomainAssociationTable() {
		return this.domainAssociationTable;
	}
	/**
	 * @param _domainAssociationTable A table showing associations between code>Routable</code> objects and the <code>Domain</code> objects they are contained within.
	 */
	public void setDomainAssociationTable(HashMap<Routable, Domain> _domainAssociationTable) {
		this.domainAssociationTable = _domainAssociationTable;
	}
	/**
	 * Adds a <code>Domain</code> object to this <code>NetworkTopology<code>.
	 * 
	 * @param _domain <code>Domain</code> object to add to this <code>NetworkTopology<code>.
	 */
	public void addDomain(Domain _domain) {
		this.domainList.add(_domain);
	}
	/**
	 * Removes a <code>Domain</code> object from this <code>NetworkTopology<code>.
	 * 
	 * @param _domain <code>Domain</code> object to remove from this <code>NetworkTopology<code>.
	 * @return <code>true</code> if given <code>Domain</code> object is removed, <code>false</code> otherwise.
	 */
	public boolean removeDomain(Domain _domain) {
		return this.domainList.remove(_domain);
	}
	/**
	 * Adds a <code>Route</code> object to this <code>NetworkTopology<code>.
	 * 
	 * @param _route <code>Route</code> object to add to this <code>NetworkTopology<code>.
	 */
	public void addRoute(Route _route) {
		this.routeList.add(_route);
	}
	/**
	 * Removes a <code>Route</code> object from this <code>NetworkTopology<code>.
	 * 
	 * @param _route <code>Route</code> object to remove from this <code>NetworkTopology<code>.
	 * @return <code>true</code> if given <code>Route</code> object is removed, <code>false</code> otherwise.
	 */
	public boolean removeRoute(Route _route) {
		return this.routeList.remove(_route);
	}
	/**
	 * Adds a <code>Routable</code> object to this <code>NetworkTopology<code>.
	 * 
	 * @param _routable <code>Routable</code> object to add to this <code>NetworkTopology<code>.
	 */
	public void addRoutable(Routable _routable) {
		this.routableList.add(_routable);
	}
	/**
	 * Removes a <code>Routable</code> object from this <code>NetworkTopology<code>.
	 * 
	 * @param _routable <code>Routable</code> object to remove from this <code>NetworkTopology<code>.
	 * @return <code>true</code> if given <code>Routable</code> object is removed, <code>false</code> otherwise.
	 */
	public boolean removeRoutable(Routable _routable) {
		return this.routableList.remove(_routable);
	}
	/**
	 * Associates an <code>{@link Routable}</code> object with a <code>Domain</code> object which implies that the <code>{@link Routable}</code> object is contained within the <code>Domain</code> object.
	 * 
	 * @param _routable <code>{@link Routable}</code> object that is to be associated with the given <code>Domain</code> object.
	 * @param _domain <code>{@link Domain}</code> object that is to be associated with the given <code>Routable</code> object.
	 * @return <code>true<code> if the domain association can be added, <code>false</code> otherwise.
	 */
	public boolean addDomainAssociation(Routable _routable, Domain _domain) {
		if(!this.routableList.contains(_routable))return false;
		if(!this.domainList.contains(_domain))return false;
		if(this.domainAssociationTable.containsKey(_routable))return false;
		this.domainAssociationTable.put(_routable, _domain);
		return true;
	}
	/**
	 * Removes the association between the given <code>Routable</code> object and the associated <code>Domain</code> object if it exists.
	 * 
	 * @param _routable <code>Routable</code> object to remove any association with.
	 * @return <code>true<code> if the association is removed, <code>false</code> otherwise.
	 */
	public boolean removeDomainAssociation(Routable _routable) {
		return this.domainAssociationTable.remove(_routable)!=null;
	}
	
}
