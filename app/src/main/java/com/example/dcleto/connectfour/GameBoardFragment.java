package com.example.dcleto.connectfour;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class GameBoardFragment extends Fragment implements GameBoardContract.View{

    FrameLayout mContainer;
    TextView mTitle;
    float barSize;
    int totalCols =7;
    int duration = 1000;
    float unitSize;


    Button resetButton;
    private GameBoardContract.Presenter mPresenter;

    public GameBoardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.game_board, container, false);

        mContainer = (FrameLayout) rootView.findViewById(R.id.balls_container);
        mTitle = (TextView) rootView.findViewById(R.id.tv_title);
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        barSize = getResources().getDimensionPixelSize(resourceId);

        float x = getResources().getDisplayMetrics().widthPixels;
         unitSize = x/totalCols;

        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int)event.getX();
                 mPresenter.addDisc(getClickedCol(x));
                return false;
            }
        };

        mContainer.setOnTouchListener(touchListener);
        return rootView;
    }

    int getClickedCol(int x){
        int col = x/ (int)unitSize;
        if( (x%(int)unitSize) != 0){
            col++;
        }

        return col-1;
    }

    public void setPresenter(GameBoardContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void displayMessage(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clearField(){
     mContainer.removeAllViews();
    }

    @Override
    public void getUserName(final GameBoardContract.UserNameListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Introduzca su nombre:");

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               String  userName = input.getText().toString();
               listener.onAccept(userName);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onCancel();
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    public void setTitle(String title) {
        mTitle.setText(title);
    }


    @Override
    public void saveScore(String username, int score) {
            Intent intent = new Intent(getActivity(),ScoreService.class);
            intent.putExtra(Constants.USER_NAME,username);
            intent.putExtra(Constants.USER_SCORE,String.valueOf(score));
            getActivity().startService(intent);
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
    public void dropDisc(int column,int row, int discResource) {
        animateView(discResource,column,row);
    }


    void animateView(int res, int col, int row){

        float position = unitSize * (col);

        ImageView playerImage = new ImageView(getActivity());
        playerImage.setImageResource(res);
        playerImage.setX(position);
        int pixels = (int) unitSize;
        playerImage.setPadding(5,5,5,5);
        playerImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        playerImage.setLayoutParams(new FrameLayout.LayoutParams(pixels,pixels));
        mContainer.addView(playerImage);

        float bottomOfScreen = getResources().getDisplayMetrics().heightPixels -(pixels*2 ) - barSize- (pixels*(row-1));
        ObjectAnimator animator = ObjectAnimator.ofFloat(playerImage,"translationY",0f,bottomOfScreen);
        animator.setInterpolator(new AccelerateInterpolator());
       // animator.setInterpolator(new BounceInterpolator());
        animator.setDuration(duration);
        animator.start();
    }

    @Override
    public void showAlert(String title, String message, DialogInterface.OnClickListener acceptListener, DialogInterface.OnClickListener cancelListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK",acceptListener);
        builder.setNeutralButton("CANCEL",cancelListener);
        builder.show();
    }

    @Override
    public void goToMenu() {
        Intent intent = new Intent(getActivity(),MenuActivity.class);
        startActivity(intent);
    }

}
