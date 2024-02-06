import game.Location;
import game.Map;
import game.MapList;
import menu.Menu;
import menu.Tools;

import java.io.IOException;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) throws IOException {
/*
        MapList listMap = new MapList();
        Map gameMap1 = new Map();
        listMap.addMap(gameMap1);
        gameMap1.generateMap(4, true, 0.5);

        System.out.println(gameMap1);
        System.out.println("start vertex index");
        int x = Tools.GetInt();

        System.out.println("destiny vertex index");
        int y = Tools.GetInt();


        Iterator<Location> iterator = gameMap1.getGraphMap().iteratorShortestPath(gameMap1.getGraphMap().getVertex(x),gameMap1.getGraphMap().getVertex(y));
        System.out.println(iterator.next());

        /*

        Map gameMap6 = new Map();
        gameMap6.generateMap(3, false, 0.7);
        listMap.addMap(gameMap6);

        FileIO.exportToJSON(listMap);
        listMap = FileIO.importFromJson();

        for (Map map : listMap.getAllMaps()) {
            System.out.println(map);
        }

        */

        Menu.MainMenu();
    }
}