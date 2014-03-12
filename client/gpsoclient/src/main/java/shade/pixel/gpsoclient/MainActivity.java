  package shade.pixel.gpsoclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

  public class MainActivity extends ActionBarActivity implements ActionBar.TabListener{

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

    MyHtmlBrowser htmlBrowser;
    GPSTracker gpsTracker;
    ContentFilesManager contentFilesManager;
    public static GameData gameData;

    int mActualViewId = 0;

      /* controls */

      public ListView mlistViewQuests(){
          return  (ListView) findViewById(R.id.listViewQuests);
      }



      AsyncResponse afterLoginCheck = new AsyncResponse() {
          @Override
          public void processFinish(Context context,String output) {
              HashMap<String,String> response = ResponseJSONParser.parseResponse(output);
              Log.d("AHA",response.toString());
              if(response != null && response.get(ResponseJSONParser.KEY_SUCCESS).equals("1")){
                  contentFilesManager.UpdateFiles();
              } else {
                  LogoutAndStartLoginActivity(null);
              }

          }
      };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }


        htmlBrowser = MyHtmlBrowser.getInstance(this);
        gameData = new GameData();
        contentFilesManager = new ContentFilesManager(this);
        gpsTracker = new GPSTracker(this);


        if (htmlBrowser.isOnline()) {
            htmlBrowser.HttpGetAsyncString(this,htmlBrowser.getServerURL()+"/api/isLoggedIn",afterLoginCheck);

        }  else {
            Toast.makeText(this, "You have no connection to internet.", Toast.LENGTH_LONG).show();
        }

    }

      public void LogoutAndStartLoginActivity(View view){
          final Intent intent = new Intent(this, LoginActivity.class);
          String url = htmlBrowser.getServerURL()+"/api/logout";
          Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
          htmlBrowser.HttpGetAsyncString(this,url, new AsyncResponse() {
              @Override
              public void processFinish(Context context, String output) {
                  startActivity(intent);
                  finish();

              }
          });
      }

    public void UpdatePosition(View view){
        LatLng latLng = gpsTracker.getLatLng();
        String url = htmlBrowser.getServerURL()+"/api/json/"+latLng.latitude+"/"+latLng.longitude;
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
        htmlBrowser.HttpGetAsyncString(this, url, new AsyncResponse() {
            @Override
            public void processFinish(Context context, String json) {
                gameData = ResponseJSONParser.parseGameData(json);
                if(gameData != null){
                  //  PlaceholderFragment f = (PlaceholderFragment)mSectionsPagerAdapter.getItem(mActualViewId);
                    StringBuilder sb = new StringBuilder();
                    ArrayList<Region> regions = gameData.getRegions();
                    sb.append("Regions:");
                    for (Region region: regions){
                        sb.append(region.getName() + ",");
                    }
                    ArrayList<Quest> quests = gameData.getQuests();
                    sb.append("\nQuests:");
                    for (Quest quest: quests){
                        quest.getName();
                        sb.append(quest.getName() + ",");
                    }
                    TextView tv = (TextView) findViewById(R.id.section_content);
                    tv.setText(sb.toString());
                  //  f.setContent(sb.toString());
                    ArrayAdapter<Quest> arrayAdapter = new ArrayAdapter<Quest>(context, android.R.layout.simple_list_item_1, quests);
                    ListView lv = (ListView) findViewById(R.id.listViewQuests);
                    if(lv != null) lv.setAdapter(arrayAdapter);
                } else {
                    Log.d("AHA", "Problem with parsing gamedata");
                }
            }
        });
    }

    public void UpdateLocalContentFiles(View view){
        contentFilesManager.UpdateFiles();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        mActualViewId = id;
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
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
            switch (position) {
                case 0:
                    // Main fragment activity
                    return new MainFragment();
                case 1:
                    // Quests fragment activity
                    return new QuestsFragment();
                case 2:
                    // Region fragment activity
                    return new RegionsFragment();
                case 3:
                    // Map fragment activity
                    return new MapFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.main_section).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 3:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

















    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {


            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            int section = getArguments().getInt(ARG_SECTION_NUMBER);
            String text;
            if (section == 1) {
                text = "all";
            } else if (section == 2) {
                text = "region";
            } else if (section == 3) {
                text = "quests";
            } else {
                text = "unset";
            }


            return rootView;
        }

    }

}
