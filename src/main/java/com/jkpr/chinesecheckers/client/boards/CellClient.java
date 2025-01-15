package com.jkpr.chinesecheckers.client.boards;

import com.jkpr.chinesecheckers.server.gamelogic.boards.Position;

/**
 * Represents a single cell on the game board in Chinese checkers.
 * <p>
 * Each {@code Cell} holds a {@code Position} that identifies its location on the board,
 * a {@code Piece} that may or may not occupy the cell, and a list of {@code AbstractPlayer}s
 * who own the cell.
 * </p>
 */
public class CellClient {

    /** The position of this cell on the game board. */
    private final Position position;

    /** The piece currently placed in this cell, or {@code null} if the cell is empty. */
    private PieceClient piece;

    public CellClient(Position position) {
        this.position = position;
    }
    public void setPiece(PieceClient piece)
    {
        this.piece=piece;
    }


    public PieceClient getPiece() {
        return piece;
    }

    @Override
    public String toString() {
        if(piece==null)
            return ".";
        return piece.toString();
    }
}
