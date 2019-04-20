package com.example.restaurantproject.Reserve;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*Database class to contain reservation status*/
public class reserveDatabase extends SQLiteOpenHelper {
    //declare variable
    private static final String DATABASE_NAME = "Reserve.db";
    private static final String TABLE_NAME = "Reserve_table";
    private static final String COL1 = "NAME";
    private static final String COL2 = "DATE";
    private static final String COL3 = "TIME";
    private static final String COL4 = "EMAIL";
    private static final String COL5 = "PASSWORD";

    //Constructor
    public reserveDatabase(Context context) {
        super (context,DATABASE_NAME,null,1);
        SQLiteDatabase db = this.getWritableDatabase();
    }
    //Create new table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = String.format("CREATE TABLE " + TABLE_NAME +
                " (NAME TEXT,DATE TEXT,TIME TEXT,EMAIL TEXT,PASSWORD TEXT)");
        db.execSQL(createTable);
    }
    //Prevent a duplicate table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXIST " + TABLE_NAME));
        onCreate(db);
    }

    //Add new row in table
    public boolean addTable(String name, String date, String time, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1,name);
        contentValues.put(COL2,date);
        contentValues.put(COL3,time);
        contentValues.put(COL4,email);
        contentValues.put(COL5,password);
        Log.d("dbHelper", " Add " + name + " to " + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) { return false; }
        else { return  true; }
    }
    //Get name from table
    public Cursor getName() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT "+ COL1 + " FROM " + TABLE_NAME , null);
        return res;
    }
    //Get email from table
    public Cursor getEmail(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT "+ COL4 + " FROM " + TABLE_NAME + " WHERE " + COL1 + " = ?" , new String[] {name});
        return res;
    }
    //Get table's name from table
    public Cursor getTable(String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res;
        res = db.rawQuery(String.format("SELECT " + COL1 + " FROM " + TABLE_NAME + " WHERE " + COL1 + " = ?"), new String[] {table});
        return res;
    }

    //Update data by using table's name
   public boolean updateData (String name, String date, String time, String email, String password) {
       SQLiteDatabase db = this.getWritableDatabase();
       ContentValues contentValues = new ContentValues();
       contentValues.put(COL1,name);
       contentValues.put(COL2,date);
       contentValues.put(COL3,time);
       contentValues.put(COL4,email);
       contentValues.put(COL5,password);
       db.update(TABLE_NAME, contentValues, "NAME = ? ", new String[] {name});
       return true;


   }
    //Get date from table
   public Cursor getDate(String name) {
       SQLiteDatabase db = this.getWritableDatabase();
       Cursor res;
       res = db.rawQuery(String.format("SELECT " + COL2 + " FROM " + TABLE_NAME + " WHERE " + COL1 + " = ?"), new String[] {name});
       return res;
   }
    //Get time from the table
   public Cursor getTime(String name) {
       SQLiteDatabase db = this.getWritableDatabase();
       Cursor res;
       res = db.rawQuery(String.format("SELECT " + COL3 + " FROM " + TABLE_NAME + " WHERE " + COL1 + " = ?"), new String[] {name});
       return res;
   }
    //Delete a row by using table's name
   public Integer delete (String name) {
       SQLiteDatabase db = this.getWritableDatabase();
       return db.delete(TABLE_NAME, "NAME = ?", new String[] {name});
   }
    //Get password from table
   public Cursor getPass(String name) {
       SQLiteDatabase db = this.getWritableDatabase();
       Cursor res;
       res = db.rawQuery(String.format("SELECT " + COL5 + " FROM " + TABLE_NAME + " WHERE " + COL1 + " = ?"), new String[] {name});
       return res;
   }
}
