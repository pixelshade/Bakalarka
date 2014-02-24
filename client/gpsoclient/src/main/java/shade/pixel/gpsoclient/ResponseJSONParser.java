package shade.pixel.gpsoclient;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by pixelshade on 23.2.2014.
 */
public class ResponseJSONParser {
    // response key names
    // login
    public static final String KEY_MESSAGE = "msg";
    public static final String KEY_SUCCESS = "success";
    public static final String KEY_DATA = "data";


    public static HashMap<String, String> getResponse(String json) {
        if (json != null) {
            try {
                JSONObject jsonObj = new JSONObject(json);
                HashMap<String, String> response = new HashMap<String, String>();

                String success = jsonObj.getString(KEY_SUCCESS);
                String msg = jsonObj.getString(KEY_MESSAGE);
                String data = jsonObj.optString(KEY_DATA);

                HashMap<String, String> contact = new HashMap<String, String>();

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


}
