package move_algorithms;

import game.Game;
import game.Location;
import interfaces.IAlgorithm;
import structures.NetworkEnhance;

import java.util.Iterator;

public class ShortestPath extends Algorithm implements IAlgorithm {

    public ShortestPath(Game game) {
        super(game);
    }

    @Override
    public Location move(Game game) {

        NetworkEnhance<Location> newMap;

        newMap = botInTheWay(super.getMap());

        Iterator<Location> list = newMap.iteratorShortestPath
                (super.getMyLocation(), super.getOpponentFlag().getLocation());
        setMyLocation(list.next());

        double shortestPathWeight = newMap.shortestPathWeight
                (super.getMyLocation(), super.getOpponentFlag().getLocation());
        System.out.println("Actual Shortest Path Weight: " + shortestPathWeight);

        return getMyLocation();
    }

    @Override
    public String toString() {
        return "This Algorithm uses the shortest path to the flag. " +
                "The bot will use the shortest path to the flag and move there.";
    }
}

