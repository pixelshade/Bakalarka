package shade.pixel.gpsoclient;

import android.content.Intent;
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
    private static final String TAG = "ResponseJSONParser";
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
    public static final String KEY_ATTRIBUTES = "attributes";
    public static final String KEY_RESPONSES = "responses";


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


    public static GameData parseGameData(String json){
        if (json != null) {
            try {
                JSONObject jsonObj = new JSONObject(json);
              //  HashMap<String, ArrayList<?>> response = new HashMap<String, ArrayList<?>>();

                JSONArray jsonRegions = jsonObj.optJSONArray(KEY_REGIONS);
                JSONArray jsonQuests = jsonObj.optJSONArray(KEY_QUESTS);
                JSONArray jsonActiveQuests = jsonObj.optJSONArray(KEY_ACTIVE_QUESTS);
                JSONArray jsonItems = jsonObj.optJSONArray(KEY_INVENTORY);
                JSONArray jsonAttributes = jsonObj.optJSONArray(KEY_ATTRIBUTES);
                JSONArray jsonResponses = jsonObj.optJSONArray(KEY_RESPONSES);

                ArrayList<Quest> quests = new ArrayList<Quest>();
                for(int i = 0; i < jsonQuests.length(); i++){
                    JSONObject quest = jsonQuests.getJSONObject(i);
                    Quest q = getQuestFromJSONObj(quest);
                    quests.add(q);
                }

                for(int i = 0; i < jsonActiveQuests.length(); i++){
                    JSONObject quest = jsonActiveQuests.getJSONObject(i);

                    Quest q = getQuestFromJSONObj(quest);
                    String timeAcceptedString = quest.getString(Quest.KEY_QUEST_TIME_ACCEPTED);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date timeAccepted = sdf.parse(timeAcceptedString);
                    boolean completed = quest.getInt(Quest.KEY_QUEST_COMPLETED) == 1;

                    q.setTimeAccepted(timeAccepted);
                    q.setCompleted(completed);
                    q.setActive(true);

                    quests.add(q);
                }

                ArrayList<Region> regions = new ArrayList<Region>();
                for(int i = 0; i < jsonRegions.length(); i++){
                    JSONObject region = jsonRegions.getJSONObject(i);
                    Region r = getRegionFromJSONObj(region);
                    regions.add(r);
                }

                ArrayList<Item> items = new ArrayList<Item>();
                for(int i = 0; i < jsonItems.length(); i++){
                    JSONObject itemJson = jsonItems.getJSONObject(i);
                    Item item = getItemFromJSONObj(itemJson);
                    items.add(item);
                }

                ArrayList<Attribute> attributes = new ArrayList<Attribute>();
                for(int i = 0; i < jsonAttributes.length(); i++){
                    JSONObject attributeJson = jsonAttributes.getJSONObject(i);

                    Attribute attribute = getAttributeJSONObj(attributeJson);
                    attributes.add(attribute);
                }

                ArrayList<Response> successfullResponses = new ArrayList<Response>();
                if(jsonResponses!=null)
                for(int i = 0; i < jsonResponses.length(); ++i){
                    String responseJSON = jsonResponses.getString(i);
                    Response response = new Response(responseJSON);
                    if(response.isSuccessful()) {
                        successfullResponses.add(response);
                        if(response.getType().equals(Response.TYPE_ACCEPT_QUEST)) {
                            Quest quest = (Quest) response.getData();
                            quests.add(quest);
                        }
                    }
                }

                GameData gameData = new GameData();
                gameData.setQuests(quests);
                gameData.setRegions(regions);
                gameData.setItems(items);
                gameData.setAttributes(attributes);
                gameData.setSuccessfullResponses(successfullResponses);

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

    public static Quest getQuestFromJSONObj(JSONObject jsonQuestObj) throws JSONException {
        int id = jsonQuestObj.getInt(Quest.KEY_QUEST_ID);
        String name = jsonQuestObj.getString(Quest.KEY_QUEST_NAME);
        boolean autostart = jsonQuestObj.getInt(Quest.KEY_QUEST_AUTOSTART) == 1;
        String code = jsonQuestObj.getString(Quest.KEY_QUEST_CODE);
        long duration = jsonQuestObj.optInt(Quest.KEY_QUEST_DURATION, 0);
        String image = jsonQuestObj.getString(Quest.KEY_QUEST_IMAGE);
        String info = jsonQuestObj.getString(Quest.KEY_QUEST_INFO);
        int regionId = jsonQuestObj.optInt(Quest.KEY_QUEST_REGION_ID, Quest.UNDEFINED_INT_VALUE);
        int requiredQuestId = jsonQuestObj.optInt(Quest.KEY_QUEST_REQUIRED_QUEST_ID, Quest.UNDEFINED_INT_VALUE);
        int requirementType = jsonQuestObj.getInt(Quest.KEY_QUEST_REQUIREMENT_TYPE);
        String requirement = jsonQuestObj.getString(Quest.KEY_QUEST_REQUIREMENT);
        int reward_id = jsonQuestObj.optInt(Quest.KEY_QUEST_REWARD_ID, Quest.UNDEFINED_INT_VALUE);

        return new Quest(id,code, name, info, image, reward_id, autostart, regionId, requiredQuestId, duration, requirementType, requirement);
    }

    public static Region getRegionFromJSONObj(JSONObject jsonRegionObj) throws JSONException {
        int id =  jsonRegionObj.getInt(Region.KEY_REGION_ID);
        String name = jsonRegionObj.getString(Region.KEY_REGION_NAME);
        String info = jsonRegionObj.getString(Region.KEY_REGION_INFO);
        String image = jsonRegionObj.getString(Region.KEY_REGION_IMAGE);
        double lat_start = jsonRegionObj.getDouble(Region.KEY_REGION_LAT_START);
        double lat_end = jsonRegionObj.getDouble(Region.KEY_REGION_LAT_END);
        double lon_start = jsonRegionObj.getDouble(Region.KEY_REGION_LON_START);
        double lon_end = jsonRegionObj.getDouble(Region.KEY_REGION_LON_END);

        return new Region(id,name,info,image,lat_start,lon_start,lat_end,lon_end);
    }

    public static Item getItemFromJSONObj(JSONObject itemJson) throws JSONException {
        int id =  itemJson.getInt(Item.KEY_ITEM_ID);
        String name = itemJson.getString(Item.KEY_ITEM_NAME);
        String info = itemJson.getString(Item.KEY_ITEM_INFO);
        String image = itemJson.getString(Item.KEY_ITEM_IMAGE);
        int amount  = itemJson.optInt(Item.KEY_ITEM_AMOUNT,1);

        return new Item(id,name,info,image,amount);
    }

    public static Attribute getAttributeJSONObj(JSONObject attributeJson) throws JSONException {
        int id =  attributeJson.getInt(Attribute.KEY_ATTRIBUTE_ID);
        String name = attributeJson.getString(Attribute.KEY_ATTRIBUTE_NAME);
        String info = attributeJson.getString(Attribute.KEY_ATTRIBUTE_INFO);
        String image = attributeJson.getString(Attribute.KEY_ATTRIBUTE_IMAGE);
        int amount  = attributeJson.optInt(Attribute.KEY_ATTRIBUTE_AMOUNT,1);
        return new Attribute(id,name,info,image,amount);
    }


}

