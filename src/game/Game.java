package game;

import collections.lists.UnorderedLinkedList;
import collections.queues.LinkedQueue;
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
    private final LinkedQueue<Player> playersQueue;
    private int round;

    public Game(Map map) {
        this.map = map;
        this.playersQueue = new LinkedQueue<>();
        this.players = new UnorderedLinkedList<>();
        this.round = 1;
    }

    public Game() {
        this.map = new Map();
        this.playersQueue = new LinkedQueue<>();
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
        System.out.println("Setting up bots...");
        defineNumberOfBots();
        defineLocationBot();
        //players.head.getElement().addBots(players.head.getElement());
        //players.tail.getElement().addBots(players.tail.getElement());
        //round
        round();
    }

    /**
     * Define the location of the bot the same as the player's base
     */
    private void defineLocationBot() {
        Iterator<Player> iPlayers = players.iterator();
        while (iPlayers.hasNext()) {
            Player currentPlayer = iPlayers.next();
            Iterator<Bot> bots = currentPlayer.getBots().iterator();
            while (bots.hasNext()) {
                Bot bot = bots.next();
                bot.setLocation(currentPlayer.getBase().getLocation());
            }
        }
    }

    public void defineNumberOfBots() throws IOException {
        for(Player player : players){
            System.out.println("How many bots do you want to add for player " + player.getName() + "?");
            int numberOfBots = Tools.GetInt();
            System.out.println("You have chosen to add " + numberOfBots + " bots to the game.");
            player.addBots2(player, numberOfBots);
        }
    }

    private void round() {
        /*
        // Iterate through players for one round
        Iterator<Player> playerIterator = players.iterator();
        while (playerIterator.hasNext()) {
            Player currentPlayer = playerIterator.next();

            // Perform the move for the player's bot
            moving(currentPlayer);

            if (checkVictory() != null) {
                System.out.println(checkVictory().getName() + " is the winner!");
                break;
            }
        }
        */
        //while (checkVictory() == null) {
        //moving();
        //moving();
        //round++;
        //}

        // Increment the round counter after each player has moved
        //round++;

        while (checkVictory() == null) {
            Iterator<Player> playerIterator = players.iterator();
            while (playerIterator.hasNext()) {
                Player currentPlayer = playerIterator.next();

                // Realiza o movimento para o bot do jogador
                moving(currentPlayer);

                // Verifica se há uma condição de vitória após cada movimento
                if (checkVictory() != null) {
                    System.out.println(checkVictory().getName() + " é o vencedor!");
                    // Encerra o jogo
                    return;
                }
            }
            // Increment the round counter after each player has moved
            round++;
        }
    }

    private void moving(Player player) {
        /*
        Player player = new Player();
        Bot bot= new Bot();
        //get player and bot by doing dequeue (dequeue returns the wished element)
        bot = player.getBotsQueue().dequeue();

        // moving the players bot
        bot.getAlgorithm().move(bot, this);

        player = playersQueue.dequeue();

        //adding the bot and the player to the end of the list
        playersQueue.enqueue(player);
        player.getBotsQueue().enqueue(bot);
        */
        //--------------------------------------------------------------------------------//

        // Get the player's bot by dequeuing from the queue
        Bot bot = player.getBotsQueue().dequeue();

        // Perform the move using the player's algorithm
        bot.getAlgorithm().move(bot, this);

        player = playersQueue.dequeue();

        // Enqueue the player and bot back to their respective queues
        playersQueue.enqueue(player);
        player.getBotsQueue().enqueue(bot);
    }

    /**
     * Set the base of the player in the map
     */
    public Flag setPlayerBaseInMap() throws IOException {

        System.out.println("Available locations: ");
        System.out.println(Arrays.toString(map.getGraphMap().getVertices()));

        System.out.println("Choose a location for your flag");
        int xCoordinate = ReadInfo.readCoordinateX();
        int yCoordinate = ReadInfo.readCoordinateY();

        Flag flag = new Flag(new Location(xCoordinate, yCoordinate));

        System.out.println("Flag was set at the following location X:"
                + xCoordinate + " Y:" + yCoordinate);

        return flag;
    }

    private String randomizeFirstPlayer(String name1, String name2) {
        Random random = new Random();
        boolean choose = random.nextBoolean();
        String first = choose ? name1 : name2;
        System.out.println("The first Player will be " + first + ".");
        return first;
    }

    /**
     * Setup Players for the game, randomizes which is Player 1 and player 2.
     *
     * @throws IOException
     */
    private void setupPlayer() throws IOException {
        Player player1 = new Player(), player2 = new Player();

        System.out.println("\nEnter the players names");
        System.out.print("\nFirst name:");
        String name1 = Tools.GetString();

        System.out.print("\nSecond name:");
        String name2 = Tools.GetString();

        player1.setName(randomizeFirstPlayer(name1, name2));
        player1.setBase(setPlayerBaseInMap());

        players.addToRear(player1);

        if (players.head.getElement().getName().equals(name1)) {
            player2.setName(name2);
            player2.setBase(setPlayerBaseInMap());
            players.addToRear(player2);
        } else {
            player2.setName(name1);
            player2.setBase(setPlayerBaseInMap());
            players.addToRear(player2);
        }
    }

    private Player checkVictory() {
        Iterator<Player> iPlayers = players.iterator();

        while (iPlayers.hasNext()) {

            Player currentPlayer = iPlayers.next();
            Iterator<Bot> bots = currentPlayer.getBots().iterator();
            // players.iterator().next().getBots().iterator()
            while (bots.hasNext()) {

                Bot bot = bots.next();
                if (bot.getLocation().equals(new Location(1000, 1000)))
                    return currentPlayer;
            }
        }
        return null;
    }
}
