package game;

import interfaces.IAlgorithm;
import structures.NetworkEnhance;

import java.util.Iterator;
import java.util.Random;

import static menu.Tools.getMe;

public class Algorithm implements IAlgorithm {
    private final NetworkEnhance<Location> map;
    private final Random random;
    private AlgorithmType type;

    public Algorithm(NetworkEnhance<Location> map) {
        this.map = map;
        this.random = new Random();
        this.type = null;
    }
    public Algorithm(){
        this.type =null;
        this.random= new Random();
        this.map= new NetworkEnhance<>();
    }

    public AlgorithmType getType() {
        return type;
    }

    public void setType(AlgorithmType type) {
        this.type = type;
    }

    @Override
    public Location move(Game game) {
        switch (getType()) {
            case SHORTEST_PATH:
                break;
            case BLOCK_ENEMY_SHORTEST_PATH:
                break;
            case BLOCK_CLOSEST_ENEMY_BOT:
                break;
            case RANDOM_PATH:
                break;
            case MINIMUM_SPANNING_TREE_PATH:
                break;
        }
    }
    public Location shortestPath(){
        NetworkEnhance<Location> newMap;

        newMap = botInTheWay(map);

        Iterator<Location> list = newMap.iteratorShortestPath
                (getMyLocation(),getOpponentFlag().getLocation());
        setMyLocation(list.next());

        double shortestPathWeight = newMap.shortestPathWeight
                (getMyLocation(), getOpponentFlag().getLocation());
        System.out.println("Actual Shortest Path Weight: " + shortestPathWeight);

        return getMyLocation();
    }
}
