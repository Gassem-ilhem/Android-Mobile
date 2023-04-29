package com.example.app01;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class ProfileActivity extends AppCompatActivity {
    Button personalInfoBtn,changePassBtn,logoutBtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initView();
        initEvent();

    }


    private void initView() {
        personalInfoBtn = (Button) findViewById(R.id.personalInfoBtnProfile);
        changePassBtn = (Button) findViewById(R.id.changePassBtnProfile);
        logoutBtn = (Button) findViewById(R.id.logoutBtnProfile);
    }
    private void initEvent() {

        personalInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, personalInfoActivity.class);
                startActivity(intent);
            }
        });


        changePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProfileActivity.this, changePasswordActivity.class);
                startActivity(intent);
            }
        });



        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the shared preferences object
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

                // Get the editor object
                SharedPreferences.Editor editor = sharedPreferences.edit();

                // Remove the email key-value pair from shared preferences
                editor.remove("email");

                // Commit the changes
                editor.commit();

                // Redirect the user to the login activity
                Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent1);
                finish(); // Close the current activity

            }
        });
    }
}