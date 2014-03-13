package shade.pixel.gpsoclient;

import java.util.ArrayList;

/**
 * Created by pixelshade on 2.3.2014.
 */
public class GameData {
    private ArrayList<Quest> quests;
    private ArrayList<Region> regions;
    private ArrayList<Item> items;
// TODO create game models

    public GameData() {
        quests = new ArrayList<Quest>();
        regions = new ArrayList<Region>();
        items = new ArrayList<Item>();
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

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
