package com.example.android.wifiawareness;

/**
 * Created by Antonis on 5/4/2017.
 */

public class WifiConnection {
    private String ssid;
    private String bssid;
    private int rssi;

    WifiConnection(String a,int b,String c){
        this.ssid=a;
        this.rssi=b;
        this.bssid=c;
    }

    public String getssid(){
        return this.ssid;
    }

    public String getbssid(){
        return this.bssid;
    }

    public int getrssi(){
        return this.rssi;
    }
    public void printData(){
        System.out.println(this.ssid);
        System.out.println(this.rssi);
        System.out.println(this.bssid);
    }
}