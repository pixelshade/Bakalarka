package shade.pixel.gpsoclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;


public class ScannerActivity extends Activity implements ZBarScannerView.ResultHandler {
    private ZBarScannerView mScannerView;
    private final String TAG = "ScannerActivity";

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZBarScannerView(this);    // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        String code = rawResult.getContents();
        Log.v(TAG, rawResult.getContents()); // Prints scan results
        Log.v(TAG, rawResult.getBarcodeFormat().getName()); // Prints the scan format (qrcode, pdf417 etc.)
        GetAsyncQRCodeResponse(code);
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.putExtra(Settings.INTENT_KEY_QRSCANNED, code);
//        startActivity(intent);

        finish();
    }

    private void StartLoginActivity() {
        Intent mLoginIntent = new Intent(this, LoginActivity.class);
        startActivity(mLoginIntent);
        finish();
    }

    private void GetAsyncQRCodeResponse(String QRScanned){
        String url = Settings.getCheckQRcodeURL() + QRScanned;
        Log.i(TAG, url);
        GameHandler.getInstance(this).getHtmlBrowser().HttpGetAsyncString  (this, url, new AsyncResponse() {
            @Override
            public void processFinish(Context context, String output) {
                Log.d(TAG, output);
                Response response = new Response(output);
                if (response.isLoggedOut()) {
                    StartLoginActivity();
                } else {
                    Log.i(TAG,"Response QR message: " + response.getMessage());
                    Log.i(TAG,"Response QR data string " + response.getDataString());
                    String responseType = response.getType();
                    if (responseType.equals(Response.TYPE_GIVE_REWARD)) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(context, RewardInfoActivity.class);
                            Reward reward = (Reward) response.getData();
                            intent.putExtra(RewardInfoActivity.INTENT_KEY_QR_REWARD, reward);
                            startActivity(intent);
                        } else {
                            Toast.makeText(context, response.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                    if (responseType.equals(Response.TYPE_ACCEPT_QUEST)) {
                        if (response.isSuccessful()) {
                            String data = response.getDataString();
                            Log.d(TAG, data);
                            Intent intent = new Intent(context, QuestInfoActivity.class);
                            Quest quest = (Quest) response.getData();
                            if(quest!=null){
                                intent.putExtra(QuestInfoActivity.INTENT_KEY_QUEST, quest);
                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(context, response.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

            }
        });
    }

}

