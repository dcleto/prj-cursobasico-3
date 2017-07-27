package com.example.dcleto.connectfour;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class GameBoardFragment extends Fragment implements GameBoardContract.View{
    LinearLayout[] cols;
    LinearLayout col1,col2,col3,col4,col5,col6,col7;
    Button resetButton;
    private GameBoardContract.Presenter mPresenter;

    public GameBoardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game_board, container, false);
        resetButton = (Button) rootView.findViewById(R.id.btn_reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.reset();
            }
        });
        col1 = (LinearLayout) rootView.findViewById(R.id.col1);
        col2 = (LinearLayout) rootView.findViewById(R.id.col2);
        col3 = (LinearLayout) rootView.findViewById(R.id.col3);
        col4 = (LinearLayout) rootView.findViewById(R.id.col4);
        col5 = (LinearLayout) rootView.findViewById(R.id.col5);
        col6 = (LinearLayout) rootView.findViewById(R.id.col6);
        col7 = (LinearLayout) rootView.findViewById(R.id.col7);

        cols = new LinearLayout[]{col1,col2,col3,col4,col5,col6,col7};
        for (int i = 0; i < cols.length; i++) {
            cols[i].setOnClickListener(listener);
        }
        return rootView;
    }

    public void setPresenter(GameBoardContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void displayMessage(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void clearField(){
        for (int i = 0; i < cols.length; i++) {
            cols[i].removeAllViews();
        }
    }

    @Override
    public void displayResetButton(boolean display) {
        int visibility = display? View.VISIBLE:View.INVISIBLE;
        resetButton.setVisibility(visibility);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LinearLayout view = (LinearLayout) v;
            int column = Integer.parseInt((String)view.getTag());
            mPresenter.addDisc(column);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void dropDisc(int column, int discResource) {
        LinearLayout view = cols[column];
        ImageView iv = new ImageView(getActivity());
        iv.setImageResource(discResource);
        final float scale = getResources().getDisplayMetrics().density;
        int dpWidthInPx  = (int) (50 * scale);
        int padding = (int) (3 * scale);
        iv.setPadding(padding,padding,padding,padding);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpWidthInPx);
        iv.setLayoutParams(params);
        view.addView(iv,0);
    }


}
