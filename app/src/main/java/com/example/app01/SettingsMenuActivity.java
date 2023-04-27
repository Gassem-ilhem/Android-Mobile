package com.example.app01;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsMenuActivity extends AppCompatActivity {
    Button batterylevelbtn,wifi;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_menu);
        initView();
        initEvent();


    }


    private void initView() {
        batterylevelbtn = (Button) findViewById(R.id.batterylevelbtn);
        wifi = (Button) findViewById(R.id.wifi);
    }


    private void initEvent() {
        batterylevelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsMenuActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });


        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SettingsMenuActivity.this, ConnexionActivity.class);
                startActivity(intent);
            }
        });
    }

}