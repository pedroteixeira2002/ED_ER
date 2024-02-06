package game;

import interfaces.IBot;

public class Bot implements IBot {
    private static int nextId = 0;
    private final int id;
    private Algorithm algorithm;
    private Location location;
    private Player owner;
    private boolean hasFlag = false;


    public Bot(Player owner, Algorithm algorithm) {
        this.id = nextId++;
        this.owner = owner;
        this.algorithm = algorithm;
    }

    public Bot() {
        this.id = nextId++;
        this.owner = new Player();
        this.algorithm = new Algorithm();
    }

    public boolean hasFlag() {
        return hasFlag;
    }

    public void setHasFlag(boolean hasFlag) {
        this.hasFlag = hasFlag;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public int getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public String toString() {
        return "\nBot:" +
                "\nID: " + id +
                "\nLocation: " + location +
                "\nAlgorithms this bot uses: " + algorithm.toString();
    }
}
