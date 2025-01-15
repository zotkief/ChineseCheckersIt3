package com.jkpr.chinesecheckers.server.gamelogic.builders;

import com.jkpr.chinesecheckers.server.gamelogic.Game;

/**
 * The {@code GameBuilder} abstract class defines the blueprint for constructing a game of Chinese Checkers.
 * <p>
 * Classes that extend this abstract class should provide concrete implementations for setting up the board,
 * configuring the rules, and returning the fully constructed {@code Game} object. The builder pattern is used
 * to separate the construction of the game setup from the actual gameplay logic.
 * </p>
 */
public abstract class GameBuilder {

    /** The game instance being constructed. */
    protected final Game game = new Game();

    /**
     * Sets up the board for the game.
     * <p>
     * This method is responsible for initializing and configuring the game board.
     * It should define how the board is arranged, including any starting positions or special settings.
     * </p>
     */
    public abstract void setBoard();

    /**
     * Sets the rules for the game.
     * <p>
     * This method configures the rules of the game based on the number of players.
     * The rules define how the game is played, including any player actions, win conditions, etc.
     * </p>
     *
     * @param count the number of players in the game
     */
    public abstract void setRules(int count);

    /**
     * Returns the fully constructed game.
     * <p>
     * After the board and rules have been set up, this method should return a {@code Game} object
     * that is ready to be played.
     * </p>
     *
     * @return the constructed {@code Game} object
     */
    public abstract Game getGame();
}
