package com.example.dcleto.connectfour;

/**
 * Created by Daniel on 15/07/2017.
 */

public class Disc {
    int player;
    int id;
    static int globalId;
    String color;
    Disc(int player){
        player = player;
        color = player == 1 ? "red" : "yellow";
        this.id = ++globalId;
    }




}

