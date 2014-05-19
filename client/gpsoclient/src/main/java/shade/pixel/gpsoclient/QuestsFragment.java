package shade.pixel.gpsoclient;

import android.content.Context;
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
    private Context mContext;

    private static String TAG = "QuestFragment";
    private ArrayList<Quest> quests;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_quests, container, false);
        mContext = getActivity();
        Log.d(TAG, "VYTVARAM FRAGMENT");

        ToggleButton toggleActive = (ToggleButton) rootView.findViewById(R.id.toggleActive);
        ToggleButton toggleAvailable = (ToggleButton) rootView.findViewById(R.id.toggleAvailable);
        ToggleButton toggleComplete = (ToggleButton) rootView.findViewById(R.id.toggleComplete);
        ListView listViewQuests = (ListView) rootView.findViewById(R.id.listViewQuests);


        toggleActive.setChecked(Settings.isShowActive());
        toggleAvailable.setChecked(Settings.isShowAvailable());
        toggleComplete.setChecked(Settings.isShowCompleted());
        this.updateFragment(mContext);

        listViewQuests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), QuestInfoActivity.class);
                intent.putExtra(QuestInfoActivity.QUEST_INDEX_LABEL, i);
                startActivity(intent);
            }
        });

        toggleActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.setShowActive(isChecked);
                updateFragment(mContext);
                Settings.saveQuestsListingSettings(getActivity(), Settings.isShowActive(), Settings.isShowAvailable(), Settings.isShowCompleted());
            }
        });

        toggleAvailable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.setShowAvailable(isChecked);
                updateFragment(mContext);
                Settings.saveQuestsListingSettings(getActivity(), Settings.isShowActive(), Settings.isShowAvailable(), Settings.isShowCompleted());
            }
        });

        toggleComplete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.setShowCompleted(isChecked);
                updateFragment(mContext);
                Settings.saveQuestsListingSettings(getActivity(), Settings.isShowActive(), Settings.isShowAvailable(), Settings.isShowCompleted());
            }
        });


        return rootView;
    }

    public void updateFragment(Context context) {
        if(context == null){
            context = getActivity();
        }
        GameHandler gameHandler = GameHandler.getInstance(context);
        GameData gameData = gameHandler.getGameData();

        if (gameData != null) {
            ArrayList<Quest> quests = gameData.getQuests(Settings.isShowAvailable(), Settings.isShowActive(), Settings.isShowCompleted());
            if (quests != null) {

                questAdapter = new QuestAdapter(context, R.layout.list_quest, quests);
                ListView lv = (ListView) rootView.findViewById(R.id.listViewQuests);
                if (lv != null) {
                    lv.setAdapter(questAdapter);
                }
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        updateFragment(mContext);
    }

}