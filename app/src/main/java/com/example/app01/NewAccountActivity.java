package com.example.app01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class NewAccountActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;
    EditText email,username, password, repassword,security_edt;
    Button signup ;
    TextView btnsignin;
    DatabaseHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        initView();
        initEvent();
    }



    private void initView() {
        email = (EditText) findViewById(R.id.email1);
        username = (EditText) findViewById(R.id.username1);
        password = (EditText) findViewById(R.id.password1);
        repassword = (EditText) findViewById(R.id.repassword1);
        security_edt= (EditText) findViewById(R.id.security_edt);
        signup = (Button) findViewById(R.id.btnsignup);
        signup.setEnabled(true);
        btnsignin = (TextView) findViewById(R.id.btnsignin);
        btnsignin.setEnabled(true);
        DB = new DatabaseHelper(this);
    }

    private void initEvent() {
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email.getText().toString();
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                String secure = security_edt.getText().toString();
                if(user.equals("")||mail.equals("")||pass.equals("")||repass.equals("")||secure.equals(""))
                    Toast.makeText(NewAccountActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    if(pass.equals(repass)){
                        Boolean checkmail = DB.get_customer_email(mail);
                        if(checkmail==false){
                            Boolean insert = DB.add_customer(mail,user, pass,secure);
                            if(insert==true){
                                sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

                                // Creating an Editor object to edit(write to the file)
                                myEdit = sharedPreferences.edit();

                                // Storing the key and its value as the data fetched from edittext
                                myEdit.putString("email", mail);

                                // Once the changes have been made, we need to commit to apply those changes made,
                                // otherwise, it will throw an error
                                myEdit.commit();
                                Toast.makeText(NewAccountActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(NewAccountActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(NewAccountActivity.this, "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(NewAccountActivity.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }
                } }
        });


        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

            }
        });


    }


}