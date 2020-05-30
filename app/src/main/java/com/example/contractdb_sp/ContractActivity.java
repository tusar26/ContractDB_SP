package com.example.contractdb_sp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ContractActivity extends AppCompatActivity  {

    RecyclerView recyclerView;
    FloatingActionButton addButton;
    DatabaseHelper databaseHelper;
    CustomAdapter adapter;
    List<Contract_SD> dataList;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract);

        this.setTitle("Contract");
        context=ContractActivity.this;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this ,R.color.colorAccent));
        }

        recyclerView           = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(ContractActivity.this));
        addButton              = findViewById(R.id.addButton);
        databaseHelper         = new DatabaseHelper(ContractActivity.this);


        loadData();


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customDialog();

            }
        });

    }
    private void loadData() {

        dataList  = new ArrayList<>();
        dataList = databaseHelper.getAllNotes();

        if (dataList.size() > 0){
            adapter = new CustomAdapter(ContractActivity.this,dataList);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }




    }


    private void customDialog() {

        AlertDialog.Builder builder  = new AlertDialog.Builder(ContractActivity.this);
        View view = getLayoutInflater().inflate(R.layout.custom_dialog,null);
        builder.setView(view);


        final AlertDialog alertDialog = builder.create();

        final EditText name       = view.findViewById(R.id.name_ID);
        final EditText location       = view.findViewById(R.id.location_Id);
        final EditText phone = view.findViewById(R.id.phoneNumber);

        Button saveButton    = view.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nameValue       = name.getText().toString();
                String locationValue       = location.getText().toString();
                String phoneValue = phone.getText().toString();

                if (nameValue.isEmpty()){
                    name.setError("Enter Name");
                    return;
                }else if (locationValue.isEmpty()){
                    location.setError("Enter location");
                    return;
                }else if (phoneValue.isEmpty()) {
                    phone.setError("Enter Phone Number");
                    return;
                }

                long status = databaseHelper.insertData(new Contract_SD(nameValue,locationValue,phoneValue));

                if (status != -1){
                    alertDialog.dismiss();
                    loadData();
                    Toast.makeText(ContractActivity.this, "Successfully Inserted", Toast.LENGTH_SHORT).show();
                }else {
                    alertDialog.dismiss();
                    Toast.makeText(ContractActivity.this, "Failed to Insert", Toast.LENGTH_SHORT).show();

                }

            }
        });

        alertDialog.show();
    }

    // SearchView
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem menuItem =menu.findItem(R.id.search);

        SearchView searchView =(SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {



                adapter.getFilter().filter(newText);

                return true;
            }
        });

        return true;
    }
}
