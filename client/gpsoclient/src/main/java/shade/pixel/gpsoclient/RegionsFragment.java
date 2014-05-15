package shade.pixel.gpsoclient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class RegionsFragment extends Fragment {
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_regions, container, false);

        this.updateFragment(getActivity());

        return rootView;


    }

    public void updateFragment(Context context){
        ListView lv = (ListView) rootView.findViewById(R.id.listViewRegions);
        GameHandler gameHandler = GameHandler.getInstance(context);
        GameData gameData = gameHandler.getGameData();
        if (gameData != null) {
            ArrayList<Region> regions = gameData.getRegions();
            RegionAdapter arrayAdapter = new RegionAdapter(context, R.layout.list_region, regions);

            if (lv != null) {
                lv.setAdapter(arrayAdapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(getActivity(), "" + i, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(), RegionInfoActivity.class);
                        intent.putExtra(RegionInfoActivity.REGION_INDEX, i);
                        startActivity(intent);
                    }
                });
            }
        }
    }
}