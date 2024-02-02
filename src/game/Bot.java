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
    private int id;
    private Algorithm algorithm;
    private Location location;
    private Player owner;

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
/*
    private void whatBot(Player player) throws IOException {
        Display.displayAlgorithm();
        switch (Tools.GetInt()) {
            case 1:
                IAlgorithm algorithm1 = new ShortestPath();
                if (botCheckIfPossible(algorithm1, player))
                    player.getBots().add(new Bot( algorithm1));
                break;
            case 2:
                IAlgorithm algorithm2 = new BlockEnemyShortestPath(game);
                if (botCheckIfPossible(algorithm2, player))
                    player.getBots().add(new Bot(game, algorithm2));
                break;
            case 3:
                IAlgorithm algorithm3 = new BlockClosestEnemyBot();
                if (botCheckIfPossible(algorithm3))
                    player.getBots().add(new Bot(algorithm3));
                break;
            case 4:
                IAlgorithm algorithm4 = new RandomPath();
                if (botCheckIfPossible(algorithm4))
                    player.getBots().add(new Bot(algorithm4));
                break;
            case 5:
                IAlgorithm algorithm5 = new MinimumSpanningTreePath();
                if (botCheckIfPossible(algorithm5))
                    player.getBots().add(new Bot(algorithm5));
                break;
        }
    }*/

    @Override
    public String toString() {
        return "\nBot:" +
                "\nID: " + id +
                "\nStart Location: " + location +
                "\nAlgorithms this bot uses: " + algorithm.toString();
    }
}
