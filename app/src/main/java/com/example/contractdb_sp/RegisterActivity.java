package com.example.contractdb_sp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {


    Button signUpButton;
    EditText editName,editPassword,confrimPassword,editEmail;
    CheckBox checkBox;
    ImageView profileImage_Register;
    SharedPreferences sharedPreferences;
    RadioButton radioButton;
    TextView textViewsignIn;

    String singUpName,singUpEmail,singUpPassword,singUpConfirmPassword,singUpPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        textViewsignIn = findViewById(R.id.Register_sing_in_ID);
        signUpButton = findViewById(R.id.SingUpID);

        editName = findViewById(R.id.Register_Name_Id);
        editPassword = findViewById(R.id.Register_Password_Id);
        confrimPassword = findViewById(R.id.Register_Confirm_Password_Id);
        checkBox = findViewById(R.id.saveLoginCheckBox);
        editEmail = findViewById(R.id.Register_Email_Id);



        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editName.length()<=0){
                    editName.setError("Fill the gap");
                }
                else if (editEmail.length()<=0){
                    editEmail.setError("Fill the gap");
                }
                else if (editPassword.length()<=0){
                    editPassword.setError("Fill the gap");
                }
                else if (confrimPassword.length()<=0){
                    confrimPassword.setError("Fill the gap");
                }
/*
                else if (singUpPhoneEditText.length()<=0){
                    singUpPhoneEditText.setError("Fill the gap");
                }
*/

                else {

                    if (editPassword.getText().toString().equals(confrimPassword.getText().toString()) ){
                        singUpName=editName.getText().toString();
                        singUpEmail=editEmail.getText().toString();
                        singUpPassword=editPassword.getText().toString();
                        singUpConfirmPassword=confrimPassword.getText().toString();
/*                        singUpPhone=singUpPhoneEditText.getText().toString();*/

                        SharePrefance sharePref=new SharePrefance();
                        sharePref.saveDetails(RegisterActivity.this,singUpEmail,singUpPassword);

                        Intent intent =new Intent(RegisterActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                    else {

                        Toast.makeText(RegisterActivity.this, "please match  password with confirm password ", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}
