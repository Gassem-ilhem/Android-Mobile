package com.example.app01;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class changePasswordActivity extends AppCompatActivity {
    EditText newPasstxt, confirmPasstxt, currentPasstxt;

    Button updatePassBtn;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;
    DatabaseHelper db;
    String varaible1,varaible2;
    String currentPass, newPass, confirmPass,password,mail;
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);




        newPasstxt = (EditText) findViewById(R.id.newPassTxtCP);
        confirmPasstxt = (EditText) findViewById(R.id.confirmPassTxtCP);
        currentPasstxt = (EditText) findViewById(R.id.currentPassTxtCP);

        updatePassBtn = (Button) findViewById(R.id.updatePassBtnCP);
        db = new DatabaseHelper(this);





        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String email = sh.getString("email", "");
        Cursor profile = db.get_customer_data(email);
        if(profile.moveToFirst()){
            do{
                varaible1 = profile.getString(profile.getColumnIndex("customer_password"));
                varaible2 = profile.getString(profile.getColumnIndex("customer_email"));

            }while (profile.moveToNext());
        }
        updatePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
       currentPass = currentPasstxt.getText().toString();
        newPass = newPasstxt.getText().toString();
        confirmPass = confirmPasstxt.getText().toString();


        if (!newPass.equals(confirmPass))
            Toast.makeText(getApplicationContext(), "Passwords Doesn't Match", Toast.LENGTH_LONG).show();


     if ( currentPass.equals(varaible1)) {

            db.reset_password(varaible2,newPasstxt.getText().toString());

         Toast.makeText(getApplicationContext(), "Password Updated!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(changePasswordActivity.this, MainActivity.class);

            startActivity(intent);
        }
        else
            Toast.makeText(getApplicationContext(), "You have entered a wrong password", Toast.LENGTH_LONG).show();
            }
        });


    }
}