package com.jkpr.chinesecheckers.server.gamelogic.states;

/**
 * Represents the state of a player who is waiting for their turn in the game.
 * <p>
 * The {@code WaitState} class implements the {@code PlayerBehavior} interface and indicates that the player is in a waiting state,
 * typically because it is not their turn to play.
 * </p>
 */
public class WaitState implements PlayerBehavior {

    /**
     * Returns the current state of the player, which is {@link PlayerState#WAIT} in this case.
     *
     * @return the player's current state, which is {@link PlayerState#WAIT}
     */
    @Override
    public PlayerState getState() {
        return PlayerState.WAIT;
    }

    /**
     * Returns the same instance of {@code WaitState} when attempting to set the player as a winner.
     * The player cannot be set to the win state while they are in the waiting state.
     *
     * @return this instance of {@code WaitState}
     */
    @Override
    public PlayerBehavior setWin() {
        return this;
    }

    /**
     * Transitions the player from the waiting state to the active state, allowing them to make a move.
     *
     * @return the new active state of the player
     */
    @Override
    public PlayerBehavior setActive() {
        return PlayerState.ACTIVE.getState();
    }

    /**
     * Returns the same instance of {@code WaitState} when attempting to set the player as waiting again.
     * The player is already in the waiting state.
     *
     * @return this instance of {@code WaitState}
     */
    @Override
    public PlayerBehavior setWait() {
        return this;
    }
}
