package com.example.camefridge;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class FavoritesDao {
    public ArrayList<FavoriteProduct> allFavorites(Database db){
       ArrayList<FavoriteProduct> favoritesArrayList = new ArrayList<>();
        SQLiteDatabase dB = db.getWritableDatabase();
        Cursor c = dB.rawQuery("SELECT * FROM favorites", null);
        while(c.moveToNext()){
            @SuppressLint("Range") FavoriteProduct f = new FavoriteProduct(c.getInt(c.getColumnIndex("favorite_id"))
                    ,c.getString(c.getColumnIndex("favorite_name"))
                    ,c.getInt(c.getColumnIndex("limit_value")));
            favoritesArrayList.add(f);

        }
        dB.close();
        return favoritesArrayList;
    }

    public ArrayList<FavoriteProduct> searchFavorites(Database db,String word){
        ArrayList<FavoriteProduct> favoritesArrayList = new ArrayList<>();
        SQLiteDatabase dB = db.getWritableDatabase();
        Cursor c = dB.rawQuery("SELECT * FROM favorites WHERE favorite_name like '%"+word+"%'", null);
        while(c.moveToNext()){
            @SuppressLint("Range") FavoriteProduct f = new FavoriteProduct(c.getInt(c.getColumnIndex("favorite_id"))
                    ,c.getString(c.getColumnIndex("favorite_name"))
                    ,c.getInt(c.getColumnIndex("limit_value")));
            favoritesArrayList.add(f);
        }
        dB.close();
        return favoritesArrayList;
    }

    public void deleteFavorite(Database db,int favorite_id){
        SQLiteDatabase dB = db.getWritableDatabase();
        dB.delete("favorites","favorite_id=?", new String[]{String.valueOf(favorite_id)});
        dB.close();

    }

    public void addFavorite(Database db,String favorite_name, int limit_value){
        SQLiteDatabase dB = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("favorite_name",favorite_name);
        values.put("limit_value",limit_value);
        System.out.println(values);
        dB.insertOrThrow("favorites",null,values);
        System.out.println("sql çalışıyor");
        dB.close();

    }

    public void updateFavorite(Database db,int favorite_id,String favorite_name, int limit_value){
        SQLiteDatabase dB = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("favorite_name",favorite_name);
        values.put("limit_value",limit_value);
        dB.update("favorites", values,"favorite_id=?",new String[]{String.valueOf(favorite_id)});
        dB.close();

    }

}
