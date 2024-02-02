package game;

import collections.lists.arrayLists.ArrayOrderedList;
import interfaces.IPlayer;
import menu.Tools;

import java.io.IOException;
import java.util.Iterator;

import static menu.Display.displayAlgorithm;

public class Player implements IPlayer {
    private String name;
    private ArrayOrderedList<Bot> bots;
    private Flag base;

    public Player(String name, Flag base) {
        this.name = name;
        this.bots = new ArrayOrderedList<>();
        this.base = base;
    }

    public Player() {
        this.name = new String();
        this.bots = new ArrayOrderedList<>();
        this.base = new Flag(null);
    }

    public ArrayOrderedList<Bot> getBots() {
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

    private void addBots() throws IOException {
        boolean addingBots = true;

        while (addingBots) {
            Bot bot = new Bot();
            whatBot();
            bots.add(bot);
            System.out.println("Want to add another bot?");
            if (!Tools.getTrue())
                addingBots = false;
        }
    }

    private Algorithm whatBot() throws IOException {
        Algorithm algorithm = new Algorithm();
        displayAlgorithm();
        switch (Tools.GetInt()) {
            default:
                if (botCheckIfPossible(AlgorithmType.SHORTEST_PATH))
                    algorithm.setType(AlgorithmType.SHORTEST_PATH);
                return algorithm;
            case 2:
                if (botCheckIfPossible(AlgorithmType.BLOCK_ENEMY_SHORTEST_PATH))
                    algorithm.setType(AlgorithmType.BLOCK_ENEMY_SHORTEST_PATH);
                return algorithm;
            case 3:
                if (botCheckIfPossible(AlgorithmType.BLOCK_CLOSEST_ENEMY_BOT))
                    algorithm.setType(AlgorithmType.BLOCK_CLOSEST_ENEMY_BOT);
                return algorithm;
            case 4:
                if (botCheckIfPossible(AlgorithmType.RANDOM_PATH))
                    algorithm.setType(AlgorithmType.RANDOM_PATH);
                return algorithm;
            case 5:
                if (botCheckIfPossible(AlgorithmType.MINIMUM_SPANNING_TREE_PATH))
                    algorithm.setType(AlgorithmType.MINIMUM_SPANNING_TREE_PATH);
                return algorithm;
        }
    }

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

    private int numberOfUsedAlgorithms() {
        if (getBots().size() > 5)
            return getBots().size()/5;
        else return 1;
    }

}
