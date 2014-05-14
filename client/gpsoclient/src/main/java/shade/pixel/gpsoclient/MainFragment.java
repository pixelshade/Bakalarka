package shade.pixel.gpsoclient;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by pixelshade on 11.3.2014.
 */
public class MainFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        TextView playerNameTextView = (TextView) rootView.findViewById(R.id.playerNameTextView);
        Button settingsButton = (Button) rootView.findViewById(R.id.settingsButton);

        if(Settings.getPlayerName().isEmpty()){
            playerNameTextView.setVisibility(View.GONE);
        } else {
            playerNameTextView.setText(Settings.getPlayerName());
        }

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSettingsActivity();
            }
        });





        Button updateBtn = (Button) rootView.findViewById(R.id.updatePositionBtn);
        updateBtn.setText(getString(R.string.fa_globe));

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fontawesome-webfont.ttf");
        updateBtn.setTypeface(font);


        return rootView;


    }


    private void startSettingsActivity(){
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
        startActivity(intent);
    }

}