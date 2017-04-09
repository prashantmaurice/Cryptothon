package com.maurice.cryptothon.app;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.maurice.cryptothon.app.Controllers.ToastMain;
import com.maurice.cryptothon.app.Dialogs.RestaurantDialog.RestaurantDialog;
import com.maurice.cryptothon.app.Models.RestaurantObj;
import com.maurice.cryptothon.app.Utils.Logg;
import com.maurice.cryptothon.app.Utils.NetworkCallback2;
import com.maurice.cryptothon.app.Utils.Router;
import com.maurice.cryptothon.app.storage.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class MasterBluetoothActivity extends AppCompatActivity {
    String TAG = "BLUETOOTH.ACTIVITY";
    int REQUEST_ENABLE_BT = 2004;
    static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 2005;
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = Data.getInstance(MainApplication.getInstance());
        attachReceivers();
        bluetoothUpstart();
        checkPermissions();
        setupTimer();


    }

    public void setupTimer(){
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Logg.d(TAG , "running timer");
                bluetoothUpstart();
            }
        }, 10000, 10000);

//        Handler h = new Handler();
//        int delay = 3000; //milliseconds
//        h.postDelayed(new Runnable(){
//            public void run(){
//            }
//        }, delay);
    }

    public void bluetoothUpstart(){
        Logg.d(TAG , "bluetoothUpstart");
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
//        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
//
//        if (pairedDevices.size() > 0) {
//            // There are paired devices. Get the name and address of each paired device.
//            for (BluetoothDevice device : pairedDevices) {
//                String deviceName = device.getName();
//                String deviceHardwareAddress = device.getAddress(); // MAC address
//                Logg.d("BLUETOOTH","deviceName : "+deviceName+deviceHardwareAddress);
//            }
//        }

        if (mBluetoothAdapter.isDiscovering()){
            mBluetoothAdapter.cancelDiscovery();
        }
        data.proximityIds.clear();
        mBluetoothAdapter.startDiscovery();
    }


    public void checkIfInLocation(BluetoothDevice device){
        String name = device.getName();
        if ( name !=null && name.contains("#B")){
            String[] comp = name.split("#B");
            if (comp.length>1){
                String id = comp[1];
                data.proximityIds.add("B"+id);
                claimForRestaurant("B"+id);
            }
        }

        Log.d(TAG , device.getName() + " : " + device.getAddress());
    }


    Set<String> shown = new HashSet<String>();

    public void claimForRestaurant(String id){
        Logg.d(TAG,"claimForRestaurant : "+id);
        if (shown.contains(id)){
            return;
        }
        Data.getInstance(MainApplication.getInstance()).pullRestaurant(id, new NetworkCallback2<RestaurantObj>() {
            @Override
            public void onSuccess(RestaurantObj restaurantObj) {
                shown.add(restaurantObj.id);
                ToastMain.showSmartToast(MasterBluetoothActivity.this,"claimForRestaurant : success");
                RestaurantDialog.show(MasterBluetoothActivity.this,restaurantObj);
            }

            @Override
            public void onError() {

            }
        });
    }

    public void checkPermissions(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Logg.d("BLUETOOTH","onRequestPermissionsResult: "+permissions);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void attachReceivers(){
        // Register for broadcasts when a device is discovered.
        IntentFilter filter3 = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter3);
    }

    //The BroadcastReceiver that listens for bluetoothUpstart broadcasts
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Logg.d("BLUETOOTH","received mReceiver: "+intent);
            String action = intent.getAction();
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            Logg.d("BLUETOOTH",action);
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                Logg.d("BLUETOOTH","deviceName : "+deviceName+deviceHardwareAddress);
                checkIfInLocation(device);
            }
            else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                //Device is now connected
            }
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //Done searching
            }
            else if (BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(action)) {
                //Device is about to disconnect
            }
            else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
                //Device has disconnected
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(mReceiver);
    }


}
