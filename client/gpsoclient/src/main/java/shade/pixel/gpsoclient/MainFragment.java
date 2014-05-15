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

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

/**
 * Created by pixelshade on 11.3.2014.
 */
public class MainFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//        TextView playerNameTextView = (TextView) rootView.findViewById(R.id.playerNameTextView);

//        if(Settings.getPlayerName().isEmpty()){
//            playerNameTextView.setVisibility(View.GONE);
//        } else {
//            playerNameTextView.setText(Settings.getPlayerName());
//        }

        Button settingsButton = (Button) rootView.findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSettingsActivity();
            }
        });
        Iconify.addIcons(settingsButton);



        Button logoutBtn = (Button) rootView.findViewById(R.id.logoutBtn);
        Iconify.addIcons(logoutBtn);

        Button updateBtn = (Button) rootView.findViewById(R.id.updatePositionBtn);
        Iconify.addIcons(updateBtn);


        return rootView;


    }


    private void startSettingsActivity(){
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
        startActivity(intent);
    }

}