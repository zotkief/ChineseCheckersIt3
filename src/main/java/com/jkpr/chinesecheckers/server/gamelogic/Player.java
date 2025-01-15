package com.jkpr.chinesecheckers.server.gamelogic;

import com.jkpr.chinesecheckers.server.gamelogic.boards.Piece;
import com.jkpr.chinesecheckers.server.gamelogic.states.PlayerBehavior;
import com.jkpr.chinesecheckers.server.gamelogic.states.PlayerState;
import com.jkpr.chinesecheckers.server.gamelogic.states.WaitState;

import java.util.List;
import java.util.Objects;

/**
 * Represents a player in the game.
 * <p>
 * The {@code Player} class serves as a base class for creating players in the game.
 * It stores basic player attributes such as their unique ID and a list of pieces they own.
 * The class also includes methods for state transitions and equality comparison based on player ID.
 * </p>
 */
public class Player {

    /** The unique identifier for the player. */
    private int id;

    /** The player's behavior state. */
    private PlayerBehavior playerBehavior = new WaitState();

    /** The list of pieces owned by the player. */
    private List<Piece> pieceList;

    /**
     * Constructs a new player with the specified ID.
     *
     * @param id the unique identifier for the player
     */
    public Player(int id) {
        this.id = id;
    }

    /**
     * Checks whether this player is equal to another object.
     * <p>
     * Two players are considered equal if they have the same ID.
     * </p>
     *
     * @param o the object to compare with this player
     * @return {@code true} if the players are the same; {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id;
    }

    /**
     * Returns a hash code for this player based on their ID.
     * <p>
     * This ensures that players with the same ID have the same hash code,
     * which is useful for collections like {@code HashMap}.
     * </p>
     *
     * @return the hash code for this player
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Gets the unique ID of this player.
     *
     * @return the player's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the player's state to "win".
     * This method transitions the player's behavior to a "win" state.
     */
    public void setWin() {
        playerBehavior = playerBehavior.setWin();
    }

    /**
     * Sets the player's state to "wait".
     * This method transitions the player's behavior to a "wait" state.
     */
    public void setWait() {
        playerBehavior = playerBehavior.setWait();
    }

    /**
     * Sets the player's state to "active".
     * This method transitions the player's behavior to an "active" state.
     */
    public void setActive() {
        playerBehavior = playerBehavior.setActive();
    }

    /**
     * Gets the current state of the player.
     *
     * @return the current state of the player
     */
    public PlayerState getState() {
        return playerBehavior.getState();
    }
}
