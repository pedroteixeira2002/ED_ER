package menu;

import game.AlgorithmType;

import java.io.IOException;

/**
 * This class contains methods that are used to read from the keyboard.
 */
public abstract class ReadInfo {
    /**
     * This method returns an integer that is read from the keyboard.
     *
     * @return The integer that was read from the keyboard.
     * @throws IOException If the string is null, empty or blank.
     */
    public static int readQuantityOfLocalizations() throws IOException {
        System.out.println("\nEnter the amount of localizations you want in your map:");
        return Tools.GetInt();
    }

    /**
     * This method returns a boolean that is read from the keyboard.
     *
     * @return The boolean that was read from the keyboard.
     * @throws IOException If the string is null, empty or blank.
     */
    public static boolean readIfIsDirectional() throws IOException {
        System.out.println(Display.displayDirectional());
        return Tools.getTrue();
    }

    /**
     * This method returns a double that is read from the keyboard.
     *
     * @return The double that was read from the keyboard.
     * @throws IOException If the string is null, empty or blank.
     */
    public static double readEdgeDensity() throws IOException {
        System.out.println("\nEnter the wished density for your edges(0.1 to 1):");
        return Tools.getDouble();
    }

    /**
     * This method returns a boolean that is read from the keyboard.
     *
     * @return The boolean that was read from the keyboard.
     * @throws IOException If the string is null, empty or blank.
     */
    public static boolean saveMap() throws IOException {
        System.out.println(Display.displaySaveMapMenu());
        return Tools.getTrue();
    }

}
