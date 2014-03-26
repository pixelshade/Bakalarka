package shade.pixel.gpsoclient;

import java.util.HashMap;

/**
 * Created by pixelshade on 15.3.2014.
 */
public class Response {
    HashMap<String, String> response;

    public Response(String json) {
        response = ResponseJSONParser.parseResponse(json);
    }

    final static public String TYPE_IS_LOGGED = "IS_LOGGED";
    final static public String TYPE_LOGIN = "LOGIN";
    final static public String TYPE_ACCEPT_QUEST = "ACCEPT_QUEST";
    final static public String TYPE_COMPLETE_QUEST = "COMPLETE_QUEST";
    final static public String TYPE_CHECK_QRCODE = "CHECK_QRCODE";



    public boolean isParsedSuccessfuly() {
        return (response != null);
    }


    /**
     * this response check should be used before every other actions with response, because user could be already logged out.
     * @return true if we get response that user is logged out, otherwise we are logged in
     */
    public boolean isLoggedOut(){
        if(isParsedSuccessfuly()) {
            if (response.containsKey(ResponseJSONParser.KEY_TYPE) && response.get(ResponseJSONParser.KEY_TYPE).equals(TYPE_IS_LOGGED)) {
                if (response.get(ResponseJSONParser.KEY_SUCCESS).equals("0")) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * isSuccessful function returns boolean which is parsed from json, if there is damaged response json or missing success info returns an false
     *
     * @return boolean
     */
    public boolean isSuccessful() {
        if (response != null)
            if (response.containsKey(ResponseJSONParser.KEY_SUCCESS) && response.get(ResponseJSONParser.KEY_SUCCESS).equals("1")) {
                return true;
            }
        return false;
    }

    /**
     * getData function returns data string which is parsed from json, if there is damaged response or missing data returns an empty string
     *
     * @return String
     */
    public String getData() {
        if (response != null)
            if (response.containsKey(ResponseJSONParser.KEY_DATA)) {
                return response.get(ResponseJSONParser.KEY_DATA);
            }
        return "";
    }

    /**
     * getMessage function returns message string which is parsed from json, if there is damaged response or missing message returns an empty string
     *
     * @return String
     */
    public String getMessage() {
        if (response != null)
            if (response.containsKey(ResponseJSONParser.KEY_MESSAGE)) {
                return response.get(ResponseJSONParser.KEY_MESSAGE);
            }
        return "";
    }
}
