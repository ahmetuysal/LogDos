package network;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

/**
 * An object that represents resource manager.
 * 
 * @author Ahmet Uysal @ahmetuysal
 *
 */
public class ResourceManager extends Routable {

	/**
	 * A map of <code>UUID</code> objects that represent <code>SID</code> instances
	 * are mapped to <code>HashSet</code> objects of <code>UUID</code> instances
	 * representing <code>Routable</code> objects.
	 */
	private HashMap<UUID, HashSet<Integer>> registrationTable;
	
	
	/**
	 * Creates a new <code>ResourceManager</code> instance and initializes its <code>registrationTable</code>.
	 */
	public ResourceManager() {
		super();
		this.registrationTable = new HashMap<>();
	}

	public ResourceManager(int id) {
		super(id);
		this.registrationTable = new HashMap<>();
	}

	/**
	 * Registers a new <code>Service</code> to the registration table of this <code>ResourceManager</code>
	 * @param _service <code>Service</code> of this <code>ResourceManager</code>
	 * @param _routable <code>Routable</code> of this <code>ResourceManager</code>
	 */
	public void registerService(Service _service, Routable _routable) {
	    HashSet<Integer> routableSet = this.registrationTable.get(_service.getSid()) ;
		if(routableSet != null) {
			routableSet.add(_routable.getId());
		} else {
			routableSet = new HashSet<Integer>();
			routableSet.add(_routable.getId());
			this.registrationTable.put(_service.getSid(), routableSet);
		}
	}

}
