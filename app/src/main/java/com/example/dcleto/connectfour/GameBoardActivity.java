package com.example.dcleto.connectfour;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class GameBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        int gameMode = getIntent().getIntExtra(Constants.GAME_MODE,-1);
        GameBoardFragment fragment = new GameBoardFragment();
        GameBoardPresenter presenter = new GameBoardPresenter(fragment,gameMode);
        getSupportFragmentManager().beginTransaction().add(R.id.frame_container,fragment).commit();

    }

}
