package shade.pixel.gpsoclient;

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
    private ArrayList<Item> items;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_items, container, false);



        Log.d(TAG, "VYTVARAM FRAGMENT");


        this.updateFragment();

        return rootView;
    }

    public void updateFragment() {
        GridView gridViewItems = (GridView) rootView.findViewById(R.id.gridViewItems);
        if (gridViewItems != null) {
            if (items != null) {
                ItemAdapter arrayAdapter = new ItemAdapter(getActivity(), R.layout.list_item, items);
                gridViewItems.setAdapter(arrayAdapter);
                gridViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(getActivity(), "" + i, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(), ItemInfoActivity.class);
                        intent.putExtra(ItemInfoActivity.ITEM_INDEX_LABEL, i);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}