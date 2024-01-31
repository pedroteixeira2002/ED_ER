package game;

import collections.lists.OrderedLinkedList;
import interfaces.IPlayer;

public class Player implements IPlayer {
    private String name;
    private OrderedLinkedList<Bot> listBots;
    private Flag flag;

    public Player(String name, Flag flag) {
        this.name = name;
        this.listBots = new OrderedLinkedList<>();
        this.flag = flag;
    }

    public Player() {
        this.name = new String();
        this.listBots = new OrderedLinkedList<>();
        this.flag = null;
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

    public OrderedLinkedList<Bot> getListBots() {
        return listBots;
    }
}
