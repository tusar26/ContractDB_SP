package com.example.contractdb_sp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;

    SharePrefance sharePrefance;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        sharePrefance=new SharePrefance();
        context =HomeActivity.this;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        int id  = item.getItemId();
        switch (id){
            case R.id.contactItemId:
                customAlert();
                return true;
            case R.id.logout_Id :



/*                Intent logout = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(logout);
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();*/

            default:
           return super.onOptionsItemSelected(item);
        }
    }




    private void customAlert() {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custom_verify_password, null);
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();

        Button okButton = view.findViewById(R.id.okButton);
        Button cancelButton = view.findViewById(R.id.cancelButton);

        final EditText passwordEditText = view.findViewById(R.id.verifyPassword);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordEditText.getText().toString();
                String savePassword = sharePrefance.loadPassword(context);
                if (password.isEmpty()) {
                    passwordEditText.setError("Enter your password");
                } else {
                    if (savePassword.equals(password)) {
                        Intent intent = new Intent(HomeActivity.this, ContractActivity.class);
                        startActivity(intent);
                        alertDialog.dismiss();
                    } else {
                        passwordEditText.setError("Enter correct password");
                    }
                }


            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }




}
