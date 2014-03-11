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
//    GameData gameData;
//   Context context;
//    MyHtmlBrowser htmlBrowser;
//    GPSTracker gpsTracker;
//    ContentFilesManager contentFilesManager;
//
//
//
//    public GameHandler(Context context){
//        this.context = context;
//    }
//
//    public void UpdatePositionAndGameData(){
//        LatLng latLng = gpsTracker.getLatLng();
//        String url = htmlBrowser.getServerURL()+"/api/json/"+latLng.latitude+"/"+latLng.longitude;
//        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
//        htmlBrowser.HttpGetAsyncString(this, url, new AsyncResponse() {
//            @Override
//            public void processFinish(Context context, String json) {
//                GameData gameData = ResponseJSONParser.parseGameData(json);
//                if(gameData != null){
//                    //  PlaceholderFragment f = (PlaceholderFragment)mSectionsPagerAdapter.getItem(mActualViewId);
//                    StringBuilder sb = new StringBuilder();
//                    ArrayList<Region> regions = gameData.getRegions();
//                    sb.append("Regions:");
//                    for (Region region: regions){
//                        sb.append(region.getName() + ",");
//                    }
//                    ArrayList<Quest> quests = gameData.getQuests();
//                    sb.append("\nQuests:");
//                    for (Quest quest: quests){
//                        quest.getName();
//                        sb.append(quest.getName() + ",");
//                    }
//                    TextView tv = (TextView) findViewById(R.id.section_content);
//                    tv.setText(sb.toString());
//                    //  f.setContent(sb.toString());
//                    ArrayAdapter<Quest> arrayAdapter = new ArrayAdapter<Quest>(context, android.R.layout.simple_list_item_1, quests);
//                    ListView lv = (ListView) findViewById(R.id.listViewQuests);
//                    if(lv != null) lv.setAdapter(arrayAdapter);
//                } else {
//                    Log.d("AHA", "Problem with parsing gamedata");
//                }
//            }
//        });
//    }
}
