package game;

import interfaces.IGame;
import menu.ReadInfo;
import menu.Tools;


import java.io.IOException;
import java.util.Arrays;
import java.util.Random;


public class Game implements IGame {
    private Map map;
    private Player player1;
    private Player player2;
    private int round;

    public Game(Map map) {
        this.map = map;
        this.player1 = new Player();
        this.player2 = new Player();
        this.round = 1;
    }

    public Game() {
        this.map = new Map();
        this.player1 = new Player();
        this.player2 = new Player();
        this.round = 1;
    }

    /**
     * Start a new game
     */
    @Override
    public void start(Game game) throws IOException {
        setupPlayers();


    }

    public void round(Game game) {

        round++;
    }

    private void addBots(Game game) throws IOException {
        boolean addingBots = true;
        while (addingBots) {
            addBot(game, game.player1);
            addBot(game, game.player2);
            System.out.println("Want to add another bot?");
            if (!Tools.getTrue())
                addingBots = false;
        }
    }

    private void addBot(Game game, Player player) throws IOException {
        boolean canAddBot;
        do {
            canAddBot = whatBot(game, player);
        } while (canAddBot);
    }

    private boolean whatBot(Game game, Player player) throws IOException {
        int algorithmCanAppear = numberOfUsedAlgorithms(player) / 5; //number of algorithms i created;
        int occurrences = 0;
        System.out.println("\nWhat Type of Bot you want to add?");
        System.out.println(
                        "\n\t1. Shortest Path" +
                        "\n\t2. Block Enemy Shortest Path" +
                        "\n\t3. Block Closest Enemy" +
                        "\n\t4. Random Path" +
                        "\n\t5. Minimum Spanning Tree Path");


        occurrences++;

        if (occurrences > algorithmCanAppear)
            return false;


        return true;

    }

    private int numberOfUsedAlgorithms(Player player) {
        if (player.getBots().size() > 5)
            return player.getBots().size();
        else return 5;
    }

    /**
     * Set the flag of the player in our base of the map
     * @param player
     */
    public void setPlayerFlagInMap(Player player) {
        int xCoordinate = ReadInfo.readCoordinateX();
        int yCoordinate = ReadInfo.readCoordinateY();

        System.out.println("Available locations: ");
        System.out.println(Arrays.toString(map.getGraphMap().getVertices()));
        System.out.println("Choose a location for your flag");
        // Acho que é preciso associar ao mapa quando inseres as coordenadas ou então não e estou a fazer confusão
        player.getFlag().getLocation().setPosX(xCoordinate);
        player.getFlag().getLocation().setPosY(yCoordinate);
        System.out.println(player.getName() + "'s flag was set at the following location X:" + xCoordinate + " Y:"
                + yCoordinate);

    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public int getRound() {
        return round;
    }

    private String randomizeFirstPlayer(String name1, String name2) {
        Random random = new Random();
        boolean choose = random.nextBoolean();
        String first = choose ? name1 : name2;
        System.out.println("The first Player will be " + first);
        return first;
    }

    private void setupPlayers() throws IOException {
        System.out.println("\nEnter the players names for the first player to be randomized");
        String name1 = Tools.GetString();
        String name2 = Tools.GetString();
        player1.setName(randomizeFirstPlayer(name1, name2));
        if (name1 == player1.getName())
            player2.setName(name2);
        else
            player2.setName(name1);
    }
}
