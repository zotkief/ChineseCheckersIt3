package com.jkpr.chinesecheckers.server;

import java.io.*;
import java.net.Socket;
import com.jkpr.chinesecheckers.server.message.*;
import java.util.UUID;
import java.util.Scanner;

/**
 * The {@code ClientHandler} class handles communication between the server and a single client.
 * It processes incoming messages from the client and broadcasts relevant messages to all players in the game.
 * This class implements the {@link Runnable} interface to support multi-threaded handling of clients.
 */
public class ClientHandler implements Runnable,PlayerHandler {

    /** The socket for communicating with the client. */
    private Socket clientSocket;

    /** Output stream for sending messages to the client. */
    private PrintWriter out;

    /** Input stream for receiving messages from the client. */
    private Scanner in;

    /** The game adapter to handle game logic and communication between players. */
    private Session gameSession;

    /** Unique player identifier. */
    private String playerId;

    /**
     * Constructs a {@code ClientHandler} for the specified client socket.
     * Generates a unique player ID for the client.
     *
     * @param clientSocket The socket representing the client connection.
     */
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.playerId = UUID.randomUUID().toString();
    }

    /**
     * Starts handling client communication in a separate thread.
     * Waits for messages from the client, processes them, and forwards them to the game if applicable.
     */
    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new Scanner(clientSocket.getInputStream());
            while (true) {
                System.out.println("Waiting for a message from " + playerId);
                String linia = in.nextLine().trim();
                System.out.println(linia);
                if (linia.isEmpty()) {
                    continue;
                }
                Message message;
                try {
                    if(linia.equals("SKIP"))
                        message=new MoveMessage();
                    else
                        message = Message.fromString(linia);
                } catch (Exception e) {
                    continue;
                }

                if (message.getType() == MessageType.MOVE) {
                    MoveMessage msg = (MoveMessage) message;
                    if (gameSession == null) {
                        continue;
                    } else {
                        gameSession.broadcastMessage(msg, this);
                    }
                } else {
                    // Unknown message type
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading message");
            e.printStackTrace();
        } finally {
            cleanUp();
        }
    }

    /**
     * Sends a message to the client.
     *
     * @param message The message to be sent to the client.
     */
    @Override
    public void sendMessage(Message message) {
        try {
            out.println(message.serialize());
            out.flush();
        } catch (Exception e) {
            System.err.println("Error sending message");
            e.printStackTrace();
        }
    }

    /**
     * Assigns a {@link GameSession} to this client handler, allowing it to participate in a game session.
     *
     * @param gameSession The game adapter that will manage game logic for this client.
     */
    @Override
    public void assignGameAdapter(Session gameSession) {
        this.gameSession = gameSession;
    }

    /**
     * Cleans up resources by closing the client socket when the handler finishes.
     */
    private void cleanUp() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error closing client socket");
            e.printStackTrace();
        }
    }
}
