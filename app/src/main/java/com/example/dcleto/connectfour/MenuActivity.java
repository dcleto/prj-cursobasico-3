package com.example.dcleto.connectfour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViewById(R.id.tv_twoplayer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,GameBoardActivity.class);
                intent.putExtra(Constants.GAME_MODE,Constants.GAME_MODE_TWO_PLAYERS);
                startActivity(intent);
            }
        });

        findViewById(R.id.tv_oneplayer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,GameBoardActivity.class);
                intent.putExtra(Constants.GAME_MODE,Constants.GAME_MODE_SINGLE);
                startActivity(intent);

            }
        });

        findViewById(R.id.tv_highscores).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,HighScoresActivity.class);
                startActivity(intent);

            }
        });
    }
}
