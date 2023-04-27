package com.example.app01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ResetPasswordActivity extends AppCompatActivity {
    String mail,newPass,confirmPass;
    EditText password3, confirPass;
    TextView btnrelogin2;
    Button btnforgetpass2;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initView();
        initEvent();
    }

    private void initView() {
        password3 = (EditText) findViewById(R.id.password3);
        confirPass = (EditText) findViewById(R.id.confirPass);
        btnrelogin2 = (TextView) findViewById(R.id.btnrelogin2);
        btnforgetpass2 = (Button) findViewById(R.id.btnforgetpass2);
        db = new DatabaseHelper(this);
    }



    private void initEvent() {
        mail=getIntent().getStringExtra("email");


        btnforgetpass2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newPass = password3.getText().toString();
                confirmPass = confirPass.getText().toString();


                if (!newPass.equals(confirmPass))
                    Toast.makeText(getApplicationContext(), "passwords doesn't match", Toast.LENGTH_LONG).show();
                else if (newPass.equals("")||confirmPass.equals("")) {
                    Toast.makeText(ResetPasswordActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }else {
                    db.reset_password(mail,newPass);

                    Toast.makeText(getApplicationContext(), "Password updated!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ResetPasswordActivity.this, MainActivity.class);

                    startActivity(intent);
                }

            }
        });


        btnrelogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ResetPasswordActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });

    }
}