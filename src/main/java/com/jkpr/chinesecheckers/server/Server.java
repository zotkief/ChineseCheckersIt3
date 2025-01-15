package com.jkpr.chinesecheckers.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.jkpr.chinesecheckers.server.UI.GameOptions;
import com.jkpr.chinesecheckers.server.UI.ServerWindow;
import com.jkpr.chinesecheckers.server.message.*;

/**
 * The Server class represents a Chinese Checkers server that handles client connections and game sessions.
 * It listens for client connections, manages game options, and starts a game session when the appropriate number of players join.
 */
public class Server {

    /** The port on which the server will listen for connections. */
    private static final int PORT = 12345;

    /** The ServerSocket used to listen for client connections. */
    private ServerSocket serverSocket;

    /** Thread pool for handling client connections concurrently. */
    private ExecutorService threadPool;

    /** A flag indicating if the server is running. */
    private volatile boolean isRunning = true;

    /** Array of client handlers representing connected players. */
    private ClientHandler[] players;

    /**
     * Initializes and starts the user interface for configuring game options.
     *
     * @return The {@link GameOptions} configured by the user.
     */
    private GameOptions startUI() {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Server Window");
        config.setWindowedMode(500, 500);
        config.setResizable(false);
        GameOptions options = new GameOptions();
        new Lwjgl3Application(new ServerWindow(options), config);
        return options;
    }

    /**
     * Starts the server, listens for client connections, and initiates a game session when the required number of players connect.
     */
    public void startServer() {
        try {
            GameOptions options = startUI();

            serverSocket = new ServerSocket(PORT);
            threadPool = Executors.newCachedThreadPool();
            System.out.println("Server started, listening on port " + PORT);

            // Validate number of players
            int numberOfPlayers = Integer.parseInt(options.getPlayerCount());
            if (numberOfPlayers < 2 || numberOfPlayers > 6 || numberOfPlayers == 5) {
                System.out.println("Invalid number of players.");
                return;
            }

            System.out.println("Waiting for " + numberOfPlayers + " players.");

            // Wait for the required number of players to connect
            players = new ClientHandler[numberOfPlayers];
            int connectedPlayers = 0;
            while (connectedPlayers < numberOfPlayers && isRunning) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket.getInetAddress());

                ClientHandler handler = new ClientHandler(clientSocket);
                System.out.println("Adding player " + handler.getPlayerId() + " to the queue.");
                players[connectedPlayers] = handler;
                threadPool.execute(handler);
                connectedPlayers++;
            }

            // Start the game session
            System.out.println("All players have joined. Creating game session.");
            GameAdapter gameAdapter = new GameAdapter(players, this, options);
            for (ClientHandler handler : players) {
                handler.assignGameAdapter(gameAdapter);
            }

            // Game loop placeholder
            while (true) {
            }

        } catch (IOException e) {
            System.err.println("Server error: Unable to start on port " + PORT);
            e.printStackTrace();
        }
    }

    /**
     * Main entry point for the server application. Creates and starts a new server instance.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
    }
}
