package com.jkpr.chinesecheckers.server.strategy;

import com.jkpr.chinesecheckers.server.gamelogic.Game;
import com.jkpr.chinesecheckers.server.gamelogic.Player;
import com.jkpr.chinesecheckers.server.gamelogic.boards.Position;
import com.jkpr.chinesecheckers.server.message.MoveMessage;

/**
 * Interface for bot strategies
 */
public interface BotStrategy {
    /**
     * Picks a move for the bot in accordance with the strategy
     * @param game the game
     * @param player the player
     * @param target the target position
     * @return the move message
     */
    public MoveMessage pickMove(Game game, Player player, Position target);
}
