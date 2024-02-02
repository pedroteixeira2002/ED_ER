package menu;

import datapersistence.FileIO;
import game.Game;
import game.ListMap;
import game.Map;
import game.Player;
import interfaces.IGame;

import java.io.IOException;

public class Menu {

    public static void MainMenu() throws IOException {
        boolean isRunning = true;

        while (isRunning) {

            ListMap maps = new ListMap();

            System.out.println(Display.displayMainMenu());

            switch (Tools.GetInt()) {
                case 1:
                    GameMenu(maps);
                    break;
                case 2:
                    Map map = new Map();
                    map.generateMap
                            (ReadInfo.readQuantityOfLocalizations(), ReadInfo.readIfIsDirectional(), ReadInfo.readEdgeDensity());
                    maps.addMap(map);
                    if (ReadInfo.saveMap())
                        FileIO.exportToJSON(maps);
                    break;
                case 0:
                    isRunning = false;
                    break;
            }
        }
    }


    public static void GameMenu(ListMap maps) throws IOException {
        System.out.println(Display.displayNewGameMenu());
        boolean isRunning = true;
        Map map = new Map();
        while (isRunning) {
            switch (Tools.GetInt()) {
                case 1:
                    map.generateMap(ReadInfo.readQuantityOfLocalizations(),
                            ReadInfo.readIfIsDirectional(),
                            ReadInfo.readEdgeDensity());
                    if (ReadInfo.saveMap())
                        FileIO.exportToJSON(maps);
                    break;
                case 2:
                    maps = FileIO.importFromJson();
                    System.out.println(maps.getAllMaps());
                    break;
                case 0:
                    isRunning = false;
                    break;
                default:
                    isRunning = true;
            }

        }
    }
}
