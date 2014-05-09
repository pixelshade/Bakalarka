package shade.pixel.gpsoclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

/**
 * Created by pixelshade on 11.3.2014.
 */
public class QuestsFragment extends Fragment {
    private View rootView;
    private QuestAdapter questAdapter;

    private static String TAG = "QuestFragment";
    private ArrayList<Quest> quests;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_quests, container, false);
        Log.d(TAG, "VYTVARAM FRAGMENT");

        ToggleButton toggleActive = (ToggleButton) rootView.findViewById(R.id.toggleActive);
        ToggleButton toggleAvailable = (ToggleButton) rootView.findViewById(R.id.toggleAvailable);
        ToggleButton toggleComplete = (ToggleButton) rootView.findViewById(R.id.toggleComplete);
        ListView listViewQuests = (ListView) rootView.findViewById(R.id.listViewQuests);

        toggleActive.setChecked(Settings.isShowActive());
        toggleAvailable.setChecked(Settings.isShowAvailable());
        toggleComplete.setChecked(Settings.isShowCompleted());
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

        toggleActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.setShowActive(isChecked);
                updateFragment();
//                questAdapter.setShowActive(isChecked);
//                questAdapter.getFilter().filter("");
            }
        });

        toggleAvailable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.setShowAvailable(isChecked);
                updateFragment();
//                questAdapter.setShowAvailable(isChecked);
//                questAdapter.getFilter().filter("");
            }
        });

        toggleComplete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.setShowCompleted(isChecked);
                updateFragment();
//                questAdapter.setShowComplete(isChecked);
//                questAdapter.getFilter().filter("");
            }
        });



        return rootView;
    }

    public void updateFragment() {
        GameHandler gameHandler = GameHandler.getInstance(getActivity());
        GameData gameData = gameHandler.getGameData();

        if(gameData!=null) {
            ArrayList<Quest> quests = gameData.getQuests(Settings.isShowAvailable(),Settings.isShowActive(),Settings.isShowCompleted());
            if (quests != null) {

                questAdapter = new QuestAdapter(getActivity(), R.layout.list_quest, quests);
                ListView lv = (ListView) rootView.findViewById(R.id.listViewQuests);
                if (lv != null) {
                    lv.setAdapter(questAdapter);
                }
            }
        }
    }


    @Override
    public void onResume(){
        super.onResume();
        updateFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Settings.saveQuestsListingSettings(getActivity(),Settings.isShowActive(),Settings.isShowAvailable(),Settings.isShowCompleted());
    }
}