package com.example.dcleto.connectfour.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Daniel on 28/07/2017.
 */

public class ScoresDbHelper extends SQLiteOpenHelper {

     private final static String DB_NAME = "connect4.db";
     private final static int DB_VERSION = 2;

    public ScoresDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+ ScoresContract.ScoreEntry.TABLE_NAME + " ( "+
                ScoresContract.ScoreEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                ScoresContract.ScoreEntry.COLUMN_USER_NAME+ " TEXT NOT NULL, "+
                ScoresContract.ScoreEntry.COLUMN_SCORE+ " INTEGER NOT NULL);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
