package com.example.app01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class personalInfoActivity extends AppCompatActivity {
    TextView nameTxt, emailTxt, phoneTxt;
    Button editbtn;
    ImageView back;
    String varaible1,varaible2,varaible3,varaible5;
    DatabaseHelper db;
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        initView();
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
       String email = sh.getString("email", "");
        Cursor profile = db.get_customer_data(email);
        if(profile.moveToFirst()){
            do{
                 varaible1 = profile.getString(profile.getColumnIndex("customer_name"));
                 varaible2 = profile.getString(profile.getColumnIndex("customer_email"));
                 varaible3 = profile.getString(profile.getColumnIndex("customer_mobile_number"));

                 varaible5 = profile.getString(profile.getColumnIndex("customer_password"));
                nameTxt.setText(varaible1);
                emailTxt.setText(varaible2);
                phoneTxt.setText(varaible3);

            }while (profile.moveToNext());
        }


        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(personalInfoActivity.this, personalInfoEditActivity.class);


                // below we are passing all our values.
                intent.putExtra("name",varaible1);
                intent.putExtra("email",varaible2);
                intent.putExtra("phone",varaible3);

                // starting our activity.
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(personalInfoActivity.this, ProfileActivity.class);
                startActivity(intent);            }
        });


    }
    private void initView() {

        nameTxt = (TextView) findViewById(R.id.nameTxtPIV);
        emailTxt = (TextView) findViewById(R.id.emailTxtPIV);
        phoneTxt = (TextView) findViewById(R.id.mobileTxtPIV);
        editbtn = (Button) findViewById(R.id.editBtnPIV);
        db = new DatabaseHelper(this);
        back=(ImageView)findViewById(R.id.back);

    }


}