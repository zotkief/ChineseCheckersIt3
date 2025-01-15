package com.jkpr.chinesecheckers.server.gamelogic.states;

/**
 * Represents the active state of a player in the game.
 * <p>
 * The {@code ActiveState} class implements the {@link PlayerBehavior} interface and defines the
 * behavior of a player when they are actively participating in the game. A player in this state
 * is allowed to make moves and perform actions in the game.
 * </p>
 */
public class ActiveState implements PlayerBehavior {

    /**
     * Retrieves the state of the player, which is {@link PlayerState#ACTIVE} in this case.
     * <p>
     * This method returns the {@link PlayerState} that indicates the player is actively
     * participating in the game and can make moves.
     * </p>
     *
     * @return the {@link PlayerState#ACTIVE} state
     */
    @Override
    public PlayerState getState() {
        return PlayerState.ACTIVE;
    }

    /**
     * Changes the player's state to {@link PlayerState#WIN}.
     * <p>
     * This method is invoked when a player wins the game. It returns a new {@link PlayerBehavior}
     * representing the WIN state, indicating that the player has won.
     * </p>
     *
     * @return a new {@link PlayerBehavior} representing the WIN state
     */
    @Override
    public PlayerBehavior setWin() {
        return PlayerState.WIN.getState();
    }

    /**
     * Keeps the player's state in the active state.
     * <p>
     * This method ensures that the player remains in the ACTIVE state, allowing them to
     * continue making moves and performing actions in the game.
     * </p>
     *
     * @return this instance of {@link ActiveState}
     */
    @Override
    public PlayerBehavior setActive() {
        return this;
    }

    /**
     * Changes the player's state to {@link PlayerState#WAIT}.
     * <p>
     * This method is invoked when a player is no longer active and needs to wait for their next turn.
     * It returns a new {@link PlayerBehavior} representing the WAIT state.
     * </p>
     *
     * @return a new {@link PlayerBehavior} representing the WAIT state
     */
    @Override
    public PlayerBehavior setWait() {
        return PlayerState.WAIT.getState();
    }
}
