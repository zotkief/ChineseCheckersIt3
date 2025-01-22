package com.jkpr.chinesecheckers.server;

import java.util.*;

import com.jkpr.chinesecheckers.server.UI.GameOptions;
import com.jkpr.chinesecheckers.server.database.DataOperator;
import com.jkpr.chinesecheckers.server.database.DatabaseManager;
import com.jkpr.chinesecheckers.server.gamelogic.*;
import com.jkpr.chinesecheckers.server.gamelogic.builders.CCBuilder;
import com.jkpr.chinesecheckers.server.gamelogic.builders.Director;
import com.jkpr.chinesecheckers.server.gamelogic.builders.FastPacedBuilder;
import com.jkpr.chinesecheckers.server.gamelogic.builders.YYBuilder;
import com.jkpr.chinesecheckers.server.message.*;
import com.jkpr.chinesecheckers.server.sessionState.Prepare;
import com.jkpr.chinesecheckers.server.sessionState.SessionBehavior;
import com.jkpr.chinesecheckers.server.sessionState.SessionState;

/**
 * The {@code LoadSession} class is responsible for initializing and managing a game session.
 * It adapts the game logic to the client-server communication, including adding players,
 * processing moves, and broadcasting updates to all connected clients.
 */
public class LoadSession implements Session {
    private SessionBehavior state = new Prepare();

    /** List of connected clients (players). */
    private List<PlayerHandler> clients = new ArrayList<>();

    /** Mapping between client handlers and their respective players in the game. */
    private HashMap<PlayerHandler, Player> clientHandlerPlayerHashMap;

    /** The game instance for managing game logic and state. */
    private Game game;
    private DatabaseManager databaseManager;

    /**
     * Constructs a {@code LoadSession} object that creates a new game based on the provided game options,
     * and initializes the mapping between clients and players.
     *
     * @param players Array of client handlers representing the players.
     * @param server The server managing the game session.
     * @param options The game options selected by the server or players.
     */
    public LoadSession(ClientHandler[] players, Server server, GameOptions options) {
        int numberOfPlayers = Integer.parseInt(options.getPlayerCount());

        // Initialize database manager and set the game ID for history retrieval
        databaseManager = new DatabaseManager(DataOperator.jdbcTemplate());
        databaseManager.setGameId(options.getGameId());

        // Create a game based on the selected game type
        switch (options.getGameType()) {
            case "Fast Paced":
                game = Director.createGame(new FastPacedBuilder(numberOfPlayers));
                break;
            case "Yin and Yang":
                game = Director.createGame(new YYBuilder());
                break;
            default:
                game = Director.createGame(new CCBuilder(numberOfPlayers));
        }

        game.generate();
        clientHandlerPlayerHashMap = new HashMap<>();

        // Add players and assign game adapters
        for (ClientHandler handler : players) {
            this.clients.add(handler);
        }

        // Map each client handler to a player and send the generated message to each client
        for (ClientHandler clientHandler : players) {
            addPlayer(clientHandler);
            clientHandler.assignGameAdapter(this);

            int id = clientHandlerPlayerHashMap.get(clientHandler).getId();
            GenMessage message = new GenMessage(game.getGenMessage() + id);
            clientHandler.sendMessage(message);
        }

        // Add bots if necessary
        for (int i = players.length; i < numberOfPlayers; i++) {
            Player player = game.join();
            Bot bot = new Bot(player, game, this);
            clientHandlerPlayerHashMap.put(bot, player);
            clients.add(bot);
        }

        // Retrieve game moves from the database and send updates to clients
        List<String> list = databaseManager.getMoves();
        UpdateMessage updateMessage;
        for (int i = 1; i < list.size(); i++) {
            String[] parts = list.get(i).split(" ");
            int id;
            switch (parts[1]) {
                case "FAIL":
                    break;
                case "SKIP":
                    id = Integer.parseInt(parts[0]);
                    updateMessage = game.nextMove(new MoveMessage(), game.getPlayer(id));
                    sendMessage(updateMessage);
                    break;
                default:
                    id = Integer.parseInt(parts[0]);
                    int x1 = Integer.parseInt(parts[1]);
                    int y1 = Integer.parseInt(parts[2]);
                    int x2 = Integer.parseInt(parts[3]);
                    int y2 = Integer.parseInt(parts[4]);
                    updateMessage = game.nextMove(new MoveMessage(x1, y1, x2, y2), game.getPlayer(id));
                    sendMessage(updateMessage);
                    break;
            }
            System.out.println("123");
        }
        System.out.println("456");
        setReady();
    }

    /**
     * Adds a new player to the game and maps the client handler to the player.
     *
     * @param clientHandler The client handler representing the player to be added.
     */
    public void addPlayer(ClientHandler clientHandler) {
        clientHandlerPlayerHashMap.put(clientHandler, game.join());
    }

    /**
     * Broadcasts the result of a player's move to all connected clients.
     *
     * @param moveMessage The move message containing the player's move.
     * @param clientHandler The client handler representing the player who made the move.
     */
    @Override
    public void broadcastMessage(MoveMessage moveMessage, PlayerHandler clientHandler) {

        // Record the move if the session is ready
        if (!isReady())
            return;
        UpdateMessage updateMessage = game.nextMove(moveMessage, clientHandlerPlayerHashMap.get(clientHandler));
        sendMessage(updateMessage);

        // Check if the game has ended and record the move
        String[] parts = updateMessage.content.split(" ");
        if (parts[parts.length - 1].equals("END")) {
            databaseManager.endGame(Integer.parseInt(parts[parts.length - 2]));
        }

        databaseManager.recordMove(clientHandlerPlayerHashMap.get(clientHandler).getId()
                + " " + updateMessage.content);
    }

    /**
     * Sends an update message to all connected clients.
     *
     * @param updateMessage The update message to send to the clients.
     */
    private void sendMessage(UpdateMessage updateMessage) {
        for (PlayerHandler player : clients) {
            player.sendMessage(updateMessage);
        }
    }

    /**
     * Sets the session state to ready, indicating that the game is ready to begin.
     */
    public void setReady() {
        state = state.setReady();
    }

    /**
     * Sets the session state to prepare, indicating that the game is in a preparation phase.
     */
    public void setPrepare() {
        state = state.setPrepare();
    }
    public boolean isReady() {return state.getState().equals(SessionState.READY);}
}
