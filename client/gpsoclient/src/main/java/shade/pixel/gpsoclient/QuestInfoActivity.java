package shade.pixel.gpsoclient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.util.ArrayList;

public class QuestInfoActivity extends ActionBarActivity {
    private static String TAG = "QuestInfoActivity";
    ArrayList<Quest> quests;
    public static final String QUEST_INDEX_LABEL = "INDEX_OF_ACTUAL_QUEST";
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
        setContentView(R.layout.activity_quest_info);

        quests = MainActivity.gameData.getQuests();
        Log.i(TAG, quests.toString());
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        Intent intent = getIntent();
        int quest_index = intent.getIntExtra(QUEST_INDEX_LABEL, 0);
        mViewPager.setCurrentItem(quest_index);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quest_info_activity, menu);
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

    public void CompleteQuest(View view) {
        GameHandler gameHandler = GameHandler.getInstance(this);
        int currentQuest = mViewPager.getCurrentItem();
        int currentQuestId = quests.get(currentQuest).getId();

        EditText questAnswer = (EditText) findViewById(R.id.answerEditText);
        String answer = "";
        if (questAnswer != null) answer = questAnswer.getText().toString();
        double latitude = gameHandler.gpsTracker.getLatitude();
        double longitude = gameHandler.gpsTracker.getLongitude();

        String completionURL = Settings.getQuestCompletionURL() + "/" +currentQuestId+"/"+latitude+"/"+longitude+"/"+answer;
        gameHandler.htmlBrowser.HttpGetAsyncString(this, completionURL, new AsyncResponse() {
            @Override
            public void processFinish(Context context, String output) {
                Response response = new Response(output);
                if (response.isSuccessful()) {
                    Button completeBtn = (Button) findViewById(R.id.completeButton);
                    if (completeBtn != null) {
                        completeBtn.setText("Quest was completed");
                        completeBtn.setEnabled(false);
                        Toast.makeText(context, response.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, response.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void AcceptQuest(View view) {
        GameHandler gameHandler = GameHandler.getInstance(this);
        int currentQuest = mViewPager.getCurrentItem();
        int currentQuestId = quests.get(currentQuest).getId();
        String acceptURL = Settings.getQuestAcceptURL() + "/" + currentQuestId;
        gameHandler.htmlBrowser.HttpGetAsyncString(this, acceptURL, new AsyncResponse() {
            @Override
            public void processFinish(Context context, String output) {
                Response response = new Response(output);
                if (response.isSuccessful()) {
                    Button acceptBtn = (Button) findViewById(R.id.acceptButton);
                    if (acceptBtn != null) {
                        acceptBtn.setText("Quest was accepted");
                        acceptBtn.setEnabled(false);
                        Toast.makeText(context, response.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, response.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

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
            return PlaceholderFragment.newInstance(getQuest(position));
        }

        @Override
        public int getCount() {
            return quests.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position < quests.size()) {
                return quests.get(position).getName();
            }
            return null;
        }


        public Quest getQuest(int index) {
            if (index < quests.size()) {
                return quests.get(index);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_KEY_QUEST = "ACT_QUEST";
        public static PlaceholderFragment newInstance(Quest quest) {

            Log.d(TAG, "actual quest je "+quest);
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putSerializable(ARG_KEY_QUEST,quest);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_quest_info, container, false);
            Quest actualQuest = (Quest)getArguments().getSerializable(ARG_KEY_QUEST);

            TextView questName = (TextView) rootView.findViewById(R.id.questNameLabel);
            TextView questInfo = (TextView) rootView.findViewById(R.id.questInfoTextView);
            TextView questCompletion = (TextView) rootView.findViewById(R.id.questCompletionTextView);
            ImageView questImage = (ImageView) rootView.findViewById(R.id.questImageView);
            EditText questAnswer = (EditText) rootView.findViewById(R.id.answerEditText);
            Button questAcceptBtn = (Button) rootView.findViewById(R.id.acceptButton);

            questName.setText(actualQuest.getName());
            questInfo.setText(Html.fromHtml(actualQuest.getInfo()));
            questCompletion.setText(actualQuest.getRequirementTypeText());

            if (actualQuest.getRequirementType() != 0) {
                questAnswer.setVisibility(View.GONE);
            }

            if(actualQuest.getImage().length() != 0) {
                String filePath = Settings.getContentFileDir() + actualQuest.getImage();
                File imageFile = new File(filePath);
                Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                questImage.setImageBitmap(bitmap);
            }

            return rootView;
        }


    }

}
