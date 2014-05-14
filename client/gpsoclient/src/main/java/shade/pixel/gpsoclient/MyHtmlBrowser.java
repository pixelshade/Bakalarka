package shade.pixel.gpsoclient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

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
    private static final String TAG = "MY_HTML_BROWSER";
    private static MyHtmlBrowser instance = null;
    private DefaultHttpClient httpClient;
    private ProgressDialog progressDialog;
    private Context mContext;
    private CookieStore cookieStore;
    private HttpContext localContext;
    private GetAsyncStringTask getAsyncStringTask;
    private PostAsyncStringTask postAsyncStringTask;


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


    public Response Register(String user, String pass, String serverURL) {
        if (serverURL.equals("")) {
            Toast.makeText(mContext, "NO SERVER TO CONNECT", Toast.LENGTH_SHORT).show();
            return null;
        } else {
            String url = serverURL + "/api/register";
            Log.d(TAG, url);
            HttpPost httppost = new HttpPost(url);

            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("email", user));
                nameValuePairs.add(new BasicNameValuePair("password", pass));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                StringBuilder result = new StringBuilder();
                HttpResponse httpresponse = httpClient.execute(httppost, localContext);
                BufferedReader br = new BufferedReader(new InputStreamReader(httpresponse.getEntity().getContent()));
                while (true) {
                    String line = br.readLine();
                    if (line == null)
                        break;
                    result.append(line + "\n");
                }
                Response response = new Response(result.toString());
                Log.d(TAG, result.toString());
                return response;
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public Response Login(String user, String pass, String serverURL) {
        Settings.setUsername(user);
        Settings.setPass(pass);
        Settings.setServerURL(serverURL);
        if (serverURL.equals("")) {
            Toast.makeText(mContext, "NO SERVER TO CONNECT", Toast.LENGTH_SHORT).show();
            return null;
        } else {
            String url = serverURL + "/api/login";
            Log.d(TAG, url);
            HttpPost httppost = new HttpPost(url);

            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("email", user));
                nameValuePairs.add(new BasicNameValuePair("password", pass));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                StringBuilder result = new StringBuilder();
                HttpResponse httpresponse = httpClient.execute(httppost, localContext);
                BufferedReader br = new BufferedReader(new InputStreamReader(httpresponse.getEntity().getContent()));
                while (true) {
                    String line = br.readLine();
                    if (line == null)
                        break;
                    result.append(line + "\n");
                }
                Response response = new Response(result.toString());  //.parseResponse(result.toString());
                Log.d(TAG, result.toString());
                return response;


            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void ChangePlayerName(String name, AsyncResponse asyncResponse){
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("name", name));
        HttpPostAsyncString(mContext,Settings.UrlSetPlayerName, nameValuePairs ,asyncResponse);
    }

    public String HttpPostString(String url, List<NameValuePair> nameValuePairs) {
        Log.d(TAG, "sending post request to this url:"+ url);
        HttpPost httppost = new HttpPost(url);
        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            StringBuilder result = new StringBuilder();
            HttpResponse httpresponse = httpClient.execute(httppost, localContext);
            BufferedReader br = new BufferedReader(new InputStreamReader(httpresponse.getEntity().getContent()));
            while (true) {
                String line = br.readLine();
                if (line == null)
                    break;
                result.append(line + "\n");
            }
            Log.d(TAG, result.toString());
            return result.toString();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }




    public class PostAsyncStringTask extends AsyncTask<Object, Integer, String> {
        public AsyncResponse delegate;
        public boolean locked;
        private Context context;

        public PostAsyncStringTask(Context context, AsyncResponse delegate) {
            this.delegate = delegate;
            this.context = context;
        }

        public boolean isLocked() {
            return locked;
        }

        @Override
        protected String doInBackground(Object... params) {
            Integer count = 0;
            StringBuilder result = new StringBuilder();
            String url = (String)params[0];
            List<NameValuePair> post = (List<NameValuePair>)params[1];

            return HttpPostString(url,post);

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(TAG, "Async post result: " + result);
            if(delegate!=null)
            delegate.processFinish(context, result);
            locked = false;
        }

        @Override
        protected void onPreExecute() {
            locked = true;
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMax(100);
            Log.d(TAG, context.toString());
            if (!((Activity) context).isFinishing()) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
//                        progressDialog.show();
                }
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.incrementProgressBy(values[0]);
        }


    }

    public void HttpPostAsyncString(Context context, String uristr, List<NameValuePair> postPairs, AsyncResponse delegate) {
        if(postAsyncStringTask==null) postAsyncStringTask = new PostAsyncStringTask(context, delegate);
        if (!postAsyncStringTask.isLocked()) postAsyncStringTask.execute(uristr, postPairs);
    }











    /**
     * Is online check method
     */
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null) && netInfo.isConnectedOrConnecting();
    }


    /**
     * GET METHODS *
     */

    public String HttpGetString(String uristr) {
        Log.d(TAG, "Getting contents of: " + uristr);

        StringBuilder result = new StringBuilder();
        try {
            httpClient = new DefaultHttpClient();
            URI uri = new URI(uristr);
            HttpGet httpget = new HttpGet(uri);
            HttpResponse httpresponse = httpClient.execute(httpget, localContext);
            if (httpresponse == null) {
                Log.d(TAG, "Httpresponse je NULL");
            } else {
                Log.d(TAG, "Httpresponse nie je NULL. Je " + uri);
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(httpresponse.getEntity().getContent()));
            while (true) {
                String line = br.readLine();
                if (line == null)
                    break;
                result.append(line + "\n");
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception> " + e.toString());
            e.printStackTrace();
        }
        Log.d(TAG, "Result of getAsyncString: " + result);
        return result.toString();
    }

    public class GetAsyncStringTask extends AsyncTask<String, Integer, String> {
        public AsyncResponse delegate;
        public boolean locked;
        private Context context;

        public GetAsyncStringTask(Context context, AsyncResponse delegate) {
            this.delegate = delegate;
            this.context = context;
        }

        public boolean isLocked() {
            return locked;
        }

        @Override
        protected String doInBackground(String... params) {
            Integer count = 0;
            StringBuilder result = new StringBuilder();
            try {
                httpClient = new DefaultHttpClient();
                URI uri = new URI(params[0]);
                Log.d(TAG, "trying to get async:" + uri);
                HttpGet httpget = new HttpGet(params[0]);
                httpClient.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);

                HttpResponse httpresponse = httpClient.execute(httpget, localContext);
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
            Log.d(TAG, "Async get result: " + result);
            if (!((Activity) context).isFinishing()) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }
            delegate.processFinish(context, result);
            locked = false;
        }

        @Override
        protected void onPreExecute() {
            locked = true;
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMax(100);
            Log.d(TAG, context.toString());
            if (!((Activity) context).isFinishing()) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
//                        progressDialog.show();
                }
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.incrementProgressBy(values[0]);
        }


    }

    public void HttpGetAsyncString(Context context, String uristr, AsyncResponse delegate) {
        getAsyncStringTask = new GetAsyncStringTask(context, delegate);
        if (!getAsyncStringTask.isLocked()) getAsyncStringTask.execute(uristr);
    }


    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }
}
