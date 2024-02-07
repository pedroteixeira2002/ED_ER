package menu;

import datapersistence.FileIO;
import game.Game;
import game.Map;
import game.MapList;

import java.io.IOException;

import static menu.Display.displayGameRules;
import static menu.Tools.GetInt;


public abstract class Menu {

    /**
     * Main menu
     * @throws IOException
     */
    public static void MainMenu() throws IOException {
        boolean isRunning = true;

        while (isRunning) {

            MapList maps = new MapList(); //Verficar propósito depois
            if(FileIO.fileIsNotEmpty()) {
                maps = FileIO.importFromJson();
            }
            //Show the main menu
            System.out.println(Display.displayMainMenu());
            System.out.print("Choose an option: ");
            int choice = GetInt();
            switch (choice) {
                case 1:
                    System.out.println("Loading game...");
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

                case 3:
                    System.out.println(displayGameRules());
                    break;
                case 0:
                    isRunning = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option, please choose a valid option.");
                    break;
            }
        }
    }

    /**
     * Game menu
     * @param maps
     * @throws IOException
     */
    public static void GameMenu(MapList maps) throws IOException {
        Map map = new Map();
        Game game = new Game(map);


        boolean isRunning = true;

        while (isRunning) {

            // Show game menu
            System.out.println(Display.displayNewGameMenu());
            System.out.print("Choose an option: ");
            int choice = GetInt();

            switch (choice) {
                case 1:
                    map.generateMap(ReadInfo.readQuantityOfLocalizations(),
                            ReadInfo.readIfIsDirectional(),
                            ReadInfo.readEdgeDensity());
                    maps.addMap(map);
                    if (ReadInfo.saveMap())
                        FileIO.exportToJSON(maps);
                    game.start();
                    break;
                case 2:
                    System.out.println("Choose a map to load: ");
                    maps.toString();
                    game = new Game(maps.getMapById(GetInt()));
                    game.start();
                    break;
                case 0:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid option, please choose a valid option.");
                    break;
            }
        }
    }
}
