package game;

import algorithms.*;
import interfaces.IAlgorithm;
import interfaces.IGame;
import menu.Display;
import menu.ReadInfo;
import menu.Tools;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
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
        //abrir ciclo para rounds enquanto não chegar à vitória


    }

    public void round(Game game) {
        //joga o primeiro
        //joga o segundo
        round++;
    }

    private void addBots(Game game, Player player) throws IOException {
        boolean addingBots = true;
        while (addingBots) {
            whatBot(game, player);
            System.out.println("Want to add another bot?");
            if (!Tools.getTrue())
                addingBots = false;
        }
    }

    private void whatBot(Game game, Player player) throws IOException {
        Display.displayAlgorithm();
        switch (Tools.GetInt()) {
            case 1:
                IAlgorithm algorithm1 = new ShortestPath(game);
                if (botCheckIfPossible(algorithm1, player))
                    player.getBots().add(new Bot(game, algorithm1));
                break;
            case 2:
                IAlgorithm algorithm2 = new BlockEnemyShortestPath(game);
                if (botCheckIfPossible(algorithm2, player))
                    player.getBots().add(new Bot(game, algorithm2));
                break;
            case 3:
                IAlgorithm algorithm3 = new BlockClosestEnemyBot(game);
                if (botCheckIfPossible(algorithm3, player))
                    player.getBots().add(new Bot(game, algorithm3));
                break;
            case 4:
                IAlgorithm algorithm4 = new RandomPath(game);
                if (botCheckIfPossible(algorithm4, player))
                    player.getBots().add(new Bot(game, algorithm4));
                break;
            case 5:
                IAlgorithm algorithm5 = new MinimumSpanningTreePath(game);
                if (botCheckIfPossible(algorithm5, player))
                    player.getBots().add(new Bot(game, algorithm5));
                break;
        }
    }

    private boolean botCheckIfPossible(IAlgorithm algorithm, Player player) {
        int algorithmCanAppear = numberOfUsedAlgorithms(player) / 5; //number of algorithms i created;
        int occurrences = 0;

        Iterator<Bot> bots = player.getBots().iterator();

        while (bots.hasNext()) {
            if (bots.next().getAlgorithm().equals(algorithm))
                occurrences++;
        }
        if (occurrences > algorithmCanAppear) {
            System.out.println("Maximum number for this algorithm has been reached");
            return false;
        }

        return true;
    }

    private int numberOfUsedAlgorithms(Player player) {
        if (player.getBots().size() > 5)
            return player.getBots().size();
        else return 5;
    }

    /**
     * Set the flag of the player in our base of the map
     *
     * @param player
     */
    public void setPlayerFlagInMap(Player player) {
        int xCoordinate = ReadInfo.readCoordinateX();
        int yCoordinate = ReadInfo.readCoordinateY();

        System.out.println("Available locations: ");
        System.out.println(Arrays.toString(map.getGraphMap().getVertices()));
        System.out.println("Choose a location for your flag");
        // Acho que é preciso associar ao mapa quando inseres as coordenadas ou então não e estou a fazer confusão
        player.getBase().getLocation().setPosX(xCoordinate);
        player.getBase().getLocation().setPosY(yCoordinate);
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
