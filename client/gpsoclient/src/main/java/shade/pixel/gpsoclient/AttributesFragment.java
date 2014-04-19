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

    private ArrayList<Attribute> attributes;
    private static String TAG = "QuestFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_quests, container, false);

        ListView listViewQuests = (ListView) rootView.findViewById(R.id.listViewQuests);
        Log.d(TAG, "VYTVARAM FRAGMENT");

        this.updateFragment();

        listViewQuests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "" + i, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), QuestInfoActivity.class);
                intent.putExtra(QuestInfoActivity.QUEST_INDEX_LABEL, i);
                startActivity(intent);
            }
        });

        return rootView;
    }


    public void updateFragment(){
        if(attributes!=null){
            ArrayAdapter<Attribute> arrayAdapter = new ArrayAdapter<Attribute>(getActivity(), android.R.layout.simple_list_item_1, attributes);
            ListView lv = (ListView) rootView.findViewById(R.id.listViewQuests);
            if (lv != null) lv.setAdapter(arrayAdapter);
        }
    }

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<Attribute> attributes) {
        this.attributes = attributes;
    }
}