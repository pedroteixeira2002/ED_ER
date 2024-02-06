package game;

import collections.queues.LinkedQueue;
import interfaces.IPlayer;
import menu.Tools;

import java.io.IOException;

import static menu.Display.displayAlgorithm;

public class Player implements IPlayer {
    private String name;
    private final LinkedQueue<Bot> bots;
    private Location base;
    private Location flag;
    private int numberOfBots;

    public Player() {
        this.name = null;
        this.bots = new LinkedQueue<>();
        this.base = new Location(0, 0);
        this.flag = this.base;
        this.numberOfBots = 0;
    }

    public void setFlag(Location flag) {
        this.flag = flag;
    }

    public LinkedQueue<Bot> getBots() {
        return bots;
    }

    public int getNumberOfBots() {
        return numberOfBots;
    }

    public void setNumberOfBots(int numberOfBots) {
        this.numberOfBots = numberOfBots;
    }

    public Location getFlag() {
        return flag;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Location getBase() {
        return base;
    }

    public void setBase(Location base) {
        this.base = base;
    }

    public void addBots(Player player, int numBots) throws IOException {
        Bot bot1 = new Bot();
        Algorithm algorithm1 = new Algorithm();
        player.setNumberOfBots(numBots);
        //setting the first bot to always be Shortest Path
        algorithm1.setType(AlgorithmType.SHORTEST_PATH);
        //configuring bots
        bot1.setAlgorithm(algorithm1);
        bot1.setOwner(player);
        bot1.setLocation(bot1.getOwner().getBase());
        bot1.setHasFlag(true);
        //add bot to the players queue of bots
        player.bots.enqueue(bot1);

        for (int i = 1; i < numBots; i++) {
            Algorithm algorithm = new Algorithm();
            Bot bot = new Bot();
            // Define o algoritmo para cada bot(definindo tipo, owner e localizacao)
            algorithm.setType(whatAlgorithm().getType());
            //configuring the bot
            bot.setAlgorithm(algorithm);
            bot.setOwner(player);
            bot.setLocation(player.getBase());
            // adds the bot to the queue
            player.bots.enqueue(bot);

            System.out.println("Bot owner: " + bot.getOwner().getName());
        }
    }

    private Algorithm whatAlgorithm() throws IOException {
        Algorithm algorithm = new Algorithm();
        System.out.println(displayAlgorithm());
        switch (Tools.GetInt()) {
            default:
                algorithm.setType(AlgorithmType.TRY_CATCH_ENEMY_PATH);
                return algorithm;
            case 2:
                algorithm.setType(AlgorithmType.RANDOM_PATH);
                return algorithm;
            case 3:
                algorithm.setType(AlgorithmType.MINIMUM_SPANNING_TREE_PATH);
                return algorithm;
        }
    }


}