package network;

import com.google.common.base.Objects;

import java.util.Stack;
import java.util.UUID;

/**
 * An object that describes a packet that can be send between
 * <code>{@link Routable}</code> objects.
 *
 * @author Ahmet Uysal @ahmetuysal
 */
public class Packet {

    /**
     * Universally unique identifier of the requested service.
     */
    private UUID sid;

    /**
     * Stack of <code>UUID</code> that represents <code>Route</code> objects that
     * this packet traveled through.
     */
    private Stack<Integer> pidStack;

    /**
     * @param _sid      Universally unique identifier of the requested service.
     * @param _pidStack Stack of <code>UUID</code> that represents
     *                  <code>Route</code> objects that this packet traveled
     *                  through.
     */
    public Packet(UUID _sid, Stack<Integer> _pidStack) {
        this.sid = _sid;
        this.pidStack = _pidStack;
    }

    /**
     * @return the universally unique identifier of the requested service of this
     * <code>Packet</code>.
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
     * @return Stack of <code>UUID</code> that represents <code>Route</code> objects
     * that this packet traveled through.
     */
    public Stack<Integer> getPidStack() {
        return this.pidStack;
    }

    /**
     * @param _pidStack Stack of <code>UUID</code> that represents
     *                  <code>Route</code> objects that this packet traveled through
     *                  to set.
     */
    public void setPidStack(Stack<Integer> _pidStack) {
        this.pidStack = _pidStack;
    }


    @Override
    public String toString() {
        return "Packet{" +
                "sid=" + sid +
                ", pidStack=" + pidStack +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Packet packet = (Packet) o;
        return Objects.equal(sid, packet.sid) &&
                Objects.equal(pidStack, packet.pidStack);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sid, pidStack);
    }
}
