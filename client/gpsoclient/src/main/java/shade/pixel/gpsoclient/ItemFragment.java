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
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by pixelshade on 13.3.2014.
 */
public class ItemFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_items, container, false);

        GridView gridViewItems = (GridView) rootView.findViewById(R.id.gridViewItems);

        Log.d("AHA", "VYTVARAM FRAGMENT");

        GameHandler gameHandler = GameHandler.getInstance(getActivity());
        GameData gameData = gameHandler.getGameData();
        if (gameData != null) {
            ArrayList<Item> items = gameData.getItems();

            ArrayAdapter<Item> arrayAdapter = new ArrayAdapter<Item>(getActivity(), android.R.layout.simple_list_item_1, items);

            if (gridViewItems != null) {
                gridViewItems.setAdapter(arrayAdapter);
                gridViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(getActivity(), "" + i, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(), ItemInfoActitivity.class);
                        intent.putExtra(ItemInfoActitivity.ITEM_ID, i);
                        startActivity(intent);
                    }
                });
            }
        }


        return rootView;
    }
}