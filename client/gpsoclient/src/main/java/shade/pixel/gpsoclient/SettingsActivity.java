package shade.pixel.gpsoclient;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


public class SettingsActivity extends ActionBarActivity {
    private static final String TAG = "SettingsACtivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

//        TextView settingsIcon = (TextView) findViewById(R.id.settingsIcon);
//        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
//        settingsIcon.setTypeface(font);

        setPlayerNameTextView(Settings.getPlayerName());

        SeekBar minDistanceSB = (SeekBar) findViewById(R.id.minDistanceSeekBar);
        minDistanceSB.setProgress((int)Settings.getPositionUpdateMinDistanceInMetres());
        setInfoTextSBDistance((int) Settings.getPositionUpdateMinDistanceInMetres());
        minDistanceSB.setOnSeekBarChangeListener(distanceSeekBarChangeListener);

        SeekBar minTimeSB = (SeekBar) findViewById(R.id.minTimeSeekBar);
        minTimeSB.setProgress((int)Settings.getPositionUpdateMinTimeInSeconds());
        setInfoTextSBTime((int)Settings.getPositionUpdateMinTimeInSeconds());
        minTimeSB.setOnSeekBarChangeListener(timeSeekBarChangeListener);

        TextView playerNameCaption = (TextView) findViewById(R.id.playerNameCaption);
        playerNameCaption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEdit();
            }
        });

        Button changeNameBtn = (Button) findViewById(R.id.changeName);
        changeNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEdit();
            }
        });


    }


    private void setInfoTextSBDistance(int distance){
        TextView tv = (TextView) findViewById(R.id.minimumDistanceTextView);
        tv.setText("Minimum update distance: "+ distance +"m");
    }

    private void setInfoTextSBTime(int time){
        TextView tv = (TextView) findViewById(R.id.minimumTimeTextView);
        tv.setText("Minimum update time: "+ time +"s");
    }

    public void saveName(final String name){
        if(name!=null && name.length()>0) {
            Log.d(TAG, name);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("name", name));
            MyHtmlBrowser.getInstance(this).HttpPostAsyncString(this, Settings.UrlSetPlayerName, nameValuePairs, new AsyncResponse() {
                @Override
                public void processFinish(Context context, String output) {
                    if (output != null && !output.isEmpty()) {
                        Response response = new Response(output);
                        Toast.makeText(context, response.getMessage(), Toast.LENGTH_LONG).show();
                        if (response.isSuccessful()) {
                            Settings.setPlayerName(name);
                            setPlayerNameTextView(name);
                        }
                    }
                }
            });
        }
    }

    public void setPlayerNameTextView(String name){
        TextView playerNameTextView =  (TextView) findViewById(R.id.settingsPlayerNameTextView);
        playerNameTextView.setText(name);
    }

    public void showEdit(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText changeNameEditText = new EditText(this);
        changeNameEditText.setText(Settings.getPlayerName());
        alert.setView(changeNameEditText);
        alert.setTitle("Change name");
//        alert.setMessage("");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String name = changeNameEditText.getText().toString().trim();
                saveName(name);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        alert.show();
    }

    private SeekBar.OnSeekBarChangeListener distanceSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Settings.setPositionUpdateMinDistanceInMetres(progress);
            setInfoTextSBDistance(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };


    private SeekBar.OnSeekBarChangeListener timeSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Settings.setPositionUpdateMinTimeInSeconds(progress);
            setInfoTextSBTime(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
