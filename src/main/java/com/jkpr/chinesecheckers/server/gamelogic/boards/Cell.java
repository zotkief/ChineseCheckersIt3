package com.jkpr.chinesecheckers.server.gamelogic.boards;

import com.jkpr.chinesecheckers.server.gamelogic.Player;

import java.util.List;

/**
 * Represents a cell on the game board in Chinese Checkers.
 * <p>
 * The {@code Cell} class encapsulates the position of a cell on the board, the piece currently occupying it (if any),
 * and the players who own the cell. A cell can either be empty or contain a piece that belongs to a player.
 * </p>
 */
public class Cell {

    /** The position of the cell on the board. */
    private final Position position;

    /** The piece occupying this cell, or {@code null} if empty. */
    private Piece piece;

    /** A list of players who own this cell. */
    private List<Player> owners;

    /**
     * Constructs a new cell at the specified position with a list of owners.
     * <p>
     * The cell can be initially empty, but owners are assigned to it.
     * </p>
     *
     * @param position the position of the cell on the board
     * @param owners the list of players who own the cell
     */
    public Cell(Position position, List<Player> owners) {
        this.position = position;
        this.owners = owners;
    }

    /**
     * Sets the piece occupying this cell.
     * <p>
     * If the cell previously contained a piece, it is replaced by the new one.
     * </p>
     *
     * @param piece the piece to place in the cell
     */
    public void setPiece(Piece piece)
    {
        this.piece = piece;
    }

    /**
     * Returns the list of players who own this cell.
     *
     * @return the list of players who own this cell
     */
    public List<Player> getOwners() {
        return owners;
    }

    /**
     * Checks if the cell is empty (does not contain a piece).
     *
     * @return {@code true} if the cell is empty, {@code false} otherwise
     */
    public boolean isEmpty() {
        return piece == null;
    }

    /**
     * Checks if the specified player owns the piece currently occupying the cell.
     *
     * @param player the player to check for ownership
     * @return {@code true} if the player owns the piece in the cell, {@code false} otherwise
     */
    public boolean checkPlayer(Player player) {
        return piece != null && piece.getOwner().equals(player);
    }

    /**
     * Returns the piece occupying the cell, or {@code null} if the cell is empty.
     *
     * @return the piece in the cell, or {@code null} if the cell is empty
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Returns a string representation of the cell.
     * <p>
     * If the cell contains a piece, its {@code toString()} representation is returned.
     * If the cell is empty, a period (".") is returned.
     * </p>
     *
     * @return a string representation of the cell
     */
    @Override
    public String toString() {
        if(piece == null)
            return ".";
        return piece.toString();
    }
}
