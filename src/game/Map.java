package game;

import interfaces.IMap;
import structures.NetworkEnhance;

import java.util.Iterator;
import java.util.Random;

public class Map implements IMap, Comparable<Map> {
    private static int nextId = 1;
    private int id;
    private NetworkEnhance<Location> graphMap;


    public Map() {
        this.id = nextId++;
        this.graphMap = new NetworkEnhance<>();
    }

    public Map(int id, NetworkEnhance<Location> graphMap) {
        this.id = id;
        this.graphMap = graphMap;
    }

    @Override
    public NetworkEnhance<Location> getGraphMap() {
        return graphMap;
    }

    /**
     * Get the map
     *
     * @param numLocations
     * @param bidirectional
     * @param edgeDensity
     */
    @Override
    public Map generateMap(int numLocations, boolean bidirectional, double edgeDensity) {
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
        return this;
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

    @Override
    public int compareTo(Map o) {
        return this.id - o.id;
    }
}
