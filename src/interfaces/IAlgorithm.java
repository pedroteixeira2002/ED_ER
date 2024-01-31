package interfaces;

import game.Game;
import game.Location;
import structures.NetworkEnhance;

public interface IAlgorithm {

    Location move(Game game);
    public NetworkEnhance<Location> botInTheWay(NetworkEnhance<Location> map);


}
