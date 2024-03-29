package game;

import collections.lists.UnorderedLinkedList;
import interfaces.IAlgorithm;
import structures.NetworkEnhance;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Class for the algorithm
 */
public class Algorithm implements IAlgorithm {
    /** Random object */
    private final Random random;
    /** The type of the algorithm */
    private AlgorithmType type;

    /**
     * Constructor for the algorithm
     */
    public Algorithm() {
        this.type = null;
        this.random = new Random();
    }

    /**
     * Getter for the type
     * @return
     */
    public AlgorithmType getType() {
        return type;
    }

    /**
     * Setter for the type
     * @param type
     */
    public void setType(AlgorithmType type) {
        this.type = type;
    }

    /**
     * Move the bot according to the algorithm
     * @param bot
     * @param game
     * @return the location of the bot
     */
    public Location move(Bot bot, Game game) {
        switch (bot.getAlgorithm().getType()) {
            default:
                return shortestPath(bot, game);
            case TRY_CATCH_ENEMY_PATH:
                return tryCatchEnemyFlag(bot, game);
            case RANDOM_PATH:
                return randomPath(bot, game);
            case MINIMUM_SPANNING_TREE_PATH:
                return minimumSpanningTreePath(bot, game);
        }
    }

    /**
     * Move the bot according to the shortest path algorithm
     * @param bot
     * @param game
     * @return the location of the bot
     */
    public Location shortestPath(Bot bot, Game game) {

        if (bot.hasFlag()) {
            //iterador para saber a minha próxima localização
            Iterator<Location> list = game.getMap().getGraphMap().iteratorShortestPath
                    (bot.getLocation(), getOpponent(bot, game).getBase());
            list.next();
            Location nextPosition = list.next();
            //atualizar a minha localização
            bot.setLocation(nextPosition);
            bot.getOwner().setFlag(bot.getLocation());
            System.out.println(bot.getOwner().getName() + ", your bot " + bot.getAlgorithm().getType() +
                    " just moved to Location: X:" + bot.getLocation().getPosX() +
                    "\tY:" + bot.getLocation().getPosY());

            //print do peso do caminho mais curto
            double shortestPathWeight = game.getMap().getGraphMap().shortestPathWeight
                    (bot.getLocation(), getOpponent(bot, game).getBase());
            System.out.println("Actual Shortest Path Weight: " + shortestPathWeight);


            flagInTheWay(bot, game);

            checkVictory(bot, game);
        } else {
            //no caso de ter perdido a flag
            goGetFlag(bot, game);
        }
        return bot.getLocation();
    }

    /**
     * Move the bot according to the random path algorithm
     * @param bot
     * @param game
     * @return the location of the bot
     */
    public Location randomPath(Bot bot, Game game) {

        Location randomLocation = randomLocation(game);

        bot.setLocation(randomLocation);

        System.out.println(bot.getOwner().getName() + ", your bot " + bot.getAlgorithm().getType() +
                " just moved to Location: X:" + bot.getLocation().getPosX() +
                "\tY:" + bot.getLocation().getPosY());

        flagInTheWay(bot, game);

        checkVictory(bot, game);

        return bot.getLocation();

    }

    /**
     * This method returns a random location in the map
     * @param game
     * @return a random location
     */
    private Location randomLocation(Game game) {
        Object[] vertices = game.getMap().getGraphMap().getVertices();
        if (vertices == null || vertices.length == 0)
            throw new RuntimeException("There are no locations in the map");

        List<Location> verticesList = Arrays.asList(Arrays.copyOf(vertices, vertices.length, Location[].class));
        Iterator<Location> iterator = game.getMap().getGraphMap().iteratorDFS(verticesList.get(0));

        int steps = random.nextInt(verticesList.size());

        for (int i = 0; i < steps && iterator.hasNext(); i++) {
            iterator.next();
        }
        return iterator.hasNext() ? iterator.next() : verticesList.get(0);
    }

    /**
     * Move the bot according to the try catch enemy path algorithm
     * @param bot
     * @param game
     * @return the location of the bot
     */
    public Location tryCatchEnemyFlag(Bot bot, Game game) {
        Player enemy = getOpponent(bot, game);
        Location enemyFlag = enemy.getFlag();
        Iterator<Location> path = game.getMap().getGraphMap().iteratorShortestPath(bot.getLocation(), enemyFlag);

        bot.setLocation(path.next());
        System.out.println(bot.getOwner().getName() + ", your bot " + bot.getAlgorithm().getType() +
                " just moved to Location: X:" + bot.getLocation().getPosX() +
                "\tY:" + bot.getLocation().getPosY());

        flagInTheWay(bot, game);

        checkVictory(bot, game);

        return bot.getLocation();

    }

    /**
     * Move the bot according to the minimum spanning tree path algorithm
     * @param bot
     * @param game
     * @return the location of the bot
     */
    public Location minimumSpanningTreePath(Bot bot, Game game) {
        // Iterador que percorre os vértices da MST
        Iterator<Location> list = iteratorMST(bot ,game);

        Location nextLocation = list.next();

        // Atualiza a localização do bot
        bot.setLocation(nextLocation);

        flagInTheWay(bot, game);

        return bot.getLocation();
    }

