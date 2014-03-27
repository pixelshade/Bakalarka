package shade.pixel.gpsoclient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

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

    ContentFilesManager contentFilesManager;
    public static GameData gameData;

    public GameHandler gameHandler;

    int mActualViewId = 0;

    AsyncResponse loginCheck = new AsyncResponse() {
        @Override
        public void processFinish(Context context, String output) {
            HashMap<String, String> response = ResponseJSONParser.parseResponse(output);
            if (response != null) {
                Log.d("AHA", response.toString());
                if (response.get(ResponseJSONParser.KEY_SUCCESS).equals("1")) {
                    contentFilesManager.UpdateFiles();
                } else {
                    LogoutAndStartLoginActivity(null);
                }
            } else {
                Log.d("AHA", "Unable to authenticate. Reposnse from server, is damaged");
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
        gameHandler = GameHandler.getInstance(this);


        if (htmlBrowser.isOnline()) {
//            htmlBrowser.HttpGetAsyncString(this, Settings.getIsLoggedInURL(), loginCheck);

        } else {
            Toast.makeText(this, "You have no connection to internet.", Toast.LENGTH_LONG).show();
        }

    }

    public void LogoutAndStartLoginActivity(View view) {
        final Intent intent = new Intent(this, LoginActivity.class);
        String url = Settings.getLogoutURL();
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
        htmlBrowser.HttpGetAsyncString(this, url, new AsyncResponse() {
            @Override
            public void processFinish(Context context, String output) {
                startActivity(intent);
                finish();

            }
        });
    }

    public void UpdatePosition(View view) {
        LatLng latLng = gameHandler.gpsTracker.getLatLng();
        String url = Settings.getServerURL() + "/api/json/" + latLng.latitude + "/" + latLng.longitude;
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
        htmlBrowser.HttpGetAsyncString(this, url, new AsyncResponse() {
            @Override
            public void processFinish(Context context, String json) {
                Response response = new Response(json);
                if(response.isLoggedOut()) {
                    LogoutAndStartLoginActivity(null);
                } else {
                    gameData = ResponseJSONParser.parseGameData(json);
                    gameHandler.setGameData(gameData);
                    if (gameData != null) {
                        StringBuilder sb = new StringBuilder();
                        ArrayList<Region> regions = gameData.getRegions();
                        sb.append("Regions:");
                        for (Region region : regions) {
                            sb.append(region.getName() + ",");
                        }
                        ArrayList<Quest> quests = gameData.getQuests();
                        sb.append("\nQuests:");
                        for (Quest quest : quests) {
                            quest.getName();
                            sb.append(quest.getName() + ",");
                        }
                        TextView tv = (TextView) findViewById(R.id.section_content);
                        LatLng latLng = gameHandler.gpsTracker.getLatLng();
                        tv.setText(json + "\n\n" + latLng.latitude + "" + latLng.longitude);


                        ArrayAdapter<Quest> questsArrayAdapter = new ArrayAdapter<Quest>(context, android.R.layout.simple_list_item_1, quests);
                        ListView lvQuests = (ListView) findViewById(R.id.listViewQuests);
                        if (lvQuests != null) lvQuests.setAdapter(questsArrayAdapter);

                        ArrayAdapter<Region> regionsArrayAdapter = new ArrayAdapter<Region>(context, android.R.layout.simple_list_item_1, regions);
                        ListView lvRegions = (ListView) findViewById(R.id.listViewRegions);
                        if (lvRegions != null) lvRegions.setAdapter(regionsArrayAdapter);

                        //todo treba pre kazdy fragment spravit to iste pre pripad, ze sa fragment znovu nevytvara len ho treba setnut


                    } else {
                        Log.d("AHA", "Problem with parsing gamedata");
                    }
                }
            }
        });
    }

    public void UpdateLocalContentFiles(View view) {
        contentFilesManager.UpdateFiles();
    }

    public void ScanQRCode(View view){
        Intent intent = new Intent(this,Scanner.class);
        startActivity(intent);
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
                    // Item fragment activity
                    return new ItemsFragment();
                case 4:
                    // Map fragment activity
                    return new MyMapFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.main_section).toUpperCase(l);
                case 1:
                    return getString(R.string.title_quests).toUpperCase(l);
                case 2:
                    return getString(R.string.title_region).toUpperCase(l);
                case 3:
                    return getString(R.string.title_inventory).toUpperCase(l);
                case 4:
                    return getString(R.string.title_map).toUpperCase(l);
            }
            return null;
        }
    }


}
