package com.example.restaurantproject.Coupon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import java.util.ArrayList;
import java.util.List;

/*SQLite database class to contain the users' coupons*/
public class couponDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME = "Coupons.db";
    private static final int DB_VER = 1;
    private static final String TABLE_NAME = "Coupon";
    private static final String KEY_UID = "uID"; //user id
    private static final String KEY_NAME = "name"; //coupon name
    private static final String KEY_DESC = "description"; //coupon description
    private static final String KEY_ENDDATE = "enddate"; //coupon's expiry date
    private static final String KEY_CODE = "code"; //coupon's code to use
    private static final String KEY_NUMOFUSES = "numOfUses"; //coupon's remaining uses

    public couponDatabase(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +" (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_UID + " TEXT, "
                + KEY_NAME + " TEXT, "
                + KEY_DESC + " TEXT, "
                + KEY_ENDDATE + " TEXT, "
                + KEY_CODE + " TEXT, "
                + KEY_NUMOFUSES + " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*Method to get coupons of a user (via userID)*/
    public List<Coupon> getCoupons(String uID) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        /*SQLiteQueryBuilder is used to simplify the SELECT query*/
        //Attributes to retrieve from the table
        String[] sqlselect = {"_id", KEY_UID, KEY_NAME, KEY_DESC, KEY_ENDDATE, KEY_CODE, KEY_NUMOFUSES};
        qb.setTables(TABLE_NAME);
        //SELECT QUERY: SELECT * FROM Coupon WHERE uID = '(userID)'
        Cursor cursor = qb.query(db, sqlselect, "uID = ?", new String[] {uID},
                null, null, null);
        List<Coupon> result = new ArrayList<>();
        //Retrieving results, put them into a Coupon object and into an ArrayList
        if (cursor.moveToFirst()) {
            do {
                String uid = cursor.getString(cursor.getColumnIndex(KEY_UID));
                String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
                String desc = cursor.getString(cursor.getColumnIndex(KEY_DESC));
                String enddate = cursor.getString(cursor.getColumnIndex(KEY_ENDDATE));
                String code = cursor.getString(cursor.getColumnIndex(KEY_CODE));
                int numOfUses = cursor.getInt(cursor.getColumnIndex(KEY_NUMOFUSES));
                result.add(new Coupon(uid, name, desc, enddate, code, numOfUses));
            } while (cursor.moveToNext());
        }
        return result;
    }

    /*Method to insert a coupon into Coupon table*/
    public long insertCoupon(Coupon coupon){
        //Insert coupon created in CouponActivity
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_UID, coupon.getuID());
        cv.put(KEY_NAME, coupon.getName());
        cv.put(KEY_DESC, coupon.getDesc());
        cv.put(KEY_ENDDATE, coupon.getEnddate());
        cv.put(KEY_CODE, coupon.getCode());
        cv.put(KEY_NUMOFUSES, coupon.getNumOfUses());
        //INSERT: INSERT INTO Coupon VALUES (coupon.getId(), coupon.getuID(), ...)
        return db.insert(TABLE_NAME, null, cv); //Insert into table using created ContentValues
    }

    //Delete a coupon from the table using the user's uID & code
    public boolean deleteCoupon(Coupon coupon) {
        SQLiteDatabase db = getWritableDatabase();
        //DELETE: DELETE FROM Coupon WHERE "uID = '(coupon.getuID())' AND code = (coupon.getCode())"
        return db.delete(TABLE_NAME, "uID = ? AND code = ?",
                new String[] {coupon.getuID(), coupon.getCode()}) > 0;
    }

    //Update a coupon's remaining uses from the table using the user's uID & code
    public long updateCoupon(Coupon coupon){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_NUMOFUSES, coupon.getNumOfUses()-1);
        //UPDATE: UPDATE Coupon SET numOfUses = (coupon.getNumOfUses()-1)
        // WHERE "uID = '(coupon.getuID())' AND code = (coupon.getCode())"
        return db.update(TABLE_NAME, cv, "uID = ? AND code = ?",
                new String[] {coupon.getuID(), coupon.getCode()});
    }

}
