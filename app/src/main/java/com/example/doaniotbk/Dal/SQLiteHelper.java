package com.example.doaniotbk.Dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.doaniotbk.Model.Item;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends android.database.sqlite.SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DoanIOT.db";
    private static int DATABASE_VERSION = 1;
    public SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE data ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "image INTEGER,time INTEGER,temperature INTEGER,humidity REAL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
    public List<Item> getAll(){
        List<Item> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String order = "id ASC";
        Cursor rs = sqLiteDatabase.query("data",null,null,null,null
        ,null,order);
        while ((rs.moveToNext())&&(rs!=null)){
            int id = rs.getInt(0);
            int img = rs.getInt(1);
            int time = rs.getInt(2);
            int temperature = rs.getInt(3);
            float humidity = rs.getFloat(4);
            list.add(new Item(id,img,time,temperature,humidity));
        }
        return list;
    }
    public long addItem(Item i){
        ContentValues contentValues = new ContentValues();
        //contentValues.put("id",i.getId());
        contentValues.put("image",i.getImage());
        contentValues.put("time",i.getTime());
        contentValues.put("temperature",i.getTemperature());
        contentValues.put("humidity",i.getHumidity());
        SQLiteDatabase db = getWritableDatabase();
        return db.insert("data",null,contentValues);
    }
    public void delete(Item i){
        String sql = "DELETE FROM data WHERE id=?";
        String args[] ={String.valueOf(i.getId())};
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql,args);
    }
    public void deleteAll(){
        String sql = "DELETE FROM data";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }
}
