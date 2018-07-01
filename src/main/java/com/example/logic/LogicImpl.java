package com.example.logic;

import com.example.entities.Board;
import com.example.interfaces.Logic;

import java.util.Random;

public class LogicImpl implements Logic {

    public int makeMove(Board board) {
        Random random = new Random();
        int newStep;
        while (true){
            newStep = random.nextInt(board.getBoard().length);
            if (board.getBoard()[newStep]==0) {
                break;
            }
        }
        return newStep;
    }
}
