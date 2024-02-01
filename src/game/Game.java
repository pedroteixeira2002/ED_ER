package game;

import interfaces.IGame;
import menu.ReadInfo;
import menu.Tools;
import move_algorithms.BlockClosestEnemyBot;
import move_algorithms.BlockEnemyShortestPath;
import move_algorithms.RandomPath;
import move_algorithms.ShortestPath;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

public class Game implements IGame {
    private Map map;
    private Player player1;
    private Player player2;
    private int round;

    public Game(Map map, Player player1, Player player2) {
        this.map = map;
        this.player1 = player1;
        this.player2 = player2;
        this.round = 0;
    }

    public Game() {
        this.map = new Map();
        this.player1 = new Player();
        this.player2 = new Player();
        this.round = 1;
    }

    /**
     * Start a new game
     */
    @Override
    public void start(Game game) throws IOException {
        addBots(this);
        Iterator<Bot> firstBots = player1.getListBots().iterator();
        Iterator<Bot> secondBots = player2.getListBots().iterator();
        player1.getCircularBots().first();
        do {
            firstBots.next().setLocation(firstBots.next().getAlgorithm().move(game));
            secondBots.next().setLocation(secondBots.next().getAlgorithm().move(game));

            if (!(firstBots.hasNext() || secondBots.hasNext())) {
//                firstBots.next() = firstBots.next().getOwner().getListBots().head.getElement();
            }
        } while (firstBots.next().getLocation().equals(player2.getFlag()) ||
                secondBots.next().getLocation().equals(player1.getFlag()));


    }

    public void round(Game game) {

        round++;
    }

    private void addBots(Game game) throws IOException {
        boolean addingBots = true;
        while (addingBots) {
            addBot(game, game.player1);
            addBot(game, game.player2);
            System.out.println("Want to add another bot?");
            if (!Tools.getTrue())
                addingBots = false;
        }
    }

    private void addBot(Game game, Player player) throws IOException {
        boolean canAddBot;
        do {
            canAddBot = whatBot(game, player);
        } while (canAddBot);
    }

    private boolean whatBot(Game game, Player player) throws IOException {
        int algorithmCanAppear = player.getListBots().size() / 4; //number of algorithms i created;
        int occurrences = 0;
        System.out.println("\nWhat Type of Bot you want to add?");
        System.out.println(
                "\n\t1.Shortest Path" +
                        "\n\t2.BlockEnemyShortestPath" +
                        "\n\t3.BlockClosestEnemy" +
                        "\n\t4.RandomPath");

        //if (bots.next().getAlgorithm().equals(bot.getAlgorithm())) {
        if (player.getCircularBots().first().getAlgorithm().equals()
            occurrences++;

        if (occurrences == algorithmCanAppear)
            return false;


        player.getCircularBots().enqueue(ReadInfo.newBot(game));
        return true;

    }

    private boolean canAddBot(Player player) {

        int algorithmCanAppear = player.getListBots().size() / 4; //number of algorithms i created;

        int occurrencesShortestPath = 0;
        int occurrencesBlockShortestPath = 0;
        int occurrencesBlockClosestEnemyBot = 0;
        int occurrencesRandomPath = 0;

        Iterator<Bot> bots = player.getListBots().iterator();

        if (bots.next().getAlgorithm() instanceof RandomPath)
            occurrencesRandomPath++;
        else if (bots.next().getAlgorithm() instanceof ShortestPath)
            occurrencesShortestPath++;
        else if (bots.next().getAlgorithm() instanceof BlockClosestEnemyBot)
            occurrencesBlockClosestEnemyBot++;
        else if (bots.next().getAlgorithm() instanceof BlockEnemyShortestPath)
            occurrencesBlockShortestPath++;
        if (occurrencesRandomPath == algorithmCanAppear ||
                occurrencesShortestPath == algorithmCanAppear ||
                occurrencesBlockShortestPath == algorithmCanAppear ||
                occurrencesBlockClosestEnemyBot == algorithmCanAppear) {
            return false;
        }
        return true;

    }

    /**
     * Set the flag of the player in our base of the map
     * @param player
     */
    public void setPlayerFlagInMap(Player player) {
        int xCoordinate = ReadInfo.readCoordinateX();
        int yCoordinate = ReadInfo.readCoordinateY();

        System.out.println("Available locations: ");
        System.out.println(Arrays.toString(map.getGraphMap().getVertices()));
        System.out.println("Choose a location for your flag");
        // Acho que é preciso associar ao mapa quando inseres as coordenadas ou então não e estou a fazer confusão
        player.getFlag().getLocation().setPosX(xCoordinate);
        player.getFlag().getLocation().setPosY(yCoordinate);
        System.out.println(player.getName() + "'s flag was set at the following location X:" + xCoordinate + " Y:"
                + yCoordinate);

    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public int getRound() {
        return round;
    }


}
