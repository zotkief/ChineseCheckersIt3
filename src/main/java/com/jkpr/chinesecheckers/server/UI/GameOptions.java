package com.jkpr.chinesecheckers.server.UI;

/**
 * The {@code GameOptions} class stores the selected game options, including the game type and the number of players.
 * It is used to pass the user's configuration choices from the server's UI to other components of the game.
 */
public class GameOptions {
    private String gameType;
    private String playerCount;
    private String botCount;

    private ResponseType responseType;

    private int gameId;

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
        responseType=ResponseType.NEW;
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

    public int getGameId() {
        return gameId;
    }

    public void setReplay(int gameId) {
        this.gameId = gameId;
        responseType=ResponseType.WATCH;
        playerCount="1";
        botCount="0";
    }
    public void setLoad(int gameId,String numberOfPlayers,String numberOfBots,String type) {
        this.gameId = gameId;
        responseType=ResponseType.LOAD;
        playerCount=numberOfPlayers;
        botCount=numberOfBots;
        gameType=type;
    }
    public ResponseType getResponseType(){return responseType;}
    public enum ResponseType{
        NEW,
        LOAD,
        WATCH
    }
}
