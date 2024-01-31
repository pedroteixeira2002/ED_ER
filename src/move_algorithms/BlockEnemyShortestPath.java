package move_algorithms;

import game.Game;
import game.Location;
import interfaces.IAlgorithm;
import structures.NetworkEnhance;

import java.util.Iterator;

public class BlockEnemyShortestPath extends Algorithm implements IAlgorithm {
    public BlockEnemyShortestPath(Game game) {
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
                (super.getOpponentFlag().getLocation(), super.getMyLocation());
        System.out.println("Actual opponent shortest path weight: " + shortestPathWeight);


        return getMyLocation();
    }
}
