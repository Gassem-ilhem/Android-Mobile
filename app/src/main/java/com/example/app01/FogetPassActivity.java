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

public class FogetPassActivity extends AppCompatActivity {

    EditText email2, secure;
    TextView btnrelogin;
    Button btnforgetpass;
    DatabaseHelper DB;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foget_pass);
        initView();
        initEvent();
    }


    private void initView() {
        email2 = (EditText) findViewById(R.id.email2);
        secure = (EditText) findViewById(R.id.secure);
        btnforgetpass = (Button) findViewById(R.id.btnforgetpass);
        btnrelogin = (TextView) findViewById(R.id.btnrelogin);
        DB = new DatabaseHelper(this);
    }



    private void initEvent() {

        btnforgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail = email2.getText().toString();
                String secr = secure.getText().toString();

                if(mail.equals("")||secr.equals(""))
                    Toast.makeText(FogetPassActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkmailpass = DB.check_customer_secure(mail, secr);
                    if(checkmailpass==true){
                        sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

                        // Creating an Editor object to edit(write to the file)
                        myEdit = sharedPreferences.edit();

                        // Storing the key and its value as the data fetched from edittext
                        myEdit.putString("email", mail);

                        // Once the changes have been made, we need to commit to apply those changes made,
                        // otherwise, it will throw an error
                        myEdit.commit();

                        Toast.makeText(FogetPassActivity.this, "Security Hint successfull", Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(getApplicationContext(), ResetPasswordActivity.class);
                        intent.putExtra("email",mail);
                        startActivity(intent);
                    }else{
                        Toast.makeText(FogetPassActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        btnrelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (FogetPassActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });
    }
}