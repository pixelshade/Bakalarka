package shade.pixel.gpsoclient;

import java.util.ArrayList;
import java.util.Locale;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class QuestInfoActitivity extends ActionBarActivity {
    ArrayList<Quest> quests;
    public static final String QUEST_ID = "QUEST_TO_SHOW_ARRAY_ID";
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
        setContentView(R.layout.activity_quest_info_actitivity);

        quests = MainActivity.gameData.getQuests();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        Intent intent = getIntent();
        int quest_id = intent.getIntExtra(QUEST_ID, 0);
        mSectionsPagerAdapter.getItem(quest_id);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quest_info_actitivity, menu);
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

    public void CompleteQuest(View view){
        Toast.makeText(this, "completing!", Toast.LENGTH_LONG).show();

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
            // Show 3 total pages.
            return quests.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position < quests.size()){
              return quests.get(position).getName();
            }
            return null;
        }


        public Quest getQuest(int index){
            if(index < quests.size()){
            return quests.get(index);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private static Quest actualQuest;

        public static PlaceholderFragment newInstance(Quest quest) {
            PlaceholderFragment fragment = new PlaceholderFragment(quest);
            Bundle args = new Bundle();

            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment(Quest quest) {
            actualQuest = quest;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_quest_info_actitivity, container, false);

            TextView questName = (TextView) rootView.findViewById(R.id.questNameLabel);
            TextView questInfo = (TextView) rootView.findViewById(R.id.questInfoTextView);
            TextView questCompletion = (TextView) rootView.findViewById(R.id.questCompletionTextView);
            ImageView questImage = (ImageView) rootView.findViewById(R.id.questImageView);
            EditText questAnswer = (EditText) rootView.findViewById(R.id.answerEditText);
            questName.setText(actualQuest.getName());
            questInfo.setText(Html.fromHtml(actualQuest.getInfo()));
            questCompletion.setText(actualQuest.getRequirementTypeText());

            if(actualQuest.getRequirementType()==0){
                questAnswer.setVisibility(View.GONE);
            }

            String imgFilename = actualQuest.getImage();
            if(imgFilename.length()==0){
                questImage.setVisibility(View.GONE);
            } else {
               // questImage.setImageBitmap(BitmapFactory.decodeFile(imgFilename);
                // todo get here content files manager to set correct path to image
                Toast.makeText(getActivity(), "Trying to show image", Toast.LENGTH_LONG).show();
            }

            return rootView;
        }



    }

}
