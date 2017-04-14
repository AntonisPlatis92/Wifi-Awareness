package com.example.android.wifiawareness;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.io.*;

import static android.content.ContentValues.TAG;

/**
 * Created by Antonis Platis on 2/4/2017.
 */

class GetUrlContentTask extends AsyncTask<String, Integer, String> {

    private String contentReturn;
    private boolean finished=false;

    protected String doInBackground(String... urls) {
        URL url = null;
        try {
            url = new URL(urls[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            connection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        connection.setDoOutput(true);
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        try {
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader rd = null;
        String content = "", line;
        try {
            rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while ((line = rd.readLine()) != null) {
                content += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.contentReturn=content;
        this.finished=true;
        return content;
    }

    protected void onPostExecute(String result) {
        // this is executed on the main thread after the process is over
        // update your UI here

    }

    public void waitUntilFinish(){
        while (!this.finished){

        }
    }

    public String getContentReturn(){
        return this.contentReturn;
    }




}