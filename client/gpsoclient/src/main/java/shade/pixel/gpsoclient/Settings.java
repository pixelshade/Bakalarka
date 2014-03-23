package shade.pixel.gpsoclient;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;

/**
 * Created by pixelshade on 15.3.2014.
 */
public class Settings {
    /** User changable variables */
    private static String username, pass;
    private static String serverURL = "http://bak.skeletopedia.sk";


    /** Server URL constants */
    private static String questCompletionURL = serverURL + "/api/complete_quest";
    private static String questAcceptURL = serverURL + "/api/accept_quest";
    private static String contentFilesListURL = serverURL + "/api/getContentFilesList";
    private static String serverContentDirURL = serverURL + "/app_content/";


    private static String loginURL = serverURL + "/api/isLoggedIn";
    private static String isLoggedInURL = serverURL + "/api/isLoggedIn";
    private static String logoutURL = serverURL + "/api/logout";



    /*
        CONSTANTS
     */

    public static final String INTENT_KEY_QRSCANNED = "QRSCANNED";

    private static String ContentFileDir = Environment.getExternalStorageDirectory() + "/GPSOData/";

    // SHARED PREFERENCES KEYS
    private static final String SHAREDPREF_USERNAME_KEY = "USER_EMAIL";

    private static final String SHAREDPREF_PASSWORD_KEY = "USER_PASS";

    private static final String SHAREDPREF_SERVER_URL_KEY = "SERVER_URL";

    private Settings() {
    }







    public static void saveLoginSettings(Context context, String username0, String pass0, String serverURL0){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHAREDPREF_USERNAME_KEY, username0);
        editor.putString(SHAREDPREF_PASSWORD_KEY, pass0);
        editor.putString(SHAREDPREF_SERVER_URL_KEY, serverURL0);
        editor.commit();
    }

    public static void loadSavedLoginSettings(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        username = sharedPreferences.getString(SHAREDPREF_USERNAME_KEY, "");
        pass = sharedPreferences.getString(SHAREDPREF_PASSWORD_KEY, "");
        serverURL = sharedPreferences.getString(SHAREDPREF_SERVER_URL_KEY, "http://bak.skeletopedia.sk");

    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Settings.username = username;
    }

    public static String getPass() {
        return pass;
    }

    public static void setPass(String pass) {
        Settings.pass = pass;
    }

    public static String getServerURL() {
        return serverURL;
    }

    public static void setServerURL(String serverURL) {
        Settings.serverURL = serverURL;
    }

    public static String getQuestCompletionURL() {
        return questCompletionURL;
    }

    public static void setQuestCompletionURL(String questCompletionURL) {
        Settings.questCompletionURL = questCompletionURL;
    }

    public static String getQuestAcceptURL() {
        return questAcceptURL;
    }

    public static void setQuestAcceptURL(String questAcceptURL) {
        Settings.questAcceptURL = questAcceptURL;
    }

    public static String getContentFilesListURL() {
        return contentFilesListURL;
    }

    public static void setContentFilesListURL(String contentFilesListURL) {
        Settings.contentFilesListURL = contentFilesListURL;
    }

    public static String getServerContentDirURL() {
        return serverContentDirURL;
    }

    public static void setServerContentDirURL(String serverContentDirURL) {
        Settings.serverContentDirURL = serverContentDirURL;
    }

    public static String getContentFileDir() {
        return ContentFileDir;
    }

    public static void setContentFileDir(String contentFileDir) {
        ContentFileDir = contentFileDir;
    }

    public static String getLoginURL() {
        return loginURL;
    }

    public static void setLoginURL(String loginURL) {
        Settings.loginURL = loginURL;
    }

    public static String getIsLoggedInURL() {
        return isLoggedInURL;
    }

    public static void setIsLoggedInURL(String isLoggedInURL) {
        Settings.isLoggedInURL = isLoggedInURL;
    }

    public static String getLogoutURL() {
        return logoutURL;
    }

    public static void setLogoutURL(String logoutURL) {
        Settings.logoutURL = logoutURL;
    }
}
