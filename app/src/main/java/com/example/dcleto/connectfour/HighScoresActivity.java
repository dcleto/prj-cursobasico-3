package com.example.dcleto.connectfour;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.dcleto.connectfour.data.ScoresContract;

public class HighScoresActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    SimpleCursorAdapter adapter;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        list = (ListView) findViewById(R.id.list_scores);

        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                ScoresContract.ScoreEntry.CONTENT_URI,
                null,
                null,
                null,
                ScoresContract.ScoreEntry.COLUMN_SCORE + "  DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor  data) {
        String[] from = new String[]{ScoresContract.ScoreEntry.COLUMN_USER_NAME, ScoresContract.ScoreEntry.COLUMN_SCORE};
        int[] to = new int[]{R.id.tv_name,R.id.tv_score};
        adapter = new SimpleCursorAdapter(this,R.layout.item_score,data,from,to);
        list.setAdapter(adapter);
        Log.v("DATASIZE",data.getCount()+"");
    }

    void insertScore(String username, int score){
        Intent intent = new Intent(this,ScoreService.class);
        intent.putExtra(Constants.USER_NAME,username);
        intent.putExtra(Constants.USER_SCORE,String.valueOf(score));
        startService(intent);
    }


    @Override
    public void onLoaderReset(Loader loader) {

    }
}
