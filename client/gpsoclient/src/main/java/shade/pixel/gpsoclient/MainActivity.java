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
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {
    private static final String TAG = "MainActivity";
    private static final String SAVED_INSTANCE_QR_KEY = "QR";
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    MyHtmlBrowser htmlBrowser;

    ContentFilesManager contentFilesManager;
//    public static GameData gameData;
    public GPSTracker gpsTracker;
    public GameHandler gameHandler;

    int mActualViewId = 0;

    AsyncResponse loginCheck = new AsyncResponse() {
        @Override
        public void processFinish(Context context, String output) {
            HashMap<String, String> response = ResponseJSONParser.parseResponse(output);
            if (response != null) {
                Log.d(TAG, response.toString());
                if (response.get(ResponseJSONParser.KEY_SUCCESS).equals("1")) {
                    contentFilesManager.UpdateFiles();
                } else {
                    StartLoginActivity();
                }
            } else {
                Log.d(TAG, "Unable to authenticate. Reposnse from server, is damaged");
                StartLoginActivity();
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Settings.loadAllSettings(this);
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
                            .setTabListener(this)
            );
        }

        htmlBrowser = MyHtmlBrowser.getInstance(this);
//        gameData = new GameData();
        contentFilesManager = new ContentFilesManager(this);
        gameHandler = GameHandler.getInstance(this);
        gpsTracker = GPSTracker.getInstance(this, this);


        if (htmlBrowser.isOnline()) {
//            htmlBrowser.HttpGetAsyncString(this, Settings.getIsLoggedInURL(), loginCheck);
            contentFilesManager.UpdateFiles();
        } else {
            Toast.makeText(this, "You have no connection to internet.", Toast.LENGTH_LONG).show();
        }

    }

    public void StartBluetoothActivity(View view) {
        Intent mBluetoothIntent = new Intent(this, BluetoothActivity.class);
        startActivity(mBluetoothIntent);

    }

    public void StartLoginActivity() {
        if(gpsTracker!=null)        gpsTracker.stopUsingGPS();
        Intent mLoginIntent = new Intent(this, LoginActivity.class);
        startActivity(mLoginIntent);
        finish();
    }

    public void LogoutAndStartLoginActivity(View view) {
        if(gpsTracker!=null)        gpsTracker.stopUsingGPS();
        String url = Settings.getLogoutURL();
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
        final Intent mLoginIntent = new Intent(this, LoginActivity.class);
        htmlBrowser.HttpGetAsyncString(this, url, new AsyncResponse() {
            @Override
            public void processFinish(Context context, String output) {
                startActivity(mLoginIntent);
                finish();

            }
        });
    }

    public void UpdatePosition(View view) {
//        gpsTracker = new GPSTracker(this,this);
        if (gpsTracker == null) {
            Log.d(TAG, "GPS tracker is null");
            return;
        }
        LatLng location = gpsTracker.getLocation();
        double latitude = location.latitude;
        double longitude = location.longitude;
        if (!gpsTracker.canGetLocation()) {
            gpsTracker.showSettingsAlert();
        } else {

            String url = Settings.getServerURL() + "/api/json/" + latitude + "/" + longitude;
            Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
            htmlBrowser.HttpGetAsyncString(this, url, new AsyncResponse() {
                @Override
                public void processFinish(Context context, String json) {
                    Response response = new Response(json);
                    if (response.isLoggedOut()) {
                        StartLoginActivity();
                    } else {
                        GameData gameData = ResponseJSONParser.parseGameData(json);
                        gameHandler.setGameData(gameData);
                        if (gameData != null) {
                            StringBuilder sb = new StringBuilder();
                            ArrayList<Region> regions = gameData.getRegions();
                            ArrayList<Quest> quests = gameData.getQuests();
                            ArrayList<Item> items = gameData.getItems();
                            sb.append("Regions:");
                            for (Region region : regions) {
                                sb.append(region.getName() + ",");
                            }
                            sb.append("\nQuests:");
                            for (Quest quest : quests) {
                                quest.getName();
                                sb.append(quest.getName() + ",");
                            }
                            double latitude = gpsTracker.getLatitude();
                            double longitude = gpsTracker.getLongitude();
                            String infoString = json + "\n\n" + latitude + " " + longitude;
                            SetTextView(infoString);

                            SetQuestsView();
                            SetRegionsView();
                            SetItemsView();
                            SetAttributesView();
                            showResponses();

                        } else {
                            Log.d(TAG, "Problem with parsing gamedata");
                        }
                    }
                }
            });
        }

    }


    public void SetTextView(String text) {
        TextView tv = (TextView) findViewById(R.id.section_content);
        if (tv != null)
            tv.setText(text);
    }

    public void showResponses(){
        GameHandler gameHandler = GameHandler.getInstance(this);
        GameData gameData = gameHandler.getGameData();
        for(Response response :gameData.getSuccessfullResponses()){
            if(response.getType().equals(Response.TYPE_ACCEPT_QUEST)){
                    String data = response.getDataString();
                    Log.d(TAG, data);
                    Intent intent = new Intent(this, QuestInfoActivity.class);
                    Quest quest = (Quest) response.getData();
                    if(quest!=null){
                        intent.putExtra(QuestInfoActivity.INTENT_KEY_QUEST, quest);
                        startActivity(intent);
                    }
            }
        }

    }

    public void SetQuestsView() {
        QuestsFragment questsFragment = (QuestsFragment) mSectionsPagerAdapter.getRegisteredFragment(SectionsPagerAdapter.questsFragmentId);
        if (questsFragment != null) {
            questsFragment.updateFragment(this);
        } else {
            Log.d(TAG, "Quests view is null.");
        }
    }

    public void SetRegionsView() {
        RegionsFragment regionFragment = (RegionsFragment) mSectionsPagerAdapter.getRegisteredFragment(SectionsPagerAdapter.regionsFragmentId);
        if (regionFragment != null) {
            regionFragment.updateFragment();
        } else {
            Log.d(TAG, "Regions view is null.");
        }
    }

    public void SetItemsView() {
        ItemsFragment itemsFragment = (ItemsFragment) mSectionsPagerAdapter.getRegisteredFragment(SectionsPagerAdapter.itemsFragmentId);
        if (itemsFragment != null) {
            itemsFragment.updateFragment();
        } else {
            Log.d(TAG, "Regions view is null.");
        }
    }

    public void SetAttributesView() {
        AttributesFragment attributesFragment = (AttributesFragment) mSectionsPagerAdapter.getRegisteredFragment(SectionsPagerAdapter.attributesFragmentId);
        if (attributesFragment != null) {
            attributesFragment.updateFragment();
        } else {
            Log.d(TAG, "Attribute view is null.");
        }
    }


    public void UpdateLocalContentFiles(View view) {
        contentFilesManager.UpdateFiles();
    }

    public void ScanQRCode(View view) {
        Intent intent = new Intent(this, ScannerActivity.class);
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
        private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public static final int mainFragmentId = 0;
        public static final int questsFragmentId = 1;
        public static final int regionsFragmentId = 2;
        public static final int itemsFragmentId = 3;
        public static final int attributesFragmentId = 4;
        public static final int mapFragmentId = 5;


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            GameData gameData = gameHandler.getGameData();
            switch (position) {
                case mainFragmentId:
                    // Main fragment activity
                    return new MainFragment();
                case questsFragmentId:
                    // Quests fragment activity
                    return new QuestsFragment();
                case regionsFragmentId:
                    // Region fragment activity
                    return new RegionsFragment();
                case itemsFragmentId:
                    // Item fragment activity
                    return new ItemsFragment();
                case attributesFragmentId:
                    // Attributes fragment activity
                    return new AttributesFragment();
                case mapFragmentId:
                    // Map fragment activity
                    return new MyMapFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case mainFragmentId:
                    return getString(R.string.main_section).toUpperCase(l);
                case questsFragmentId:
                    return getString(R.string.title_quests).toUpperCase(l);
                case regionsFragmentId:
                    return getString(R.string.title_region).toUpperCase(l);
                case itemsFragmentId:
                    return getString(R.string.title_inventory).toUpperCase(l);
                case attributesFragmentId:
                    return getString(R.string.title_attributes).toUpperCase(l);
                case mapFragmentId:
                    return getString(R.string.title_map).toUpperCase(l);
            }
            return null;
        }
    }


}
