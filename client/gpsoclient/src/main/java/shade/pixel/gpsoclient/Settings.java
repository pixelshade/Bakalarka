package shade.pixel.gpsoclient;

import android.os.Environment;

/**
 * Created by pixelshade on 15.3.2014.
 */
public class Settings {
    private static Settings ourInstance = new Settings();

    public static Settings getInstance() {
        return ourInstance;
    }

    private static String user, pass;
    private static String serverURL = "http://bak.skeletopedia.sk";

    private static String questCompletionURL = serverURL + "/api/complete_quest";
    private static String questAcceptURL = serverURL + "/api/accept_quest";
    private static String contentFilesListURL = serverURL + "/api/getContentFilesList";
    private static String serverContentDirURL = serverURL + "/app_content/";


    private static String loginURL = serverURL + "/api/isLoggedIn";
    private static String isLoggedInURL = serverURL + "/api/isLoggedIn";
    private static String logoutURL = serverURL + "/api/logout";

    /**
     * android local file dir
     */
    private static String ContentFileDir = Environment.getExternalStorageDirectory() + "/GPSOData/";


    private Settings() {
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        Settings.user = user;
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
