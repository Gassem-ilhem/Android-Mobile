package com.example.app01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {
    TextView battery;
    BroadcastReceiver batterylevelReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            battery.setText(String.valueOf(level)+"%");

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        battery = (TextView) findViewById(R.id.batterylevel);
        this.registerReceiver(this.batterylevelReceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }
}