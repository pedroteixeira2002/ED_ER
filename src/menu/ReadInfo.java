package menu;

import game.AlgorithmType;

import java.io.IOException;

public abstract class ReadInfo {

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

    public static int readCoordinateX() throws IOException {
        System.out.println("\nEnter the coordinate for X");
        return Tools.GetInt();
    }

    public static int readCoordinateY() throws IOException {
        System.out.println("\nEnter the coordinate for Y");
        return Tools.GetInt();
    }

    public static String readName() throws IOException {
        System.out.println("Enter the player name");
        return Tools.GetString();
    }


    public static AlgorithmType chooseAlgorithm() throws IOException {
        Display.displayAlgorithm();
        switch (Tools.GetInt()) {
            case 2:
                return AlgorithmType.TRY_CATCH_ENEMY_PATH;

            case 3:
                return AlgorithmType.RANDOM_PATH;

            case 4:
                return AlgorithmType.MINIMUM_SPANNING_TREE_PATH;

            default:
                return AlgorithmType.SHORTEST_PATH;
        }
    }

}
