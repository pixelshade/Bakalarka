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
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

/**
 * Created by pixelshade on 9.2.2014.
 */

public class MyHtmlBrowser {
    private DefaultHttpClient httpClient;
    private ProgressDialog progressDialog;
    private Context mContext;

    public MyHtmlBrowser(Context context) {
        mContext = context;
    }

    private void SetCredenatials(String login, String pass) {
        CredentialsProvider credProvider = new BasicCredentialsProvider();
        credProvider.setCredentials(
                new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
                new UsernamePasswordCredentials(login, pass));
        httpClient.setCredentialsProvider(credProvider);
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
            Toast.makeText(mContext, e.toString(), Toast.LENGTH_LONG).show();
        }


    }

    public void HttpGetAsyncString(String uristr, final GoogleMap kam) {
        AsyncTask<String, Integer, String> ast = new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... params) {
                Integer count = 0;
                StringBuilder result = new StringBuilder();
                try {
                    httpClient = new DefaultHttpClient();
                    URI uri = new URI(params[0]);

                    // Basic Authorization
                    SetCredenatials("java", "vaja");

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

                if (!result.contains("Insert successfull")) {
                    SetMarkersFromJSON(kam, result);
                }
                Log.d("DEBUG", result);
//                progressDialog.dismiss();progressDialog.dismiss();
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(mContext);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMax(100);
//                progressDialog.show();
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                progressDialog.incrementProgressBy(values[0]);
            }
        };
        ast.execute(uristr);
    }


}
