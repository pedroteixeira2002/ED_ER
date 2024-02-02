package algorithms;

import game.Location;
import game.Map;
import structures.NetworkEnhance;

import java.util.Iterator;


public class MinimumSpanningTreePath {
    private NetworkEnhance<Location> graph;
    private Map map;

    public MinimumSpanningTreePath(NetworkEnhance<Location> graph) {
        this.graph = map.getGraphMap(); ;
    }

    public NetworkEnhance<Location> getGraph() {
        return graph;
    }

    public void setGraph(NetworkEnhance<Location> graph) {
        this.graph = graph;
    }

    public Iterator<Location> iteratorMST() {
        NetworkEnhance<Location> mstNetwork = (NetworkEnhance<Location>) graph.mstNetwork();
        return mstNetwork.iteratorDFS(mstNetwork.getVertex(0));
    }

}
