package com.example.dcleto.connectfour;

import android.content.DialogInterface;

/**
 * Created by Daniel on 20/07/2017.
 */

 class GameBoardContract {

    interface View{
        void dropDisc(int column,int row, int discResource);
        void setPresenter(GameBoardContract.Presenter mPresenter);
        void displayMessage(String msg);
        void clearField();
        void getUserName(UserNameListener listener);
        void setTitle(String title);
        void saveScore(String username,int score);
        void showAlert(String title, String message, DialogInterface.OnClickListener acceptListener, DialogInterface.OnClickListener cancelListener);
        void goToMenu();
    }

    interface UserNameListener{
        void onAccept(String userName);
        void onCancel();
    }
    interface Presenter{
        void addDisc(int column);
        void start();
        void reset();
    }

}
