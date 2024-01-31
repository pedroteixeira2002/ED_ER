package move_algorithms;

import game.Game;
import game.Location;
import interfaces.IAlgorithm;
import structures.NetworkEnhance;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class RandomPath extends Algorithm implements IAlgorithm {
    Location location;

    public RandomPath(Game game) {
        super(game);

    }

    @Override
    public Location move(Game game) {

        NetworkEnhance<Location> newMap;
        newMap = botInTheWay(super.getMap());

        Location randomLocation = randomLocation(newMap);

        setMyLocation(randomLocation);

        if (getMyLocation().equals(getOpponentFlag())) {
            System.out.println("Winner:" + getMe(game));
        }

        return getMyLocation();
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

    @Override
    public String toString() {
        return "\nThis Algorithm don´t follow a particular set of rules. " +
                "\nThe bot will randomly move in the map, without the intention to achieve the flag." +
                "\nThis algorithm can be considered a defense method, since it can block the enemy moves." +
                " On the other hand, it can also block your moves." +
                " To Avoid this, try to set this bot as the last one";
    }

}
