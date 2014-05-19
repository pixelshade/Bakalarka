package shade.pixel.gpsoclient;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by pixelshade on 15.3.2014.
 */
public class Response implements Serializable{
    private static String TAG = "Response";
    //  private HashMap<String, String> response;
    private boolean successfullyParsed;
    private String type;
    private boolean success;
    private String message;
    private String dataString;
    private Object data;
    private boolean loggedOut;

    final static public String TYPE_REGISTER = "REGISTER_USER";
    final static public String TYPE_IS_LOGGED = "IS_LOGGED";
    final static public String TYPE_LOGIN = "LOGIN";
    final static public String TYPE_ACCEPT_QUEST = "ACCEPT_QUEST";
    final static public String TYPE_COMPLETE_QUEST = "COMPLETE_QUEST";
    final static public String TYPE_QUEST_INFO = "QUEST_INFO";
    final static public String TYPE_GIVE_REWARD = "GIVE_REWARD";

    // struct
    public static final String KEY_TYPE = "type";
    public static final String KEY_MESSAGE = "msg";
    public static final String KEY_SUCCESS = "success";
    public static final String KEY_DATA = "data";

    //
    public static final String KEY_QUESTS = "quests";
    public static final String KEY_REGIONS = "regions";
    public static final String KEY_INVENTORY = "items";
    public static final String KEY_REWARDS = "rewards";
    public static final String KEY_ENEMIES = "enemies";


    public Response(String json) {
        parseJson(json);
    }


    private void parseJson(String json) {
        if (json != null && !json.isEmpty()) {
            try {
                JSONObject jsonResponseObj = new JSONObject(json);
                type = jsonResponseObj.optString(KEY_TYPE, "");
                success = jsonResponseObj.optString(KEY_SUCCESS, "0").equals("1");
                message = jsonResponseObj.optString(KEY_MESSAGE, "");
                dataString = jsonResponseObj.optString(KEY_DATA, "");
                Log.d(TAG, dataString);
                if (type.equals(TYPE_IS_LOGGED)) {
                    loggedOut = !success;
                }
                if (!dataString.equals("")) data = getDataFromResponse(dataString, type);
                successfullyParsed = true;
            } catch (JSONException e) {
                e.printStackTrace();
                successfullyParsed = false;
            }
        } else {
            Log.e(TAG, "No json string to parse");
        }
        successfullyParsed = false;
    }

    private Reward getReward(String giveRewardJson) {
        if (type.equals(TYPE_GIVE_REWARD)) {
            Reward reward = new Reward();
            JSONObject data = null;
            try {
                data = new JSONObject(giveRewardJson);
                reward.setName(data.optString("name",""));

                if (!data.isNull("attribute")) {
                    JSONObject attributeJSON = data.getJSONObject("attribute");

                    Attribute attribute = new Attribute(
                            attributeJSON.optInt("id", -1),
                            attributeJSON.optString("name", ""),
                            attributeJSON.optString("info", ""),
                            attributeJSON.optString("image", "")
                    );
                    reward.setAttribute(attribute);
                    reward.setAttributeAmount(attributeJSON.optInt("amount", 0));

                }

                if (!data.isNull("item")) {
                    JSONObject itemJSON = data.getJSONObject("item");
                    Item item = new Item(
                            itemJSON.optInt("id", -1),
                            itemJSON.optString("name", ""),
                            itemJSON.optString("info", ""),
                            itemJSON.optString("image", "")
                    );
                    reward.setItem(item);
                    reward.setItemAmount(itemJSON.optInt("amount", 0));
                }
                return reward;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Quest getAcceptedQuest(String json){
        if (type.equals(TYPE_ACCEPT_QUEST)) {

            try {

                JSONObject quest = new JSONObject(json);

                Quest q = ResponseJSONParser.getQuestFromJSONObj(quest);
                String timeAcceptedString = quest.getString(Quest.KEY_QUEST_TIME_ACCEPTED);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date timeAccepted = sdf.parse(timeAcceptedString);
                boolean completed = quest.getInt(Quest.KEY_QUEST_COMPLETED) == 1;

                q.setTimeAccepted(timeAccepted);
                q.setCompleted(completed);
                q.setActive(true);

                return q;

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    private GameSettings getGameSettings(String json) {
        if (type.equals(TYPE_LOGIN)) {
            GameSettings gameSettings = new GameSettings();
            JSONObject data = null;
            try {
                data = new JSONObject(json);
                gameSettings.setGameFilenameLogo(data.optString("gameFilenameLogo", ""));
                gameSettings.setPlayerName(data.optString("name", ""));
                gameSettings.setPlayerId(data.optInt("id", -1));
                gameSettings.setGameName(data.optString("gameName", ""));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return gameSettings;
        }
        return null;
    }

    private Object getDataFromResponse(String dataJSON, String type) {

        if (type.equals(TYPE_GIVE_REWARD)) {
            return getReward(dataJSON);
        }
        if (type.equals(TYPE_COMPLETE_QUEST)) {
            return new Response(dataJSON);
        }
        if (type.equals(TYPE_ACCEPT_QUEST)) {
            return getAcceptedQuest(dataJSON);
        }
        if (type.equals(TYPE_LOGIN)) {
            return getGameSettings(dataJSON);
        }
        return dataJSON;
    }




            /*
             *
             * {"type":"COMPLETE_QUEST","success":1,"msg":"Quest was successfully completed","reward":{"type":"GIVE_REWARD","data":{"attribute":{"id":"1","name":"Skusenost","info":"
treba<\/p>","image":""},"item":{"id":"1","name":"sword","info":"its sharp<\/p>","image":"sword_anduril.png"}},"msg":"You received reward.","success":1}}
             *
             */

    /**
     * Public getters
     */

    public String getType() {
        return type;
    }

    /**
     * this response check should be used before every other actions with response, because user could be already logged out.
     *
     * @return true if we get response that user is logged out, otherwise we are logged in
     */
    public boolean isLoggedOut() {
        return loggedOut;
    }

    /**
     * isSuccessful function returns boolean which is parsed from json, if there is damaged response json or missing success info returns an false
     *
     * @return boolean
     */
    public boolean isSuccessful() {
        return success;
    }

    /**
     * getDataString function returns dataString string which is parsed from json, if there is damaged response or missing dataString returns an empty string
     *
     * @return String
     */
    public String getDataString() {
        return dataString;
    }


    public Object getData() {
        return data;
    }

    /**
     * getMessage function returns message string which is parsed from json, if there is damaged response or missing message returns an empty string
     *
     * @return String
     */
    public String getMessage() {
        return message;
    }

    public boolean isSuccessfullyParsed() {
        return successfullyParsed;
    }


}
