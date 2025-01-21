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

/**
 * The {@code GameAdapter} class is responsible for adapting the game logic to the client-server communication.
 * It manages the game state, processes moves, and broadcasts updates to all connected players.
 */
public class GameSession implements Session{

    /** List of connected clients (players). */
    private List<PlayerHandler> clients=new ArrayList<>();

    /** Mapping between client handlers and their respective players in the game. */
    private HashMap<PlayerHandler, Player> clientHandlerPlayerHashMap;

    /** The game instance for managing game logic and state. */
    private Game game;
    private DatabaseManager databaseManager;

    /**
     * Constructs a {@code GameAdapter} object that creates a new game based on the provided game options,
     * and initializes the mapping between clients and players.
     *
     * @param players Array of client handlers representing the players.
     * @param server  The server managing the game session.
     * @param options The game options selected by the server or players.
     */
    public GameSession(ClientHandler[] players, Server server, GameOptions options) {
        int numberOfPlayers=Integer.parseInt(options.getPlayerCount());
        int numberOfBots=Integer.parseInt(options.getBotCount());


        databaseManager=new DatabaseManager(DataOperator.jdbcTemplate());
        databaseManager.createGame(options.getGameType(),numberOfPlayers,numberOfBots);


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

        for(ClientHandler handler : players)
        {
            this.clients.add(handler);
        }

        for (ClientHandler clientHandler : players) {
            addPlayer(clientHandler);
            clientHandler.assignGameAdapter(this);

            int id = clientHandlerPlayerHashMap.get(clientHandler).getId();
            GenMessage message = new GenMessage(game.getGenMessage() + id);
            clientHandler.sendMessage(message);
        }
        databaseManager.recordMove("-1 "+game.getGenMessage() + "0");
        for (int i=players.length;i<numberOfPlayers;i++) {
            Player player = game.join();
            Bot bot = new Bot(player, game, this);
            clientHandlerPlayerHashMap.put(bot, player);
            clients.add(bot);
        }
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
     * @param moveMessage          The move message containing the player's move.
     * @param clientHandler The client handler representing the player who made the move.
     */
    @Override
    public void broadcastMessage(MoveMessage moveMessage, PlayerHandler clientHandler) {
        UpdateMessage updateMessage = game.nextMove(moveMessage, clientHandlerPlayerHashMap.get(clientHandler));
        databaseManager.recordMove(clientHandlerPlayerHashMap.get(clientHandler).getId()
                +" "+updateMessage.content);
        sendMessage(updateMessage);
        String[] parts=updateMessage.content.split(" ");
        if(parts[parts.length-1].equals("END"))
        {
            databaseManager.endGame(Integer.parseInt(parts[parts.length-2]));
        }
    }
    private void sendMessage(UpdateMessage updateMessage){
        for (PlayerHandler player : clients) {
            // System.out.println("Sending message to " + player.getPlayerId());
            player.sendMessage(updateMessage);
        }
    }
}
