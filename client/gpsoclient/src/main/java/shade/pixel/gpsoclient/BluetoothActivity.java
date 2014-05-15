package shade.pixel.gpsoclient;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;


public class BluetoothActivity extends ActionBarActivity {
    private static final String TAG = "BLUETOOTH ACTIVITY";

    public static final String ARG_ITEM = "ARG_ITEM";

    private static final int REQUEST_CONNECT_BT = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    public static final int READ = 1;
    public static final int WRITE = 2;


    private int myId = Settings.getPlayerId();

    private Item mSendingItem;
    private int mSendingAmount = 1;

    private int TYPE_ITEM = 10;
    private int TYPE_ID = 20;
    private int TYPE_ROLE = 30;


    private Button btnSend;


    private StringBuffer messages;
    private BluetoothAdapter btAdapter = null;
    private BTCommunicator chat = null;


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

        btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRole();
            }
        });


        chat = new BTCommunicator(this, handler);
        messages = new StringBuffer("");

        Intent intent = getIntent();
        mSendingItem = (Item) intent.getSerializableExtra(ARG_ITEM);

        if(mSendingItem==null) {
            setViewReceiver();
        } else {
            setViewGiver();
        }
    }

    public void setViewGiver(){
        btnSend.setVisibility(View.VISIBLE);
    }

    public void setViewReceiver(){
        btnSend.setVisibility(View.GONE);
    }

    private void sendRole() {
        if (!chat.state.equals("CONNECTED")) {
            Toast.makeText(this, "not connected", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Log.d(TAG,"WRITING_ID");
            MSGContainer msgContainer = new MSGContainer(TYPE_ROLE);
            byte[] data = ByteConverter.toByteArray(msgContainer);
            chat.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void sendId(int id) {
        if (!chat.state.equals("CONNECTED")) {
            Toast.makeText(this, "not connected", Toast.LENGTH_SHORT).show();
            return;
        }

            try {
                Log.d(TAG,"WRITING_ID");
                MSGContainer msgContainer = new MSGContainer(TYPE_ID, id);
                byte[] data = ByteConverter.toByteArray(msgContainer);
                chat.write(data);
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    private void sendItem(Item item) {
        if (!chat.state.equals("CONNECTED")) {
            Toast.makeText(this, "not connected", Toast.LENGTH_SHORT).show();
            return;
        }
        if (item != null && item.getAmount() > 0) {
            try {
                Log.d(TAG,"WRITING_ITEM");
                MSGContainer msgContainer = new MSGContainer(TYPE_ITEM, item);
                byte[] data = ByteConverter.toByteArray(msgContainer);
                chat.write(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ;
        } else
            Toast.makeText(this, "item is null", Toast.LENGTH_SHORT).show();
    }

    private boolean sendItemRequestHTTPGETAsync(int itemId, int itemAmount, int receiverId){
        MyHtmlBrowser.getInstance(this).HttpGetAsyncString(this,Settings.getSendItemURL(itemId,itemAmount,receiverId), new AsyncResponse() {
            @Override
            public void processFinish(Context context, String output) {
                Response response = new Response(output);
                if(response.isSuccessful()){
                    mSendingItem.setAmount(mSendingAmount);
                    sendItem(mSendingItem);
                } else {
                    MyAlertDialog mad = MyAlertDialog.newInstance("There was a problem :(", response.getMessage());
                    mad.show(getSupportFragmentManager(), "problem_fragment");
                }
            }
        });
        return true;
    }

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.i(TAG, "Message:"+msg.toString());
            switch (msg.what) {
                case WRITE:
                    Log.i(TAG, "WRITE");
                    MyAlertDialog mad = MyAlertDialog.newInstance("Item successfully sent", "hopefully");
                    mad.show(getSupportFragmentManager(), "yes");
                    break;
                case READ:
                    try {
                        MSGContainer receivedMSG = (MSGContainer) ByteConverter.toObject((byte[]) msg.obj);
                        if(receivedMSG!=null){
                            if(receivedMSG.getType() == TYPE_ID){
                                int receiverId = receivedMSG.getId();
                                Log.i(TAG, "TYPE ID MAME:" +receiverId);
                                sendItemRequestHTTPGETAsync(mSendingItem.getId(),mSendingAmount,receiverId);
                            }
                            if(receivedMSG.getType() == TYPE_ROLE){
                                Log.i(TAG, "TYPE ROLE MAME");
                                sendId(myId);
                            }
                            if(receivedMSG.getType() == TYPE_ITEM){
                                Log.i(TAG, "TYPE ITEM:");
                                Item receivedItem = receivedMSG.getItem();

                                if (receivedItem != null) {
                                    GameHandler.gameData.getItems().add(receivedItem);
                                    MyAlertDialog myAlertDialog = MyAlertDialog.newInstance(receivedItem);
                                    myAlertDialog.show(getSupportFragmentManager(), "votevr");
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
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







