package interfaces;

import game.Location;
import game.Map;
import structures.NetworkEnhance;

public interface IMap {
    NetworkEnhance<Location> getGraphMap();

    Map generateMap(int numLocations, boolean bidirectional, double edgeDensity);

    void setId(int id);

    int getId();

}
