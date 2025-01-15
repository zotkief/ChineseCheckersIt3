package com.jkpr.chinesecheckers.server.gamelogic.builders;

import com.jkpr.chinesecheckers.server.gamelogic.Game;
import com.jkpr.chinesecheckers.server.gamelogic.rules.YYRules;
import com.jkpr.chinesecheckers.server.gamelogic.boards.CCBoard;

/**
 * Builder class to construct a game with specific configurations for the "YY" variant of Chinese Checkers.
 * <p>
 * The {@code YYBuilder} class extends {@link GameBuilder} and implements the necessary methods to create a game
 * setup for the "YY" version. This includes setting up the board, configuring the rules, and returning the complete
 * game object.
 * </p>
 */
public class YYBuilder extends GameBuilder {

    /**
     * Sets the game board for the "YY" variant.
     * <p>
     * This method initializes the game board with a new instance of {@link CCBoard}, which represents the
     * game board for the "YY" variant of Chinese Checkers.
     * </p>
     */
    @Override
    public void setBoard() {
        game.setBoard(new CCBoard());
    }

    /**
     * Sets the game rules for the "YY" variant.
     * <p>
     * This method configures the game rules using the {@link YYRules} class, which defines the specific rules
     * for the "YY" version of Chinese Checkers.
     * </p>
     *
     * @param count the number of players in the game
     */
    @Override
    public void setRules(int count) {
        game.setRules(new YYRules());
    }

    /**
     * Returns the constructed game object.
     * <p>
     * This method returns the fully constructed game object with the appropriate board and rules set for the
     * "YY" variant of Chinese Checkers.
     * </p>
     *
     * @return the constructed {@link Game} object
     */
    @Override
    public Game getGame() {
        return game;
    }
}
