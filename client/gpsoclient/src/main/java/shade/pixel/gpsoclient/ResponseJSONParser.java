package shade.pixel.gpsoclient;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by pixelshade on 23.2.2014.
 */
public class ResponseJSONParser {
    // response key names
    // login
    public static final String KEY_TYPE = "type";
    public static final String KEY_MESSAGE = "msg";
    public static final String KEY_SUCCESS = "success";
    public static final String KEY_DATA = "data";

    //
    public static final String KEY_QUESTS = "quests";
    public static final String KEY_ACTIVE_QUESTS = "active_quests";
    public static final String KEY_REGIONS = "regions";
    public static final String KEY_INVENTORY = "items";
    public static final String KEY_ENEMIES = "enemies";


    // quest keys
    public static final String KEY_QUEST_ID = "id";
    public static final String KEY_QUEST_CODE = "code";
    public static final String KEY_QUEST_NAME = "name";
    public static final String KEY_QUEST_INFO = "info";
    public static final String KEY_QUEST_IMAGE = "image";
    public static final String KEY_QUEST_REWARD_ID = "reward_id";
    public static final String KEY_QUEST_AUTOSTART = "autostart";
    public static final String KEY_QUEST_REGION_ID = "region_id";
    public static final String KEY_QUEST_REQUIRED_QUEST = "required_completed_quest_id";
    public static final String KEY_QUEST_DURATION = "duration";
    public static final String KEY_QUEST_REQUIREMENT_TYPE = "completion_requirement_type";
    public static final String KEY_QUEST_REQUIREMENT = "completion_requirement";
    public static final String KEY_QUEST_TIME_ACCEPTED = "time_accepted";
    public static final String KEY_QUEST_COMPLETED = "completed";



    // region keys
    public static final String KEY_REGION_ID = "id";
    public static final String KEY_REGION_NAME = "name";
    public static final String KEY_REGION_INFO = "info";
    public static final String KEY_REGION_IMAGE = "image";
    public static final String KEY_REGION_LAT_START = "lat_start";
    public static final String KEY_REGION_LON_START = "lon_start";
    public static final String KEY_REGION_LAT_END = "lat_end";
    public static final String KEY_REGION_LON_END = "lon_end";



