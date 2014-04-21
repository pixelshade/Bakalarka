package shade.pixel.gpsoclient;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/**
 * Created by pixelshade on 11.3.2014.
 */
public class MyMapFragment extends Fragment {
    private GoogleMap map;
    private static View view;
    private SupportMapFragment fragment;


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_map, container, false);
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        FragmentManager fm = getChildFragmentManager();
//        fragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
//        if (fragment == null) {
//            fragment = SupportMapFragment.newInstance();
//            fm.beginTransaction().replace(R.id.map, fragment).commit();
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (map == null) {
//            map = fragment.getMap();
//            map.addMarker(new MarkerOptions().position(new LatLng(0, 0)));
//        }
//    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_map, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }

        initilizeMap();

        return view;
    }


    /**
     * set up the map
     */
    private void initilizeMap() {
        android.support.v4.app.FragmentManager fm = getFragmentManager();
        if (fm != null) {



            SupportMapFragment supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
            if (supportMapFragment != null) {
                map = supportMapFragment.getMap();
                if (map == null) {
                    Toast.makeText(getActivity(), "Unable to create map", Toast.LENGTH_SHORT).show();
                } else {
                    map.setMyLocationEnabled(true);
                    GameData gameData = GameHandler.gameData;
                    if (gameData != null) {
                        drawRegions(gameData.getRegions());
                    }


                    Location location = map.getMyLocation();
                    animateCameraToLocation(location);

                    map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                        @Override
                        public void onMyLocationChange(Location location) {
//                            animateCameraToLocation(location);
                        }
                    });
                }
            }

        }
    }




    private void animateCameraToLocation(Location location) {
        if (location != null) {
            LatLng myLocation = new LatLng(location.getLatitude(),
                    location.getLongitude());
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 16));
        }
    }

    public void drawRegions(ArrayList<Region> regions) {
        for (Region region : regions) {
            drawRegion(region);
        }
    }

    private void drawRegion(Region region) {
        if (map != null && region != null) {
            LatLng a = new LatLng(region.getLat_start(), region.getLon_start());
            LatLng b = new LatLng(region.getLat_end(), region.getLon_start());
            LatLng c = new LatLng(region.getLat_end(), region.getLon_end());
            LatLng d = new LatLng(region.getLat_start(), region.getLon_end());

            LatLng regionCenter = new LatLng((region.getLat_start() + region.getLat_end()) / 2,
                    (region.getLon_start() + region.getLon_end()) / 2);
            map.addPolyline(new PolylineOptions().add(a).add(b).add(c).add(d).add(a));
            map.addMarker(new MarkerOptions().position(regionCenter).title(region.getName()));
        }
    }

}