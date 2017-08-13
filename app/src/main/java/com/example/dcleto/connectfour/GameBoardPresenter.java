package com.example.dcleto.connectfour;

import android.content.DialogInterface;
import android.os.Handler;

/**
 * Created by Daniel on 20/07/2017.
 */

class GameBoardPresenter implements GameBoardContract.Presenter {
    int currentPlayer = 1;
    int[][] gameField = new int[6][7];
    int mGameMode;
    int mScore = 21000;
    boolean IS_SINGLEPLAYER_MODE;
    boolean AIPlaying = false;
    String mCurrentUserName ="SIN NOMBRE";
    private GameBoardContract.View  mView;
    boolean mGameCompleted;
    AIPlayer mAIPlayer;

    public GameBoardPresenter(GameBoardContract.View view, int gameMode) {
        this.mView = view;
        mGameMode = gameMode;
        IS_SINGLEPLAYER_MODE = gameMode == Constants.GAME_MODE_SINGLE;
        view.setPresenter(this);
    }
    @Override
    public void start() {

        if(IS_SINGLEPLAYER_MODE)
        {
            mAIPlayer = new AIPlayer(gameField);
        }
        startGame();

    }

    void startGame(){
        mView.displayMessage("Juego Iniciado");
        prepareField();
    }

    @Override
    public void reset() {
        prepareField();
        currentPlayer = 1;
        mGameCompleted = false;
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
        if(isGameCompleted() || AIPlaying) return;
        if(gameField[5][column] != 0) return;

        int row = -1;
        for (int i = 0; i < 6; i++) {
            if(gameField[i][column]== 0){
                gameField[i][column] = currentPlayer;
                row = i;
                break;
            }
        }

        if(currentPlayer == 1){
            mView.dropDisc(column, row,R.drawable.player1);
            if(IS_SINGLEPLAYER_MODE){
            if(!isGameCompleted()){
                AIPlaying = true;
                mAIPlayer.play(new AIPlayer.MoveListener() {
                    @Override
                    public void OnMoveProcessed(final int row, final int col) {
                        gameField[row][col] = 2;
                        new Handler().postDelayed(new Runnable(){
                                @Override
                                public void run() {
                                mView.dropDisc(col,row,R.drawable.player2);
                                boolean won = checkForVictory(row,col);
                                if(won)onVictory();
                                switchPlayer();
                                mScore-=1000;

                                AIPlaying = false;
                            }
                        }, 1000);
                    }
                });}
            }

        }
       else if(currentPlayer == 2){
            mView.dropDisc(column,row,R.drawable.player2);
        }

        boolean win = checkForVictory(row,column);
        if(win){
            onVictory();
        }
        else
        switchPlayer();

    }

    void onVictory(){
        final String message;
        if(IS_SINGLEPLAYER_MODE){
            message = "Has " + (currentPlayer ==1?  "ganado" : "perdido") +
                    "\nPuntuacion : " + mScore+
                    "\n¿Deseas jugar de nuevo?";
            mView.getUserName(new GameBoardContract.UserNameListener() {
            @Override
            public void onAccept(String userName) {
                mCurrentUserName = userName;
                mView.saveScore(userName,mScore);
                mView.displayMessage(userName);
                gameOverMessage(message);
            }
            @Override
            public void onCancel() {
                mView.saveScore("NO NAME",mScore);

            }
        });

        }else{
            message = "Jugador " + currentPlayer + " ha ganado \n ¿Deseas jugar de nuevo?";
            gameOverMessage(message);
        }
        setIsGameCompleted(true);

    }

    void gameOverMessage(String message){
        mView.showAlert("Fin del Juego",message , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reset();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mView.goToMenu();
            }
        });
    }
    boolean isFullBoard(){
    for (int i = 0; i < 7; i++) {
        if(gameField[5][i] == 0){
            return false;
        }
    }
    return true;
}

    boolean checkForVictory(int row,int col){
        if(getAdj(row,col,0,1)+getAdj(row,col,0,-1) > 2){
            return true;
        } else {
            if(getAdj(row,col,1,0)+getAdj(row,col,-1,0) > 2){
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
        if(IS_SINGLEPLAYER_MODE) return;
        mView.setTitle("Turno: Jugador " + currentPlayer);
    }

    boolean isGameCompleted(){
        return mGameCompleted;
    };

    void  setIsGameCompleted(boolean completed){
          mGameCompleted =completed;
    };
}
