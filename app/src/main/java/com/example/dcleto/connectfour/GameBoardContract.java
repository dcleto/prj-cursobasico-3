package com.example.dcleto.connectfour;

/**
 * Created by Daniel on 20/07/2017.
 */

public class GameBoardContract {

    interface View{
        void dropDisc(int column, int discResource);
        void setPresenter(GameBoardContract.Presenter mPresenter);
        void displayMessage(String msg);
        void clearField();
        void displayResetButton(boolean visibility);
    }

    interface Presenter{
        void addDisc(int column);
        void start();
        void reset();
    }

}
