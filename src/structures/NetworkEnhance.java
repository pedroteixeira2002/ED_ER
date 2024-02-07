package structures;

import collections.graphs.Network;
import game.Location;

import java.lang.reflect.Field;

/**
 * This class is an enhancement of the Network class. It provides a method to get the index of a vertex and a method to
 * get the weight of an edge between two vertices.
 *
 * @param <T>
 */
public class NetworkEnhance<T> extends Network<T> {

    /**
     * This constructor calls the constructor of the parent class (Network)
     */
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

    /**
     * This method returns the index of a vertex
     * @return
     */
    public T[] getVertices() {
        return this.vertices;
    }

    /**
     * This method returns the weight of the edge between two vertices
     *
     * @param vertex1
     * @param vertex2
     * @return
     */
    public double getEdgeWeight(Location vertex1, Location vertex2) {
        int index1 = getIndex((T) vertex1);
        int index2 = getIndex((T) vertex2);

        if (indexIsValid(index1) && indexIsValid(index2)) {
            return getEdgeWeightUsingReflection(index1, index2);
        } else {
            throw new IllegalArgumentException("Vértice não encontrado");
        }
    }

    /**
     * This method returns the weight of the edge between two vertices using reflection
     *
     * @param index1
     * @param index2
     * @return
     */
    private double getEdgeWeightUsingReflection(int index1, int index2) {
        try {
            // Obter o campo adjMatrix da classe pai (Network) usando reflexão
            Field adjMatrixField = Network.class.getDeclaredField("adjMatrix");
            adjMatrixField.setAccessible(true);  // Tornar o campo acessível
            double[][] adjMatrix = (double[][]) adjMatrixField.get(this);

            if (indexIsValid(index1) && indexIsValid(index2)) {
                return adjMatrix[index1][index2];
            } else {
                throw new IllegalArgumentException("Vértice não encontrado");
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Lidar com exceções (por exemplo, imprimir um log ou lançar uma exceção personalizada)
            e.printStackTrace();
            throw new RuntimeException("Erro ao acessar adjMatrix usando reflexão");
        }
    }

}