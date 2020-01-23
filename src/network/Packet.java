package network;

import com.google.common.base.Objects;

import java.util.Stack;
import java.util.UUID;

/**
 * An object that describes a packet that can be send between
 * <code>{@link AutonomousSystem}</code> objects.
 *
 * @author Ahmet Uysal @ahmetuysal, Ceren Kocaogullar @ckocaogullar15, Kaan Yıldırım @kyildirim
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
     * @param sid      Universally unique identifier of the requested service.
     * @param pidStack Stack of <code>UUID</code> that represents
     *                  <code>Route</code> objects that this packet traveled
     *                  through.
     */
    public Packet(UUID sid, Stack<Integer> pidStack) {
        this.sid = sid;
        this.pidStack = pidStack;
    }

    /**
     * @return the universally unique identifier of the requested service of this
     * <code>Packet</code>.
     */
    public UUID getSid() {
        return this.sid;
    }

    /**
     * @param sid the universally unique identifier of the requested service of
     *             this <code>Packet</code> to set.
     */
    public void setSid(UUID sid) {
        this.sid = sid;
    }

    /**
     * @return Stack of <code>UUID</code> that represents <code>Route</code> objects
     * that this packet traveled through.
     */
    public Stack<Integer> getPidStack() {
        return this.pidStack;
    }

    /**
     * @param pidStack Stack of <code>UUID</code> that represents
     *                  <code>Route</code> objects that this packet traveled through
     *                  to set.
     */
    public void setPidStack(Stack<Integer> pidStack) {
        this.pidStack = pidStack;
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
