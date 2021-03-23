package com.yusby.bancomat20;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alessandro on 24/03/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Clienti3.db";
    public static final String TABLE_NAME= "clienti_table3";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "PASSWORD";
    public static final String COL_4 = "MONEY";
    public static final String COL_5 = "EMAIL";
    public static final String COL_6 = "SECUREKEY";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,PASSWORD TEXT,MONEY,EMAIL TEXT,SECUREKEY TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name,String password, String money,String email,String securekey){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,password);
        contentValues.put(COL_4,money);
        contentValues.put(COL_5,email);
        contentValues.put(COL_6,securekey);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return  res;
    }

    public boolean updateData(String id,String money){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_4,money);
        db.update(TABLE_NAME,contentValues,"ID = ?",new String[] { id });
        return true;
    }

    public boolean updatePassword(String id,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_3,password);
        db.update(TABLE_NAME,contentValues,"ID = ?",new String[] { id });
        return true;
    }
}
