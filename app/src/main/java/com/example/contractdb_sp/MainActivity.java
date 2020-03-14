package com.example.contractdb_sp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    Button loginButton;
    EditText editUserName,editPassword;
    CheckBox checkBox;
    TextView createNewAccount;

    String email,password;
    SharePrefance sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Id Find
        loginButton = findViewById(R.id.LoginButton);
        editUserName = findViewById(R.id.login_UserName_Id);
        editPassword = findViewById(R.id.login_Password_Id);
        checkBox = findViewById(R.id.saveLoginCheckBox);
        createNewAccount = findViewById(R.id.createNewAccount);


        sharedPreferences=new SharePrefance() ;
        String emailValue= sharedPreferences.loadRememberEmail(MainActivity.this);
        String passwordValue= sharedPreferences.loadRememberPassword(MainActivity.this);


        if (!emailValue.isEmpty()||!passwordValue.isEmpty()){
            editUserName.setText(emailValue);
            editPassword.setText(passwordValue);
            if (editUserName.getText().toString().equals(emailValue) && editPassword.getText().toString().equals(passwordValue)){
                Intent intent =new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        }
        else {
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
        }


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=editUserName.getText().toString();
                password=editPassword.getText().toString();

                if (email.isEmpty()){
                    editUserName.setError("Your name is empty");
                }
                else if (password.isEmpty()){
                    editPassword.setError("Your password is empty");
                }
                else {
                    sharedPreferences=new SharePrefance();
                    String emailValue= sharedPreferences.loadEmail(MainActivity.this);
                    String passwordValue= sharedPreferences.loadPassword(MainActivity.this);
/*
                    if (checkBox.isChecked()){
                        sharedPreferences.rememberData(MainActivity.this,email,password);
                    }*/

                    if (email.equals(emailValue) && password.equals(passwordValue)){

                        sharedPreferences.rememberData(MainActivity.this,email,password);
                        Intent intent =new Intent(MainActivity.this,HomeActivity.class);
                        startActivity(intent);

                    }
                    else {
                        Toast.makeText(MainActivity.this, "Can not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
