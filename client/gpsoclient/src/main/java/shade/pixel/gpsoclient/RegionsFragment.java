package shade.pixel.gpsoclient;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * Created by pixelshade on 11.3.2014.
 */
public class RegionsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_regions, container, false);

//        findView(R.id.listViewQuests);

        return rootView;


    }
}