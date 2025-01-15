package com.jkpr.chinesecheckers.server.gamelogic.states;

/**
 * Represents the state of a player who has won the game.
 * <p>
 * The {@code WinState} class implements the {@code PlayerBehavior} interface and indicates that the player has achieved victory.
 * In this state, the player is no longer active and cannot take further actions in the game.
 * </p>
 */
public class WinState implements PlayerBehavior {

    /**
     * Returns the current state of the player, which is {@link PlayerState#WIN} in this case.
     *
     * @return the player's current state, which is {@link PlayerState#WIN}
     */
    @Override
    public PlayerState getState() {
        return PlayerState.WIN;
    }

    /**
     * Returns the same instance of {@code WinState} when attempting to set the player as a winner.
     *
     * @return this instance of {@code WinState}
     */
    @Override
    public PlayerBehavior setWin() {
        return this;
    }

    /**
     * Returns the same instance of {@code WinState} when attempting to set the player as active.
     * The player is already in the win state and cannot be active anymore.
     *
     * @return this instance of {@code WinState}
     */
    @Override
    public PlayerBehavior setActive() {
        return this;
    }

    /**
     * Returns the same instance of {@code WinState} when attempting to set the player as waiting.
     * The player is already in the win state and cannot be waiting anymore.
     *
     * @return this instance of {@code WinState}
     */
    @Override
    public PlayerBehavior setWait() {
        return this;
    }
}
