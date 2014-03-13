package shade.pixel.gpsoclient;

import java.util.ArrayList;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RegionInfoActitivity extends ActionBarActivity {
    ArrayList<Region> regions;
    public static final String REGION_ID = "REGION_TO_SHOW_ARRAY_ID";
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_info_actitivity);

        regions = MainActivity.gameData.getRegions();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        Intent intent = getIntent();
        int region_id = intent.getIntExtra(REGION_ID, 0);
        mSectionsPagerAdapter.getItem(region_id);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.region_info_actitivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(getRegion(position));
        }

        @Override
        public int getCount() {
            return regions.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position < regions.size()){
                return regions.get(position).getName();
            }
            return null;
        }

        public Region getRegion(int index){
            if(index < regions.size()){
                return regions.get(index);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private static Region actualRegion;

        public static PlaceholderFragment newInstance(Region region) {
            PlaceholderFragment fragment = new PlaceholderFragment(region);
            return fragment;
        }

        public PlaceholderFragment(Region region) {
            actualRegion = region;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_region_info_actitivity, container, false);
            TextView regionNameText = (TextView) rootView.findViewById(R.id.regionNameText);
            regionNameText.setText(actualRegion.getName());
            TextView regionInfoText = (TextView) rootView.findViewById(R.id.regionInfoText);
            regionInfoText.setText(Html.fromHtml(actualRegion.getInfo()));
            return rootView;
        }
    }

}
