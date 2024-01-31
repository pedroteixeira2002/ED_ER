package move_algorithms;

import collections.lists.OrderedLinkedList;
import game.*;
import interfaces.IAlgorithm;
import structures.NetworkEnhance;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class RandomPath implements IAlgorithm {
    private NetworkEnhance<Location> map;
    private OrderedLinkedList<Bot> bots;
    private Flag opponentFlag;
    private Flag myFlag;
    private Location myLocation;
    private Game game;

    public RandomPath(Game game) {
        this.map = game.getMap().getGraphMap();
        this.opponentFlag = getOpponent(game).getFlag();
        this.myLocation = getMe(game).getFlag().getLocation();
        this.myFlag = getMe(game).getFlag();
        this.game = game;
    }

    public NetworkEnhance<Location> getMap() {
        return map;
    }

    public void setMap(NetworkEnhance<Location> map) {
        this.map = map;
    }

    public OrderedLinkedList<Bot> getBots() {
        return bots;
    }

    public void setBots(OrderedLinkedList<Bot> bots) {
        this.bots = bots;
    }

    public Flag getOpponentFlag() {
        return opponentFlag;
    }

    public void setOpponentFlag(Flag opponentFlag) {
        this.opponentFlag = opponentFlag;
    }

    public Flag getMyFlag() {
        return myFlag;
    }

    public void setMyFlag(Flag myFlag) {
        this.myFlag = myFlag;
    }

    public Location getMyLocation() {
        return myLocation;
    }

    public void setMyLocation(Location myLocation) {
        this.myLocation = myLocation;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public Location move(Game game) {

        NetworkEnhance<Location> newMap;
        newMap = botInTheWay(getMap());

        Location randomLocation = randomLocation(newMap);

        setMyLocation(randomLocation);

        if (getMyLocation().equals(getOpponentFlag())) {
            System.out.println("Winner:" + getMe(game));
        }

        return getMyLocation();
    }

    @Override
    public NetworkEnhance<Location> botInTheWay(NetworkEnhance<Location> map) {
        return null;
    }

    private Location randomLocation(NetworkEnhance<Location> map) {
        Object[] vertices = map.getVertices();
        if (vertices == null || vertices.length == 0)
            throw new RuntimeException("There are no locations in the map");

        List<Location> verticesList = Arrays.asList(Arrays.copyOf(vertices, vertices.length, Location[].class));
        Iterator<Location> vList = map.iteratorDFS(map.getVertex(0));
        Iterator<Location> iterator = map.iteratorDFS(verticesList.get(0));


        Random random = new Random();
        int steps = random.nextInt(verticesList.size());

        for (int i = 0; i < steps && iterator.hasNext(); i++) {
            iterator.next();
        }
        return iterator.hasNext() ? iterator.next() : verticesList.get(0);
    }
    Player getOpponent(Game game) {
        if (game.getRound() % 2 == 0)
            return game.getPlayer2();
        else return game.getPlayer1();
    }

    Player getMe(Game game) {
        if (game.getRound() % 2 == 0) {
            return game.getPlayer1();
        } else {
            return game.getPlayer2();
        }
    }
    @Override
    public String toString() {
        return "\nThis Algorithm don´t follow a particular set of rules. " +
                "\nThe bot will randomly move in the map, without the intention to achieve the flag." +
                "\nThis algorithm can be considered a defense method, since it can block the enemy moves." +
                " On the other hand, it can also block your moves." +
                " To Avoid this, try to set this bot as the last one";
    }

}
