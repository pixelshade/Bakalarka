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
public class QuestsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_quests, container, false);

        ListView listViewQuests = (ListView) rootView.findViewById(R.id.listViewQuests);

        Log.d("AHA", "VYTVARAM FRAGMENT");

        GameHandler gameHandler = GameHandler.getInstance(getActivity());
        GameData gameData = gameHandler.getGameData();
        if (gameData != null) {
            ArrayList<Quest> quests = gameData.getQuests();

            ArrayAdapter<Quest> arrayAdapter = new ArrayAdapter<Quest>(getActivity(), android.R.layout.simple_list_item_1, quests);
            ListView lv = (ListView) rootView.findViewById(R.id.listViewQuests);
            if (lv != null) lv.setAdapter(arrayAdapter);
        }

        listViewQuests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "" + i, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), QuestInfoActitivity.class);
                intent.putExtra(QuestInfoActitivity.QUEST_INDEX_LABEL, i);
                startActivity(intent);
            }
        });

        return rootView;
    }
}