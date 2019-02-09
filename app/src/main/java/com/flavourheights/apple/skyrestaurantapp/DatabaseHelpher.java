package com.flavourheights.apple.skyrestaurantapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;



import java.util.ArrayList;
import java.util.List;

public class DatabaseHelpher extends SQLiteOpenHelper {

public static final String DATABSE_NAME= "Restaurant.db";

    //private final String MY_QUERY = "SELECT Amount,Amount1 FROM GIVEMONEY a INNER JOIN TAKEMONEY b ON a.Id=b.Id WHERE a.Nameg=?";
//GiveMoney Table

    public static final String TABLE_Registration="Registration";
    public static final String COL0_Registration="Id";
    public static final String COL1_Registration="FName";
    public static final String COL2_Registration="LName";
    public static final String COL3_Registration="Email";
    public static final String COL4_Registration="PhoneNo";
    public static final String COL5_Registration="Password";


    public DatabaseHelpher(Context context) {
        super(context, DATABSE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL("create table " +TABLE_Registration+ "(Id Integer  primary key autoincrement, FName Text ,LName Text, Email Text,PhoneNo Text,Password Text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE " +TABLE_Registration);

    }


public Boolean RegistrationData(String fname,String lname,String email,String phoneno,String password)
{
    SQLiteDatabase db= getWritableDatabase();

    ContentValues contentValues=new ContentValues();

    contentValues.put(COL1_Registration,fname);
    contentValues.put(COL2_Registration,lname);
    contentValues.put(COL3_Registration,email);
    contentValues.put(COL4_Registration,phoneno);
    contentValues.put(COL5_Registration,password);


    long result = db.insert(TABLE_Registration, null, contentValues);

    if (result == -1) {
        return false;
    } else {
        return true;
    }
}




//    public Boolean UpdateGiveTakeData(String id, String name, String amount, String des, String cdate, String duedate, String type)
//    {
//        SQLiteDatabase db= getWritableDatabase();
//
//        ContentValues contentValues=new ContentValues();
//        contentValues.put(COL0_GIVETAKE,id);
//        contentValues.put(COL1_GIVETAKE,name);
//        contentValues.put(COL2_GIVETAKE,amount);
//        contentValues.put(COL3_GIVETAKE,des);
//        contentValues.put(COL4_GIVETAKE,cdate);
//        contentValues.put(COL5_GIVETAKE,duedate);
//        contentValues.put(COL6_GIVETAKE,type);
//
//        db.update(TABLE_GIVETAKE, contentValues, "Id = ?", new String[]{id});
//
//       return true;
//    }




    public Cursor GetRegData()
    {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from Registration", null);
        return cursor;
    }








}
