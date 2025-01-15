package com.jkpr.chinesecheckers.server.message;

/**
 * The {@code MessageType} enum defines the possible types of messages
 * that can be exchanged between the server and the client in the game.
 *
 * <p>This enum helps categorize messages into different types such as
 * move messages, updates, or game start signals.</p>
 */
public enum MessageType {
    /**
     * A message representing a move action made by a player.
     * This type is used for transmitting the details of a player's move.
     */
    MOVE,

    /**
     * A message used to provide updates to players during the game.
     * This type is used for non-move related updates, such as game state changes.
     */
    UPDATE,

    /**
     * A message representing the generation of a new game.
     * This type is used to communicate the creation or initialization of a game.
     */
    GEN;

    // Possible future extensions:
    // START_GAME, ERROR
}
