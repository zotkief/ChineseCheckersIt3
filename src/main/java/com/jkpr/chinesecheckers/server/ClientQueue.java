package com.jkpr.chinesecheckers.server;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * The {@code ClientQueue} class manages a queue of waiting clients.
 * It allows adding, retrieving, and removing clients in a thread-safe manner using a blocking queue.
 */
public class ClientQueue {

    /** A blocking queue to store waiting clients. */
    private final BlockingQueue<ClientHandler> waitingClients = new LinkedBlockingQueue<>();

    /**
     * Adds a client to the queue of waiting clients.
     *
     * @param client The {@link ClientHandler} representing the client to be added to the queue.
     */
    public void addClient(ClientHandler client) {
        waitingClients.add(client);
    }

    /**
     * Retrieves and removes a client from the queue, blocking if no clients are available.
     *
     * @return The {@link ClientHandler} representing the client retrieved from the queue.
     * @throws InterruptedException if interrupted while waiting.
     */
    public ClientHandler takeClient() throws InterruptedException {
        return waitingClients.take();
    }

    /**
     * Retrieves and removes a client from the queue, waiting up to the specified timeout if no clients are available.
     *
     * @param timeout The maximum time to wait for a client.
     * @param unit    The time unit of the {@code timeout} argument.
     * @return The {@link ClientHandler} if available, or {@code null} if the timeout is reached.
     * @throws InterruptedException if interrupted while waiting.
     */
    public ClientHandler pollClient(long timeout, TimeUnit unit) throws InterruptedException {
        return waitingClients.poll(timeout, unit);
    }

    /**
     * Removes a specific client from the queue of waiting clients.
     *
     * @param client The {@link ClientHandler} representing the client to be removed.
     * @return {@code true} if the client was successfully removed, {@code false} otherwise.
     */
    public boolean removeClient(ClientHandler client) {
        return waitingClients.remove(client);
    }

    /**
     * Returns the number of clients currently waiting in the queue.
     *
     * @return The number of clients in the queue.
     */
    public int getWaitingClientsCount() {
        return waitingClients.size();
    }
}
