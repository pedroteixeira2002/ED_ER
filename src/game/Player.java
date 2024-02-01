package game;

import collections.lists.arrayLists.ArrayOrderedList;
import interfaces.IPlayer;

public class Player implements IPlayer {
    private String name;
    private ArrayOrderedList<Bot> circularBots;
    private Flag flag;

    public Player(String name, Flag flag) {
        this.name = name;
        this.circularBots = new ArrayOrderedList<>();
        this.flag = flag;
    }
    public Player() {
        this.name = new String();
        this.circularBots = new ArrayOrderedList<>();
        this.flag = new Flag(null);
    }
    public ArrayOrderedList<Bot> getBots() {
        return circularBots;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Flag getFlag() {
        return flag;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }
}
