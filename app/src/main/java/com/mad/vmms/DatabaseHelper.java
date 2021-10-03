package com.mad.vmms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE = "Vehicle Monitoring System";
    private static String USERS = "Driver";
    private static String UCOL_1 = "UID";
    private static String UCOL_2 = "Name";
    private static String UCOL_3 = "Email";
    private static String UCOL_4 = "Password";
    private static String UCOL_5 = "Contact";
    private static String UCOL_6 = "Licence";

    private static String FUEL = "FuelDetail";
    private static String COL_1 = "DID";
    private static String COL_2 = "UID";
    private static String COL_3 = "DateTime";
    private static String COL_4 = "Volume";
    private static String COL_5 = "Price";
    private static String COL_6 = "Total";
    private static String COL_7 = "Odometer";
    private static String COL_8 = "Average";
    private static String COL_9 = "Rs";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists "+USERS+" ("+
                UCOL_1+" integer primary key autoincrement," +
                UCOL_2+" text," +
                UCOL_3+" text," +
                UCOL_4+" text," +
                UCOL_5+" text," +
                UCOL_6+" text)");

        sqLiteDatabase.execSQL("create table if not exists "+FUEL+" ("+
                COL_1+" integer primary key autoincrement," +
                COL_2+" integer," +
                COL_3+" integer," +
                COL_4+" text," +
                COL_5+" text," +
                COL_6+" text," +
                COL_7+" text," +
                COL_8+" text," +
                COL_9+" text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+USERS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+FUEL);
        onCreate(sqLiteDatabase);
    }

    public boolean insert(String name, String email, String pwd, String contact, String licence)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(UCOL_2,name);
        cv.put(UCOL_3,email);
        cv.put(UCOL_4,pwd);
        cv.put(UCOL_5,contact);
        cv.put(UCOL_6,licence);
        long result = sqLiteDatabase.insert(USERS,null,cv);
        return (result==-1)?false:true;
    }

    public boolean insertFuel(String uid, String date, String volume, String price, String total, String odometer, String valAvg, String valRs)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2,uid);
        cv.put(COL_3,date);
        cv.put(COL_4,volume);
        cv.put(COL_5,price);
        cv.put(COL_6,total);
        cv.put(COL_7,odometer);
        cv.put(COL_8,valAvg);
        cv.put(COL_9,valRs);
        long result = sqLiteDatabase.insert(FUEL,null,cv);
        return (result==-1)?false:true;
    }

    public Cursor selectUser(String valUsername, String valPassword)
    {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(USERS,
                new String[]{"UID","Name"},
                "Email = ? and Password = ?",
                new String[]{valUsername,valPassword},null,null,null);
    }

    public Cursor selectEntry(String uid)
    {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(FUEL,
                new String[]{"DID","Price","Odometer"},
                "UID = ?",
                new String[]{uid},null,null,"DID DESC","1");
//        Log.e("query",sql);
    }

    public boolean updateEntry(String uid,String did, String avg, String rs) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE FuelDetail SET Average = "+avg+" , Rs = "+rs+" where DID = "+did+" and UID = "+uid;
//        Log.e("query",sql);
        db.execSQL(sql);
        return true;
    }

    public Cursor selectEntries(String uid) {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(FUEL,
                new String[]{"DID","DateTime","Volume","Price","Odometer","Average","Rs"},
                "UID = ?",
                new String[]{uid},null,null,null,null);
    }

}

