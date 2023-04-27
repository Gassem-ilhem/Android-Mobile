package com.example.app01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;

public class ConnexionActivity extends AppCompatActivity {

    Boolean wifiConnected = false;
    Boolean mobileConnected = false;
    TextView connexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);


        connexion = (TextView) findViewById(R.id.connexion);
        checkNetworkConnection();
    }


    public void checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            wifiConnected = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;

            if (wifiConnected) {
                connexion.setText("Connected to wifi");
            } else if (mobileConnected) {
                connexion.setText("Connected to mobile");
            }





        } else {
            connexion.setText("Not Connected to Internet");
        }


        }
    }
