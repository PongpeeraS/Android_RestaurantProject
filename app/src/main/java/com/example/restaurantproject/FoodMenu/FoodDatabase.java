package com.example.restaurantproject.FoodMenu;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/*Helper class to use the pre-created Food menu database*/
public class FoodDatabase extends SQLiteAssetHelper {

    private static final String DB_NAME = "Food.db";
    private static final int DB_VER = 1;

    public FoodDatabase(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }
    /*
     * This method was created for get all Food from the database and return them to List of Food.
     * */
    public List<Food> getFood() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        //Define columns that need to return in this method
        String[] sqlselect = {"id", "name", "price", "des"};
        //Define table that need to find the data
        qb.setTables("Food");
        //Initial cursor for searching the data from the database.
        Cursor cursor = qb.query(db, sqlselect, null, null, null, null, null);
        List<Food> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int price = cursor.getInt(cursor.getColumnIndex("price"));
                String des = cursor.getString(cursor.getColumnIndex("des"));
                Food food = new Food(id, name, price, des);
                result.add(food);
            } while (cursor.moveToNext());
        }
        return result;
    }
    /*
     * This method was created for get the name of Food from the database and return them to List of String.
     * Name of Food will use for create a suggest list when the user start to type the query for searching */
    public List<String> getName(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        //Define columns that need to return in this method
        String[] sqlselect = {"name"};
        //Define table that need to find the data
        qb.setTables("Food");
        //Initial cursor for searching the data from the database.
        Cursor cursor = qb.query(db,sqlselect,null,null,null,null,null);
        List<String> result = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                result.add(cursor.getString(cursor.getColumnIndex("name")));
            }while (cursor.moveToNext());
        }
        return result;
    }
    /*
     * This method was created for get the Food from the database by using name as a criteria
     * and return them to List of Food.
     * */
    public List<Food> getFoodbyName(String qname){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        //Define columns that need to return in this method
        String[] sqlselect = {"id","name","price","des"};
        //Define table that need to find the data
        qb.setTables("Food");
        //Initial cursor for searching the data from the database.
        Cursor cursor = qb.query(db,sqlselect,"name LIKE?",new String[]{"%"+qname+"%"},null,null,null);
        List<Food> result = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int price = cursor.getInt(cursor.getColumnIndex("price"));
                String des = cursor.getString(cursor.getColumnIndex("des"));
                Food food = new Food(id,name,price,des);
                result.add(food);
            }while (cursor.moveToNext());
        }
        return result;
    }
}
