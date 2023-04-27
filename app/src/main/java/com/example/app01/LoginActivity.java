package com.example.app01;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;
    EditText email, password;
    Button btnlogin;
    TextView signupbtn,fogetpass;
    DatabaseHelper DB;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initView();
        initEvent();



    }
    private void initView() {
        email = (EditText) findViewById(R.id.email1);
        password = (EditText) findViewById(R.id.password1);
        btnlogin = (Button) findViewById(R.id.btnsignin1);
        signupbtn = (TextView) findViewById(R.id.signupbtn);
        fogetpass = (TextView) findViewById(R.id.fogetpass);
        DB = new DatabaseHelper(this);
    }
    private void initEvent() {
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail = email.getText().toString();
                String pass = password.getText().toString();

                if(mail.equals("")||pass.equals(""))
                    Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkmailpass = DB.check_customer(mail, pass);
                    if(checkmailpass==true){
                        sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

                        // Creating an Editor object to edit(write to the file)
                        myEdit = sharedPreferences.edit();

                        // Storing the key and its value as the data fetched from edittext
                        myEdit.putString("email", mail);

                        // Once the changes have been made, we need to commit to apply those changes made,
                        // otherwise, it will throw an error
                        myEdit.commit();

                        Toast.makeText(LoginActivity.this, "Sign in successfull", Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (LoginActivity.this,NewAccountActivity.class);
                startActivity(intent);

            }
        });

        fogetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (LoginActivity.this,FogetPassActivity.class);
                startActivity(intent);

            }
        });
    }







}