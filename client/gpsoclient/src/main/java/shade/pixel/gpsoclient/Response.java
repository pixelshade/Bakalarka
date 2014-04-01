package shade.pixel.gpsoclient;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pixelshade on 15.3.2014.
 */
public class Response {
    private static String TAG = "Response";
    //  private HashMap<String, String> response;
    private boolean successfullyParsed;
    private String type;
    private boolean success;
    private String message;
    private String dataString;
    private Object data;
    private boolean loggedOut;

    final static public String TYPE_IS_LOGGED = "IS_LOGGED";
    final static public String TYPE_LOGIN = "LOGIN";
    final static public String TYPE_ACCEPT_QUEST = "ACCEPT_QUEST";
    final static public String TYPE_COMPLETE_QUEST = "COMPLETE_QUEST";
    final static public String TYPE_CHECK_QRCODE = "CHECK_QRCODE";
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
        loggedOut = isLoggetOutResponse();
        data = getDataFromResponse(dataString, type);
    }


    private void parseJson(String json) {
        if (json != null && !json.isEmpty()) {
            try {
                JSONObject jsonResponseObj = new JSONObject(json);
                type = jsonResponseObj.optString(KEY_TYPE, "");
                success = jsonResponseObj.optString(KEY_SUCCESS, "0").equals("1");
                message = jsonResponseObj.optString(KEY_MESSAGE, "");
                dataString = jsonResponseObj.optString(KEY_DATA, "");
                //TODO logged out
                Log.d(TAG, dataString);
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

    public Reward getReward(String giveRewardJson) {
        if (type.equals(TYPE_GIVE_REWARD)) {
            Reward reward = new Reward();
            JSONObject data = null;
            try {
                data = new JSONObject(giveRewardJson);
                if (!data.isNull("attribute")) {
                    JSONObject attribute = data.getJSONObject("attribute");
                    reward.setAttributeId(attribute.optInt("id", -1));
                    reward.setAttributeName(attribute.optString("name", ""));
                    reward.setAttributeAmount(attribute.optInt("amount", 0));
                    reward.setAttributeImage(attribute.optString("image", ""));
                }

                if (!data.isNull("item")) {
                    JSONObject item = data.getJSONObject("item");
                    reward.setItemId(item.optInt("id", -1));
                    reward.setItemName(item.optString("name", ""));
                    reward.setItemAmount(item.optInt("amount", 0));
                    reward.setItemImage(item.optString("image", ""));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return reward;
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
        return dataJSON;
    }

            /*
             *
             * {"type":"COMPLETE_QUEST","success":1,"msg":"Quest was successfully completed","reward":{"type":"GIVE_REWARD","data":{"attribute":{"id":"1","name":"Skusenost","info":"
treba<\/p>","image":""},"item":{"id":"1","name":"sword","info":"its sharp<\/p>","image":"sword_anduril.png"}},"msg":"You received reward.","success":1}}
             *
             */


    /**
     * private setters
     */

    private String getDataStringFromResponse() {
//        if (response != null)
//            if (response.containsKey(ResponseJSONParser.KEY_DATA)) {
//                return response.get(ResponseJSONParser.KEY_DATA);
//            }
//        return "";
    }

    private String getMessageFromResponse() {
//        if (response != null)
//            if (response.containsKey(ResponseJSONParser.KEY_MESSAGE)) {
//                return response.get(ResponseJSONParser.KEY_MESSAGE);
//            }
//        return "";
    }

    private boolean isSuccessfulResponse() {
//        if (response != null)
//            if (response.containsKey(ResponseJSONParser.KEY_SUCCESS) && response.get(ResponseJSONParser.KEY_SUCCESS).equals("1")) {
//                return true;
//            }
//        return false;
    }

    private boolean isLoggetOutResponse() {
//        if(isParsedSuccessfuly()) {
//            if (response.containsKey(ResponseJSONParser.KEY_TYPE) && response.get(ResponseJSONParser.KEY_TYPE).equals(TYPE_IS_LOGGED)) {
//                if (response.get(ResponseJSONParser.KEY_SUCCESS).equals("0")) {
//                    return true;
//                }
//            }
//        }
//        return false;
    }

    private String getTypeFromResponse() {
//        if(isParsedSuccessfuly()) {
//            if(response.containsKey(ResponseJSONParser.KEY_TYPE)){
//                return response.get(ResponseJSONParser.KEY_TYPE);
//            }
//        }
//        return "";
    }


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
