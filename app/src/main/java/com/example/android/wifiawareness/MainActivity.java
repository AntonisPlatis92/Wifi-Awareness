package com.example.android.wifiawareness;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MyActivity";



    /* Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }

    public void scanAndPrint(View view){
        // Create a new intent to open the {@link FamilyActivity}
        Intent scanAndPrintIntent = new Intent(MainActivity.this, ScanAndPrintActivity.class);

        // Start the new activity
        startActivity(scanAndPrintIntent);
    }

    public void scanAndSend(View view){

        // Create a new intent to open the {@link FamilyActivity}
        Intent scanAndSendIntent = new Intent(MainActivity.this, ScanAndSendActivity.class);

        // Start the new activity
        startActivity(scanAndSendIntent);
    }






}