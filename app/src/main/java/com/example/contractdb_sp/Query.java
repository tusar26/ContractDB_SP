package com.example.contractdb_sp;

public class Query {

    public static final String DATABASE_NAME = "ContractList.db";
    public static final int VERSION = 1;
    public static final String TABLE_NAME = "contract";


    public static final String ID           ="id";
    public static final String Name         ="name";
    public static final String Location     ="location";
    public static final String PhoneNumber  ="phoneNumber";


    public static String CREATE_TABLE_QUERY ="CREATE TABLE "+TABLE_NAME+"("
            +ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +Name+" TEXT, "
            +Location+" TEXT, "
            +PhoneNumber+ " TEXT"
            +")";
}
