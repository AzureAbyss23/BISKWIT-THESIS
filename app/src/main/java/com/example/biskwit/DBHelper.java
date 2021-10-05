package com.example.biskwit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }

    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("CREATE TABLE User(User_ID INTEGER PRIMARY KEY AUTOINCREMENT, Email TEXT, Password TEXT, Parent_Instructor TEXT, Child_Name TEXT, Age INTEGER, Birthdate TEXT, Severity TEXT)");
    }

    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists User");
    }

    public Boolean insertuserdata(String email, String password, String parent_instructor
        , String namechild, int age, String birthdate, String sever_level){

        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Email", email);
        contentValues.put("Password", password);
        contentValues.put("Parent_Instructor", parent_instructor);
        contentValues.put("Child_Name", namechild);
        contentValues.put("Age", age);
        contentValues.put("Birthdate", birthdate);
        contentValues.put("Severity", sever_level);

        long result=DB.insert("User", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public boolean login(String email, String password){

        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM User WHERE Email IN ('"+email+"') and Password IN ('"+password+"');",null);
        if(cursor.getCount()==0){
            return false;
            }
            else {
            return true;
            }
    }

    public Cursor getdata ()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from User", null);
        return cursor;

    }

}
