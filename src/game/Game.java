package game;

import collections.lists.OrderedLinkedList;
import interfaces.IGame;
import menu.ReadInfo;
import menu.Tools;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class Game implements IGame {
    private Map map;
    private final OrderedLinkedList<Player> players;
    private int round;

    public Game(Map map) {
        this.map = map;
        this.players = new OrderedLinkedList<>();
        this.round = 1;
    }

    public Game() {
        this.map = new Map();
        this.players = new OrderedLinkedList<>();
        this.round = 1;
    }

    public OrderedLinkedList<Player> getPlayers() {
        return players;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public int getRound() {
        return round;
    }


    /**
     * Start a new game
     */
    @Override
    public void start() throws IOException {
        //setup players
        setupPlayer();
        //setup bots
        setupBots();
        //round
        round();
    }

    private void round() {
        while (checkVictory() != null){

        }
            round++;
    }


    /**
     * Set the base of the player in the map
     */
    public Flag setPlayerBaseInMap() {
        int xCoordinate = ReadInfo.readCoordinateX();
        int yCoordinate = ReadInfo.readCoordinateY();

        System.out.println("Available locations: ");
        System.out.println(Arrays.toString(map.getGraphMap().getVertices()));

        System.out.println("Choose a location for your flag");
        System.out.println("Flag was set at the following location X:"
                + xCoordinate + " Y:" + yCoordinate);
        return new Flag(new Location(xCoordinate, yCoordinate));
    }

    private String randomizeFirstPlayer(String name1, String name2) {
        Random random = new Random();
        boolean choose = random.nextBoolean();
        String first = choose ? name1 : name2;
        System.out.println("The first Player will be " + first);
        return first;
    }

    /**
     * Setup Players for the game, randomizes which is Player 1 and player 2
     *
     * @throws IOException
     */
    private void setupPlayer() throws IOException {
        while (players.size() <= 1) {
            System.out.println("\nEnter the players names");
            System.out.println("\nFirst");
            String name1 = Tools.GetString();

            System.out.println("\nSecond");
            String name2 = Tools.GetString();

            players.add(new Player(randomizeFirstPlayer(name1, name2), setPlayerBaseInMap()));

            if (players.head.getElement().getName().equals(name1))
                players.add(new Player(name2, setPlayerBaseInMap()));
        }
    }

    private void setupBots() throws IOException {
        while (players.iterator().hasNext()) {
            players.iterator().next().addBots();
        }
    }

    private Player checkVictory() {
        Iterator<Player> iPlayers = players.iterator();

        while (iPlayers.hasNext()) {

            Player currentPlayer = iPlayers.next();
            Iterator<Bot> bots = players.iterator().next().getBots().iterator();

            while (bots.hasNext()) {

                Bot bot = bots.next();
                if (bot.getLocation().equals(new Location(1000, 1000)))
                    return currentPlayer;
            }
        }
        return null;
    }
}
