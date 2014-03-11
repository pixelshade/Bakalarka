package shade.pixel.gpsoclient;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pixelshade on 2.3.2014.
 */
public class GameData {
    private ArrayList<Quest> quests;
    private ArrayList<Region> regions;
// TODO create game models

    public GameData(){
        quests = new ArrayList<Quest>();
        regions = new ArrayList<Region>();
    }

    public ArrayList<Quest> getQuests() {
        return quests;
    }

    public void setQuests(ArrayList<Quest> quests) {
        this.quests = quests;
    }

    public ArrayList<Region> getRegions() {
        return regions;
    }

    public void setRegions(ArrayList<Region> regions) {
        this.regions = regions;
    }
}
