package game;

import collections.lists.UnorderedLinkedList;
import collections.queues.LinkedQueue;
import interfaces.IAlgorithm;
import structures.NetworkEnhance;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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
            System.out.println("Bot:" + bot.getAlgorithm().getType() +
                    "\t\tLocation: X:" + bot.getLocation().getPosX() +
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

    public Location randomPath(Bot bot, Game game) {

        Location randomLocation = randomLocation(game);

        bot.setLocation(randomLocation);

        flagInTheWay(bot, game);
        if (bot.getLocation().equals(getOpponent(bot, game).getBase())) {
            System.out.println("Winner:" + bot.getOwner());
        }
        checkVictory(bot, game);

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
        Player enemy = getOpponent(bot, game);
        Location enemyFlag = enemy.getFlag();
        Iterator<Location> path = game.getMap().getGraphMap().iteratorShortestPath(bot.getLocation(), enemyFlag);

        bot.setLocation(path.next());

        flagInTheWay(bot, game);

        checkVictory(bot, game);

        return bot.getLocation();

    }

    public Location minimumSpanningTreePath(Bot bot, Game game) {

        // Iterador que percorre os vértices da MST
        Iterator<Location> list = iteratorMST(game);

        // Atualizar a localização do bot
        bot.setLocation(list.next());

        flagInTheWay(bot, game);

        checkVictory(bot, game);

        return bot.getLocation();

    }

    public Iterator<Location> iteratorMST(Game game) {
        NetworkEnhance<Location> mstNetwork = (NetworkEnhance<Location>) game.getMap().getGraphMap().mstNetwork();
        return mstNetwork.iteratorDFS(mstNetwork.getVertex(0));
        //se noa funcionar o erro está aqui, n pode ser zero
    }

    /**
     * If my location is the same of the enemy flag location, sends the enemy flag to the base
     * If I have the flag and other enemy bot is in my location, my flag returns to the base
     *
     * @param bot
     * @param game
     **/
    public void flagInTheWay(Bot bot, Game game) {

        Iterator<Bot> bots = allBots(bot, game);

        if (bot.getLocation().
                equals(getOpponent(bot, game).getFlag()))//mandar a flag do enimigo para a base
            getOpponent(bot, game).setFlag(getOpponent(bot, game).getBase());
        if (bot.getLocation().
                equals(bot.getOwner().getFlag())) {//se eu tiver a flag
            while (bots.hasNext()) {
                if (bots.next().getLocation().
                        equals(bot.getLocation())) {//itera por todos os bots no mape se alguma das suas localizações for igual à minha

                    bot.getOwner().setFlag(bot.getOwner().getBase());//envia a minha flag par a minha base
                }
            }
        }
    }

    /**
     * @param game
     * @return an iterator with all the bots playing
     */
    private Iterator<Bot> allBots(Bot bot, Game game) {
        int round = 0;
        UnorderedLinkedList<Bot> bots = new UnorderedLinkedList<>();

        // Access bots owned by the player associated with the provided bot
        LinkedQueue<Bot> playerBotsQueue = bot.getOwner().getBots();
        while (round < bot.getOwner().getNumberOfBots()) {
            Bot playerBot = playerBotsQueue.dequeue();
            bots.addToRear(playerBot);
            playerBotsQueue.enqueue(bot);
            round++;
        }

        round = 0;
        // Access bots owned by the opponent through the game object
        Player opponent = getOpponent(bot, game);
        LinkedQueue<Bot> opponentBotsQueue = opponent.getBots();
        while (round < opponent.getNumberOfBots()) {
            Bot opponentBot = opponentBotsQueue.dequeue();
            bots.addToRear(opponentBot);
            opponentBotsQueue.enqueue(opponentBot);
            round++;
        }

        // Return an iterator for the UnorderedLinkedList
        return bots.iterator();
    }

    private void checkVictory(Bot bot, Game game) {
        if (bot.getLocation().equals(getOpponent(bot, game).getBase())) {
            System.out.println(bot.getOwner().getName() + " is the Winner!");
            bot.setLocation(new Location(1000, 1000));
        }
    }

    private Player getOpponent(Bot bot, Game game) {
        Player myself = bot.getOwner();
        Player opponent = game.getPlayers().dequeue();
        game.getPlayers().enqueue(opponent);

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

        flagInTheWay(bot, game);

        checkVictory(bot, game);
    }

    @Override
    public String toString() {
        return "Algorithm{" +
                "type=" + type +
                '}';
    }
}
