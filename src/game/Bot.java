package game;

import algorithms.*;
import interfaces.IAlgorithm;
import interfaces.IBot;
import menu.Tools;

import java.io.IOException;

public class Bot implements IBot {
    private static int nextId = 0;
    private int id;
    private IAlgorithm algorithm;
    private Location location;
    private Player owner;

    public Bot(Game game, IAlgorithm algorithm) throws IOException {
        this.id = nextId++;
        this.owner = getMe(game);
        this.algorithm = algorithm;
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

    public IAlgorithm getAlgorithm() {
        return algorithm;
    }


    public IAlgorithm setAlgorithm(Game game) throws IOException {
        switch (Tools.GetInt()) {
            case 1:
                return algorithm = new RandomPath(game);
            case 2:
                return algorithm = new BlockEnemyShortestPath(game);
            case 3:
                return algorithm = new BlockClosestEnemyBot(game);
            default:
                return algorithm = new ShortestPath(game);
        }
    }

    private Player getMe(Game game) {
        if (game.getRound() % 2 == 0) {
            return game.getPlayer1();
        } else {
            return game.getPlayer2();
        }
    }
    @Override
    public String toString() {
        return "\nBot:" +
                "\nID: " + id +
                "\nStart Location: " + location +
                "\nAlgorithms this bot uses: " + algorithm.toString();
    }
}
