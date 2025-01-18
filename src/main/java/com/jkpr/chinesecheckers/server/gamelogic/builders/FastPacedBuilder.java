package com.jkpr.chinesecheckers.server.gamelogic.builders;

import com.jkpr.chinesecheckers.server.gamelogic.rules.FastPacedRules;
import com.jkpr.chinesecheckers.server.gamelogic.Game;
import com.jkpr.chinesecheckers.server.gamelogic.boards.CCBoard;

/**
 * The {@code FastPacedBuilder} class is a concrete implementation of the {@code GameBuilder} abstract class.
 * <p>
 * This builder is responsible for constructing a game of Chinese Checkers with fast-paced rules. It defines how
 * the game board is set up and how the rules are configured based on the number of players.
 * </p>
 */
public class FastPacedBuilder extends GameBuilder {

    private int count;
    public FastPacedBuilder(int count){this.count=count;}
    /**
     * Sets up the board for the fast-paced game.
     * <p>
     * This method initializes the board specifically for the fast-paced version of Chinese Checkers.
     * </p>
     */
    @Override
    public void setBoard() {
        game.setBoard(new CCBoard());
    }

    /**
     * Sets the rules for the fast-paced game.
     * <p>
     * This method configures the rules based on the number of players in the game. The rules are specifically
     * tailored for a fast-paced variant of Chinese Checkers.
     * </p>
     *
     */
    @Override
    public void setRules() {
        game.setRules(new FastPacedRules(count));
    }

    /**
     * Returns the fully constructed fast-paced game.
     * <p>
     * After setting the board and rules, this method returns the constructed {@code Game} object, which is
     * ready to be played with the fast-paced rules.
     * </p>
     *
     * @return the constructed {@code Game} object for the fast-paced variant
     */
    @Override
    public Game getGame() {
        return game;
    }
}
