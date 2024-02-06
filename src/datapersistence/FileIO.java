package datapersistence;

import game.Location;
import game.Map;
import game.MapList;
import interfaces.IMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Iterator;


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

                // Itera sobre os vértices para obter informações das arestas
                Iterator<Location> vertexIterator = nextMap.getGraphMap().iteratorDFS(nextMap.getGraphMap().getVertex(0));
                while (vertexIterator.hasNext()) {
                    Location fromVertex = vertexIterator.next();

                    // Itera sobre os vértices adjacentes para obter informações das arestas
                    Iterator<Location> adjacentVerticesIterator = nextMap.getGraphMap().iteratorBFS(fromVertex);
                    while (adjacentVerticesIterator.hasNext()) {
                        Location toVertex = adjacentVerticesIterator.next();
                        double weight = nextMap.getGraphMap().getEdgeWeight(fromVertex, toVertex);

                        if (weight != Double.POSITIVE_INFINITY) {
                            JSONObject edgeObject = new JSONObject();
                            edgeObject.put("fromIndex", nextMap.getGraphMap().getIndex(fromVertex));
                            edgeObject.put("toIndex", nextMap.getGraphMap().getIndex(toVertex));
                            edgeObject.put("weight", weight);
                            edgesArray.add(edgeObject);
                        }
                    }
                }

                mapObject.put("edges", edgesArray);
                mapsArray.add(mapObject);
            }

            jsonObject.put("maps", mapsArray);

            writer.write(jsonObject.toJSONString());
            System.out.println("Exportação concluída com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao exportar o grafo para JSON: " + e.getMessage());
        }
    }


    public static MapList importFromJson() {
        MapList mapList = new MapList();

        try {
            Reader reader = new FileReader(directory);
            JSONParser parser = new JSONParser();

            JSONObject object = (JSONObject) parser.parse(reader);

            JSONArray maps = (JSONArray) object.get("maps");
            IMap[] map = new IMap[maps.size()];

            for (int i = 0; i < maps.size(); i++) {
                map[i] = new Map();

                JSONObject mapTmp = (JSONObject) maps.get(i);

                long id = (Long) mapTmp.get("id");

                JSONArray edgesArray = (JSONArray) mapTmp.get("edges");
                for (int k = 0; k < edgesArray.size(); k++) {
                    JSONObject edgeTmp = (JSONObject) edgesArray.get(k);

                    long fromIndex = (Long) edgeTmp.get("fromIndex");
                    long toIndex = (Long) edgeTmp.get("toIndex");
                    double weight = (Double) edgeTmp.get("weight");

                    map[i].getGraphMap().addVertex(new Location((int) fromIndex, 0)); // Adiciona o vértice de origem
                    map[i].getGraphMap().addVertex(new Location((int) toIndex, 0));   // Adiciona o vértice de destino
                    map[i].getGraphMap().addEdge((int) fromIndex, (int) toIndex, weight);
                }

                map[i].setId((int) id);
                mapList.addMap((Map) map[i]);
            }

            return mapList;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
