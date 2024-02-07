package game;

import interfaces.IBot;

/**
 * Class for the bot
 */
public class Bot implements IBot {
    /** The nextId of the bot */
    private static int nextId = 0;
    /** The id of the bot */
    private final int id;
    /** The algorithm that the bot uses */
    private Algorithm algorithm;
    /** The location of the bot */
    private Location location;
    /** The owner of the bot */
    private Player owner;
    /** If the bot has the flag */
    private boolean hasFlag = false;


    /**
     * Constructor for the bot
     * @param owner
     * @param algorithm
     */
    public Bot(Player owner, Algorithm algorithm) {
        this.id = nextId++;
        this.owner = owner;
        this.algorithm = algorithm;
    }

    /**
     * Constructor for the bot
     */
    public Bot() {
        this.id = nextId++;
        this.owner = new Player();
        this.algorithm = new Algorithm();
    }

    /**
     * Getter for hasFlag
     * @return the hasFlag
     */
    public boolean hasFlag() {
        return hasFlag;
    }
    /**
     * Setter for hasFlag
     * @param hasFlag
     */
    public void setHasFlag(boolean hasFlag) {
        this.hasFlag = hasFlag;
    }
    /**
     * Setter for the owner
     * @return the owner
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }
    /** Getter for the owner
     * @return the owner
     */
    public Player getOwner() {
        return owner;
    }
    /** Getter for the id
     * @return the id
     */
    public int getId() {
        return id;
    }

    /** Getter for the location
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /** Setter for the location
     * @param location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /** Getter for the algorithm
     * @return the algorithm
     */
    public Algorithm getAlgorithm() {
        return algorithm;
    }

    /** Setter for the algorithm
     * @param algorithm
     */
    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * To string method
     * return the bot as a string
     */
    @Override
    public String toString() {
        return "\nBot:" +
                "\nID: " + id +
                "\nLocation: " + location +
                "\nAlgorithms this bot uses: " + algorithm.toString();
    }
}
