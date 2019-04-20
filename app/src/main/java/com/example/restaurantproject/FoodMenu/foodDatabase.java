package com.example.restaurantproject.FoodMenu;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class foodDatabase extends SQLiteAssetHelper {

    private static final String DB_NAME = "Food.db";
    private static final int DB_VER = 1;

    public foodDatabase(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public List<Food> getFood() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlselect = {"id", "name", "price", "des"};
        qb.setTables("Food");
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

    public List<String> getName(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlselect = {"name"};
        qb.setTables("Food");
        Cursor cursor = qb.query(db,sqlselect,null,null,null,null,null);
        List<String> result = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                result.add(cursor.getString(cursor.getColumnIndex("name")));
            }while (cursor.moveToNext());
        }
        return result;
    }

    public List<Food> getFoodbyName(String qname){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlselect = {"id","name","price","des"};

        qb.setTables("Food");
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
