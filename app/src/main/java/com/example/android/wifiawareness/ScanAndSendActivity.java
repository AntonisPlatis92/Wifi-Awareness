package com.example.android.wifiawareness;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.media.CamcorderProfile.get;
import static com.example.android.wifiawareness.R.id.rssi;
import static com.example.android.wifiawareness.R.id.textView;

/**
 * Created by Antonis on 2/4/2017.
 */

public class ScanAndSendActivity extends AppCompatActivity {

    private static final String TAG = "SendActivity";
    public WifiManager wifi;
    public String message;
    public boolean finished=false;
    BroadcastReceiver mWifiScanReceiver;
    public boolean wasConnected;

    /* Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        wifi =(WifiManager) getSystemService(Context.WIFI_SERVICE);
        wasConnected=wifi.isWifiEnabled();
        if (!wasConnected) wifi.setWifiEnabled(true);


        scanAndSend();

    }

    public void onBackPressed(){
        wifi.setWifiEnabled(wasConnected);
        super.onBackPressed();
        finish();
    }


    public void scanAndSend(){

        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        mWifiScanReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context c, Intent intent) {

                if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                    List<ScanResult> mScanResults = wifi.getScanResults();

                    ArrayList<WifiConnection> connections = new ArrayList<WifiConnection>();
                    for (int i = 0; i < mScanResults.size(); i++) {
                        connections.add(new WifiConnection(mScanResults.get(i).SSID,mScanResults.get(i).level,mScanResults.get(i).BSSID));
                    }
                    Collections.sort(connections, new Comparator<WifiConnection>() {
                        @Override
                        public int compare(WifiConnection w1, WifiConnection w2) {
                            if (w1.getrssi() < w2.getrssi())
                                return 1;
                            if (w1.getrssi() > w2.getrssi())
                                return -1;
                            return 0;
                        }
                    });
                    URL url = null;
                    for (int i=0;i<connections.size();i++){
                        System.out.println(connections.get(i).getrssi());
                    }
                    int k=0;

                    String finalMessage="";
                    for (int j=0;j<2;j++){
                        if (connections.get(k).getssid().equals("OTE WiFi Fon")){
                            k++;
                        }
                        if (k<connections.size()) {
                            GetUrlContentTask urltask = new GetUrlContentTask();
                            String ssidSend = connections.get(k).getssid().replaceAll(" ", "");
                            String urlString = "http://locaware.esy.es/SSIDinput.php?ssid='" + ssidSend;
                            urlString=urlString+"'&bssid='" + connections.get(k).getbssid();
                            urlString=urlString+"'&rssi='" + connections.get(k).getrssi() + "'";

                            urltask.execute(urlString);

                            urltask.waitUntilFinish();

                            String returnMessage = urltask.getContentReturn();

                            String[] splitMessage = returnMessage.split("<br>");
                            for (int i = 0; i < splitMessage.length; i++) {
                                finalMessage = finalMessage + "\n" + splitMessage[i];
                            }
                            k++;
                        }
                        else {
                            finalMessage="Not enough WiFi Connections!";
                        }
                    }
                    TextView textView = (TextView) findViewById(R.id.server_responce_view);
                    textView.setText(finalMessage);

                }
            }

        };
        registerReceiver(mWifiScanReceiver,
                new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifi.startScan();




    }

    public void refreshButton(View view){

        finished=false;
        message="";
        unregisterReceiver(mWifiScanReceiver);
        scanAndSend();
    }


}


