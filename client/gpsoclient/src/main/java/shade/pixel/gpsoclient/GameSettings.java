package shade.pixel.gpsoclient;

/**
 * Created by pixelshade on 11.5.2014.
 */
public class GameSettings {
    private String playerName;
    private String gameFilenameLogo;

    public GameSettings() {
    }

    public GameSettings(String playerName, String gameFilenameLogo) {
        this.playerName = playerName;
        this.gameFilenameLogo = gameFilenameLogo;
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