    public static HashMap<String, String> parseResponse(String json) {
        if (json != null && !json.isEmpty()) {
            try {
                JSONObject jsonObj = new JSONObject(json);
                HashMap<String, String> response = new HashMap<String, String>();

                String type = jsonObj.optString(KEY_TYPE);
                String success = jsonObj.optString(KEY_SUCCESS);
                String msg = jsonObj.optString(KEY_MESSAGE);
                String data = jsonObj.optString(KEY_DATA);

                response.put(KEY_TYPE, type);
                response.put(KEY_SUCCESS, success);
                response.put(KEY_MESSAGE, msg);
                response.put(KEY_DATA, data);
                return response;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("JSON PARSER", "No json string to parse");

        }
        return null;
    }

    public static GameData parseGameData(String json){
        if (json != null) {
            try {
                JSONObject jsonObj = new JSONObject(json);
              //  HashMap<String, ArrayList<?>> response = new HashMap<String, ArrayList<?>>();

                JSONArray jsonRegions = jsonObj.optJSONArray(KEY_REGIONS);
                JSONArray jsonQuests = jsonObj.optJSONArray(KEY_QUESTS);
                JSONArray jsonActiveQuests = jsonObj.optJSONArray(KEY_ACTIVE_QUESTS);
                JSONArray jsonItems = jsonObj.optJSONArray(KEY_INVENTORY);



                ArrayList<Quest> quests = new ArrayList<Quest>();
                for(int i = 0; i < jsonQuests.length(); i++){
                    JSONObject quest = jsonQuests.getJSONObject(i);

                    int id = quest.getInt(Quest.KEY_QUEST_ID);
                    String name = quest.getString(Quest.KEY_QUEST_NAME);
                    boolean autostart = quest.getInt(Quest.KEY_QUEST_AUTOSTART) == 1;
                    String code = quest.getString(Quest.KEY_QUEST_CODE);
                    int duration = quest.optInt(Quest.KEY_QUEST_DURATION, 0);
                    String image = quest.getString(Quest.KEY_QUEST_IMAGE);
                    String info = quest.getString(Quest.KEY_QUEST_INFO);
                    int regionId = quest.optInt(Quest.KEY_QUEST_REGION_ID, Quest.UNDEFINED_INT_VALUE);
                    int requiredQuestId = quest.optInt(Quest.KEY_QUEST_REQUIRED_QUEST_ID, Quest.UNDEFINED_INT_VALUE);
                    int requirementType = quest.getInt(Quest.KEY_QUEST_REQUIREMENT_TYPE);
                    String requirement = quest.getString(Quest.KEY_QUEST_REQUIREMENT);
                    int reward_id = quest.optInt(Quest.KEY_QUEST_REWARD_ID, Quest.UNDEFINED_INT_VALUE);

                    Quest q = new Quest(id,code, name, info, image, reward_id, autostart, regionId, requiredQuestId, duration, requirementType, requirement);
                    quests.add(q);
                }


                for(int i = 0; i < jsonActiveQuests.length(); i++){
                    JSONObject quest = jsonActiveQuests.getJSONObject(i);

                    int id = quest.getInt(Quest.KEY_QUEST_ID);
                    String name = quest.getString(Quest.KEY_QUEST_NAME);
                    boolean autostart = quest.getInt(Quest.KEY_QUEST_AUTOSTART) == 1;
                    String code = quest.getString(Quest.KEY_QUEST_CODE);
                    long duration = quest.optInt(Quest.KEY_QUEST_DURATION, 0);
                    String image = quest.getString(Quest.KEY_QUEST_IMAGE);
                    String info = quest.getString(Quest.KEY_QUEST_INFO);
                    int regionId = quest.optInt(Quest.KEY_QUEST_REGION_ID, Quest.UNDEFINED_INT_VALUE);
                    int requiredQuestId = quest.optInt(Quest.KEY_QUEST_REQUIRED_QUEST_ID, Quest.UNDEFINED_INT_VALUE);
                    int requirementType = quest.getInt(Quest.KEY_QUEST_REQUIREMENT_TYPE);
                    String requirement = quest.getString(Quest.KEY_QUEST_REQUIREMENT);
                    int reward_id = quest.optInt(Quest.KEY_QUEST_REWARD_ID, Quest.UNDEFINED_INT_VALUE);
                    String timeAcceptedString = quest.getString(Quest.KEY_QUEST_TIME_ACCEPTED);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date timeAccepted = sdf.parse(timeAcceptedString);
                    Boolean completed = quest.getInt(Quest.KEY_QUEST_COMPLETED) == 1;

                    Quest q = new Quest(id,code, name, info, image, reward_id, autostart, regionId, requiredQuestId, duration, requirementType, requirement,true,completed,timeAccepted);
                    quests.add(q);
                }

                ArrayList<Region> regions = new ArrayList<Region>();
                for(int i = 0; i < jsonRegions.length(); i++){
                    JSONObject region = jsonRegions.getJSONObject(i);
                    int id =  region.getInt(Region.KEY_REGION_ID);
                    String name = region.getString(Region.KEY_REGION_NAME);
                    String info = region.getString(Region.KEY_REGION_INFO);
                    String image = region.getString(Region.KEY_REGION_IMAGE);
                    int lat_start = region.getInt(Region.KEY_REGION_LAT_START);
                    int lat_end = region.getInt(Region.KEY_REGION_LAT_END);
                    int lon_start = region.getInt(Region.KEY_REGION_LON_START);
                    int lon_end = region.getInt(Region.KEY_REGION_LON_END);


                    Region r = new Region(id,name,info,image,lat_start,lon_start,lat_end,lon_end);
                    regions.add(r);
                }

                ArrayList<Item> items = new ArrayList<Item>();
                for(int i = 0; i < jsonItems.length(); i++){
                    JSONObject itemJson = jsonItems.getJSONObject(i);
                    int id =  itemJson.getInt(Item.KEY_ITEM_ID);
                    String name = itemJson.getString(Item.KEY_ITEM_NAME);
                    String info = itemJson.getString(Item.KEY_ITEM_INFO);
                    String image = itemJson.getString(Item.KEY_ITEM_IMAGE);

                    Item item = new Item(id,name,info,image);
                    items.add(item);
                }


                GameData gameData = new GameData();
                gameData.setQuests(quests);
                gameData.setRegions(regions);
                gameData.setItems(items);

                return gameData;
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("JSON PARSER", "No json string to parse");

        }
        return null;
    }


}

