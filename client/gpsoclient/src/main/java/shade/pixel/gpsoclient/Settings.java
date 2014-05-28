package shade.pixel.gpsoclient;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by pixelshade on 15.3.2014.
 */
public class Settings {
    /**
     * User changable variables
     */
    private static int playerId;
    private static String playerName;
    private static String username, pass;
    private static String serverURL = "http://bak.skeletopedia.sk";

    private static boolean showAvailable = true;
    private static boolean showActive = true;
    private static boolean showCompleted = true;

    public static String gameName = "";

    private static boolean automaticStartupFilesUpdate = true;

    private static long defeaultIpdateMinTime = 1;
    private static long defeaultIpdateMinDistance = 2;
    private static long positionUpdateMinTimeInSeconds = defeaultIpdateMinTime;
    private static long positionUpdateMinDistanceInMetres = defeaultIpdateMinDistance;

    /**
     * Server URL constants
     */
    private static String UrlUpdatePosition = serverURL + "/api/json/";
    private static String questCompletionURL = serverURL + "/api/complete_quest";
    private static String questAcceptURL = serverURL + "/api/accept_quest";
    private static String checkQRcodeURL = serverURL + "/api/check_qrcode/";
    private static String contentFilesListURL = serverURL + "/api/getContentFilesList";
    private static String serverContentDirURL = serverURL + "/app_content/";
    public static String removeActiveQuestURL =  serverURL + "/api/remove_my_active_quest/";
    private static String sendItemURL = serverURL + "/api/char_give_item_to_char/";

    private static String loginURL = serverURL + "/api/loggin";
    private static String isLoggedInURL = serverURL + "/api/isLoggedIn";
    private static String logoutURL = serverURL + "/api/logout";
    public static final String UrlSetPlayerName =  serverURL + "/api/set_my_name";

    /*
        CONSTANTS
     */

    public static final String QR_REWARD = "R";
    public static final String QR_QUEST = "Q";






    public static final String INTENT_KEY_QRSCANNED = "QRSCANNED";

    private static String ContentFileDir = Environment.getExternalStorageDirectory() + "/GPSOData/"; //TODO Ability to change directory

    // SHARED PREFERENCES KEYS
    private static final String SHAREDPREF_STARTUP_FILES_UPDATE_KEY = "START_UP_FILES_UPDATE";

    private static final String SHAREDPREF_TRACKING_MINTIME_KEY = "MIN_TIME";

    private static final String SHAREDPREF_TRACKING_MINDISTANCE_KEY = "MIN_DISTANCE";

    private static final String SHAREDPREF_USERNAME_KEY = "USER_EMAIL";

    private static final String SHAREDPREF_PASSWORD_KEY = "USER_PASS";

    private static final String SHAREDPREF_SERVER_URL_KEY = "SERVER_URL";

    private static final String SHAREDPREF_SHOW_ACTIVE = "SHOW_ACTIVE";

    private static final String SHAREDPREF_SHOW_AVAILABLE = "SHOW_AVAILABLE";

    private static final String SHAREDPREF_SHOW_COMPLETED = "SHOW_COMPLETED";


    private Settings() {
    }


    public static void loadAllSettings(Context context){
        loadQuestsListingSettings(context);
        loadSavedTrackingSettings(context);
        loadAutomaticFilesUpdateSettings(context);
    }

    public static void saveLoginSettings(Context context, String username0, String pass0, String serverURL0) {
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

    public static void loadSavedTrackingSettings(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        positionUpdateMinDistanceInMetres = sharedPreferences.getLong(SHAREDPREF_TRACKING_MINDISTANCE_KEY, defeaultIpdateMinDistance);
        positionUpdateMinTimeInSeconds = sharedPreferences.getLong(SHAREDPREF_TRACKING_MINTIME_KEY, defeaultIpdateMinTime);
    }


    public static void saveTrackingSettings(Context context, long minDistance, long minTime){
        positionUpdateMinDistanceInMetres = minDistance;
        positionUpdateMinTimeInSeconds = minTime;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(SHAREDPREF_TRACKING_MINDISTANCE_KEY, minDistance);
        editor.putLong(SHAREDPREF_TRACKING_MINTIME_KEY, minTime);
        editor.commit();
    }

    public static void loadAutomaticFilesUpdateSettings(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        automaticStartupFilesUpdate = sharedPreferences.getBoolean(SHAREDPREF_STARTUP_FILES_UPDATE_KEY, true);
    }


    public static void saveAutomaticFilesUpdateSettings(Context context, boolean startupUpdate){
        automaticStartupFilesUpdate = startupUpdate;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SHAREDPREF_STARTUP_FILES_UPDATE_KEY, startupUpdate);
        editor.commit();
    }

