package shade.pixel.gpsoclient;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by pixelshade on 11.3.2014.
 */
public class GameHandler {
    private static final String TAG = "Game Handler";
    public static GameData gameData;
    static GameHandler gameHandler;

    Context context;
   MyHtmlBrowser htmlBrowser;
   ContentFilesManager contentFilesManager;


    public static GameHandler getInstance(Context context){
        if(gameHandler == null){
            gameHandler = new GameHandler(context);
        }
        gameHandler.setContext(context);
        return gameHandler;
    }

    public GameHandler(Context context){
        this.context = context;
        htmlBrowser = MyHtmlBrowser.getInstance(context);
        gameData = new GameData();
        contentFilesManager = new ContentFilesManager(context);
    }

    public GameData getGameData() {
        return gameData;
    }

    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }

    public MyHtmlBrowser getHtmlBrowser() {
        return htmlBrowser;
    }

    public void setHtmlBrowser(MyHtmlBrowser htmlBrowser) {
        this.htmlBrowser = htmlBrowser;
    }

    public ContentFilesManager getContentFilesManager() {
        return contentFilesManager;
    }

    public void setContentFilesManager(ContentFilesManager contentFilesManager) {
        this.contentFilesManager = contentFilesManager;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
        this.htmlBrowser.setmContext(context);
        this.contentFilesManager.setmContext(context);
    }
}
