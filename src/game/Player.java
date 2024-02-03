package game;

import collections.lists.UnorderedLinkedList;
import collections.lists.arrayLists.ArrayOrderedList;
import collections.queues.LinkedQueue;
import interfaces.IPlayer;
import menu.Tools;

import java.io.IOException;
import java.util.Iterator;

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
        this.bots = new UnorderedLinkedList<>();
        this.botsQueue = new LinkedQueue<>();
        this.base = new Flag(null);
        this.flag = this.base;
    }

    public LinkedQueue<Bot> getBotsQueue() {
        return botsQueue;
    }

    public Flag getFlag() {
        return flag;
    }

    public UnorderedLinkedList<Bot> getBots() {
        return bots;
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

    public void addBots() throws IOException {
        boolean addingBots = true;

        while (addingBots) {
            Bot bot = new Bot();
            whatBot();
            bots.addToRear(bot);
            System.out.println("Want to add another bot?");
            addingBots = Tools.getTrue();
        }
    }

    private Algorithm whatBot() throws IOException {
        Algorithm algorithm = new Algorithm();
        System.out.println(displayAlgorithm());
        switch (Tools.GetInt()) {
            default:
              //  if (botCheckIfPossible(AlgorithmType.SHORTEST_PATH))
                    algorithm.setType(AlgorithmType.SHORTEST_PATH);
                return algorithm;
            case 2:
              //  if (botCheckIfPossible(AlgorithmType.TRY_CATCH_ENEMY_PATH))
                    algorithm.setType(AlgorithmType.TRY_CATCH_ENEMY_PATH);
                return algorithm;
            case 3:
              //  if (botCheckIfPossible(AlgorithmType.RANDOM_PATH))
                    algorithm.setType(AlgorithmType.RANDOM_PATH);
                return algorithm;
            case 4:
               // if (botCheckIfPossible(AlgorithmType.MINIMUM_SPANNING_TREE_PATH))
                    algorithm.setType(AlgorithmType.MINIMUM_SPANNING_TREE_PATH);
                return algorithm;
        }
    }
/*
    private boolean botCheckIfPossible(AlgorithmType algorithmType) {
        int algorithmCanAppear = numberOfUsedAlgorithms() / 5; //number of algorithms i created;
        int occurrences = 0;

        Iterator<Bot> bots = getBots().iterator();

        while (bots.hasNext()) {
            if (bots.next().getAlgorithm().getType().equals(algorithmType))
                occurrences++;
        }
        if (occurrences > algorithmCanAppear) {
            System.out.println("Maximum number for this algorithm has been reached");
            return false;
        }
        return true;
    }
 */

    private int numberOfUsedAlgorithms() {
        return getBots().size() / 5;
    }

}
