package menu;

import game.*;
import interfaces.IAlgorithm;

import java.io.IOException;

public class ReadInfo {

    public static int readQuantityOfLocalizations() throws IOException {
        System.out.println("\nEnter the amount of localizations you want in your map:");
        return Tools.GetInt();
    }

    public static boolean readIfIsDirectional() throws IOException {
        System.out.println(Display.displayDirectional());
        return Tools.getTrue();
    }

    public static double readEdgeDensity() throws IOException {
        System.out.println("\nEnter the wished density for your edges(0.1 to 1):");
        return Tools.getDouble();
    }

    public static boolean saveMap() throws IOException {
        System.out.println(Display.displaySaveMapMenu());
        return Tools.getTrue();
    }

    public static int readCoordinateX() {
        System.out.println("\nEnter the coordinate for X");
        return Tools.readInt();
    }
    public static int readCoordinateY() {
        System.out.println("\nEnter the coordinate for Y");
        return Tools.readInt();
    }
    public static Player readPlayer() throws IOException {
        return new Player(readName(),readFlag());
    }
    public static Flag readFlag(){
        Location location = new Location(readCoordinateX(),readCoordinateY());
        return new Flag(location);

    }

    public static String readName() throws IOException {
        System.out.println("Enter the player name");
        return Tools.readString();
    }


    public static Bot newBot(Game game, IAlgorithm algorithm) throws IOException {
        System.out.println("Add your bot");
        Bot bot = new Bot(game, algorithm);

        return bot;
    }

}
