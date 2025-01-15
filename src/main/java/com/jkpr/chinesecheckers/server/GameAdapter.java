package com.jkpr.chinesecheckers.server;

import java.util.*;

import com.jkpr.chinesecheckers.server.UI.GameOptions;
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
public class GameAdapter {

    /** List of connected clients (players). */
    private List<ClientHandler> clients = new ArrayList<>();
    private List<Bot> bots=new ArrayList<>();

    /** Mapping between client handlers and their respective players in the game. */
    private final HashMap<ClientHandler, Player> clientHandlerPlayerHashMap;

    /** The game instance for managing game logic and state. */
    private final Game game;

    /**
     * Constructs a {@code GameAdapter} object that creates a new game based on the provided game options,
     * and initializes the mapping between clients and players.
     *
     * @param players Array of client handlers representing the players.
     * @param server  The server managing the game session.
     * @param options The game options selected by the server or players.
     */
    public GameAdapter(ClientHandler[] players, Server server, GameOptions options) {
        int numberOfPlayers=Integer.parseInt(options.getPlayerCount());
        switch (options.getGameType()) {
            case "Fast Paced":
                game = Director.createGame(new FastPacedBuilder(), numberOfPlayers);
                break;
            case "Yin and Yang":
                game = Director.createGame(new YYBuilder(), 2);
                break;
            default:
                game = Director.createGame(new CCBuilder(), numberOfPlayers);
        }

        game.generate();
        clientHandlerPlayerHashMap = new HashMap<>();
        this.clients = Arrays.asList(players);

        for (ClientHandler clientHandler : players) {
            addPlayer(clientHandler);
            clientHandler.assignGameAdapter(this);

            int id = clientHandlerPlayerHashMap.get(clientHandler).getId();
            GenMessage message = new GenMessage(game.getGenMessage() + id);
            clientHandler.sendMessage(message);
        }
        for (int i=players.length;i<numberOfPlayers;i++)
        {
            bots.add(new Bot(game.join(),game,this));
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
     * @param move          The move message containing the player's move.
     * @param clientHandler The client handler representing the player who made the move.
     */
    public void brodcastMessage(MoveMessage move, ClientHandler clientHandler) {
        System.out.println("123"+move.getSkip());
        UpdateMessage updateMessage = game.nextMove(move, clientHandlerPlayerHashMap.get(clientHandler));
        for (ClientHandler player : clients) {
            // System.out.println("Sending message to " + player.getPlayerId());
            player.sendMessage(updateMessage);
        }
        for(Bot bot:bots)
        {
            bot.broadcastMessage(updateMessage);
        }
    }
    public void brodcastBotMessage(MoveMessage move, Player player) {
        System.out.println("456"+move.getSkip());
        UpdateMessage updateMessage = game.nextMove(move, player);
        for (ClientHandler handler : clients) {
            // System.out.println("Sending message to " + player.getPlayerId());
            handler.sendMessage(updateMessage);
        }
        for(Bot bot:bots)
        {
            bot.broadcastMessage(updateMessage);
        }
    }

    /**
     * Retrieves the player ID associated with the given client handler.
     *
     * @param clientHandler The client handler representing the player.
     * @return The ID of the player associated with the given client handler.
     */
    public int getPlayerId(ClientHandler clientHandler) {
        return clientHandlerPlayerHashMap.get(clientHandler).getId();
    }
}
