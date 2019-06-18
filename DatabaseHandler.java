package com.example.game.spidertask2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context){
        super(context,"ScoreManager",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String Query="CREATE TABLE time( Time INTEGER NOT NULL )";
        db.execSQL(Query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS time");
        onCreate(db);
    }

    public void addValue(Time time){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put("TIME",time.getTime());
        db.insert("time",null,values);
        db.close();
    }

    public List<Time> getAll(){
        List<Time> timelist=new ArrayList<Time>();
        String Query="SELECT* FROM time";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(Query,null);
        if(cursor.moveToFirst()){
            do{
                Time time=new Time();
                time.setTime(Integer.parseInt(cursor.getString(0)));
                timelist.add(time);
            }while(cursor.moveToNext());
        }
        return timelist;
    }

}
