package algorithms;

import collections.lists.OrderedLinkedList;
import game.*;
import structures.NetworkEnhance;

import java.util.Iterator;

public class BlockEnemyShortestPath {
    private NetworkEnhance<Location> map;
    private OrderedLinkedList<Bot> bots;
    private Flag opponentFlag;
    private Flag myFlag;
    private Location myLocation;
    private Game game;

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

    public BlockEnemyShortestPath(Game game) {
        this.map = game.getMap().getGraphMap();
        this.opponentFlag = getOpponent(game).getBase();
        this.myLocation = getMe(game).getBase().getLocation();
        this.myFlag = getMe(game).getBase();
        this.game = game;
    }

    public Location move(Game game) {

        NetworkEnhance<Location> newMap;

        newMap = botInTheWay(getMap());

        Iterator<Location> list = newMap.iteratorShortestPath
                (getMyLocation(), getOpponentFlag().getLocation());
        setMyLocation(list.next());

        double shortestPathWeight = newMap.shortestPathWeight
                (getOpponentFlag().getLocation(), getMyLocation());
        System.out.println("Actual opponent shortest path weight: " + shortestPathWeight);

        return getMyLocation();
    }
    public NetworkEnhance<Location> botInTheWay(NetworkEnhance<Location> map) {
        Iterator<Location> list = map.iteratorShortestPath
                (myLocation, opponentFlag.getLocation());

        Iterator<Bot> botIterator = bots.iterator();
        while (botIterator.hasNext()) {
            Bot nextBot = botIterator.next();
            do {
                if (nextBot.getLocation().equals(list.next()) &&
                        !nextBot.getLocation().equals(myFlag) &&
                        !nextBot.getLocation().equals(opponentFlag)) {
                    System.out.println("Bot in the way");
                    map.removeVertex(nextBot.getLocation());
                }
            } while (list.hasNext());
        }
        return map;
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
}