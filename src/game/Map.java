package game;

import collections.graphs.Graph;
import interfaces.IMap;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import structures.NetworkEnhance;

import java.util.Iterator;
import java.util.Random;

/**
 * Represents a map in the game
 */
public class Map implements IMap, Comparable<Map> {
    /** The nextID of the map */
    private static int nextId = 1;
    /** The id of the map */
    private int id;
    /** The graph of the map */
    private NetworkEnhance<Location> graphMap;
    /** The GraphStream graph */
    private Graph graph; // GraphStream graph

    /**
     * Constructor for the map
     */
    public Map() {
        this.id = nextId++;
        this.graphMap = new NetworkEnhance<>();
    }

    /**
     * Constructor for the map
     * @param id
     * @param graphMap
     */
    public Map(int id, NetworkEnhance<Location> graphMap) {
        this.id = id;
        this.graphMap = graphMap;
    }
    /**
     * Get the map
     * @return the map
     */
    @Override
    public NetworkEnhance<Location> getGraphMap() {
        return graphMap;
    }

    /**
     * This method generates a map with a given number of locations, edge density and bidirectional
     * @param numLocations
     * @param bidirectional
     * @param edgeDensity
     */
    @Override
    public NetworkEnhance<Location> generateMap(int numLocations, boolean bidirectional, double edgeDensity) {
        // Clear the map and flag locations
        this.graphMap = new NetworkEnhance<>();

        // Check if edgeDensity is in the correct range
        if (edgeDensity < 0.01 || edgeDensity > 1.00) {
            throw new IllegalArgumentException("Choose a density between 0.01 and 1.00");
        }


        // Generate the locations(vertices) in the map
        for (int i = 0; i < numLocations; i++) {
            int x = (int) (Math.random() * 100); // Give random coordinates between 0 and 100
            int y = (int) (Math.random() * 100); // Give random coordinates between 0 and 100
            Location location = new Location(x, y);
            this.graphMap.addVertex(location);
        }


        // Connect locations based on edge density
        int maxEdges = (int) (numLocations * (numLocations - 1) * edgeDensity);
        Random random = new Random();

        for (int i = 0; i < maxEdges; i++) {
            Location from = getRandomLocation();
            Location to = getRandomLocation();

            if (from != null && to != null && !from.equals(to)) {
                double weight = 1 + random.nextInt(15); // Distância aleatória entre 1 e 15 quilômetros (peso da aresta)

                graphMap.addEdge(from, to, weight);

                if (bidirectional) {  // SAME AS: if (bidirectional == true)
                    // If bidirectional, add the edge in the opposite direction
                    graphMap.addEdge(to, from, weight);
                }
            }
        }

        // Check if the generated map is connected
        if (!isConnected()) {
            // If not connected, regenerate the map until it is connected
            return generateMap(numLocations, bidirectional, edgeDensity);
        }


        if (isConnected()) {
            System.out.println("Mapa conexo.");
        } else {
            System.out.println("Mapa não conexo.");
        }


        // Atualize esta linha para armazenar o ID do mapa
        System.out.println("Mapa gerado com ID: " + this.id);


        // Return the generated map
        return this.graphMap;
    }

    /**
     * Check if the generated map is connected
     *
     * @return true if the map is connected, false otherwise
     */
    private boolean isConnected() {
        Iterator<Location> iterator = graphMap.iteratorBFS(graphMap.getVertex(0));

        int count = 0;

        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }

        return count == graphMap.size();
    }

    /**
     * Get the map id
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Set the map id
     * @param id
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get a random location in the map
     *
     * @return a random location in the map
     */
    private Location getRandomLocation() {
        Random random = new Random();
        int randomIndex = random.nextInt(this.graphMap.size());

        return this.graphMap.getVertex(randomIndex);
    }

    /**
     * To String
     * @return the map
     */
    @Override
    public String toString() {
        String str = "\nMap Id: " + this.id + ":\n";
        str += this.graphMap.toString() + "\n";

        /*
        str += "Locais:\n";
        for (int i = 0; i < this.graphMap.size(); i++) {
            str += "\t" + this.graphMap.getVertex(i) + "\n";
        }
        */
        return str;
    }

    /**
     * Compare to
     * @param o
     * @return
     */
    @Override
    public int compareTo(Map o) {
        return this.id - o.id;
    }

    /**
     * Prints a visual representation of the map using a graphical interface.
     */
    public void visualizeGraph() {
        // Criar um gráfico do GraphStream
        org.graphstream.graph.Graph graph = new SingleGraph("Map Visualization");

        // Configurar o layout (opcional)
        graph.addAttribute("ui.stylesheet", "node { fill-color: red; size: 20px; text-size: 20; } edge " +
                "{ fill-color: grey; size: 2px; text-size: 12; text-color: black; text-background-mode: plain; " +
                "text-background-color: white; }");

        // Adicionar vértices ao gráfico
        Iterator<Location> vertexIterator = graphMap.iteratorDFS(graphMap.getVertex(0));
        while (vertexIterator.hasNext()) {
            Location location = vertexIterator.next();
            Node node = graph.addNode(Integer.toString(location.hashCode()));
            node.addAttribute("ui.label", location.toString());
        }

        // Adicionar arestas ao gráfico
        for (int i = 0; i < graphMap.size(); i++) {
            Iterator<Location> adjacentVerticesIterator = graphMap.iteratorBFS(graphMap.getVertex(i));
            while (adjacentVerticesIterator.hasNext()) {
                Location fromVertex = graphMap.getVertex(i);
                Location toVertex = adjacentVerticesIterator.next();
                double weight = graphMap.getEdgeWeight(fromVertex, toVertex);

                // Verificar se o peso não é "Infinity" antes de adicionar a aresta
                if (weight > 0 && weight != Double.POSITIVE_INFINITY) {
                    Edge edge = graph.addEdge(Integer.toString(fromVertex.hashCode()) + "-" +
                                    Integer.toString(toVertex.hashCode()),
                            Integer.toString(fromVertex.hashCode()), Integer.toString(toVertex.hashCode()), true);
                    edge.addAttribute("ui.label", weight);
                }
            }
        }

        // Exibir o gráfico
        graph.display();
    }
}
