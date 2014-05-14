package shade.pixel.gpsoclient;

/**
 * Created by pixelshade on 11.5.2014.
 */
public class GameSettings {
    private int playerId;
    private String playerName;
    private String gameFilenameLogo;

    public GameSettings() {
    }

    public GameSettings(String playerName, String gameFilenameLogo, int playerId) {
        this.playerName = playerName;
        this.gameFilenameLogo = gameFilenameLogo;
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getGameFilenameLogo() {
        return gameFilenameLogo;
    }

    public void setGameFilenameLogo(String gameFilenameLogo) {
        this.gameFilenameLogo = gameFilenameLogo;
    }
}
