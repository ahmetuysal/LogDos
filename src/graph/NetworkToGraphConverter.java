package graph;

import network.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.Random;

/**
 * A class that converts a <code>{@linkNetworkTopology}</code> to a <code>{@link org.graphstream.graph.Graph Graph}</code> object.
 *
 * @author Kaan Yıldırım @kyildirim
 */
public class NetworkToGraphConverter {

    public static Graph convertNetwork(NetworkTopology _network) {
        Graph graph = new MultiGraph("NetworkTopology"); // TODO Unique id.
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        String css = "node.networkRouter { size: 15px, 15px; shape: circle; }\nnode.networkResourceManager { size: 15px, 15px; shape: box; }\n";
        for (Object domain : _network.getDomainList()) {
            Random obj = new Random();
            int rand_num = obj.nextInt(0xffffff + 1);
            String colorCode = String.format("#%06x", rand_num);
            css += "node.class" + (((Routable) domain).getId() + "").replace("-", "") + " {fill-color: " + colorCode + ";}\n";
        }
        graph.addAttribute("ui.stylesheet", css);
        _network.getRoutableList().forEach(router -> {
            System.out.println(router);
            System.out.println(router.getClass().getName());
            graph.addNode(router.getId() + "").addAttribute("ui.class", router.getClass().getName().replace(".", "") + ", class" + router.getDomain().getId());
        });
        _network.getRouteList().forEach(route -> {
            //graph.addNode(((Route) route).getOrigin().getUUID().toString());
            //graph.addNode(((Route) route).getDestination().getUUID().toString());
            graph.addEdge(route.getPid().toString(), route.getOrigin().getId() + "", route.getDestination().getId() + "");
        });
        return graph;
    }


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
