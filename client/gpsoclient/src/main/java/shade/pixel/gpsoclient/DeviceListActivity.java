package shade.pixel.gpsoclient;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class DeviceListActivity extends Activity {

	private BluetoothAdapter btAdapter;
	private Set<BluetoothDevice> pairedDevices;
	private ListView pairedListView;
	private ArrayAdapter<String> pairedAdapter;
	final int BT_ENABLE_REQUEST = 777;
	final int BT_DISCOVERABLE_REQUEST = 888;
	String model;
    public static String DEVICE_ADDRESS = "devaddr";
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_devices);
		model = android.os.Build.MODEL.split(" ")[0];

		Log.d("myBT",model);
		btAdapter = BluetoothAdapter.getDefaultAdapter();
		if (btAdapter == null) {
			Log.e("myBT",model+":"+"BT not supported");
			finish();
		}
		// zoznam sparovanych zariadeni
		pairedAdapter = new ArrayAdapter<String>(this, R.layout.list_item_device);
		pairedListView = (ListView) findViewById(R.id.boundedDevices);
		pairedListView.setAdapter(pairedAdapter);
		findBTDevices();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == BT_ENABLE_REQUEST) // BT-ON
			if (resultCode == RESULT_OK)
				Log.d("myBT",model+":"+"BT enabled");
			else
				Log.d("myBT",model+":"+"BT not enabled");
		else if (requestCode == BT_DISCOVERABLE_REQUEST)
			if (resultCode == RESULT_OK)
				Log.d("myBT",model+":"+"BT discoverable");
			else if (resultCode == RESULT_CANCELED)
				Log.d("myBT",model+":"+"BT not discoverable");
	}

	public void enableBT() {
		Log.d("myBT",model+":"+"enable BT");
		if (!btAdapter.isEnabled())
			startActivityForResult(	// enable BT dialog
					new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE),
					BT_ENABLE_REQUEST);
	}

	public void statusBT() {
		if (btAdapter.isEnabled()) {
			btAdapter.setName("myBT"+model);

			String status = btAdapter.getAddress() + ", " + btAdapter.getName();
			switch (btAdapter.getState()) {
			case BluetoothAdapter.STATE_ON:
				status += ", on";
				break;
			case BluetoothAdapter.STATE_TURNING_ON:
				status += ", turning on";
				break;
			case BluetoothAdapter.STATE_OFF:
				status += ", off";
				break;
			case BluetoothAdapter.STATE_TURNING_OFF:
				status += ", turning off";
				break;
			}
			Toast.makeText(DeviceListActivity.this, status, Toast.LENGTH_LONG).show();
			status += ", connecting";
		}
	}

	private void findBTDevices() {
		pairedAdapter.clear();

        if(btAdapter.isDiscovering()){
            btAdapter.cancelDiscovery();
        }

        btAdapter.startDiscovery();

		pairedDevices = btAdapter.getBondedDevices();
		if (pairedDevices.size() > 0) {
			for (BluetoothDevice device : pairedDevices)
				pairedAdapter
						.add(device.getName() + "::" + device.getAddress());
		}
		pairedListView
				.setOnItemClickListener(new ListView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						String item = pairedListView
								.getItemAtPosition(position).toString();
						String[] items = item.split("::");
						if (items.length > 1) {
							btAdapter.cancelDiscovery();
				            Intent intent = new Intent();
				            intent.putExtra(DEVICE_ADDRESS, items[1]);
				            setResult(Activity.RESULT_OK, intent);
				            finish();
						} else {
							setResult(RESULT_CANCELED);
						}
					}
				});
	}


	public void setDiscoverableBT() {
		Intent din = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		din.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 240);
		startActivityForResult(din, BT_DISCOVERABLE_REQUEST);
	}

	public void pairDevice(BluetoothDevice device) {
		String ACTION_PAIRING_REQUEST = "android.bluetooth.device.action.PAIRING_REQUEST";
		Intent intent = new Intent(ACTION_PAIRING_REQUEST);
		String EXTRA_DEVICE = "android.bluetooth.device.extra.DEVICE";
		intent.putExtra(EXTRA_DEVICE, device);
		String EXTRA_PAIRING_VARIANT = "android.bluetooth.device.extra.PAIRING_VARIANT";
		int PAIRING_VARIANT_PIN = 0;
		intent.putExtra(EXTRA_PAIRING_VARIANT, PAIRING_VARIANT_PIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		DeviceListActivity.this.startActivity(intent);
	}
}
