package structures;

import collections.graphs.Network;
import game.Location;


public class NetworkEnhance<T> extends Network<T> {
    private Object[][] adjMatrix;

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

    public void expandVertexList() {
        T[] largerVertices = (T[]) new Object[this.vertices.length * 2];

        for (int i = 0; i < numVertices; i++) {
            largerVertices[i] = vertices[i];
        }

        vertices = largerVertices;
    }

    public Location[] getNeighbors(T from) {
        int fromIndex = getIndex(from);
        if (fromIndex == -1) {
            return null;
        }


        Location[] neighbors = new Location[adjMatrix[fromIndex].length];
        int numNeighbors = 0;
        for (int i = 0; i < adjMatrix[fromIndex].length; i++) {
            if (adjMatrix[fromIndex][i] != null) {
                neighbors[numNeighbors++] = (Location) vertices[i];
            }
        }

        Location[] neighbors2 = new Location[numNeighbors];
        System.arraycopy(neighbors, 0, neighbors2, 0, numNeighbors);

        return neighbors2;
    }

    public T getEdgeWeight(T from, T to) {
        int fromIndex = getIndex(from);
        int toIndex = getIndex(to);
        if (fromIndex == -1 || toIndex == -1) {
            return null;
        }

        return (T) adjMatrix[fromIndex][toIndex];
    }

    public T[] getVertices() {
        return this.vertices;
    }


    public boolean containsEdge(T from, T to) {
        int fromIndex = getIndex(from);
        int toIndex = getIndex(to);
        if (fromIndex == -1 || toIndex == -1) {
            return false;
        }
        return adjMatrix[fromIndex][toIndex] != null;
    }

    /**
     * Converte o grafo em uma representação JSON
     *
     * @return
     */
    public String toJSON() {
        String str = "{\n";
        str += "\t\"vertices\": [\n";
        for (int i = 0; i < this.numVertices; i++) {
            str += "\t\t{\n";
            str += "\t\t\t\"id\": " + i + ",\n";
            str += "\t\t\t\"label\": \"" + this.vertices[i] + "\"\n";
            str += "\t\t}";
            if (i < this.numVertices - 1) {
                str += ",";
            }
            str += "\n";
        }
        str += "\t],\n";
        str += "\t\"edges\": [\n";
        for (int i = 0; i < this.numVertices; i++) {
            for (int j = 0; j < this.numVertices; j++) {
                if (this.adjMatrix[i][j] != null) {
                    str += "\t\t{\n";
                    str += "\t\t\t\"from\": " + i + ",\n";
                    str += "\t\t\t\"to\": " + j + ",\n";
                    str += "\t\t\t\"label\": \"" + this.adjMatrix[i][j] + "\"\n";
                    str += "\t\t}";
                    if (i < this.numVertices - 1) {
                        str += ",";
                    }
                    str += "\n";
                }
            }
        }
        str += "\t]\n";
        str += "}\n";

        return str;
    }
}