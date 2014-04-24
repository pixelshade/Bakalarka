package shade.pixel.gpsoclient;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class BluetoothActivity extends ActionBarActivity {
    private static final String TAG = "BLUETOOTH ACTIVITY";


    private static final int REQUEST_CONNECT_BT = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    public static final int READ = 1;
    public static final int WRITE = 2;

    private EditText edTxt;
    private ArrayAdapter<String> arrayAdapter;
    private StringBuffer messages;
    private BluetoothAdapter btAdapter = null;
    private BTHandler chat = null;


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_bluetooth);
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
//        }
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.bluetooth, menu);
//        return true;
//    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        if (!btAdapter.isEnabled())
            startActivityForResult(new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BT);
        else if (chat == null)
            init();


    }

    @Override
    public void onResume() {
        super.onResume();
        if (chat != null)
            if (chat.state.equals("NONE"))
                chat.start();
    }

    private void init() {
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.message);
        Log.d(TAG, "ARRAY "+ arrayAdapter);
        ListView lv = (ListView) findViewById(R.id.in);
        if(lv==null){
            Log.d(TAG, "LV JE NUL");
        } else
        lv.setAdapter(arrayAdapter);

        edTxt = (EditText) findViewById(R.id.edTxt);
        edTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL
                        && event.getAction() == KeyEvent.ACTION_UP) {
                    sendMessage(view.getText().toString());
                }
                return true;
            }
        });
        chat = new BTHandler(this, handler);
        messages = new StringBuffer("");
    }

    private void sendMessage(String message) {
        if (!chat.state.equals("CONNECTED")) {
            Toast.makeText(this, "not connected", Toast.LENGTH_SHORT).show();
            return;
        }
        if (message.length() > 0) {
            byte[] send = message.getBytes();
            chat.write(send);
            messages.setLength(0);
            edTxt.setText(messages);
        } else
            Toast.makeText(this, "empty message", Toast.LENGTH_SHORT).show();

    }

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WRITE:
                    arrayAdapter.add("->" + new String((byte[]) msg.obj));
                    break;
                case READ:
                    arrayAdapter.add("<-"
                            + new String((byte[]) msg.obj, 0, msg.arg1));
                    break;
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_BT:
                if (resultCode == Activity.RESULT_OK) {
                    String address = data.getExtras().getString(
                            DeviceListActivity.DEVICE_ADDRESS);
                    BluetoothDevice device = btAdapter.getRemoteDevice(address);
                    chat.connect(device);
                }
                break;
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK)
                    init();
                else {
                    Toast.makeText(this, "BT not enabled", Toast.LENGTH_SHORT)
                            .show();
                    finish();
                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.connect:
                startActivityForResult(new Intent(this, DeviceListActivity.class),
                        REQUEST_CONNECT_BT);
                return true;
            case R.id.disco:
                if (btAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
                    Intent discoverableIntent = new Intent(
                            BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    discoverableIntent.putExtra(
                            BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                    startActivity(discoverableIntent);
                }
                return true;
        }
        return false;
    }

}






