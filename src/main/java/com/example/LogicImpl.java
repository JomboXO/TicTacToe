package com.example;

import java.util.Random;

public class LogicImpl implements Logic {

    public int makeMove(Board board) {
        Random random = new Random();
        int newStep = 0;
        while (true){
            newStep = random.nextInt(board.getBoard().length);
            if (board.getBoard()[newStep]==0) {
                break;
            }
        }
        return newStep;
    }
}
