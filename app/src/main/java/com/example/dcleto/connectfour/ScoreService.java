package com.example.dcleto.connectfour;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.dcleto.connectfour.data.ScoresContract;

/**
 * Created by Daniel on 29/07/2017.
 */

public class ScoreService extends IntentService{

    public ScoreService() {
        super("ScoreService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.v("Servicio","Servicio Iniciado");
        String username = intent.getStringExtra(Constants.USER_NAME);
        String score = intent.getStringExtra(Constants.USER_SCORE);
        ContentValues values = new ContentValues();
        values.put(ScoresContract.ScoreEntry.COLUMN_USER_NAME,username);
        values.put(ScoresContract.ScoreEntry.COLUMN_SCORE,score);
        getContentResolver().insert(ScoresContract.ScoreEntry.CONTENT_URI,values);
    }
}
