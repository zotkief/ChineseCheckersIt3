package com.jkpr.chinesecheckers.server.gamelogic.builders;

import com.jkpr.chinesecheckers.server.gamelogic.Game;

/**
 * The {@code Director} class is responsible for orchestrating the construction of a game using a {@code GameBuilder}.
 * <p>
 * It follows the builder design pattern and directs the game-building process by invoking methods on a {@code GameBuilder}
 * to set up the board, players, and return a fully constructed {@code Game} object.
 * </p>
 */
public class Director {

    /**
     * Creates and returns a new game using the provided {@code GameBuilder}.
     * <p>
     * This method directs the {@code GameBuilder} to configure the game board and players, and then returns the
     * constructed {@code Game} object.
     * </p>
     *
     * @param builder the {@code GameBuilder} used to create the game
     * @return the fully constructed {@code Game}
     */
    public static Game createGame(GameBuilder builder, int playerCount) {
        builder.setBoard();
        builder.setRules(playerCount);
        return builder.getGame();
    }
}
