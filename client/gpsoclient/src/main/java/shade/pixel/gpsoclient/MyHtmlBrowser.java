package shade.pixel.gpsoclient;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixelshade on 9.2.2014.
 */

public class MyHtmlBrowser {
    private static MyHtmlBrowser instance = null;
    private static DefaultHttpClient httpClient;
    private String user, pass;
    private String serverURL = "http://bak.skeletopedia.sk";
    private ProgressDialog progressDialog;
    private Context mContext;

    public static MyHtmlBrowser getInstance(Context context) {
        if (instance == null) {
            instance = new MyHtmlBrowser(context);
            httpClient = new DefaultHttpClient();
        }
        return instance;
    }

    protected MyHtmlBrowser(Context context) {
        mContext = context;
    }

    public boolean Login(String user, String pass) {
        setUser(user);
        setPass(pass);
        if (serverURL == "") {
            Toast.makeText(mContext, "NO SERVER TO CONNECT", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            HttpPost httppost = new HttpPost(serverURL + "/admin/user/login");

            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("email", user));
                nameValuePairs.add(new BasicNameValuePair("password", pass));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                StringBuilder result = new StringBuilder();
                HttpResponse httpresponse = httpClient.execute(httppost);
                BufferedReader br = new BufferedReader(new InputStreamReader(httpresponse.getEntity().getContent()));
                while (true) {
                    String line = br.readLine();
                    if (line == null)
                        break;
                    result.append(line + "\n");
                }
                Log.d("AHA", result.toString());
                //     Toast.makeText(mContext, result, Toast.LENGTH_LONG).show();
                return true;
            } catch (ClientProtocolException e) {
                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }

    private void SetMarkersFromJSON(final GoogleMap googleMap, String JSON) {

        try {
            JSONArray friends = new JSONArray(JSON);
            int poc = 0;
            for (int i = friends.length() - 1; i > -1; i--) {
                if (poc > 100) break;
                JSONObject item = friends.getJSONObject(i);
                String time = item.getString("time");
                double lati = item.getDouble("lati");
                double longi = item.getDouble("longi");
                String name = item.getString("name");
                LatLng latLng = new LatLng(lati, longi);
                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(name).snippet(time);
                googleMap.addMarker(markerOptions);
                poc++;
            }
            Log.d("DEBUG", JSON);
            Log.d("DEBUG", "" + friends.length());
            Toast.makeText(mContext, "JSON je ?" + friends.length(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }


    /**
     * GET METHODS *
     */

    public String HttpGetString(String uristr) {
        Log.d("AHA","Getting contents of: " +uristr);

        StringBuilder result = new StringBuilder();
        try{
            httpClient = new DefaultHttpClient();
            URI uri = new URI(uristr);
            HttpGet httpget = new HttpGet(uri);
            HttpResponse httpresponse = httpClient.execute(httpget);
            if(httpresponse == null){
                Log.d("AHA","HAHA");
            } else {
                Log.d("AHA","NENI NULL JE "+ uri);
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(httpresponse.getEntity().getContent()));
            while (true) {
                String line = br.readLine();
                if (line == null)
                    break;
                result.append(line + "\n");
            }
        } catch (Exception e) {
            Log.d("AHA","NENI NULL JE"+ e.toString());
            e.printStackTrace();
        }
        Log.d("AHA","and we have: " +result);
        return result.toString();
    }

    public void HttpGetAsyncString(String uristr) {
        AsyncTask<String, Integer, String> ast = new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... params) {
                Integer count = 0;
                StringBuilder result = new StringBuilder();
                try {
                    httpClient = new DefaultHttpClient();
                    URI uri = new URI(params[0]);

                    HttpGet httpget = new HttpGet(params[0]);
//                    httpget.setURI(uri);
                    HttpResponse httpresponse = httpClient.execute(httpget);
                    BufferedReader br = new BufferedReader(new InputStreamReader(httpresponse.getEntity().getContent()));
                    while (true) {
                        String line = br.readLine();
                        if (line == null)
                            break;
                        result.append(line + "\n");
                        publishProgress(count++);
                    }
                } catch (Exception e) {
                    Log.e("HttpClient", e.getMessage());
                    e.printStackTrace();
                }
                return result.toString();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                progressDialog.dismiss();
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(mContext);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMax(100);
                progressDialog.show();
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                progressDialog.incrementProgressBy(values[0]);
            }
        };
        ast.execute(uristr);
    }


    public String getServerURL() {
        return serverURL;
    }

    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
