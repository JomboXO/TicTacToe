package com.example.logic.impl;

import com.example.entities.Board;
import com.example.logic.Logic;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class LogicImpl implements Logic, Runnable {
    public void makeMove(int... index) {
        Board board = Board.getInstance();
        Random random = new Random();
        int newStep;
        do {
            newStep = random.nextInt(board.getInlineDimension());
        } while (board.getSquare(newStep) != 0);
        board.addLogicChoice(newStep);
    }

    @Override
    public void run() {

        while (true) {
            synchronized (Board.getInstance()) {
                while(Board.getInstance().isLogicStep.get()){
                    try{
                        System.out.println("Do wait for O:" + Board.getInstance());
                        makeMove();
                        Board.getInstance().wait();
                    }catch (InterruptedException e){
                        System.err.println("Wait for O interrupted "+e.getMessage());

                    }
                }
                Board.getInstance().isLogicStep.set(false);
                Board.getInstance().notifyAll();
            }
        }
    }
}
