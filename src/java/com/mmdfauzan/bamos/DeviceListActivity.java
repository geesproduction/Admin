/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.bluetooth.BluetoothAdapter
 *  android.bluetooth.BluetoothDevice
 *  android.content.Context
 *  android.os.Bundle
 *  android.support.v7.app.ActionBar
 *  android.support.v7.app.AppCompatActivity
 *  android.view.MenuItem
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ArrayAdapter
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.TextView
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Set
 */
package com.mmdfauzan.bamos;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.mmdfauzan.bamos.DeviceListActivity;
import java.util.Set;

public class DeviceListActivity
extends AppCompatActivity {
    protected static final String TAG = "TAG";
    private BluetoothAdapter mBluetoothAdapter;
    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener(this){
        final /* synthetic */ DeviceListActivity this$0;
        {
            this.this$0 = deviceListActivity;
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
            try {
                DeviceListActivity.access$000(this.this$0).cancelDiscovery();
                String string = ((TextView)view).getText().toString();
                String string2 = string.substring(-17 + string.length());
                android.util.Log.v((String)"TAG", (String)("Device_Address " + string2));
                Bundle bundle = new Bundle();
                bundle.putString("DeviceAddress", string2);
                android.content.Intent intent = new android.content.Intent();
                intent.putExtras(bundle);
                this.this$0.setResult(-1, intent);
                this.this$0.finish();
                return;
            }
            catch (java.lang.Exception exception) {
                return;
            }
        }
    };
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;

    static /* synthetic */ BluetoothAdapter access$000(DeviceListActivity deviceListActivity) {
        return deviceListActivity.mBluetoothAdapter;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.requestWindowFeature(5);
        this.setContentView(2131427366);
        this.setResult(0);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setTitle((CharSequence)"Pilih Printer");
        (TextView)this.findViewById(2131297067);
        this.mPairedDevicesArrayAdapter = new ArrayAdapter((Context)this, 2131427450);
        ListView listView = (ListView)this.findViewById(2131296713);
        listView.setAdapter(this.mPairedDevicesArrayAdapter);
        listView.setOnItemClickListener(this.mDeviceClickListener);
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set set = this.mBluetoothAdapter.getBondedDevices();
        if (set.size() > 0) {
            for (BluetoothDevice bluetoothDevice : set) {
                this.mPairedDevicesArrayAdapter.add((Object)(bluetoothDevice.getName() + "\n" + bluetoothDevice.getAddress()));
            }
        } else {
            this.mPairedDevicesArrayAdapter.add((Object)"Tidak ada perangkat");
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.mBluetoothAdapter != null) {
            this.mBluetoothAdapter.cancelDiscovery();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            default: {
                do {
                    return true;
                    break;
                } while (true);
            }
            case 16908332: 
        }
        this.finish();
        return true;
    }
}

