package interfaces;

import game.Location;
import structures.NetworkEnhance;

public interface IMap {
    NetworkEnhance<Location> getGraphMap();

    NetworkEnhance<Location> generateMap(int numLocations, boolean bidirectional, double edgeDensity);

    void setId(int id);

    int getId();

}
