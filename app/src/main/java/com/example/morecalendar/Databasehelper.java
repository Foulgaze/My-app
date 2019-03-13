package com.example.morecalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Databasehelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "people_table";
    private static final String COL0 = "ID";
    private static final String COL1 = "name";
    private static final String COL2 = "weight";
    private static final String COL3 = "reps";
    private static final String COL4 = "date";
    public Databasehelper(Context context){
        super(context, TABLE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL1 + " TEXT," + COL2 + " REAL," + COL3 + " REAL," + COL4 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS  " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String item, Double weight, Double reps, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, item);
        contentValues.put(COL2, weight);
        contentValues.put(COL3, reps);
        contentValues.put(COL4, date);
        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);
        Log.d(TAG, "addData: Adding " + weight + " to " + TABLE_NAME);
        Log.d(TAG, "addData: Adding " + reps + " to " + TABLE_NAME);
        Log.d(TAG, "addData: Adding " + date + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }
    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        return data;
    }
    public void delete(String s){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(s, null, null);
    }
}
