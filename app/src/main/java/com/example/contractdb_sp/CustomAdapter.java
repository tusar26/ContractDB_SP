package com.example.contractdb_sp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {


    private Context context;
    private List<Contract_SD> allContract;
    private DatabaseHelper databaseHelper;

    public CustomAdapter(Context context, List<Contract_SD> allNotes) {
        this.context = context;
        this.allContract = allNotes;
        databaseHelper = new DatabaseHelper(context);
        this.context = context;
    }


    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_layout_design,parent,false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, final int position) {

        holder.name.setText(allContract.get(position).getName());
        holder.location.setText(allContract.get(position).getLocation());
        holder.phone.setText(allContract.get(position).getPhone());

        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Select Action");
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        customDialog(position);

                    }
                });


                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        int status = databaseHelper.deleteData(allContract.get(position).getId());
                        if (status == 1){
                            allContract.remove(allContract.get(position));
                            notifyDataSetChanged();
                        }else {

                        }

                    }
                });

                builder.show();
                return true;
            }
        });

        //set ClickListener
        holder.callImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber=allContract.get(position).getPhone();

                String s="tel:"+phoneNumber;
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(s));
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.smsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                String number = allContract.get(position).getPhone();
                intent.setData(Uri.parse("sms:"+number));
                String myMessage="Hello! How are You ?";
                intent.putExtra("Sms Body",myMessage);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return allContract.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView name,location,phone;
        ImageView callImage,smsImage;
        LinearLayout layout;



        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            name  = itemView.findViewById(R.id.customNameId);
            location   = itemView.findViewById(R.id.customLocationId);
            phone   = itemView.findViewById(R.id.customPhoneId);
            layout = itemView.findViewById(R.id.layout);
            callImage=itemView.findViewById(R.id.callID);
            smsImage=itemView.findViewById(R.id.smsID);

        }
    }

    private void customDialog(final int position) {

        androidx.appcompat.app.AlertDialog.Builder builder  = new androidx.appcompat.app.AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_dialog,null);
        builder.setView(view);


        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();

        final EditText name       = view.findViewById(R.id.name_ID);
        final EditText location       = view.findViewById(R.id.location_Id);
        final EditText phone = view.findViewById(R.id.phoneNumber);

        name.setText(allContract.get(position).getName());
        location.setText(allContract.get(position).getLocation());
        phone.setText(allContract.get(position).getPhone());

        Button saveButton    = view.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nameValue       = name.getText().toString();
                String locationValue = location.getText().toString();
                String phoneValue = phone.getText().toString();

                if (nameValue.isEmpty()){
                    name.setError("Enter Name");
                    return;
                }else if (locationValue.isEmpty()){
                    location.setError("Enter Location");
                    return;
                }else if (phoneValue.isEmpty()){
                    phone.setError("Enter PhoneNumber");
                    return;
                }

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("MM-dd");
                String date = format.format(calendar.getTimeInMillis());


                long status = databaseHelper.updateData(new Contract_SD(allContract.get(position).getId(),nameValue,locationValue,phoneValue));
                if (status == 1){
                    alertDialog.dismiss();
                    allContract.clear();
                    allContract.addAll(databaseHelper.getAllNotes());
                    notifyDataSetChanged();
                    Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
                }else {
                    alertDialog.dismiss();
                    Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();

                }
            }
        });

        alertDialog.show();


    }

}
