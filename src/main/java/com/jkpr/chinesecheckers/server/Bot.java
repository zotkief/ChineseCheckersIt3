package com.jkpr.chinesecheckers.server;

import com.jkpr.chinesecheckers.server.gamelogic.Game;
import com.jkpr.chinesecheckers.server.gamelogic.Player;
import com.jkpr.chinesecheckers.server.gamelogic.boards.Position;
import com.jkpr.chinesecheckers.server.message.Message;
import com.jkpr.chinesecheckers.server.message.MoveMessage;
import com.jkpr.chinesecheckers.server.message.UpdateMessage;

import java.util.List;

/**
 * The {@code Bot} class represents a bot player in a game of Chinese Checkers.
 * It implements the {@link PlayerHandler} interface and handles communication with
 * the game session, making moves based on the bot's strategy.
 */
public class Bot implements PlayerHandler {
    private Player player;
    private Game game;
    private Session gameSession;
    private Position target;

    /**
     * Constructs a {@code Bot} object, initializing the bot with the given player, game,
     * and game session. The target position is determined based on the player's current position.
     *
     * @param player The {@code Player} object representing the bot in the game.
     * @param game The {@code Game} object representing the current game.
     * @param adapter The {@code Session} object that acts as an adapter to the game session.
     */
    public Bot(Player player, Game game, Session adapter) {
        this.game = game;
        this.player = player;
        this.gameSession = adapter;
        target = game.getTarget(player);
    }

    /**
     * Handles incoming messages by processing update messages and making a move
     * if the bot's turn has arrived.
     *
     * @param message The {@code Message} object to process.
     */
    @Override
    public void sendMessage(Message message) {
        UpdateMessage update = (UpdateMessage) message;
        System.out.println("broadcast" + update.content + " id" + player.getId());

        String[] parts = update.content.split(" ");
        int i = 0;
        while (i < parts.length - 1) {
            if (parts[i].equals("NEXT_ID") && parts[i + 1].equals(String.valueOf(player.getId()))) {
                makeMove();
            }
            i++;
        }
    }

    /**
     * Assigns a new game session to the bot.
     *
     * @param gameSession The {@code Session} object representing the game session to assign.
     */
    @Override
    public void assignGameAdapter(Session gameSession) {
        this.gameSession = gameSession;
    }

    /**
     * The bot's decision-making process to select and perform a move based on its target position.
     * The bot evaluates the best move by calculating the distance between its current and destination
     * positions, choosing the one that brings it closest to its target.
     */
    private void makeMove() {
        System.out.println("makeMove");
        List<Position> pieces = game.getPiecePositions(player);
        MoveMessage move = null;
        int min = 100, maxDest = 0;

        // Evaluate each piece and determine the best move based on distance
        for (Position pos : pieces) {
            List<Position> destinations = game.getLegalMoves(player, pos);
            for (Position destination : destinations) {
                // Decision algorithm to calculate distances
                int dxdest = Math.abs(destination.getX() - target.getX());
                int dydest = Math.abs(destination.getY() - target.getY());
                int dzdest = Math.abs(destination.getX() + destination.getY() - target.getX() - target.getY());
                int dxstart = Math.abs(pos.getX() - target.getX());
                int dystart = Math.abs(pos.getY() - target.getY());
                int dzstart = Math.abs(pos.getX() + pos.getY() - target.getX() - target.getY());

                int dStart = dxstart + dystart + dzstart;
                int dDest = dxdest + dydest + dzdest;

                int destLen = dDest - dStart;

                if (destLen < min) {
                    move = new MoveMessage(pos.getX(), pos.getY(), destination.getX(), destination.getY());
                    min = destLen;
                    maxDest = dStart;
                } else if (destLen == min && dStart > maxDest) {
                    move = new MoveMessage(pos.getX(), pos.getY(), destination.getX(), destination.getY());
                    min = destLen;
                }
            }
        }

        // Broadcast the selected move
        if (move != null) {
            gameSession.broadcastMessage(move, this);
        } else {
            gameSession.broadcastMessage(new MoveMessage(), this);
        }
    }
}
