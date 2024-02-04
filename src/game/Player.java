package game;

import collections.lists.UnorderedLinkedList;
import collections.queues.LinkedQueue;
import interfaces.IPlayer;
import menu.Tools;

import java.io.IOException;

import static menu.Display.displayAlgorithm;

public class Player implements IPlayer {
    private String name;
    private final UnorderedLinkedList<Bot> bots;
    private final LinkedQueue<Bot> botsQueue;
    private Flag base;
    private Flag flag;
    private Bot actualBot;

    public Bot getActualBot() {
        return actualBot;
    }

    public void setActualBot(Bot actualBot) {
        this.actualBot = actualBot;
    }

    public Player(String name, Flag base) {
        this.name = name;
        this.bots = new UnorderedLinkedList<>();
        this.botsQueue = new LinkedQueue<>();
        this.base = base;
    }

    public Player() {
        this.name = null;
        this.botsQueue = new LinkedQueue<>();
        this.bots = new UnorderedLinkedList<>();
        this.base = new Flag(null);
        this.flag = this.base;
    }

    public LinkedQueue<Bot> getBotsQueue() {
        return botsQueue;
    }

    public Flag getFlag() {
        return flag;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Flag getBase() {
        return base;
    }

    public void setBase(Flag base) {
        this.base = base;
    }

    public UnorderedLinkedList<Bot> getBots() {
        return bots;
    }

    public void addBots(Player player) throws IOException {
        Bot bot = new Bot();
        Algorithm algorithm = new Algorithm();

        algorithm.setType(AlgorithmType.SHORTEST_PATH);

        bot.setAlgorithm(whatAlgorithm());
        botsQueue.enqueue(bot);
        bots.addToRear(bot);

        Bot bot1= new Bot();
        bot.setAlgorithm(whatAlgorithm());
        botsQueue.enqueue(bot1);
        bots.addToRear(bot1);
    }

    private Algorithm whatAlgorithm() throws IOException {
        Algorithm algorithm = new Algorithm();
        System.out.println(displayAlgorithm());
        switch (Tools.GetInt()) {
            default:
                algorithm.setType(AlgorithmType.TRY_CATCH_ENEMY_PATH);
                return algorithm;
            case 3:
                algorithm.setType(AlgorithmType.RANDOM_PATH);
                return algorithm;
            case 4:
                algorithm.setType(AlgorithmType.MINIMUM_SPANNING_TREE_PATH);
                return algorithm;
        }
    }
}