package graph;

import network.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

/**
 * A class that converts a <code>{@link AutonomousSystemTopology}</code> to a <code>{@link org.graphstream.graph.Graph Graph}</code> object.
 *
 * @author Ahmet Uysal @ahmetuysal, Ceren Kocaogullar @ckocaogullar15, Kaan Yıldırım @kyildirim
 */
public class NetworkToGraphConverter {

    public static Graph convertAutonomousSystemTopology(AutonomousSystemTopology _autonomousSystemTopology) {
        Graph graph = new MultiGraph("AutonomousSystemTopology"); // TODO Unique id.
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        String css = "node.core { size: 5px, 5px; shape: circle; fill-color: blue; }\nnode.transient { size: 5px, 5px; shape: circle; fill-color: red; }\n";
        graph.addAttribute("ui.stylesheet", css);


        _autonomousSystemTopology.getAutonomousSystems().forEach(autonomousSystem -> {
            graph.addNode(autonomousSystem.getId() + "").addAttribute("ui.class", autonomousSystem.getType() == AutonomousSystemType.TRANSIENT ? "transient" : "core");
        });

        _autonomousSystemTopology.getRouteSet().forEach(route -> {
            graph.addEdge(route.getPid().toString(), route.getOrigin().getId() + "", route.getDestination().getId() + "");
        });

        return graph;
    }

}
