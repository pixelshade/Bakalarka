package shade.pixel.gpsoclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by pixelshade on 11.3.2014.
 */
public class QuestsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_quests, container, false);

        ListView listViewQuests = (ListView) rootView.findViewById(R.id.listViewQuests);
        listViewQuests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), ""+i, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(),QuestInfoActitivity.class);
                intent.putExtra(QuestInfoActitivity.QUEST_ID,i);
                startActivity(intent);
            }
        });

        return rootView;
    }
}