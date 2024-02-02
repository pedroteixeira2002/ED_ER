package algorithms;

import collections.lists.OrderedLinkedList;
import game.*;
import menu.Tools;
import structures.NetworkEnhance;

import java.util.Iterator;

import static menu.Tools.getMe;

public class BlockClosestEnemyBot {
    private NetworkEnhance<Location> map;
    private OrderedLinkedList<Bot> bots;
    private Flag opponentFlag;
    private Flag myFlag;
    private Location myLocation;
    private Game game;
    public BlockClosestEnemyBot(Game game) {
        this.map = game.getMap().getGraphMap();
        this.opponentFlag = Tools.getOpponent(game).getBase();
        this.myLocation = getMe(game).getBase().getLocation();
        this.myFlag = getMe(game).getBase();
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

    public Location move(Game game) {
        NetworkEnhance<Location> tempMap = getMap();
        Iterator<Location> list = tempMap.iteratorShortestPath(getMyLocation(), findNearestEnemyBot(game, tempMap));
        if (findNearestEnemyBot(game, tempMap).equals(list.next())) {
            System.out.println("You are now blocking your opponent move!");
            return getMyLocation();
        }
        setMyLocation(list.next());
        return getMyLocation();
    }
    private Location findNearestEnemyBot(Game game, NetworkEnhance<Location> map) {
        Bot nearestEnemyBot = null;
        double shortestPath = Double.POSITIVE_INFINITY;
        for (Bot bot : getBots()) {
            if (bot.getOwner().equals(getMe(game)); {
                if (map.shortestPathWeight(getMyLocation(), bot.getLocation()) <= shortestPath) {
                    shortestPath = map.shortestPathWeight
                            (getMyLocation(), bot.getLocation());
                    nearestEnemyBot = bot;
                    System.out.println("Actual Shortest Path Weight: " + shortestPath +
                            "Bot: " + bot.getAlgorithm());
                }
            }
        }
        return nearestEnemyBot != null ? nearestEnemyBot.getLocation() : getOpponentFlag().getLocation();
    }
    @Override
    public String toString() {
        return "\nThis Algorithm tries to block the closest opponent bot. " +
                "The bot will pursuit the opponent bot the whole game. " +
                "The purpose of this bot is not to achieve the flag.";
    }
}
