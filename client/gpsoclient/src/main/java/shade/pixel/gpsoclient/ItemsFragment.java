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
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by pixelshade on 13.3.2014.
 */
public class ItemsFragment extends Fragment {
    private View rootView;

    private static String TAG = "ItemsFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_items, container, false);
        Log.d(TAG, "VYTVARAM FRAGMENT");

        GridView gridViewItems = (GridView) rootView.findViewById(R.id.gridViewItems);
        this.updateFragment(getActivity());
        gridViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ItemInfoActivity.class);
                intent.putExtra(ItemInfoActivity.ITEM_INDEX_LABEL, i);
                startActivity(intent);
            }
        });

        return rootView;
    }

    public void updateFragment(Context context) {
        GameHandler gameHandler = GameHandler.getInstance(context);
        GameData gameData = gameHandler.getGameData();
        if(gameData!=null) {
            ArrayList<Item> items = gameData.getItems();
            if (items != null) {
                ItemAdapter itemAdapter = new ItemAdapter(context, R.layout.list_item, items);
                GridView gridViewItems = (GridView) rootView.findViewById(R.id.gridViewItems);
                if (gridViewItems != null) gridViewItems.setAdapter(itemAdapter);
            }
        }

    }

}