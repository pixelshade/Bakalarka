package shade.pixel.gpsoclient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;


public class RewardInfoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_info);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
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
    public static class PlaceholderFragment extends Fragment {

        public static PlaceholderFragment newInstance(Reward reward) {

            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putSerializable("reward", reward);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Reward reward = (Reward) getArguments().getSerializable("reward");

            View rootView = inflater.inflate(R.layout.fragment_reward_info, container, false);

            TextView rewardNameTextView = (TextView) rootView.findViewById(R.id.rewardNameTextView);
            TextView attributeAmountTextView = (TextView) rootView.findViewById(R.id.attributeAmountTextView);
            TextView itemAmountTextView = (TextView) rootView.findViewById(R.id.itemAmountTextView);
            ImageView attributeImageView = (ImageView) rootView.findViewById(R.id.attributeImageView);
            ImageView itemImageView = (ImageView) rootView.findViewById(R.id.itemImageView);
            Button acceptRewardButton = (Button) rootView.findViewById(R.id.acceptRewardButton);
            if (reward != null) {
                rewardNameTextView.setText(reward.getName());
                attributeAmountTextView.setText((reward.getAttributeAmount()));
                itemAmountTextView.setText(reward.getItemAmount());

                if(reward.getItemImage().length() != 0) {
                    String filePath = Settings.getContentFileDir() + reward.getItemImage();
                    File imageFile = new File(filePath);
                    Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                    itemImageView.setImageBitmap(bitmap);
                }

                if(reward.getAttributeImage().length() != 0) {
                    String filePath = Settings.getContentFileDir() + reward.getAttributeImage();
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
            }

            return rootView;
        }
    }
}
