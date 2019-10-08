package graph;

import java.util.Random;

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
		String css = "node.networkRouter { size: 15px, 15px; shape: circle; }\n";
		for(Object domain : _network.getDomainList()) {
			Random obj = new Random();
			int rand_num = obj.nextInt(0xffffff + 1);
			String colorCode = String.format("#%06x", rand_num);
			System.out.println(colorCode);
			css += "node.class"+((Routable) domain).getUUID().toString().replace("-", "")+" {fill-color: "+ colorCode +";}\n";
		}
		graph.addAttribute("ui.stylesheet", css);
		_network.getRoutableList().forEach(router -> {
			graph.addNode(((Routable) router).getUUID().toString()).addAttribute("ui.class", router.getClass().getName().replace(".", "") +", class"+((Routable) router).getDomain().getUUID().toString().replace("-", ""));
		});
		_network.getRouteList().forEach(route -> {
			//graph.addNode(((Route) route).getOrigin().getUUID().toString());
			//graph.addNode(((Route) route).getDestination().getUUID().toString());
			graph.addEdge(((Route) route).getPid().toString(), ((Route) route).getOrigin().getUUID().toString(), ((Route) route).getDestination().getUUID().toString());
		});
		return graph;
	}
	
}
