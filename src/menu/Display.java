package menu;
/**
 * This class contains methods that are used to display the menu.
 */
public abstract class Display {
    /**
     * This method returns a string that is used to display the main menu.
     *
     * @return The string that was used to display the main menu.
     */
    public static String displayMainMenu() {
        return " ____________________________________________________________________\n" +
                "|                         Capture the Flag                          |\n" +
                "|___________________________________________________________________|\n" +
                "| 1. New game                                                       |\n" +
                "| 2. Create map                                                     |\n" +
                "| 3. Rules                                                          |\n" +
                "| 0. Exit                                                           |\n" +
                "|___________________________________________________________________|";
    }

    /**
     * This method returns a string that is used to display the new game menu.
     *
     * @return The string that was used to display the new game menu.
     */
    public static String displayNewGameMenu() {
        return " ____________________________________________________________________\n" +
                "|                              New Game                             |\n" +
                "|___________________________________________________________________|\n" +
                "| 1. New map                                                        |\n" +
                "| 2. Import map                                                     |\n" +
                "| 0. Exit                                                           |\n" +
                "|___________________________________________________________________|\n";
    }

    /**
     * This method returns a string that is used to display the Bidirectional menu.
     *
     * @return The string that was used to display the bidirectional menu.
     */
    public static String displayDirectional() {
        return " ___________________________________________________________________\n" +
                "|                   Bidirectional paths                             |\n" +
                "|___________________________________________________________________|\n" +
                "| 1. Yes                                                            |\n" +
                "| 2. No                                                             |\n" +
                "|___________________________________________________________________|\n";

    }

    /**
     * This method returns a string that is used to display the save map menu.
     *
     * @return The string that was used to display the save map menu.
     */
    public static String displaySaveMapMenu() {
        return "  ___________________________________________________________________\n" +
                "|                        Creating new map                           |\n" +
                "|___________________________________________________________________|\n" +
                "|  1. Save map                                                      |\n" +
                "|  2. Continue without saving                                       |\n" +
                "|___________________________________________________________________|\\n";
    }

    /**
     * This method returns a string that is used to display the algorithm menu.
     *
     * @return The string that was used to display the algorithm menu.
     */
    public static String displayAlgorithm() {
        return "\n Choose your algorithm." +
                "\n\t1. Try Catch Enemy Flag" +
                "\n\t2. Random Path" +
                "\n\t3. Minimum Spanning Tree Path";
    }

    /**
     * This method returns a string that is used to display the game rules.
     *
     * @return The string that was used to display the game rules.
     */
    public static String displayGameRules() {
        return "  ___________________________________________________________________\n" +
                "|                              Rules                                |\n" +
                "|___________________________________________________________________|\n" +
                "| 1. The game is played by two players.                             |\n" +
                "| 2. Each player has a base and a flag.                             |\n" +
                "| 3. Players agree and configure game map features.                 |\n" +
                "| 4. After the map is defined, both players select the location of  |\n" +
                "|    the flags on the map that corresponds to the player's base.    |\n" +
                "| 5. Players agree and stipulate the number of bots that each       |\n" +
                "|    player will have at their disposal your availability.          |\n" +
                "|    The number of bots for each player can be different.           |\n" +
                "| 6. Given the predefined number of bots mentioned above, each of   |\n" +
                "|    them will be assigned an algorithm from the various available  |\n" +
                "|    options.                                                       |\n" +
                "| 7. It is randomly decided which player that will begin. At the    |\n" +
                "|    start of the match, all bots will be located in the same       |\n" +
                "|    position than your player's flag.                              |\n" +
                "| 8. In each round, alternating between players, one of the bots    |\n" +
                "|    will move.                                                     |\n" +
                "| 9. Each location can host more than one bot. If when entering a   |\n" +
                "|    location there is an opposing bot with your team's flag, it    |\n" +
                "|    must return to its base. However, if a bot with the flag       |\n" +
                "|    enters a location, the flag will remain there possession of    |\n" +
                "|    the bot. If the two flags coincide in the same location, both  |\n" +
                "|    flags return to the base.                                      |\n" +
                "| 10. The game ends when a player captures the enemy base with their|\n" +
                "|     own flag.                                                     |\n" +
                "|___________________________________________________________________|\n";
    }
}

