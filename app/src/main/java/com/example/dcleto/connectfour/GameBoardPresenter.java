package com.example.dcleto.connectfour;

/**
 * Created by Daniel on 20/07/2017.
 */

public class GameBoardPresenter implements GameBoardContract.Presenter {
    int currentPlayer = 1;
    int[][] gameField = new int[6][7];

    private GameBoardContract.View  mView;

    public GameBoardPresenter(GameBoardContract.View view) {
        this.mView = view;
        view.setPresenter(this);
    }
    @Override
    public void start() {
        mView.displayMessage("Juego Iniciado");
        prepareField();
    }

    @Override
    public void reset() {
        prepareField();
        currentPlayer = 1;
        mView.clearField();
    }

    public GameBoardContract.View getmView() {
        return mView;
    }

    public void setmView(GameBoardContract.View mView) {
        this.mView = mView;
    }

    void prepareField(){
        for(int i=0; i<6; i++){
            for(int j=0; j<7; j++){
                gameField[i][j]=(0);
            }
        }
    }

    @Override
    public void addDisc(int column) {
        if(gameField[5][column] != 0) return;

        if(currentPlayer == 1){
            mView.dropDisc(column,R.drawable.player1);
        }
       else if(currentPlayer == 2){
            mView.dropDisc(column,R.drawable.player2);
        }
        int row = -1;
        for (int i = 0; i < 6; i++) {
            if(gameField[i][column]== 0){
                gameField[i][column] = currentPlayer;
                row = i;
                break;
            }
        }
        boolean win = checkForVictory(row,column);
        if(win){
        mView.displayMessage("Usted Gano");
        reset();
        }
        else
        switchPlayer();

    }


    boolean checkForVictory(int row,int col){
        if(getAdj(row,col,0,1)+getAdj(row,col,0,-1) > 2){
            return true;
        } else {
            if(getAdj(row,col,1,0) > 2){
                return true;
            } else {
                if(getAdj(row,col,-1,1)+getAdj(row,col,1,-1) > 2){
                    return true;
                } else {
                    if(getAdj(row,col,1,1)+getAdj(row,col,-1,-1) > 2){
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
    }
    int getAdj(int row,int col,int row_inc,int col_inc){
        if(getCell(row,col) == getCell(row+row_inc,col+col_inc)){
            return 1+getAdj(row+row_inc,col+col_inc,row_inc,col_inc);
        } else {
            return 0;
        }
    }

    int getCell(int row,int col){
        try {
            int val = gameField[row][col];
            return  val;
        }catch (IndexOutOfBoundsException e){
            return -1;
        }
    }

    public  void switchPlayer(){
        if(currentPlayer == 1){
            currentPlayer =2;
        }
        else {
            currentPlayer =1;
        }
    }
}
