package menu;

import collections.lists.UnorderedLinkedList;
import collections.queues.LinkedQueue;
import game.Bot;
import game.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class contains methods that are used to read from the keyboard.
 */
public abstract class Tools {

    /**
     * This method returns a string that is read from the keyboard.
     *
     * @return The string that was read from the keyboard.
     * @throws IOException If the string is null, empty or blank.
     */
    public static String GetString() throws IOException {

        BufferedReader stringIn = new BufferedReader(new
                InputStreamReader(System.in));

        return stringIn.readLine();

    }

    /**
     * This method returns an integer that is read from the keyboard.
     *
     * @return The integer that was read from the keyboard.
     */
    public static int GetInt() throws IOException {

        String aux = GetString();

        return Integer.parseInt(aux);

    }

    /**
     * This method returns a double that is read from the keyboard.
     *
     * @return The double that was read from the keyboard.
     */
    public static double getDouble() throws IOException {
        String aux = GetString();
        return Double.parseDouble(aux);
    }

    /**
     * This method returns a boolean that is read from the keyboard.
     *
     * @return The boolean that was read from the keyboard.
     */
    public static boolean getTrue() throws IOException {
        if (Tools.GetInt() == 1)
            return true;
        else
            return false;
    }

}