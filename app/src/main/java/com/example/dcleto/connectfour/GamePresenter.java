package com.example.dcleto.connectfour;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 15/07/2017.
 */

public class GamePresenter {

    int[][] gameField = new int[6][7];
    int currentCol;
    int currentRow;
    int currentPlayer;
    int id = 1;



    void prepareField(){
         for(int i=0; i<6; i++){
            for(int j=0; j<7; j++){
                gameField[i][j]=(0);
            }
        }
    }

    int getAdj(int row,int col,int row_inc,int col_inc){
        if(cellVal(row,col) == cellVal(row+row_inc,col+col_inc)){
            return 1+getAdj(row+row_inc,col+col_inc,row_inc,col_inc);
        } else {
            return 0;
        }
    }

    int cellVal(int row,int col){

            return gameField[row][col];

    }
    int firstFreeRow(int col,int player){
        int i;
        for( i = 0; i<6; i++){
            if(gameField[i][col]!=0){
                break;
            }
        }
        gameField[i-1][col] = player;
        return i-1;
    }

    int[] possibleColumns(){
        int[] moves_array = new int[7];
        for(int i=0; i<7; i++){
            if(gameField[0][i] == 0){
                moves_array[i]=i;
            }
        }
        return moves_array;
    }

    List<Integer> think(){
        int[] possibleMoves = possibleColumns();
        List<Integer> aiMoves = new ArrayList<>();
        int blocked;
        int bestBlocked = 0;
        int i,j;
        for( i=0; i<possibleMoves.length; i++){
            for( j=0; j<6; j++){
                if(gameField[j][possibleMoves[i]] != 0){
                    break;
                }
            }

            gameField[j-1][possibleMoves[i]] = 1;
            blocked = getAdj(j-1,possibleMoves[i],0,1)+getAdj(j-1,possibleMoves[i],0,-1);
            blocked = Math.max(blocked,getAdj(j-1,possibleMoves[i],1,0));
            blocked = Math.max(blocked,getAdj(j-1,possibleMoves[i],-1,1));
            blocked = Math.max(blocked,getAdj(j-1,possibleMoves[i],1,1)+getAdj(j-1, possibleMoves[i],-1,-1));

            if(blocked >= bestBlocked){
                if(blocked>bestBlocked){
                    bestBlocked = blocked;
                    aiMoves = new ArrayList();
                }
                aiMoves.add(possibleMoves[i]);
            }
            gameField[j-1][possibleMoves[i]] = 0;
        }

        return aiMoves;
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


    void addToScene(Disc disk){
         if(currentPlayer==2){
            //computer move
            List<Integer> possibleMoves = think();
            int cpuMove = (int) Math.floor( Math.random() * possibleMoves.size());
            currentCol = possibleMoves.get(cpuMove);
            dropDisc(this.id,currentPlayer);
        }
    }

    void init(){
        if(currentPlayer == 1){
            currentCol =1;
            if(currentCol<0){currentCol=0;}
            if(currentCol>6){currentCol=6;}

        }
    }
    void dropDisc(int id,int player){
        currentRow = firstFreeRow(currentCol,player);
        moveit(new Disc(id),(14+currentRow*60));
        currentPlayer = player;
        checkForMoveVictory();
    }
    void checkForMoveVictory(){
        if(!checkForVictory(currentRow,currentCol)){
            placeDisc(3-currentPlayer);
        } else {
            String ww = currentPlayer == 2 ? "Computer" : "Player";
            placeDisc(3-currentPlayer);
           // alert(ww+" win!");
            //board.innerHTML = "";
            newgame();
        }
    }

    void placeDisc(int player){
        currentPlayer = player;
        Disc disc = new Disc(player);
        addToScene(disc);
    }

    void moveit(Disc disc,int col){

    }

    void newgame(){
        prepareField();
     //   placeDisc(Math.floor(Math.random()*2)+1);
    }


    void onClick(int column){
        if(currentPlayer == 1){
            boolean validColumn = false;
            int[] posCols = possibleColumns();
            for (int i = 0; i < posCols.length; i++) {
                if(posCols[i] ==column)
                {validColumn = true; break;}
            }
            if(validColumn){
                dropDisc(currentPlayer,currentPlayer);
            }
        }
    }
}
