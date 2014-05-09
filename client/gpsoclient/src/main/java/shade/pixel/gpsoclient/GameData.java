package shade.pixel.gpsoclient;

import java.util.ArrayList;

/**
 * Created by pixelshade on 2.3.2014.
 */
public class GameData {
    private ArrayList<Quest> quests;
    private ArrayList<Region> regions;
    private ArrayList<Item> items;
    private ArrayList<Attribute> attributes;
// TODO create game models

    public GameData() {
        quests = new ArrayList<Quest>();
        regions = new ArrayList<Region>();
        items = new ArrayList<Item>();
        attributes = new ArrayList<Attribute>();
    }

    public ArrayList<Quest> getQuests() {
        return quests;
    }

    public ArrayList<Quest> getQuests(boolean getAvailable, boolean getActive, boolean getComplete) {
        ArrayList<Quest> questsToShow = new ArrayList<Quest>();
        for (Quest q : quests){
            if(q.isActive()){
                if(q.isCompleted()){
                    if(getComplete) questsToShow.add(q);
                } else {
                    if(getActive) questsToShow.add(q);
                }
            } else {
                if(getAvailable){
                    questsToShow.add(q);
                }
            }
        }
        return questsToShow;
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

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<Attribute> attributes) {
        this.attributes = attributes;
    }
}
