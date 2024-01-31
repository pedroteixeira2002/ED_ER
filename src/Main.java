import datapersistence.FileIO;
import game.Bot;
import game.Game;
import game.ListMap;
import game.Map;
import menu.Menu;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
       /* System.out.println("Hello world!");
        ListMap listMap = new ListMap();

        Map gameMap1 = new Map();
        listMap.addMap(gameMap1);
        Map gameMap2 = new Map();
        listMap.addMap(gameMap2);
        Map gameMap3 = new Map();
        listMap.addMap(gameMap3);
        Map gameMap4 = new Map();
        listMap.addMap(gameMap4);
        Map gameMap5 = new Map();
        listMap.addMap(gameMap5);

        gameMap1.generateMap(1, true, 0.5);
        gameMap2.generateMap(15, true, 0.7);
        gameMap3.generateMap(10, false, 0.9);
        gameMap4.generateMap(5, false, 0.9);
        gameMap5.generateMap(5, false, 0.9);

        Map gameMap6 = new Map();
        gameMap6.generateMap(3, false, 0.7);
        listMap.addMap(gameMap6);

        FileIO.exportToJSON(listMap);
        listMap = FileIO.importFromJson();

        for (Map map : listMap.getAllMaps()) {
            System.out.println(map);
        }

        */
        Game game = new Game();

        Bot bot1 = new Bot(game);
        Bot bot2 = new Bot(game);
        Bot bot3 = new Bot(game);
        Bot bot4 = new Bot(game);
        Bot bot5 = new Bot(game);




        //Menu.MainMenu();
    }
}