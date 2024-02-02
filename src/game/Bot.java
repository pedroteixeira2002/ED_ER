package game;

import algorithms.*;
import interfaces.IAlgorithm;
import interfaces.IBot;
import menu.Display;
import menu.Tools;

import java.io.IOException;

import static menu.Tools.getMe;

public class Bot implements IBot {
    private static int nextId = 0;
    private final int id;
    private Algorithm algorithm;
    private Location location;
    private final Player owner;

    public Bot(Game game, Algorithm algorithm) throws IOException {
        this.id = nextId++;
        this.owner = getMe(game);
        this.algorithm = algorithm;
    }
    public Bot(){
        this.id = nextId++;
        this.owner= new Player();
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
