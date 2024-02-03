package game;

import interfaces.IBot;

import java.io.IOException;

public class Bot implements IBot {
    private static int nextId = 0;
    private final int id;
    private Algorithm algorithm;
    private Location location;
    private final Player owner;

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
                "\nStart Location: " + location +
                "\nAlgorithms this bot uses: " + algorithm.toString();
    }
}
