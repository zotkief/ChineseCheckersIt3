package com.jkpr.chinesecheckers.server.gamelogic.states;

/**
 * Represents the various states a player can be in during the game.
 * <p>
 * The {@code PlayerState} enum defines three possible states for a player:
 * {@link PlayerState#WIN}, {@link PlayerState#ACTIVE}, and {@link PlayerState#WAIT}.
 * Each state corresponds to different behaviors and interactions a player can have within the game.
 * </p>
 */
public enum PlayerState {

    /**
     * The player has won the game.
     * A player in this state cannot make any moves and is considered the winner.
     */
    WIN {
        /**
         * Returns the {@link PlayerBehavior} associated with the WIN state, which is {@link WinState}.
         *
         * @return an instance of {@link WinState}
         */
        public PlayerBehavior getState() {
            return new WinState();
        }
    },

    /**
     * The player is currently taking their turn.
     * A player in this state can make moves in the game.
     */
    ACTIVE {
        /**
         * Returns the {@link PlayerBehavior} associated with the ACTIVE state, which is {@link ActiveState}.
         *
         * @return an instance of {@link ActiveState}
         */
        public PlayerBehavior getState() {
            return new ActiveState();
        }
    },

    /**
     * The player is waiting for their turn in the game.
     * A player in this state cannot make moves until their turn is active.
     */
    WAIT {
        /**
         * Returns the {@link PlayerBehavior} associated with the WAIT state, which is {@link WaitState}.
         *
         * @return an instance of {@link WaitState}
         */
        public PlayerBehavior getState() {
            return new WaitState();
        }
    };

    /**
     * Gets the {@link PlayerBehavior} associated with the player's state.
     * This method is overridden in each enum constant to return the appropriate {@link PlayerBehavior}.
     *
     * @return the player's behavior for the current state
     */
    public PlayerBehavior getState() {
        return null;
    }
}
