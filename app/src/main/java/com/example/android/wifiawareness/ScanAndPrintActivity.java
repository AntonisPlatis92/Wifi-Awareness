package com.example.android.wifiawareness;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static android.R.id.message;

/**
 * Created by Antonis Platis on 2/4/2017.
 */

public class ScanAndPrintActivity extends AppCompatActivity {

    private static final String TAG = "PrintActivity";
    public WifiManager wifi;
    BroadcastReceiver mWifiScanReceiver;
    public boolean wasConnected;

    /* Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        wifi =(WifiManager) getSystemService(Context.WIFI_SERVICE);
        wasConnected=wifi.isWifiEnabled();
        if (!wasConnected) wifi.setWifiEnabled(true);

        scanAndShow();
    }

    public void onBackPressed(){
        wifi.setWifiEnabled(wasConnected);
        super.onBackPressed();
        finish();
    }


    public void scanAndShow(){

        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        final ArrayList<String> SSID = new ArrayList<String>();
        final ArrayList<Integer> RSSI = new ArrayList<Integer>();
        final ArrayList<String> BSSID = new ArrayList<String>();

        mWifiScanReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent intent) {
                if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                    List<ScanResult> mScanResults = wifi.getScanResults();

                    for (int i = 0; i < mScanResults.size(); i++) {
                        SSID.add(mScanResults.get(i).SSID);
                        RSSI.add(mScanResults.get(i).level);
                        BSSID.add(mScanResults.get(i).BSSID);
                    }
                    TextView ssidView=(TextView) findViewById(R.id.ssid);
                    TextView rssiView=(TextView) findViewById(R.id.rssi);
                    TextView bssidView=(TextView) findViewById(R.id.bssid);
                    String ssidString="";
                    String bssidString="";
                    String rssiString="";
                    for (int i=0;i<SSID.size();i++){
                        ssidString=ssidString+"\n"+SSID.get(i);
                        bssidString=bssidString+"\n"+BSSID.get(i);
                        rssiString=rssiString+"\n"+RSSI.get(i);
                    }
                    ssidView.setText(ssidString);
                    rssiView.setText(rssiString);
                    bssidView.setText(bssidString);
                }
            }
        };
        registerReceiver(mWifiScanReceiver,
                new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifi.startScan();
        Log.i(TAG,"Started Scanning");
    }




    public void refreshButton(View view){
        unregisterReceiver(mWifiScanReceiver);
        scanAndShow();
    }

}
