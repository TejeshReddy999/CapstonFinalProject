package com.example.sunny.moviemagazine;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sunny on 25-05-2018.
 */

public class DataBase extends SQLiteOpenHelper {
    public static final int version = 2;


    public DataBase(Context context) {
        super(context, MovieAttributes.dataAttributes.TABLE_NAME, null, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + MovieAttributes.dataAttributes.TABLE_NAME + "("
                + MovieAttributes.dataAttributes.COLUMN_MOVIEID + " INTEGER PRIMARY KEY " + ","
                + MovieAttributes.dataAttributes.COLUMN_ORG_TITLE + " TEXT " + ","
                + MovieAttributes.dataAttributes.COLUMN_ORG_IMG + " TEXT " + ","
                + MovieAttributes.dataAttributes.COLUMN_ORG_RAT + " TEXT " + ","
                + MovieAttributes.dataAttributes.COLUMN_ORG_REL + " TEXT " + ")";


        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieAttributes.dataAttributes.TABLE_NAME);
        onCreate(db);
    }

    public boolean isFavorite(int fav) {
        SQLiteDatabase database = getWritableDatabase();
        String s = "SELECT * FROM " + MovieAttributes.dataAttributes
                .TABLE_NAME + " WHERE " + MovieAttributes.dataAttributes.COLUMN_MOVIEID + " =?";
        Cursor cursor = database.rawQuery(s, new String[]{String.valueOf(fav)});
        boolean isFavorite = false;
        if (cursor.moveToFirst()) {
            isFavorite = true;
            int count = 0;
            while (cursor.moveToNext()) {
                count++;
            }
        }
        cursor.close();
        database.close();
        return isFavorite;
    }
}
