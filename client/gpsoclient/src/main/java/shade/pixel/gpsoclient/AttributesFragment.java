package shade.pixel.gpsoclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by pixelshade on 11.3.2014.
 */
public class AttributesFragment extends Fragment {
    View rootView;

    private static String TAG = "AttributesFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_attributes, container, false);

        ListView listViewQuests = (ListView) rootView.findViewById(R.id.listViewAttributes);
        Log.d(TAG, "VYTVARAM FRAGMENT");

        this.updateFragment();

        return rootView;
    }


    public void updateFragment(){
        if(GameHandler.gameData==null) return;
        ArrayList<Attribute> attributes = GameHandler.gameData.getAttributes();
        if(attributes!=null){
            AttributeAdapter attributeAdapter = new AttributeAdapter(getActivity(), R.layout.list_attribute, attributes);
            ListView lv = (ListView) rootView.findViewById(R.id.listViewAttributes);
            if (lv != null) lv.setAdapter(attributeAdapter);
        }
    }

}