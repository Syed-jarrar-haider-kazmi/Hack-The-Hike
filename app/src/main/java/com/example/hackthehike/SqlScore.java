package com.example.hackthehike;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlScore extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "hike.db";
    public static final int version = 1;
    public SqlScore(Context context) {
        super(context, DATABASE_NAME, null, version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String dbQuery = "CREATE TABLE Game (question String primary key not null, execute Integer )";
        sqLiteDatabase.execSQL(dbQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

//       sqLiteDatabase.execSQL();

    }

    public boolean InsetuserData(String question, Integer execute)
    {
        SQLiteDatabase DB=this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put("question",question);
        contentValues.put("execute",execute);


        long result=DB.insert("Game",null,contentValues);

        if(result==-1)
        {
            return  false;
        }
        else{
            return true;
        }



    }

    public boolean Update(String question, Integer execute)
    {
        SQLiteDatabase DB=this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("execute",execute);


        Cursor cursor = DB.rawQuery("Select * from Game where question =?", new String[] {question});

        if(cursor.getCount()>0)
        {
            long result=DB.update("Game",contentValues, "score=?", new String[]{question});


            if(result==-1)
            {
                return  false;
            }
            else{
                return true;
            }


        }
        else{
            return  false;
        }





    }

    public Cursor getData()
    {
        SQLiteDatabase DB=this.getReadableDatabase();

        Cursor cursor = DB.rawQuery("Select * from Game ", null);
 return cursor;




    }
}
