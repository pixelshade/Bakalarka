package shade.pixel.gpsoclient;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by pixelshade on 11.3.2014.
 */
public class MainFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        Button updateBtn = (Button) rootView.findViewById(R.id.updatePositionBtn);
        updateBtn.setText(getString(R.string.fa_globe));

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fontawesome-webfont.ttf");
        updateBtn.setTypeface(font);


        return rootView;


    }

}