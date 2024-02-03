package menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public abstract class Tools {

    /**
     * This method returns a string that is read from the keyboard.
     *
     * @return The string that was read from the keyboard.
     * @throws IOException If the string is null, empty or blank.
     */
    public static String readString() throws IOException {
        String string;
        Scanner scanner = new Scanner(System.in);
        string = scanner.nextLine();
        if (string == null || string.isEmpty() || string.isBlank()) {
            throw new IOException("String can't be Null, Empty or Blank");
        }
        return string;
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
    public static double readDouble() {
        double num;
        Scanner scanner = new Scanner(System.in);
        num = scanner.nextDouble();
        return num;
    }

    public static String GetString() throws IOException {

        BufferedReader stringIn = new BufferedReader(new
                InputStreamReader(System.in));

        return stringIn.readLine();

    }


    public static long GetLong() throws IOException {

        String aux = GetString();

        return Long.parseLong(aux);

    }

    public static char GetChar() throws IOException {

        String aux = GetString();

        return aux.charAt(0);

    }

    public static boolean getTrue() throws IOException {;
        if (Tools.GetInt() == 1)
            return true;
        return false;
    }

    public static double getDouble() throws IOException {
        String aux = GetString();
        return Double.parseDouble(aux);
    }


}