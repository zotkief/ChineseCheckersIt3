package com.jkpr.chinesecheckers.server.gamelogic.states;

/**
 * Represents the behavior of a player in different states of the game.
 * <p>
 * The {@code PlayerBehavior} interface defines the actions that a player can take
 * depending on their current state. It is used in conjunction with the {@link PlayerState}
 * enum to define how a player's behavior changes between states.
 * </p>
 */
public interface PlayerBehavior {

    /**
     * Retrieves the state of the player.
     * <p>
     * This method returns the current {@link PlayerState} associated with the player,
     * defining their status and role in the game.
     * </p>
     *
     * @return the player's current state
     */
    PlayerState getState();

    /**
     * Changes the player's state to {@link PlayerState#WIN}.
     * <p>
     * This method is invoked when a player wins the game. It returns a new {@link PlayerBehavior}
     * reflecting the WIN state.
     * </p>
     *
     * @return a new {@link PlayerBehavior} representing the WIN state
     */
    PlayerBehavior setWin();

    /**
     * Changes the player's state to {@link PlayerState#ACTIVE}.
     * <p>
     * This method is invoked when a player is actively participating in the game, allowing
     * them to make moves. It returns a new {@link PlayerBehavior} reflecting the ACTIVE state.
     * </p>
     *
     * @return a new {@link PlayerBehavior} representing the ACTIVE state
     */
    PlayerBehavior setActive();

    /**
     * Changes the player's state to {@link PlayerState#WAIT}.
     * <p>
     * This method is invoked when a player is waiting for their turn. It returns a new
     * {@link PlayerBehavior} reflecting the WAIT state, preventing the player from making moves.
     * </p>
     *
     * @return a new {@link PlayerBehavior} representing the WAIT state
     */
    PlayerBehavior setWait();
}
