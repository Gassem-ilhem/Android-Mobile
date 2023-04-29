package com.example.app01;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Login2.db";
    // Database Version
    private static final int DATABASE_VERSION = 4;
    SQLiteDatabase MyDB;
    public DatabaseHelper(Context context) {
        super(context, "Login2.db", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table customer(customer_id integer primary key autoincrement, customer_name text not null , customer_email text not null unique, customer_password text not null, customer_mobile_number text, customer_birth_date text,customer_secure text);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists customer");
        onCreate(db);
    }

    /////////select data from the database////////////
    public boolean add_customer(String email, String name, String password,String secure){
        MyDB = getWritableDatabase();
        ContentValues customerRow = new ContentValues();
        boolean emailExist = get_customer_email(email);
        if(emailExist)
            return false;//user isn't added
        else {
            customerRow.put("customer_name", name);
            customerRow.put("customer_email", email);
            customerRow.put("customer_password", password);
            customerRow.put("customer_secure", secure);
            MyDB.insert("customer", null, customerRow);
            MyDB.close();
            return true; //user is added
        }
    }


    /////////select data from the database////////////
   //login
    public boolean check_customer(String email, String password) {
        MyDB = getReadableDatabase();
        String[] arg = {email, password};
        Cursor cursor = MyDB.rawQuery("select * from customer where customer_email like ? and customer_password like ?", arg);
        if (cursor.getCount()>0)
            return true; //customer exists
        else
            return false; //customer doesn't exist
    }


    public boolean check_customer_secure(String email, String hint) {
        MyDB = getReadableDatabase();
        String[] arg = {email, hint};
        Cursor cursor = MyDB.rawQuery("select * from customer where customer_email like ? and customer_secure like ?", arg);
        if (cursor.getCount()>0)
            return true; //customer exists
        else
            return false; //customer doesn't exist
    }
    public boolean get_customer_email(String customer_email) {
        MyDB = getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("select customer_email from customer", null);
        if (cursor != null)
            cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getString(0).equals(customer_email))
                return true;//email exists
            else
                cursor.moveToNext();
        }
        return false; //email doesn't exist
    }


    public Cursor get_customer_data(String customer_email) {
            SQLiteDatabase MyDatabase = this.getWritableDatabase();
            Cursor cursor = MyDatabase.rawQuery("Select * from customer where customer_email = ?", new String[]{customer_email});

            return cursor;
    }

    /////////update data in the database////////////
    public void update_personalInfo(String customer_email,String name, String email, String mobileNumber) {
        MyDB = this.getWritableDatabase();

        ContentValues customer = new ContentValues();
        customer.put("customer_name", name);
        customer.put("customer_email", email);
        customer.put("customer_mobile_number", mobileNumber);
        MyDB.update("customer", customer, "customer_email like ?",new String[]{customer_email});
        MyDB.close();
    }

    public void reset_password(String customer_email, String newPassword) {
        MyDB = this.getWritableDatabase();
        ContentValues customer = new ContentValues();
        customer.put("customer_password", newPassword);
        MyDB.update("customer", customer, "customer_email=?",new String[]{customer_email});

        MyDB.close();
    }

}