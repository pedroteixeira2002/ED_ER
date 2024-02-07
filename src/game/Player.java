package game;

import collections.queues.LinkedQueue;
import interfaces.IPlayer;
import menu.Tools;

import java.io.IOException;

import static menu.Display.displayAlgorithm;

public class Player implements IPlayer {
    /** The name of the player */
    private String name;
    /** The queue of bots that the player has */
    private final LinkedQueue<Bot> bots;
    /** The location of the base of the player */
    private Location base;
    /** The location of the flag of the player */
    private Location flag;
    /** The number of bots that the player has */
    private int numberOfBots;

    /**
     * Constructor for the player
     */
    public Player() {
        this.name = null;
        this.bots = new LinkedQueue<>();
        this.base = new Location(0, 0);
        this.flag = this.base;
        this.numberOfBots = 0;
    }

    /**
     * Setter for the flag
     * @param flag
     */
    public void setFlag(Location flag) {
        this.flag = flag;
    }

    /**
     * Getter for the bots
     * @return the queue of bots
     */
    public LinkedQueue<Bot> getBots() {
        return bots;
    }

    /**
     * Getter for the number of bots
     * @return the number of bots
     */
    public int getNumberOfBots() {
        return numberOfBots;
    }

    /**
     * Setter for the number of bots
     * @param numberOfBots
     */
    public void setNumberOfBots(int numberOfBots) {
        this.numberOfBots = numberOfBots;
    }

    /**
     * Getter for the flag
     * @return the location of the flag
     */
    public Location getFlag() {
        return flag;
    }

    /**
     * Setter for the name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the name
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the base
     * @return
     */
    public Location getBase() {
        return base;
    }

    /**
     * Setter for the base
     * @param base
     */
    public void setBase(Location base) {
        this.base = base;
    }

    /**
     * Adds bots to the player
     * Set the number of bots that the player has
     * Set the shortest path algorithm to the first bot
     * Set the owner of the bot
     * Set the location of the bot
     * Set the bot to have the flag
     * Add the bot to the queue of bots
     * @param player
     * @param numBots
     * @throws IOException
     */
    public void addBots(Player player, int numBots) throws IOException {
        Bot bot1 = new Bot();
        Algorithm algorithm1 = new Algorithm();
        player.setNumberOfBots(numBots);
        //setting the first bot to always be Shortest Path
        algorithm1.setType(AlgorithmType.SHORTEST_PATH);
        //configuring bots
        bot1.setAlgorithm(algorithm1);
        bot1.setOwner(player);
        bot1.setLocation(bot1.getOwner().getBase());
        bot1.setHasFlag(true);
        //add bot to the players queue of bots
        player.bots.enqueue(bot1);

        for (int i = 1; i < numBots; i++) {
            Algorithm algorithm = new Algorithm();
            Bot bot = new Bot();
            // Define o algoritmo para cada bot(definindo tipo, owner e localizacao)
            algorithm.setType(whatAlgorithm().getType());
            //configuring the bot
            bot.setAlgorithm(algorithm);
            bot.setOwner(player);
            bot.setLocation(player.getBase());
            // adds the bot to the queue
            player.bots.enqueue(bot);

            System.out.println("Bot owner: " + bot.getOwner().getName());
        }
    }

    /**
     * Method to choose the algorithm for the bot
     * @return the algorithm
     * @throws IOException
     */
    private Algorithm whatAlgorithm() throws IOException {
        Algorithm algorithm = new Algorithm();
        System.out.println(displayAlgorithm());
        switch (Tools.GetInt()) {
            default:
                algorithm.setType(AlgorithmType.TRY_CATCH_ENEMY_PATH);
                return algorithm;
            case 2:
                algorithm.setType(AlgorithmType.RANDOM_PATH);
                return algorithm;
            case 3:
                algorithm.setType(AlgorithmType.MINIMUM_SPANNING_TREE_PATH);
                return algorithm;

        }
    }


}