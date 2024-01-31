package menu;

public class Display {
    public static String displayMainMenu() {
        return " ____________________________________________________________________\n" +
                "|                                Menu                               |\n" +
                "|___________________________________________________________________|\n" +
                "| 1. New game                                                       |\n" +
                "| 2. Create new map                                                 |\n" +
                "|___________________________________________________________________|\n";
    }

    public static String displayNewGameMenu() {
        return " ____________________________________________________________________\n" +
                "|                              New Game                             |\n" +
                "|___________________________________________________________________|\n" +
                "| 1. New map                                                        |\n" +
                "| 2. Import map                                                     |\n" +
                "|___________________________________________________________________|\n";
    }

    public static String displayDirectional() {
        return " ___________________________________________________________________\n" +
                "|                   Bidirectional paths                             |\n" +
                "|___________________________________________________________________|\n" +
                "| 1. Yes                                                            |\n" +
                "| 2. No                                                             |\n" +
                "|___________________________________________________________________|\n";

    }

    public static String displaySaveMapMenu() {
        return " ___________________________________________________________________\n" +
                "|                        Creating new map                           |\n" +
                "|___________________________________________________________________|\n" +
                "|  1. Save map                                                      |\n" +
                "|  2. Continue without saving                                       |\n" +
                "|___________________________________________________________________|\\n";
    }
}
