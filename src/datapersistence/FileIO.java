package datapersistence;

import game.ListMap;
import game.Location;
import game.Map;
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
    public static void exportToJSON(ListMap listTmp) {
        JSONObject jsonObject = new JSONObject();

        try (FileWriter writer = new FileWriter(directory)) {

            JSONArray mapsArray = new JSONArray();
            Iterator<Map> mapsIterator = listTmp.getAllMaps().iterator();

            while (mapsIterator.hasNext()) {
                JSONObject mapObject = new JSONObject();

                IMap nextMap = mapsIterator.next();
                mapObject.put("id", nextMap.getId());

                JSONArray verticesArray = new JSONArray();
                Iterator<Location> vertexIterator = nextMap.getGraphMap().iteratorDFS(nextMap.getGraphMap().getVertex(0));

                while (vertexIterator.hasNext()) {
                    Location nextLocation = vertexIterator.next();
                    JSONObject location = new JSONObject();
                    location.put("x", nextLocation.getPosX());
                    location.put("y", nextLocation.getPosY());
                    verticesArray.add(location);
                }

                mapObject.put("vertices", verticesArray);
                mapsArray.add(mapObject);
            }

            jsonObject.put("maps", mapsArray);

            writer.write(jsonObject.toJSONString());
            System.out.println("Exportação concluída com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao exportar o grafo para JSON: " + e.getMessage());
        }
    }

    public static ListMap importFromJson() {
        ListMap mapList = new ListMap();

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

                JSONArray vertices = (JSONArray) mapTmp.get("vertices");
                Location[] location = new Location[vertices.size()];

                for (int j = 0; j < vertices.size(); j++) {

                    JSONObject locationTmp = (JSONObject) vertices.get(j);
                    long x = (Long) locationTmp.get("x");
                    long y = (Long) locationTmp.get("y");

                    location[j] = new Location((int) x, (int) y);

                    map[i].getGraphMap().addVertex((Location) location[j]);
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
