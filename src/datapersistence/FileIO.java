package datapersistence;

import collections.lists.UnorderedLinkedList;
import game.Location;
import game.Map;
import game.MapList;
import interfaces.IMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public abstract class FileIO {
    private static final String directory = "src\\database\\maps.json";

    /**
     * Export the map list to a json file
     */
    public static void exportToJSON(MapList listTmp) {
        JSONObject jsonObject = new JSONObject();


        try (FileWriter writer = new FileWriter(directory)) {

            JSONArray mapsArray = new JSONArray();
            Iterator<Map> mapsIterator = listTmp.getAllMaps().iterator();

            while (mapsIterator.hasNext()) {
                JSONObject mapObject = new JSONObject();

                IMap nextMap = mapsIterator.next();
                mapObject.put("id", nextMap.getId());

                JSONArray edgesArray = new JSONArray();

                try {
                    // Itera sobre os vértices para obter informações das arestas
                    Iterator<Location> vertexIterator = nextMap.getGraphMap().iteratorDFS(nextMap.getGraphMap().getVertex(0));
                    while (vertexIterator.hasNext()) {
                        Location fromVertex = vertexIterator.next();

                        // Itera sobre os vértices adjacentes para obter informações das arestas
                        Iterator<Location> adjacentVerticesIterator = nextMap.getGraphMap().iteratorBFS(fromVertex);
                        while (adjacentVerticesIterator.hasNext()) {
                            Location toVertex = adjacentVerticesIterator.next();
                            double weight = nextMap.getGraphMap().getEdgeWeight(fromVertex, toVertex);
                           /* if (weight != Double.POSITIVE_INFINITY) {
                                JSONObject edgeObject = new JSONObject();
                                edgeObject.put("fromIndex", nextMap.getGraphMap().getIndex(fromVertex));
                                edgeObject.put("toIndex", nextMap.getGraphMap().getIndex(toVertex));
                                edgeObject.put("weight", weight);
                                edgesArray.add(edgeObject);
*/
                            if (weight != Double.POSITIVE_INFINITY) {
                                JSONObject edgeObject = new JSONObject();
                                JSONObject fromVertexObject = new JSONObject();
                                JSONObject toVertexObject = new JSONObject();

                                fromVertexObject.put("x", fromVertex.getPosX());
                                fromVertexObject.put("y", fromVertex.getPosY());
                                toVertexObject.put("x", toVertex.getPosX());
                                toVertexObject.put("y", toVertex.getPosY());
                                edgeObject.put("fromIndex", nextMap.getGraphMap().getIndex(fromVertex));
                                edgeObject.put("toIndex", nextMap.getGraphMap().getIndex(toVertex));
                                edgeObject.put("weight", weight);
                                edgeObject.put("fromVertex", fromVertexObject);
                                edgeObject.put("toVertex", toVertexObject);
                                edgesArray.add(edgeObject);
                            }

                        }
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao processar as arestas do grafo: " + e.getMessage());
                    e.printStackTrace(); // Isso imprimirá o rastreamento da pilha para ajudar a diagnosticar o problema
                }

                mapObject.put("edges", edgesArray);
                mapsArray.add(mapObject);
            }

            jsonObject.put("maps", mapsArray);

            writer.write(jsonObject.toJSONString());
            System.out.println("Exportação concluída com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao exportar o grafo para JSON: " + e.getMessage());
            e.printStackTrace(); // Isso imprimirá o rastreamento da pilha para ajudar a diagnosticar o problema
        }
    }

    public static MapList importFromJson() {
        Set<Location> addedVertices = new HashSet<>(); // Set to store added vertices

        MapList mapList = new MapList();

        try (Reader reader = new FileReader(directory)) {
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(reader);

            JSONArray maps = (JSONArray) object.get("maps");

            if (maps != null)
            {  // Verifica se o array de mapas existe no JSON
                IMap[] map = new IMap[maps.size()];

                for (int i = 0; i < maps.size(); i++) {
                    map[i] = new Map();

                    JSONObject mapTmp = (JSONObject) maps.get(i);

                    long id = (Long) mapTmp.get("id");

                    JSONArray edgesArray = (JSONArray) mapTmp.get("edges");
                    for (int k = 0; k < edgesArray.size(); k++) {
                        /*
                        JSONObject edgeTmp = (JSONObject) edgesArray.get(k);

                        long fromIndex = (Long) edgeTmp.get("fromIndex");
                        long toIndex = (Long) edgeTmp.get("toIndex");
                        double weight = (Double) edgeTmp.get("weight");

                        map[i].getGraphMap().addVertex(new Location((int) fromIndex, 0));
                        map[i].getGraphMap().addVertex(new Location((int) toIndex, 0));
                        map[i].getGraphMap().addEdge((int) fromIndex, (int) toIndex, weight);
                    }*/
                        JSONObject edgeTmp = (JSONObject) edgesArray.get(k);

                        // Read vertex coordinates (x, y) from JSON
                        JSONObject fromVertex = (JSONObject) edgeTmp.get("fromVertex");
                        long fromX = ((Long) fromVertex.get("x")).intValue();
                        long fromY = ((Long) fromVertex.get("y")).intValue();

                        JSONObject toVertex = (JSONObject) edgeTmp.get("toVertex");
                        long toX = ((Long) toVertex.get("x")).intValue();
                        long toY = ((Long) toVertex.get("y")).intValue();

                        // Create Location objects for vertices with x and y coordinates
                        Location fromLocation = new Location((int) fromX, (int) fromY);
                        Location toLocation = new Location((int) toX, (int) toY);

                        //read weight
                        double weight = (Double) edgeTmp.get("weight");

                        if (!addedVertices.contains(fromLocation)) {
                            // Add the vertex to the graph if it's not already added
                            map[i].getGraphMap().addVertex(fromLocation);
                            addedVertices.add(fromLocation); // Add the vertex to the set
                        }
                        if (!addedVertices.contains(toLocation)) {
                            // Add the vertex to the graph if it's not already added
                            map[i].getGraphMap().addVertex(toLocation);
                            addedVertices.add(toLocation); // Add the vertex to the set
                        }

                        map[i].getGraphMap().addEdge(fromLocation,toLocation, weight);
                    }
                    map[i].setId((int) id);
                    mapList.addMap((Map) map[i]);
                }
            }

            return mapList;

        } catch (FileNotFoundException e) {
            System.err.println("Arquivo JSON não encontrado: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException | ParseException e) {
            System.err.println("Erro ao importar JSON: " + e.getMessage());
            e.printStackTrace(); // Isso imprimirá o rastreamento da pilha para ajudar a diagnosticar o problema
            throw new RuntimeException(e);
        }
    }

    // This method check if the file is not empty
    public static boolean fileIsNotEmpty() {
        File file = new File(directory);
        return file.length() > 0;
    }

}
