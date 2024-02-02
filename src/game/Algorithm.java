package game;

import collections.lists.arrayLists.ArrayOrderedList;
import interfaces.IAlgorithm;
import structures.NetworkEnhance;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static menu.Tools.getMe;
import static menu.Tools.getOpponent;

public class Algorithm implements IAlgorithm {
    private final Random random;
    private AlgorithmType type;

    public Algorithm() {
        this.type = null;
        this.random = new Random();
    }

    public AlgorithmType getType() {
        return type;
    }

    public void setType(AlgorithmType type) {
        this.type = type;
    }

    public Location move(Bot bot, Game game) {
        switch (getType()) {
            default:
                return shortestPath(bot, game);
            case TRY_CATCH_ENEMY_PATH:
                return tryCatchEnemyFlag(bot, game);
            case RANDOM_PATH:
                return randomPath(bot, game);
            case MINIMUM_SPANNING_TREE_PATH:
                return minumumSpanningTreePath(bot, game);
        }
    }

    public Location shortestPath(Bot bot, Game game) {

        //iterador para saber a minha próxima localização
        Iterator<Location> list = game.getMap().getGraphMap().iteratorShortestPath
                (bot.getLocation(), getOpponent(game).getBase().getLocation());

        //atualizar a minha localização
        bot.setLocation(list.next());

        //print do peso do caminho mais curto
        double shortestPathWeight = game.getMap().getGraphMap().shortestPathWeight
                (bot.getLocation(), getOpponent(game).getBase().getLocation());
        System.out.println("Actual Shortest Path Weight: " + shortestPathWeight);


        flagInTheWay(bot, game);

        return bot.getLocation();
    }

    public Location randomPath(Bot bot, Game game) {

        Location randomLocation = randomLocation(game);

        bot.setLocation(randomLocation);


        flagInTheWay(bot, game);
        if (bot.getLocation().equals(getOpponent(game).getBase())) {
            System.out.println("Winner:" + getMe(game));
        }
        return bot.getLocation();
    }

    private Location randomLocation(Game game) {
        Object[] vertices = game.getMap().getGraphMap().getVertices();
        if (vertices == null || vertices.length == 0)
            throw new RuntimeException("There are no locations in the map");

        List<Location> verticesList = Arrays.asList(Arrays.copyOf(vertices, vertices.length, Location[].class));
        Iterator<Location> vList = game.getMap().getGraphMap().iteratorDFS(game.getMap().getGraphMap().getVertex(0));
        Iterator<Location> iterator = game.getMap().getGraphMap().iteratorDFS(verticesList.get(0));

        int steps = random.nextInt(verticesList.size());

        for (int i = 0; i < steps && iterator.hasNext(); i++) {
            iterator.next();
        }
        return iterator.hasNext() ? iterator.next() : verticesList.get(0);
    }

    public Location tryCatchEnemyFlag(Bot bot, Game game) {

    }

    public Location minumumSpanningTreePath(Bot bot, Game game) {

    }
    public Iterator<Location> iteratorMST(Game game) {
        NetworkEnhance<Location> mstNetwork = (NetworkEnhance<Location>) game.getMap().getGraphMap().mstNetwork();
        return mstNetwork.iteratorDFS(mstNetwork.getVertex(0));
    }

    /**
     * If my location is the same of the enemy flag location, sends the enemy flag to the base
     * If I have the flag and other enemy bot is in my location, my flag returns to the base
     *
     * @param bot
     * @param game
     **/
    public void flagInTheWay(Bot bot, Game game) {
        Iterator<Bot> bots = allBots(game);

        if (bot.getLocation().
                equals(getOpponent(game).getFlag().getLocation()))//mandar a flag do enimigo para a base
            getOpponent(game).getFlag().
                    setLocation(getOpponent(game).getBase().getLocation());
        if (bot.getLocation().
                equals(getMe(game).getFlag())) {//se eu tiver a flag
            while (bots.hasNext()) {
                if (bots.next().getLocation().
                        equals(bot.getLocation())) {//itera por todos os bots no mape se alguma das suas localizações for igual à minha
                    getMe(game).getFlag().
                            setLocation(getMe(game).getBase().getLocation());//envia a minha flag par a minha base
                }
            }
        }
    }

    /**
     * @param game
     * @return an iterator with all the bots playing
     */
    private Iterator<Bot> allBots(Game game) {
        ArrayOrderedList<Bot> bots = new ArrayOrderedList<>();

        Iterator<Bot> myBots = getMe(game).getBots().iterator();
        Iterator<Bot> enemyBots = getOpponent(game).getBots().iterator();

        while (myBots.hasNext()) {
            bots.add(myBots.next());
        }
        while (enemyBots.hasNext()) {
            bots.add(enemyBots.next());
        }

        return bots.iterator();
    }

}
