package com.example.restaurantproject.Order;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import java.util.ArrayList;
import java.util.List;

/*SQLite database class to contain the users' orders*/
public class OrderDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME = "Orders.db";
    private static final int DB_VER = 1;
    private static final String TABLE_NAME = "Order1";
    private static final String KEY_UID = "uID"; //user id
    private static final String KEY_FOOD = "food"; //food name
    private static final String KEY_AMOUNT = "amount"; //amount ordered
    private static final String KEY_PRICE = "price"; //order price
    private static final String KEY_ADDRESS = "address"; //delivery address
    private static final String KEY_STATUS = "status"; //delivery status (current/history)


    public OrderDatabase(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_UID + " TEXT, "
                + KEY_FOOD + " TEXT, "
                + KEY_AMOUNT + " INTEGER, "
                + KEY_PRICE + " INTEGER, "
                + KEY_ADDRESS + " TEXT, "
                + KEY_STATUS + " BOOLEAN);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*Method to get current orders of a user (via userID)*/
    public List<Order> getCurrentOrder(String uID) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        /*SQLiteQueryBuilder is used to simplify the SELECT query*/
        //Attributes to retrieve from the table
        String[] sqlselect = {"_id", KEY_UID, KEY_FOOD, KEY_AMOUNT, KEY_PRICE, KEY_ADDRESS, KEY_STATUS};
        qb.setTables(TABLE_NAME);
        //SELECT QUERY: SELECT * FROM Order1 WHERE uID = '(userID)'
        Cursor cursor = qb.query(db, sqlselect, "uID = ? AND status = 0", new String[] {uID},
                null, null, null);
        List<Order> result = new ArrayList<>();
        //Retrieving results, put them into a Order object and into an ArrayList
        if (cursor.moveToFirst()) {
            do {
                String uid = cursor.getString(cursor.getColumnIndex(KEY_UID));
                String food = cursor.getString(cursor.getColumnIndex(KEY_FOOD));
                int amount = cursor.getInt(cursor.getColumnIndex(KEY_AMOUNT));
                int price = cursor.getInt(cursor.getColumnIndex(KEY_PRICE));
                String address = cursor.getString(cursor.getColumnIndex(KEY_ADDRESS));
                boolean status = cursor.getInt(cursor.getColumnIndex(KEY_STATUS))>0;
                result.add(new Order(uid, food, amount, price, address, status));
            } while (cursor.moveToNext());
        }
        return result;
    }

    /*Method to get previous orders of a user (via userID)*/
    public List<Order> getHistoryOrder(String uID) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        /*SQLiteQueryBuilder is used to simplify the SELECT query*/
        //Attributes to retrieve from the table
        String[] sqlselect = {"_id", KEY_UID, KEY_FOOD, KEY_AMOUNT, KEY_PRICE, KEY_ADDRESS, KEY_STATUS};
        qb.setTables(TABLE_NAME);
        //SELECT QUERY: SELECT * FROM Order1 WHERE uID = '(userID)'
        Cursor cursor = qb.query(db, sqlselect, "uID = ? AND status = 1", new String[] {uID},
                null, null, null);
        List<Order> result = new ArrayList<>();
        //Retrieving results, put them into a Order object and into an ArrayList
        if (cursor.moveToFirst()) {
            do {
                String uid = cursor.getString(cursor.getColumnIndex(KEY_UID));
                String food = cursor.getString(cursor.getColumnIndex(KEY_FOOD));
                int amount = cursor.getInt(cursor.getColumnIndex(KEY_AMOUNT));
                int price = cursor.getInt(cursor.getColumnIndex(KEY_PRICE));
                String address = cursor.getString(cursor.getColumnIndex(KEY_ADDRESS));
                boolean status = cursor.getInt(cursor.getColumnIndex(KEY_STATUS))>0;
                result.add(new Order(uid, food, amount, price, address, status));
            } while (cursor.moveToNext());
        }
        return result;
    }

    /*Method to insert an order into Order table*/
    public long insertOrder(Order order){
        //Insert order created in FoodMenuActivity
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_UID, order.getUID());
        cv.put(KEY_FOOD, order.getFood());
        cv.put(KEY_AMOUNT, order.getAmount());
        cv.put(KEY_PRICE, order.getPrice());
        cv.put(KEY_ADDRESS, order.getAddress());
        cv.put(KEY_STATUS, order.isReceived());
        //INSERT: INSERT INTO Order VALUES (order.getId(), order.getuID(), ...)
        return db.insert(TABLE_NAME, null, cv); //Insert into table using created ContentValues
    }

    public boolean UpdateStatusOrder(String uID,String food_name) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_STATUS, true);
        //UPDATE: UPDATE Order1 SET status = 0/1 WHERE uID = ? AND food = ?
        return db.update(TABLE_NAME,contentValues,"uID = ? AND food = ?",new String[] {uID,food_name}) > 0;
    }

}
