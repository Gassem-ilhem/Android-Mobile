package com.example.app01;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class personalInfoEditActivity extends AppCompatActivity {


    EditText nameTxt, emailTxt, phoneTxt;
    Button savebtn;
    DatabaseHelper db;
    String name,mail,phone;
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info_edit);
        initView();

        // on below lines we are getting data which
        // we passed in our adapter class.
        name=getIntent().getStringExtra("name");
        mail=getIntent().getStringExtra("email");
        phone=getIntent().getStringExtra("phone");

        // setting data to edit text
        // of our update activity.

        nameTxt.setText(name);
        emailTxt.setText(mail);
        phoneTxt.setText(phone);


    // adding on click listener to our update course button.
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inside this method we are calling an update course
                // method and passing all our edit text values.
                db.update_personalInfo(mail,nameTxt.getText().toString(),emailTxt.getText().toString(),phoneTxt.getText().toString());

                // displaying a toast message that our course has been updated.
                Toast.makeText(personalInfoEditActivity.this, "Course Updated..", Toast.LENGTH_SHORT).show();
                // launching our main activity.
                Intent i = new Intent(personalInfoEditActivity.this, personalInfoActivity.class);
                startActivity(i);
            }
        });

    }


    private void initView() {
        nameTxt = (EditText) findViewById(R.id.nameTxtPIE);
        emailTxt = (EditText) findViewById(R.id.emailTxtPIE);
        phoneTxt = (EditText) findViewById(R.id.mobileTxtPIE);

        savebtn = (Button) findViewById(R.id.saveBtnPIE);
        db = new DatabaseHelper(this);
    }
}