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
    static GameHandler gameHandler;
    Context context;

   GameData gameData;
   MyHtmlBrowser htmlBrowser;
   GPSTracker gpsTracker;
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
        gpsTracker = new GPSTracker(context);
    }

    public void UpdatePositionAndGameData(){
        LatLng latLng = gpsTracker.getLatLng();
        String url = htmlBrowser.getServerURL()+"/api/json/"+latLng.latitude+"/"+latLng.longitude;
        Toast.makeText(context, url, Toast.LENGTH_SHORT).show();
        htmlBrowser.HttpGetAsyncString(context, url, new AsyncResponse() {
            @Override
            public void processFinish(Context context, String json) {
                GameData tempGameData = ResponseJSONParser.parseGameData(json);
                if(gameData != null){
                   gameData = tempGameData;
                } else {
                    Log.d("AHA", "Problem with parsing gamedata, using the old ones");
                }
            }
        });
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

    public GPSTracker getGpsTracker() {
        return gpsTracker;
    }

    public void setGpsTracker(GPSTracker gpsTracker) {
        this.gpsTracker = gpsTracker;
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
        this.gpsTracker.setmContext(context);
        this.contentFilesManager.setmContext(context);
    }
}
