package network;

import java.util.Stack;
import java.util.UUID;

/**
 * An object that describes a packet that can be send between
 * <code>{@link Routable}</code> objects.
 * 
 * @author Ahmet Uysal @ahmetuysal
 *
 */
public class Packet {

	/**
	 * Universally unique identifier of the requested service.
	 */
	private UUID sid;

	/**
	 * Universally unique identifier of the packet source <code>Routable</code>.
	 */
	private UUID sourceId;

	/**
	 * Stack of <code>UUID</code> that represents <code>Route</code> objects that
	 * this packet traveled through.
	 */
	private Stack<UUID> pidStack;

	/**
	 * @param _sid      Universally unique identifier of the requested service.
	 * @param _sourceId Universally unique identifier of the packet source
	 *                  <code>Routable</code>.
	 * @param _pidStack Stack of <code>UUID</code> that represents
	 *                  <code>Route</code> objects that this packet traveled
	 *                  through.
	 */
	public Packet(UUID _sid, UUID _sourceId, Stack<UUID> _pidStack) {
		this.sid = _sid;
		this.sourceId = _sourceId;
		this.pidStack = _pidStack;
	}

	/**
	 * @return the universally unique identifier of the requested service of this
	 *         <code>Packet</code>.
	 */
	public UUID getSid() {
		return this.sid;
	}

	/**
	 * @param _sid the universally unique identifier of the requested service of
	 *             this <code>Packet</code> to set.
	 */
	public void setSid(UUID _sid) {
		this.sid = _sid;
	}

	/**
	 * @return Universally unique identifier of the packet source
	 *         <code>Routable</code>.
	 */
	public UUID getSourceId() {
		return this.sourceId;
	}

	/**
	 * @param _sourceId Universally unique identifier of the packet source
	 *                  <code>Routable</code> to set.
	 */
	public void setSourceId(UUID _sourceId) {
		this.sourceId = _sourceId;
	}

	/**
	 * @return Stack of <code>UUID</code> that represents <code>Route</code> objects
	 *         that this packet traveled through.
	 */
	public Stack<UUID> getPidStack() {
		return this.pidStack;
	}

	/**
	 * @param _pidStack Stack of <code>UUID</code> that represents
	 *                  <code>Route</code> objects that this packet traveled through
	 *                  to set.
	 */
	public void setPidStack(Stack<UUID> _pidStack) {
		this.pidStack = _pidStack;
	}

}
