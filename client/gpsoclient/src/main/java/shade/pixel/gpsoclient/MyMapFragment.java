package shade.pixel.gpsoclient;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by pixelshade on 11.3.2014.
 */
public class MyMapFragment extends Fragment {
    private GoogleMap map;
    private static View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        } else {
            view = inflater.inflate(R.layout.fragment_map, container, false);
        }

        initilizeMap();

        return view;
    }

    /**
     * set up the map
     */
    private void initilizeMap() {
        map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.fragment_map)).getMap();
        if (map == null) {
            Toast.makeText(getActivity(), "Unable to create map", Toast.LENGTH_SHORT).show();
        } else {
            map.setMyLocationEnabled(true);
            Location location = map.getMyLocation();
            animateCameraToLocation(location);
            map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    animateCameraToLocation(location);
                }
            });
        }
    }


    private void animateCameraToLocation(Location location) {
        if (location != null) {
            LatLng myLocation = new LatLng(location.getLatitude(),
                    location.getLongitude());
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 16));
        }
    }

}