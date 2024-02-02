package structures;

import collections.graphs.Network;
import game.Location;


public class NetworkEnhance<T> extends Network<T> {

    public NetworkEnhance() {
        super();
    }
    /**
     * Get the index of a vertex
     *
     * @param index
     * @return
     */
    public T getVertex(int index) {
        if (index >= 0 && index < numVertices) {
            return vertices[index];
        } else {
            return null;
        }
    }
    public T[] getVertices() {
        return this.vertices;
    }
}