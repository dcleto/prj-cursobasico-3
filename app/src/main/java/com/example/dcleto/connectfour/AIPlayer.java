package com.example.dcleto.connectfour;

import android.os.Handler;
import android.util.Log;

import java.util.Random;

/**
 * Created by Daniel on 27/07/2017.
 */

public class AIPlayer {
    int[][] board;

    public AIPlayer(int[][] board) {
        this.board = board;
    }

    public void play(MoveListener listener){
        int[] moves = getAvailableMoves();
        Random random = new Random();
        int max = moves.length -1;
        int min = 0;

       int move = random.nextInt(max - min + 1) + min;;
       while (moves[move] == -1){
          move = random.nextInt(max - min + 1) + min;
       }


        listener.OnMoveProcessed(moves[move],move);
    }

    int[] getAvailableMoves(){

        int[] moves = new int[7];
        mainloop:
        for (int i = 0; i < 7; i++) {
            if(board[5][i] != 0) {
                moves[i]= -1;
                continue mainloop;
            }

             for (int j = 0; j < 6; j++) {
                if(board[j][i] == 0){
                    moves[i]= j;
                    continue mainloop;
                }
            }
        }

        String result = "";
        for (int i = 0; i < moves.length; i++) {
            result+= "["+moves[i]+ "], ";
        }
        Log.d("IA",result );
        return moves;
    }


    public interface MoveListener{
        void OnMoveProcessed(int row, int col);
    }
}
