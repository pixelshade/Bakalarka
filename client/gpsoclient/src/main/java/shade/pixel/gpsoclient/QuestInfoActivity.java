package shade.pixel.gpsoclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class QuestInfoActivity extends ActionBarActivity {
    private static String TAG = "QuestInfoActivity";
    ArrayList<Quest> quests;
    public static final String QUEST_INDEX_LABEL = "INDEX_OF_ACTUAL_QUEST";
    public static final String INTENT_KEY_QUEST = "SERIALIZABLE_QUEST";

    SectionsPagerAdapter mSectionsPagerAdapter;


    ViewPager mViewPager;
    public static Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_info);

        mActivity = this;
        Intent intent = getIntent();
        int quest_index = intent.getIntExtra(QUEST_INDEX_LABEL, 0);
        Quest quest = (Quest) intent.getSerializableExtra(INTENT_KEY_QUEST);
        if (quest != null) {
            quests = new ArrayList<Quest>(1);
            quests.add(quest);
        } else {
            quests = GameHandler.gameData.getQuests();
        }
        Log.i(TAG, quests.toString());
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

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
        private View rootView;
        private Quest actualQuest;
        private QuestTimeLeftTimer questTimeLeftTimer;

        public static PlaceholderFragment newInstance(Quest quest) {

            Log.d(TAG, "actual quest je " + quest);
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putSerializable(ARG_KEY_QUEST, quest);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_quest_info, container, false);
            actualQuest = (Quest) getArguments().getSerializable(ARG_KEY_QUEST);

            TextView questName = (TextView) rootView.findViewById(R.id.questNameLabel);
            TextView questInfo = (TextView) rootView.findViewById(R.id.questInfoTextView);
            TextView questCompletion = (TextView) rootView.findViewById(R.id.questCompletionTextView);
            ImageView questImage = (ImageView) rootView.findViewById(R.id.questImageView);
            Button questRemoveBtn = (Button) rootView.findViewById(R.id.removeButton);
            Button questCompleteButton = (Button) rootView.findViewById(R.id.completeButton);
            Button questAcceptButton = (Button) rootView.findViewById(R.id.acceptButton);

            questAcceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AcceptQuest();
                }
            });

            questCompleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CompleteQuest();
                }
            });

            questRemoveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RemoveActiveQuest();
                }
            });

            questName.setText(actualQuest.getName());
            questInfo.setText(Html.fromHtml(actualQuest.getInfo()) + "" + actualQuest.getTimeAccepted() + actualQuest.getDuration() + "Act:" + actualQuest.isActive() + "Compl:" + actualQuest.isCompleted());
            questCompletion.setText(actualQuest.getRequirementType().toString());


            if (actualQuest.isActive()) {
                changeViewActiveQuest();
                if (actualQuest.isCompleted()) {
                    changeViewCompleteQuest();
                }
            } else {
                changeViewNotActiveQuest();
            }

            if (actualQuest.getImage().length() != 0) {
                String filePath = Settings.getContentFileDir() + actualQuest.getImage();
                File imageFile = new File(filePath);
                Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                questImage.setImageBitmap(bitmap);
            }

            return rootView;
        }

        public class QuestTimeLeftTimer extends CountDownTimer {
            public QuestTimeLeftTimer(long millisInFuture, long countDownInterval) {
                super(millisInFuture, countDownInterval);
            }

            @Override
            public void onTick(long millisUntilFinished) {
                long secondsLeft = getQuestTimeLeft(actualQuest);
                setQuestTimeLeftTextView(secondsLeft);

            }

            @Override
            public void onFinish() {
                if (rootView != null) {
                    TextView timeLeftTextView = (TextView) rootView.findViewById(R.id.timeLeftTextView);
                    timeLeftTextView.setText("Time's up!");
                    Button completeButton = (Button) rootView.findViewById(R.id.completeButton);
                    if (completeButton != null) completeButton.setEnabled(false);
                }
            }
        }

        public void changeViewCompleteQuest() {
            if (rootView == null) return;
            ImageView questCompletedImage = (ImageView) rootView.findViewById(R.id.questCompletedImage);
            Button questCompleteBtn = (Button) rootView.findViewById(R.id.completeButton);
            EditText questAnswer = (EditText) rootView.findViewById(R.id.answerEditText);
            Button questRemoveBtn = (Button) rootView.findViewById(R.id.removeButton);
            TextView timeLeftTextView = (TextView) rootView.findViewById(R.id.timeLeftTextView);
            TextView questStatusTextView = (TextView) rootView.findViewById(R.id.questStatusTextView);
            if (actualQuest.getRequirementType() == Quest.QuestRequirement.input_answer) {
                questAnswer.setText(actualQuest.getRequirement());
                questAnswer.setEnabled(false);
            }
            timeLeftTextView.setVisibility(View.GONE);
            questRemoveBtn.setVisibility(View.GONE);
            questCompletedImage.setVisibility(View.VISIBLE);
            questStatusTextView.setVisibility(View.VISIBLE);

            questCompletedImage.setImageResource(R.drawable.icon_completed);
            questStatusTextView.setText("Completed");
            questCompleteBtn.setEnabled(false);
            questCompleteBtn.setText("Quest was completed");
            if (questTimeLeftTimer != null) questTimeLeftTimer.cancel();
        }

        public void changeViewActiveQuest() {
            if (rootView == null) return;
            ImageView questCompletedImage = (ImageView) rootView.findViewById(R.id.questCompletedImage);
            Button questRemoveBtn = (Button) rootView.findViewById(R.id.removeButton);
            Button questCompleteBtn = (Button) rootView.findViewById(R.id.completeButton);
            Button questAcceptBtn = (Button) rootView.findViewById(R.id.acceptButton);
            EditText questAnswer = (EditText) rootView.findViewById(R.id.answerEditText);
            TextView questStatusTextView = (TextView) rootView.findViewById(R.id.questStatusTextView);

            questAcceptBtn.setVisibility(View.GONE);
            questRemoveBtn.setVisibility(View.VISIBLE);
            questCompleteBtn.setVisibility(View.VISIBLE);
            questStatusTextView.setVisibility(View.VISIBLE);
            questCompletedImage.setVisibility(View.VISIBLE);
            questCompletedImage.setImageResource(R.drawable.icon_active);
            questStatusTextView.setText("Active");


            if (actualQuest.getRequirementType() == Quest.QuestRequirement.input_answer) {
                questAnswer.setVisibility(View.VISIBLE);
            } else {
                questAnswer.setVisibility(View.GONE);
            }

            if (actualQuest.getDuration() > -1) {   // its timed quest
                long timeLeftSeconds = getQuestTimeLeft(actualQuest);
                questTimeLeftTimer = new QuestTimeLeftTimer(timeLeftSeconds * 1000, 1000);
                questTimeLeftTimer.start();
            } else {
                TextView timeLeftTextView = (TextView) rootView.findViewById(R.id.timeLeftTextView);
                timeLeftTextView.setVisibility(View.GONE);
            }
        }

        public void changeViewNotActiveQuest() {
            if (rootView == null) return;
            ImageView questCompletedImage = (ImageView) rootView.findViewById(R.id.questCompletedImage);
            Button questRemoveBtn = (Button) rootView.findViewById(R.id.removeButton);
            Button questCompleteBtn = (Button) rootView.findViewById(R.id.completeButton);
            EditText questAnswer = (EditText) rootView.findViewById(R.id.answerEditText);
            TextView timeLeftTextView = (TextView) rootView.findViewById(R.id.timeLeftTextView);
            TextView questStatusTextView = (TextView) rootView.findViewById(R.id.questStatusTextView);

            questCompletedImage.setVisibility(View.GONE);
            questStatusTextView.setVisibility(View.GONE);
            questRemoveBtn.setVisibility(View.GONE);
            questCompleteBtn.setVisibility(View.GONE);
            questAnswer.setVisibility(View.GONE);

            if (actualQuest.getDuration() > -1) {   // its timed quest
                setQuestTimeLeftTextView(actualQuest.getDuration());
            } else {
                timeLeftTextView.setVisibility(View.GONE);
            }
        }

        private long getQuestTimeLeft(Quest quest) {
            if (quest != null) {
                long timeAcceptedSeconds = quest.getTimeAccepted().getTime() / 1000;
                long questDurationSeconds = quest.getDuration();
                long currentTimeSeconds = (System.currentTimeMillis() / 1000);
                return timeAcceptedSeconds + questDurationSeconds - currentTimeSeconds;
            } else {
                return -1;
            }
        }


        private void setQuestTimeLeftTextView(long secondsLeft) {
            if (rootView != null) {
                TextView timeLeftTextView = (TextView) rootView.findViewById(R.id.timeLeftTextView);
                if (actualQuest == null || timeLeftTextView == null) return;
                if (actualQuest.getDuration() > -1) {
                    timeLeftTextView.setText("Time Left: " + secondsLeft);
                } else {
                    timeLeftTextView.setVisibility(View.GONE);
                }
            }
        }


        /*        mostly Button events */

        public void RemoveActiveQuest() {
            int currentQuestId = actualQuest.getId();

            String removeURL = Settings.removeActiveQuestURL + currentQuestId;

            GameHandler.gameHandler.htmlBrowser.HttpGetAsyncString(mActivity, removeURL, new AsyncResponse() {
                @Override
                public void processFinish(Context context, String output) {
                    Response response = new Response(output);
                    if (response.isSuccessful()) {
                        Toast.makeText(context, response.getMessage(), Toast.LENGTH_LONG).show();
                        actualQuest.setActive(false);
                        mActivity.finish();
                    } else {
                        Toast.makeText(context, response.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

        public void CompleteQuest() {
            GameHandler gameHandler = GameHandler.getInstance(mActivity);

            int currentQuestId = actualQuest.getId();

            EditText questAnswer = (EditText) rootView.findViewById(R.id.answerEditText);
            String answer = "";
            if (questAnswer != null) answer = questAnswer.getText().toString();
            GPSTracker gpsTracker = GPSTracker.getInstance();
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

            String completionURL = Settings.getQuestCompletionURL(currentQuestId,latitude,longitude, answer);
            gameHandler.htmlBrowser.HttpGetAsyncString(mActivity, completionURL, new AsyncResponse() {
                @Override
                public void processFinish(Context context, String output) {
                    Response response = new Response(output);
                    if (response.isSuccessful()) {
                        actualQuest.setCompleted(true);

                        changeViewCompleteQuest();

                        Response rewardResponse = (Response)(response.getData());
                        if(rewardResponse.isSuccessful()){
                            Reward reward = (Reward)rewardResponse.getData();
                            if(reward!=null) {
                                Intent intent = new Intent(mActivity, RewardInfoActivity.class);
                                intent.putExtra(RewardInfoActivity.INTENT_KEY_QR_REWARD, reward);
                                startActivity(intent);
//                                MyAlertDialog myAlertDialog = MyAlertDialog.newInstance(reward);
//                                myAlertDialog.show(getFragmentManager(), "reward_popup");
                            }
                        }
                        Toast.makeText(context, response.getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, response.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }


        public void AcceptQuest() {
            GameHandler gameHandler = GameHandler.getInstance(mActivity);
            int currentQuestId = actualQuest.getId();
            String acceptURL = Settings.getQuestAcceptURL() + "/" + currentQuestId;
            gameHandler.htmlBrowser.HttpGetAsyncString(mActivity, acceptURL, new AsyncResponse() {
                @Override
                public void processFinish(Context context, String output) {
                    Response response = new Response(output);
                    if (response.isSuccessful()) {
                        actualQuest.setActive(true);
                        actualQuest.setTimeAccepted(new Date());
                        Button acceptBtn = (Button) rootView.findViewById(R.id.acceptButton);
                        if (acceptBtn != null) {
                            acceptBtn.setText("Quest was accepted");
                            acceptBtn.setEnabled(false);
                            Toast.makeText(context, response.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        changeViewActiveQuest();
                    } else {
                        Toast.makeText(context, response.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

    }
}
