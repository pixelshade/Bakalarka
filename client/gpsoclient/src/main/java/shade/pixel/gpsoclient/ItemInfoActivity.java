package shade.pixel.gpsoclient;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ItemInfoActivity extends ActionBarActivity {
    ArrayList<Item> items;
    private static Item actualItem;
    public static final String ITEM_INDEX_LABEL = "ITEM_INDEX_IN_ARRAY";
    private static Context mContext;

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
        setContentView(R.layout.activity_item_info);

        items = GameHandler.getInstance(this).getGameData().getItems();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        Intent intent = getIntent();
        int item_id = intent.getIntExtra(ITEM_INDEX_LABEL, 0);
        actualItem = items.get(item_id);
        mSectionsPagerAdapter.getItem(item_id);
        mContext = this;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.item_info_activity, menu);
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
            return PlaceholderFragment.newInstance(getItemObject(position));
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position < items.size()){
                return items.get(position).getName();
            }
            return null;
        }

        public Item getItemObject(int index){
            if(index < items.size()){
                return items.get(index);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {


        public static PlaceholderFragment newInstance(Item item) {
            actualItem = item;
            PlaceholderFragment fragment = new PlaceholderFragment();
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_item_info, container, false);
            TextView itemAmountText = (TextView) rootView.findViewById(R.id.itemInfoAmountTextView);
            TextView itemNameText = (TextView) rootView.findViewById(R.id.itemInfoNameText);
            TextView itemInfoText = (TextView) rootView.findViewById(R.id.itemInfoText);
            ImageView itemImageView = (ImageView) rootView.findViewById(R.id.itemImageView);
            Button buttonSendItem = (Button) rootView.findViewById(R.id.itemBtnSend);
            buttonSendItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendItemViaBluetooth(actualItem);
                }
            });

            itemNameText.setText(actualItem.getName());
            itemInfoText.setText(Html.fromHtml(actualItem.getInfo()));
            itemAmountText.setText(actualItem.getAmount()+"x");

            if(actualItem.getImage().length() != 0) {
                String filePath = Settings.getContentFileDir() + actualItem.getImage();
                File imageFile = new File(filePath);
                Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                itemImageView.setImageBitmap(bitmap);
            }

            return rootView;
        }

        public void sendItemViaBluetooth(Item item){
            if(item!=null) {
                Intent intent = new Intent(mContext,BluetoothActivity.class);
                intent.putExtra(BluetoothActivity.ARG_ITEM, item);
                startActivity(intent);
            }
        }
    }

}
