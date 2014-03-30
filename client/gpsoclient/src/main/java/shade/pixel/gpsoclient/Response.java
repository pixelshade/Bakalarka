package shade.pixel.gpsoclient;

import java.util.HashMap;

/**
 * Created by pixelshade on 15.3.2014.
 */
public class Response {
    private static String TAG = "Response";
    private HashMap<String, String> response;
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


    public Response(String json) {
        response = ResponseJSONParser.parseResponse(json);
        type = getTypeFromResponse();
        loggedOut = isLoggetOutResponse();
        success = isSuccessfulResponse();
        message = getMessageFromResponse();
        dataString =  getDataStringFromResponse();
        data = getDataFromResponse();
    }

    private Object getDataFromResponse(){
        return null;
    }

    /** private setters */

    private String getDataStringFromResponse(){
        if (response != null)
            if (response.containsKey(ResponseJSONParser.KEY_DATA)) {
                return response.get(ResponseJSONParser.KEY_DATA);
            }
        return "";
    }

    private String getMessageFromResponse(){
        if (response != null)
            if (response.containsKey(ResponseJSONParser.KEY_MESSAGE)) {
                return response.get(ResponseJSONParser.KEY_MESSAGE);
            }
        return "";
    }

    private boolean isSuccessfulResponse(){
        if (response != null)
            if (response.containsKey(ResponseJSONParser.KEY_SUCCESS) && response.get(ResponseJSONParser.KEY_SUCCESS).equals("1")) {
                return true;
            }
        return false;
    }

    private boolean isLoggetOutResponse(){
        if(isParsedSuccessfuly()) {
            if (response.containsKey(ResponseJSONParser.KEY_TYPE) && response.get(ResponseJSONParser.KEY_TYPE).equals(TYPE_IS_LOGGED)) {
                if (response.get(ResponseJSONParser.KEY_SUCCESS).equals("0")) {
                    return true;
                }
            }
        }
        return false;
    }

    private String getTypeFromResponse(){
        if(isParsedSuccessfuly()) {
            if(response.containsKey(ResponseJSONParser.KEY_TYPE)){
                return response.get(ResponseJSONParser.KEY_TYPE);
            }
        }
        return "";
    }


    /** Public getters  */


    public boolean isParsedSuccessfuly() {
        return (response != null);
    }

    public String getType(){
       return type;
    }


    /**
     * this response check should be used before every other actions with response, because user could be already logged out.
     * @return true if we get response that user is logged out, otherwise we are logged in
     */
    public boolean isLoggedOut(){
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
    return (String) dataString;
    }

    /**
     * getMessage function returns message string which is parsed from json, if there is damaged response or missing message returns an empty string
     *
     * @return String
     */
    public String getMessage() {
       return message;
    }
}
