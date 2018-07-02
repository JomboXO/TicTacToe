package com.example.logic.impl;

import com.example.entities.Board;
import com.example.logic.Logic;

import java.util.Random;

public class LogicImpl implements Logic {

    public void makeMove(int index) {
        Board board = Board.getInstance();
        Random random = new Random();
        int newStep;
        do {
            newStep = random.nextInt(board.getInlineDimension());
        } while (board.getSquare(newStep) != 0);
        board.addLogicChoice(newStep);
    }
}
