package game;

import collections.lists.OrderedLinkedList;
import collections.lists.UnorderedLinkedList;
import interfaces.IGame;
import menu.ReadInfo;
import menu.Tools;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class Game implements IGame {
    private Map map;
    private final UnorderedLinkedList<Player> players;
    private int round;

    public Game(Map map) {
        this.map = map;
        this.players = new UnorderedLinkedList<>();
        this.round = 1;
    }

    public Game() {
        this.map = new Map();
        this.players = new UnorderedLinkedList<>();
        this.round = 1;
    }

    public UnorderedLinkedList<Player> getPlayers() {
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
        while (checkVictory() != null) {
            Iterator<Player> playersIterator = players.iterator();
            while (playersIterator.hasNext()) {
                Player currentPlayer = playersIterator.next();
                Bot bot = currentPlayer.getBotsQueue().dequeue();
                bot.getAlgorithm().move(bot, this);
                currentPlayer.getBotsQueue().enqueue(bot);
            }
        }
        round++;
    }


    /**
     * Set the base of the player in the map
     */
    public Flag setPlayerBaseInMap() throws IOException {
        System.out.println("Available locations: ");
        System.out.println(Arrays.toString(map.getGraphMap().getVertices()));

        int xCoordinate = ReadInfo.readCoordinateX();
        int yCoordinate = ReadInfo.readCoordinateY();


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
            System.out.println("\nEnter the players names");
            System.out.println("\nFirst");
            String name1 = Tools.GetString();

            System.out.println("\nSecond");
            String name2 = Tools.GetString();

            Player player1 = new Player(randomizeFirstPlayer(name1, name2), setPlayerBaseInMap());


            players.addToRear(player1);

            if (players.head.getElement().getName().equals(name1)) {
                Player player2 = new Player(name2, setPlayerBaseInMap());
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
