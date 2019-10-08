package network;

import java.util.ArrayList;

public class Domain extends Routable {
	
	/**
	 * Constructor for <code>Domain</code> objects.
	 */
	public Domain() {
		super();
	}
	
	/**
	 * A list of <code>Routable</code> objects that are in this <code>NetworkTopology</code>.
	 */
	private ArrayList<Routable> routableList = new ArrayList<>();

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
}
