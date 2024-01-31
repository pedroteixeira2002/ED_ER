package move_algorithms;

import game.Bot;
import game.Game;
import game.Location;
import interfaces.IAlgorithm;
import structures.NetworkEnhance;

import java.util.Iterator;

public class BlockClosestEnemyBot extends Algorithm implements IAlgorithm {

    public BlockClosestEnemyBot(Game game) {
        super(game);
    }


    @Override
    public Location move(Game game) {
        NetworkEnhance<Location> tempMap = super.getMap();
        Iterator<Location> list = tempMap.iteratorShortestPath(super.getMyLocation(), findNearestEnemyBot(game, tempMap));
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
            if (bot.getOwner().equals(getOpponent(game))) {
                if (map.shortestPathWeight(super.getMyLocation(), bot.getLocation()) <= shortestPath) {
                    shortestPath = map.shortestPathWeight
                            (super.getMyLocation(), bot.getLocation());
                    nearestEnemyBot = bot;
                    System.out.println("Actual Shortest Path Weight: " + shortestPath +
                            "Bot: " + bot.getAlgorithm());
                }
            }
        }
        return nearestEnemyBot != null ? nearestEnemyBot.getLocation() : super.getOpponentFlag().getLocation();
    }

    @Override
    public String toString() {
        return "\nThis Algorithm tries to block the closest opponent bot. " +
                "The bot will pursuit the opponent bot the whole game. " +
                "The purpose of this bot is not to achieve the flag.";
    }
}
