package com.example.contractdb_sp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context,Query.DATABASE_NAME,null, Query.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

      db.execSQL(Query.CREATE_TABLE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " + Query.TABLE_NAME);
        onCreate(db);

    }


    //insertdata

    public long insertData(Contract_SD contract_sd){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Query.Name,contract_sd.getName());
        contentValues.put(Query.Location,contract_sd.getLocation());
        contentValues.put(Query.PhoneNumber,contract_sd.getPhone());

        long status = sqLiteDatabase.insert(Query.TABLE_NAME,null,contentValues);

        return status;
    }

    public List<Contract_SD> getAllNotes(){

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        List<Contract_SD> dataList    = new ArrayList<>();


        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+Query.TABLE_NAME,null);
        if (cursor.moveToFirst()){

            do {
                Contract_SD note = new Contract_SD(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));

                dataList.add(note);


            }while (cursor.moveToNext());

        }


        return dataList;
    }


    public int updateData(Contract_SD note){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Query.Name,note.getName());
        contentValues.put(Query.Location,note.getLocation());
        contentValues.put(Query.PhoneNumber,note.getPhone());

        int status = sqLiteDatabase.update(Query.TABLE_NAME,contentValues,"id=?",new String[]{String.valueOf(note.getId())});


        return status;
    }

    public int deleteData(int id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        int status = sqLiteDatabase.delete(Query.TABLE_NAME,"id=?",new String[]{String.valueOf(id)});

        return status;

    }

}