    /**
     * This method returns an iterator that iterates over the vertices of the minimum spanning tree
     * @param bot
     * @param game
     * @return
     */
    public Iterator<Location> iteratorMST(Bot bot, Game game) {
        NetworkEnhance<Location> mstNetwork = (NetworkEnhance<Location>) game.getMap().getGraphMap().mstNetwork();

        int botLocationX = bot.getLocation().getPosX();
        int botLocationY = bot.getLocation().getPosY();

        // Encontre o índice correspondente no grafo
        int initialLocationIndex = findLocationIndex(mstNetwork, botLocationX, botLocationY);

        return mstNetwork.iteratorBFS(mstNetwork.getVertex(initialLocationIndex));
    }

    /**
     * This method finds the index of the bot location in the graph
     * @param mstNetwork
     * @param botLocationX
     * @param botLocationY
     * @return
     */
    private int findLocationIndex(NetworkEnhance<Location> mstNetwork, int botLocationX, int botLocationY) {
        Location[] vertices = mstNetwork.getVertices();
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i].getPosX() == botLocationX && vertices[i].getPosY() == botLocationY) {
                return i;
            }
        }
        return -1;
    }

    /**
     * If my location is the same of the enemy flag location, sends the enemy flag to the base
     * If I have the flag and other enemy bot is in my location, my flag returns to the base
     * If my location is the same of the enemy flag location, sends the enemy flag to the base
     *
     * @param bot
     * @param game
     **/
    public void flagInTheWay(Bot bot, Game game) {

        Iterator<Bot> bots = allBots(bot, game);

        while (bots.hasNext()) {

            Bot botTmp = bots.next();//itera por todos os bots no mape

            if (botTmp.getLocation().equals(bot.getLocation()) //se a localização do bot temporário for igual à localização do meu bot
                    && botTmp.getOwner().equals(getOpponent(bot, game)) //e o bot temporário for do inimigo
                    && bot.getAlgorithm().getType().equals(AlgorithmType.SHORTEST_PATH)) {//se o meu bot for do tipo Shortest Path
                //envia a minha flag par a minha base
                bot.getOwner().setFlag(bot.getOwner().getBase());
                System.out.println("The flag of " + bot.getOwner().getName() + " was sent to the base");
                bot.setHasFlag(false);
            }

            if (botTmp.getOwner().equals(getOpponent(bot, game))//se o bot temporário for do inimigo
                    && botTmp.getAlgorithm().getType().equals(AlgorithmType.SHORTEST_PATH)// e se o bot temporário for do tipo Shortest Path
                    && bot.getLocation().equals(getOpponent(bot, game).getFlag())) {//se a minha localização for igual à flag do inimigo
                //mandar a flag do enimigo para a base
                getOpponent(bot, game).setFlag(getOpponent(bot, game).getBase());
                System.out.println("The flag of " + getOpponent(bot, game).getName() + " was sent to the base");
                botTmp.setHasFlag(false);
            }
        }
    }

    /**
     * This method returns an iterator with all the bots playing
     * @param game
     * @return an iterator with all the bots playing
     */
    private Iterator<Bot> allBots(Bot bot, Game game) {
        int round = 0;
        UnorderedLinkedList<Bot> bots = new UnorderedLinkedList<>();

        while (round < bot.getOwner().getNumberOfBots()) {
            Bot playerBot = bot.getOwner().getBots().dequeue();
            bot.getOwner().getBots().enqueue(playerBot);
            bots.addToRear(playerBot);
            round++;
        }

        round = 0;
        // Access bots owned by the opponent through the game object
        Player opponent = getOpponent(bot, game);
        while (round < opponent.getNumberOfBots()) {
            Bot opponentBot = opponent.getBots().dequeue();
            opponent.getBots().enqueue(opponentBot);
            bots.addToRear(opponentBot);
            round++;
        }

        // Return an iterator for the UnorderedLinkedList
        return bots.iterator();
    }

    /**
     * This method checks if the bot is in the base of the opponent
     * @param bot
     * @param game
     */
    private void checkVictory(Bot bot, Game game) {
        if (bot.getLocation().equals(getOpponent(bot, game).getBase())) {
            System.out.println(bot.getOwner().getName() + " is the Winner!");
            bot.setLocation(new Location(1000, 1000));
        }
    }

    /**
     * This method returns the opponent of the bot
     * @param bot
     * @param game
     * @return
     */
    private Player getOpponent(Bot bot, Game game) {
        Player myself = bot.getOwner();
        Player opponent = game.getPlayers().dequeue();
        game.getPlayers().enqueue(opponent);

        //return to the same position
        Player temp = game.getPlayers().dequeue();
        game.getPlayers().enqueue(temp);


        if (myself.equals(opponent)) {
            opponent = game.getPlayers().dequeue();
            game.getPlayers().enqueue(opponent);
            return opponent;
        }
        return opponent;
    }

    /**
     * If the bot loss the flag, he returns to the base to get it back
     *
     * @param bot
     * @param game
     */
    public void goGetFlag(Bot bot, Game game) {

        Iterator<Location> list = game.getMap().getGraphMap().iteratorShortestPath
                (bot.getLocation(), bot.getOwner().getFlag());

        //atualizar a minha localização
        bot.setLocation(list.next());
        System.out.println(bot.getOwner().getName() + ", your bot " + bot.getAlgorithm().getType() +
                " just moved to Location: X:" + bot.getLocation().getPosX() +
                "\tY:" + bot.getLocation().getPosY());

        flagInTheWay(bot, game);

        checkVictory(bot, game);
    }

    /**
     * To String method
     * @return
     */
    @Override
    public String toString() {
        return "Algorithm{" +
                "type=" + type +
                '}';
    }
}
