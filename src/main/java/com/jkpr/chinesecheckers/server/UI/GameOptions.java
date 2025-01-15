package com.jkpr.chinesecheckers.server.UI;

/**
 * The {@code GameOptions} class stores the selected game options, including the game type and the number of players.
 * It is used to pass the user's configuration choices from the server's UI to other components of the game.
 */
public class GameOptions {
    private String gameType;
    private String playerCount;
    private String botCount;

    /**
     * Sets the game type and player count based on the user's selection.
     *
     * @param gameType The type of the game selected by the user (e.g., "Standard", "Fast Paced").
     * @param playerCount The number of players for the game (e.g., "2", "3", "4", "6").
     */
    public void setData(String gameType, String playerCount,String botCount) {
        this.gameType = gameType;
        this.playerCount = playerCount;
        this.botCount=botCount;
    }

    /**
     * Returns the selected game type.
     *
     * @return The type of the game selected by the user.
     */
    public String getGameType() {
        return gameType;
    }

    /**
     * Returns the selected player count.
     *
     * @return The number of players selected by the user.
     */
    public String getPlayerCount() {
        return playerCount;
    }
    public String getBotCount(){return botCount;}
}