    public static void saveQuestsListingSettings(Context context, boolean showActive, boolean showAvailable, boolean showCompleted){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SHAREDPREF_SHOW_ACTIVE, showActive);
        editor.putBoolean(SHAREDPREF_SHOW_AVAILABLE, showAvailable);
        editor.putBoolean(SHAREDPREF_SHOW_COMPLETED, showCompleted);
        editor.commit();
    }


    public static void loadQuestsListingSettings(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        showActive = sharedPreferences.getBoolean(SHAREDPREF_SHOW_ACTIVE, true);
        showAvailable = sharedPreferences.getBoolean(SHAREDPREF_SHOW_AVAILABLE, true);
        showCompleted = sharedPreferences.getBoolean(SHAREDPREF_SHOW_COMPLETED, true);

    }


    public static int getPlayerId() {
        return playerId;
    }

    public static void setPlayerId(int playerId) {
        Settings.playerId = playerId;
    }

    public static String getPlayerName() {
        return playerName;
    }

    public static void setPlayerName(String name) {
        Settings.playerName = name;
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

    public static String getQuestCompletionURL(int questId, double latitude, double longitude, String answer) {
        return questCompletionURL + "/" + questId + "/" + latitude + "/" + longitude + "/" + answer;
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

    public static String getCheckQRcodeURL() {
        return checkQRcodeURL;
    }

    public static void setCheckQRcodeURL(String checkQRcodeURL) {
        Settings.checkQRcodeURL = checkQRcodeURL;
    }

    public static boolean isShowAvailable() {
        return showAvailable;
    }

    public static void setShowAvailable(boolean showAvailable) {
        Settings.showAvailable = showAvailable;
    }

    public static boolean isShowActive() {
        return showActive;
    }

    public static void setShowActive(boolean showActive) {
        Settings.showActive = showActive;
    }

    public static boolean isShowCompleted() {
        return showCompleted;
    }

    public static void setShowCompleted(boolean showCompleted) {
        Settings.showCompleted = showCompleted;
    }

    public static long getPositionUpdateMinTimeInSeconds() {
        return positionUpdateMinTimeInSeconds;
    }

    public static void setPositionUpdateMinTimeInSeconds(int positionUpdateMinTimeInSeconds) {
        Settings.positionUpdateMinTimeInSeconds = positionUpdateMinTimeInSeconds;
    }

    public static long getPositionUpdateMinDistanceInMetres() {
        return positionUpdateMinDistanceInMetres;
    }

    public static void setPositionUpdateMinDistanceInMetres(int positionUpdateMinDistanceInMetres) {
        Settings.positionUpdateMinDistanceInMetres = positionUpdateMinDistanceInMetres;
    }

    public static boolean isAutomaticStartupFilesUpdate() {
        return automaticStartupFilesUpdate;
    }

    public static void setAutomaticStartupFilesUpdate(boolean automaticStartupFilesUpdate) {
        Settings.automaticStartupFilesUpdate = automaticStartupFilesUpdate;
    }

    public static String getSendItemURL(int itemId, int amount, int receiverId) {
        return sendItemURL + itemId +"/"+amount+"/"+receiverId;
    }

    public static void setSendItemURL(String sendItemURL) {
        Settings.sendItemURL = sendItemURL;
    }

    public static String getUrlUpdatePosition() {
        return UrlUpdatePosition;
    }
    public static String getUrlUpdatePosition(double latitude, double longtitude) {
        return UrlUpdatePosition + latitude + "/" + longtitude;
    }

}
