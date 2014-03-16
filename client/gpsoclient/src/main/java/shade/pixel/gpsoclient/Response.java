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


    public boolean isParsedSuccessfuly() {
        return (response != null);
    }

    /**
     * isSuccessful function returns boolean which is parsed from json, if there is damaged response json or missing success info returns an false
     *
     * @return
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
     * @return
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
     * @return
     */
    public String getMessage() {
        if (response != null)
            if (response.containsKey(ResponseJSONParser.KEY_MESSAGE)) {
                return response.get(ResponseJSONParser.KEY_MESSAGE);
            }
        return "";
    }
}
