package game;

import collections.queues.LinkedQueue;
import interfaces.IGame;
import menu.Tools;

import java.io.IOException;
import java.util.Random;

public class Game implements IGame {
    private Map map;
    private LinkedQueue<Player> players;

    public LinkedQueue<Player> getPlayers() {
        return players;
    }

    public Game(Map map) {
        this.map = map;
        this.players = new LinkedQueue<>();

    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }


    /**
     * Start a new game
     */
    @Override
    public void start() throws IOException {
        map.visualizeGraph();
        Location win = new Location(0, 0);
        //setup players
        setupPlayers();

        //setup bots
        System.out.println("Setting up bots...");
        defineNumberOfBots();

        //players play in their turn
        //while nobody wins
        while (!(turn().getPosX() == 1000)) ;
    }

    private Location turn() {
        Player player = players.dequeue();
        players.enqueue(player);
        // Realiza o movimento para o bot do jogador
        return moving(player);
    }

    private Location moving(Player player) {
        Bot bot = player.getBots().dequeue();
        player.getBots().enqueue(bot);

        // Perform the move using the bot's algorithm
        return bot.getAlgorithm().move(bot, this);
    }

    private void defineNumberOfBots() throws IOException {
        Player player;
        int numberOfBots;

        player = players.dequeue();

        System.out.println("How many bots do you want to add for player " + player.getName() + "?");
        numberOfBots = Tools.GetInt();
        System.out.println("You have chosen to add " + numberOfBots + " bots to the game.");

        player.addBots(player, numberOfBots);

        // Re-enqueue the player
        players.enqueue(player);


        player = players.dequeue();

        System.out.println("How many bots do you want to add for player " + player.getName() + "?");
        numberOfBots = Tools.GetInt();
        System.out.println("You have chosen to add " + numberOfBots + " bots to the game.");

        player.addBots(player, numberOfBots);

        // Re-enqueue the player
        players.enqueue(player);
    }

    /**
     * Set the base of the player in the map
     */
    public Location setPlayerBaseInMap(Player player) throws IOException {

        System.out.println("Available locations: ");
        System.out.println(map.getGraphMap().toString());

        System.out.println(player.getName() + ", choose a location for your flag (vertex index)");
        int index = Tools.GetInt();

        Location base = map.getGraphMap().getVertex(index);

        System.out.println("Flag was set at the following location\t\tX:"
                + base.getPosX() + "\tY:" + base.getPosY());

        return base;
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
    private void setupPlayers() throws IOException {

        Player player1 = new Player();
        Player player2 = new Player();

        System.out.println("\nEnter the players names");
        System.out.print("\nFirst name:");
        String name1 = Tools.GetString();

        System.out.print("\nSecond name:");
        String name2 = Tools.GetString();

        player1.setName(randomizeFirstPlayer(name1, name2));
        player1.setBase(setPlayerBaseInMap(player1));
        player1.setFlag(player1.getBase());

        players.enqueue(player1);

        if (player1.getName().equals(name1)) {
            player2.setName(name2);
        } else {
            player2.setName(name1);
        }
        player2.setBase(setPlayerBaseInMap(player2));
        player2.setFlag(player2.getBase());
        players.enqueue(player2);
    }
}
