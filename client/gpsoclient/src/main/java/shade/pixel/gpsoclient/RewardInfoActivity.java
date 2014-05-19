package shade.pixel.gpsoclient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;


public class RewardInfoActivity extends ActionBarActivity {
    private final String TAG = "RewardInfoActivity";
    public static final String INTENT_KEY_QR_REWARD = "REWARD_SERIALIZABLE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(Settings.gameName);
        setContentView(R.layout.activity_reward_info);
        Intent intent = getIntent();
        Reward reward = (Reward) intent.getSerializableExtra(INTENT_KEY_QR_REWARD);
        if(reward==null) {
            finish();
        } else {
            Log.d(TAG, "" + reward.getName());
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, RewardInfoFragment.newInstance(reward))
                        .commit();
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.reward_info, menu);
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
     * A placeholder fragment containing a simple view.
     */
    public static class RewardInfoFragment extends Fragment {

        public static RewardInfoFragment newInstance(Reward reward) {
            RewardInfoFragment fragment = new RewardInfoFragment();
            Bundle args = new Bundle();
            args.putSerializable("reward", reward);
            fragment.setArguments(args);
            return fragment;
        }

        public RewardInfoFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_reward_info, container, false);
            Reward reward = (Reward) getArguments().getSerializable("reward");

            TextView rewardNameTextView = (TextView) rootView.findViewById(R.id.rewardNameTextView);
            TextView attributeAmountTextView = (TextView) rootView.findViewById(R.id.attributeAmountTextView);
            TextView itemAmountTextView = (TextView) rootView.findViewById(R.id.itemAmountTextView);
            ImageView attributeImageView = (ImageView) rootView.findViewById(R.id.attributeImageView);
            ImageView itemImageView = (ImageView) rootView.findViewById(R.id.itemImageView);
            Button acceptRewardButton = (Button) rootView.findViewById(R.id.acceptRewardButton);
            if (reward != null) {
                if(!reward.getName().equals("")) rewardNameTextView.setText(reward.getName());
                attributeAmountTextView.setText((""+reward.getAttributeAmount()+"x "+ reward.getAttribute().getName()));
                itemAmountTextView.setText(""+reward.getItemAmount()+"x "+reward.getItem().getName());

                Item item = reward.getItem();
                Attribute attribute = reward.getAttribute();



                if(item.getImage().length() != 0) {
                    String filePath = Settings.getContentFileDir() + item.getImage();
                    File imageFile = new File(filePath);
                    Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                    itemImageView.setImageBitmap(bitmap);
                }

                if(attribute.getImage().length() != 0) {
                    String filePath = Settings.getContentFileDir() + attribute.getImage();
                    File imageFile = new File(filePath);
                    Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                    attributeImageView.setImageBitmap(bitmap);
                }


                acceptRewardButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().finish();
                    }
                });
            } else {
                Toast.makeText(getActivity(), "There was a problem with reward info", Toast.LENGTH_SHORT).show();
            }

            return rootView;
        }
    }
}
