package shade.pixel.gpsoclient;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by pixelshade on 21.4.2014.
 */
public class BTCommunicator {
    private final UUID myUUID =
            // UUID.fromString("00001101-0000-1000-8000-00805F9B35FB");
            UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");

    private final BluetoothAdapter btAdapter;
    private final Handler handler;
    private AcceptThread acceptThread;
    private ConnectThread connectThread;
    private ConnectedThread connectedThread;
    public String state;
    String model;

    public BTCommunicator(Context context, Handler handler) {
        this.handler = handler;
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        model = android.os.Build.MODEL.split(" ")[0];
        state = "NONE";
        Log.i("myChat", model + ":" + state);
    }

    public void start() {
        Log.i("myChat", model + ":" + "start " + state);
        if (connectThread != null) {
            connectThread.cancel();
            connectThread = null;
        }
        if (connectedThread != null) {
            connectedThread.cancel();
            connectedThread = null;
        }
        state = "LISTEN";
        Log.i("myChat", model + ":" + state);

        if (acceptThread == null) {
            acceptThread = new AcceptThread();
            acceptThread.start();
        }
    }

    public void connect(BluetoothDevice device) {
        Log.i("myChat", model + ":" + "connect " + state);
        if (state.equals("CONNECTING")) {
            if (connectThread != null) {
                connectThread.cancel();
                connectThread = null;
            }
        }
        if (connectedThread != null) {
            connectedThread.cancel();
            connectedThread = null;
        }
        connectThread = new ConnectThread(device);
        connectThread.start();
        state = "CONNECTING";
        Log.i("myChat", model + ":" + state);
    }

    public void connected(BluetoothSocket socket/*, BluetoothDevice device*/) {
        Log.i("myChat", model + ":" + "connected " + state);
        if (connectThread != null) {
            connectThread.cancel();
            connectThread = null;
        }
        if (connectedThread != null) {
            connectedThread.cancel();
            connectedThread = null;
        }
        if (acceptThread != null) {
            acceptThread.cancel();
            acceptThread = null;
        }
        state = "CONNECTED";
        Log.i("myChat", model + ":" + state);

        connectedThread = new ConnectedThread(socket);
        connectedThread.start();
    }

    public void stop() {
        Log.i("myChat", model + ":" + "stop " + state);
        if (connectThread != null) {
            connectThread.cancel();
            connectThread = null;
        }
        if (connectedThread != null) {
            connectedThread.cancel();
            connectedThread = null;
        }
        if (acceptThread != null) {
            acceptThread.cancel();
            acceptThread = null;
        }
        state = "NONE";
        Log.i("myChat", model + ":" + state);

    }

    public void write(byte[] out) {
        Log.i("myChat", model + ":" + "write " + state);
        if (!state.equals("CONNECTED"))
            return;
        ConnectedThread r = connectedThread;
        Log.i("myChat", model + ":" + "write.. " + state);
        r.write(out);
    }

    // ------------------------------------------ AcceptThread
    private class AcceptThread extends Thread {
        private final BluetoothServerSocket serverSocket;

        public AcceptThread() {
            Log.i("myChat", model + ":" + "acceptThread " + state);
            BluetoothServerSocket tmp = null;
            try {
                tmp = btAdapter.listenUsingRfcommWithServiceRecord(
                        "BluetoothChatSecure", myUUID);
            } catch (IOException e) {
                Log.e("myChat", model + ":" + "::" + e.getMessage());
            }
            serverSocket = tmp;
        }

        public void run() {
            setName("AcceptThread");
            Log.i("myChat", model + ":" + "acceptThreadRun " + state);
            BluetoothSocket socket = null;
            Log.i("myChat", model + ":" + state);
            while (!state.equals("CONNECTED")) {
                try {
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    Log.e("myChat", model + ":" + ":" + e.getMessage());
                    break;
                }
                if (socket != null) {
                    Log.i("myChat", model + ":" + state);
                    if (state.equals("LISTEN") || state.equals("CONNECTING"))
                        connected(socket /*, socket.getRemoteDevice()*/);
                    else
                        try {
                            socket.close();
                        } catch (IOException e) {
                            Log.e("myChat", model + ":" + ":" + e.getMessage());
                        }
                    break;
                }
            }
        }

        public void cancel() {
            Log.i("myChat", model + ":" + "cancel " + state);
            try {
                serverSocket.close();
            } catch (IOException e) {
                Log.e("myChat", model + ":" + ":" + e.getMessage());
            }
        }
    }

    // ------------------------------------------ ConnectThread
    private class ConnectThread extends Thread {
        private final BluetoothSocket clientSocket;
        //private final BluetoothDevice device;

        public ConnectThread(BluetoothDevice device) {
            Log.i("myChat", model + ":" + "connectThread " + state);
            //this.device = device;
            BluetoothSocket tmp = null;
            try {
                tmp = device.createRfcommSocketToServiceRecord(myUUID);
            } catch (IOException e) {
                Log.e("myChat", model + ":" + ":" + e.getMessage());
            }
            clientSocket = tmp;
        }

        public void run() {
            setName("ConnectThread");
            Log.i("myChat", model + ":" + "connectThreadRun " + state);
            btAdapter.cancelDiscovery();
            try {
                clientSocket.connect();
            } catch (IOException e) {
                try {
                    clientSocket.close();
                } catch (IOException e2) {
                    Log.e("myChat", model + ":" + ":" + e.getMessage());
                }
                start();
            }
            connectThread = null;
            connected(clientSocket/*, device*/);
        }

        public void cancel() {
            try {
                clientSocket.close();
            } catch (IOException e) {
                Log.e("myChat", model + ":" + ":" + e.getMessage());
            }
        }
    }

    // ---------------------------------------------------------- ConnectedThread
    private class ConnectedThread extends Thread {
        private final BluetoothSocket socket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        public ConnectedThread(BluetoothSocket socket) {
            Log.i("myChat", model + ":" + "connectedThread " + state);
            this.socket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e("myChat", model + ":" + ":" + e.getMessage());
            }
            inputStream = tmpIn;
            outputStream = tmpOut;
        }

        public void run() {
            Log.i("myChat", model + ":" + "connectedThreadRun " + state);
            byte[] buffer = new byte[1024];
            int bytes;
            while (true) {
                try {
                    Log.i("myChat", model + ":" + "connectedThreadReading "
                            + state);
                    bytes = inputStream.read(buffer);
                    Log.i("myChat", model + ":" + "connectedThreadRead "
                            + state);
                    handler.obtainMessage(BluetoothActivity.READ, bytes, -1, buffer)
                            .sendToTarget();
                    Log.i("myChat", model + ":" + "connectedThreadSendtoUI "
                            + state);
                } catch (IOException e) {
                    Log.i("myChat",
                            model + ":" + "connectedThreadIO " + e.getMessage());
                    BTCommunicator.this.start();
                    break;
                }
            }
        }

        public void write(byte[] buffer) {
            try {
                outputStream.write(buffer);
                handler.obtainMessage(BluetoothActivity.WRITE, -1, -1, buffer)
                        .sendToTarget();
            } catch (IOException e) {
                Log.e("myChat", model + ":" + ":" + e.getMessage());
            }
        }

        public void cancel() {
            try {
                socket.close();
            } catch (IOException e) {
                Log.e("myChat", model + ":" + ":" + e.getMessage());
            }
        }
    }
}

