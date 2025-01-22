package com.jkpr.chinesecheckers.server;

import com.jkpr.chinesecheckers.server.UI.GameOptions;
import com.jkpr.chinesecheckers.server.database.DataOperator;
import com.jkpr.chinesecheckers.server.database.DatabaseManager;
import com.jkpr.chinesecheckers.server.message.GenMessage;
import com.jkpr.chinesecheckers.server.message.MoveMessage;
import com.jkpr.chinesecheckers.server.message.UpdateMessage;
import com.jkpr.chinesecheckers.server.sessionState.Ready;
import com.jkpr.chinesecheckers.server.sessionState.SessionBehavior;
import com.jkpr.chinesecheckers.server.sessionState.SessionState;

import java.util.List;

/**
 * The {@code WatchSession} class represents a session for watching a game in progress.
 * It allows clients to observe the game moves as they happen, retrieving the game history
 * from the database and sending updates to the clients.
 */
public class WatchSession implements Session {
    private PlayerHandler clients;
    private SessionBehavior sessionBehavior=new Ready();

    /**
     * Constructs a {@code WatchSession} object. This constructor initializes the session
     * by retrieving the game history from the database and sending it to the client.
     *
     * @param players The {@code ClientHandler} responsible for handling clients in the session.
     * @param server The {@code Server} object managing the session.
     * @param options The {@code GameOptions} object containing the game ID and other settings.
     */
    public WatchSession(ClientHandler players, Server server, GameOptions options) {
        clients = players;

        // Initialize database manager and set the game ID for history retrieval
        DatabaseManager databaseManager = new DatabaseManager(DataOperator.jdbcTemplate());
        int id = options.getGameId();
        databaseManager.setGameId(id);

        // Retrieve the list of game moves from the database
        List<String> list = databaseManager.getMoves();

        // Send the first move as a GenMessage
        int start = list.get(0).indexOf(' ');
        GenMessage genMessage=(new GenMessage(list.get(0).substring(start + 1)));
        clients.sendMessage(genMessage);

        // Send the rest of the moves as UpdateMessages, with a delay between each update
        for (int i = 1; i < list.size(); i++) {
            start = list.get(i).indexOf(' ');
            sendMessage(new UpdateMessage(list.get(i).substring(start + 1)));
            try {
                // Delay between updates to simulate game progress
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Broadcasts a move message to the clients. This method is not used in the current class
     * but is required by the {@link Session} interface.
     *
     * @param moveMessage The {@code MoveMessage} to broadcast.
     * @param clientHandler The {@code PlayerHandler} for the client to send the message to.
     */
    @Override
    public void broadcastMessage(MoveMessage moveMessage, PlayerHandler clientHandler) {
        // No action taken in this class for broadcasting move messages
    }

    @Override
    public boolean isReady() {
        return sessionBehavior.getState().equals(SessionState.READY);
    }

    @Override
    public void setPrepare() {

    }

    @Override
    public void setReady() {

    }

    /**
     * Sends an update message to the clients.
     *
     * @param updateMessage The {@code UpdateMessage} to send.
     */
    private void sendMessage(UpdateMessage updateMessage) {
        clients.sendMessage(updateMessage);
    }
}
