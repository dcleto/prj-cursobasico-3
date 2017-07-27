package com.example.dcleto.connectfour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {


    LinearLayout[] cols;
    LinearLayout col1,col2,col3,col4,col5,col6,col7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linears);
        col1 = (LinearLayout) findViewById(R.id.col1);
        col2 = (LinearLayout) findViewById(R.id.col2);
        col3 = (LinearLayout) findViewById(R.id.col3);
        col4 = (LinearLayout) findViewById(R.id.col4);
        col5 = (LinearLayout) findViewById(R.id.col5);
        col6 = (LinearLayout) findViewById(R.id.col6);
        col7 = (LinearLayout) findViewById(R.id.col7);

        cols = new LinearLayout[]{col1,col2,col3,col4,col5,col6,col7};
        for (int i = 0; i < cols.length; i++) {
            cols[i].setOnClickListener(listener);
        }
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LinearLayout view = (LinearLayout) v;
            ImageView iv = new ImageView(MainActivity.this);
            iv.setImageResource(R.drawable.player1);
            final float scale = getResources().getDisplayMetrics().density;
            int dpWidthInPx  = (int) (50 * scale);
            int padding = (int) (3 * scale);
            iv.setPadding(padding,padding,padding,padding);

            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpWidthInPx);
            iv.setLayoutParams(params);
            view.addView(iv,0);
        }
    };


}
