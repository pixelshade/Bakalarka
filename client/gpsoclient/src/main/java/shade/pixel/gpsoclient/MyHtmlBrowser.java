package shade.pixel.gpsoclient;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
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
    private DefaultHttpClient httpClient;
    private String user, pass;
    private String serverURL = "http://bak.skeletopedia.sk";
    private ProgressDialog progressDialog;
    private Context mContext;
    private CookieStore cookieStore;
    private HttpContext localContext;


    public static MyHtmlBrowser getInstance(Context context) {
        if (instance == null) {
            instance = new MyHtmlBrowser(context);
            
        }
        instance.setmContext(context);
        return instance;
    }

    protected MyHtmlBrowser(Context context) {
        mContext = context;
        httpClient = new DefaultHttpClient();
        cookieStore = new BasicCookieStore();
        localContext = new BasicHttpContext();
        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
    }


    public boolean Login(String user, String pass) {
        setUser(user);
        setPass(pass);
        if (serverURL.equals("")) {
            Toast.makeText(mContext, "NO SERVER TO CONNECT", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            String url = serverURL + "/api/login";
            Log.d("AHA",url);
            HttpPost httppost = new HttpPost(url);

            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("email", user));
                nameValuePairs.add(new BasicNameValuePair("password", pass));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                StringBuilder result = new StringBuilder();
                HttpResponse httpresponse = httpClient.execute(httppost,localContext);
                BufferedReader br = new BufferedReader(new InputStreamReader(httpresponse.getEntity().getContent()));
                while (true) {
                    String line = br.readLine();
                    if (line == null)
                        break;
                    result.append(line + "\n");
                }
                Log.d("AHA", result.toString());
                return true;
            } catch (ClientProtocolException e) {
                 e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Is online check method
     */
    public boolean isOnline() {
        ConnectivityManager cm =  (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
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
            HttpResponse httpresponse = httpClient.execute(httpget,localContext);
            if(httpresponse == null){
                Log.d("AHA","HAHA");
            } else {
                Log.d("AHA","NENI NULL JE " + uri);
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

    public class getAsyncStringTask extends AsyncTask<String, Integer, String>{
            public AsyncResponse delegate;
            public boolean locked;

        public getAsyncStringTask(AsyncResponse delegate){
            this.delegate = delegate;
        }

            public boolean isLocked(){
                return locked;
            }

            @Override
            protected String doInBackground(String... params) {
                Integer count = 0;
                StringBuilder result = new StringBuilder();
                try {
                    httpClient = new DefaultHttpClient();
                    URI uri = new URI(params[0]);
                    Log.d("AHA","trying to get async:"+uri);
                    HttpGet httpget = new HttpGet(params[0]);

                    HttpResponse httpresponse = httpClient.execute(httpget,localContext);
                    BufferedReader br = new BufferedReader(new InputStreamReader(httpresponse.getEntity().getContent()));
                    while (true) {
                        String line = br.readLine();
                        if (line == null)
                            break;
                        result.append(line + "\n");
                        publishProgress(count++);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result.toString();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Log.d("AHA", "Async get result: "+ result);
                progressDialog.dismiss();
                delegate.processFinish(result);
                locked = false;
            }

            @Override
            protected void onPreExecute() {
                locked = true;
               // super.onPreExecute();
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


    }

    public void HttpGetAsyncString(String uristr, AsyncResponse delegate) {
        getAsyncStringTask ast = new getAsyncStringTask(delegate);
        if(!ast.isLocked())   ast.execute(uristr);
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

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }
}
