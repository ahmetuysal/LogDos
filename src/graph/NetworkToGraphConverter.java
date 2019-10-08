package graph;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import network.NetworkTopology;
import network.Routable;
import network.Route;

/**
 * A class that converts a <code>{@linkNetworkTopology}</code> to a <code>{@link org.graphstream.graph.Graph Graph}</code> object.
 * 
 * @author Kaan Yıldırım @kyildirim
 *
 */
public class NetworkToGraphConverter {

	public static Graph convertNetwork(NetworkTopology _network) {
		Graph graph = new SingleGraph("NetworkTopology"); // TODO Unique id.
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		graph.addAttribute("ui.stylesheet", "node.foo { fill-color: red; size: 15px, 15px; shape: cross; }");
		_network.getRoutableList().forEach(router -> {
			graph.addNode(((Routable) router).getUUID().toString()).addAttribute("ui.class", "foo");
		});
		_network.getRouteList().forEach(route -> {
			//graph.addNode(((Route) route).getOrigin().getUUID().toString());
			//graph.addNode(((Route) route).getDestination().getUUID().toString());
			graph.addEdge(((Route) route).getPid().toString(), ((Route) route).getOrigin().getUUID().toString(), ((Route) route).getDestination().getUUID().toString());
		});
		return graph;
	}
	
}
