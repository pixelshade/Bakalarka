package shade.pixel.gpsoclient;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by pixelshade on 11.3.2014.
 */
public class QuestsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_quests, container, false);

        ListView listviewQuests = (ListView) getActivity().findViewById(R.id.listViewQuests);

        return rootView;
    }
}