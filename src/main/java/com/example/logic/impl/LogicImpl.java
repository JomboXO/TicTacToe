package com.example.logic.impl;

import com.example.entities.Board;
import com.example.logic.Logic;

import java.util.Random;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class LogicImpl implements Logic, Runnable {

    private ReentrantLock lock;

    public LogicImpl(ReentrantLock lock) {
        this.lock = lock;
    }

    public void makeMove(int... index) {
        lock.lock();
        try {
            Board board = Board.getInstance();
            Random random = new Random();
            int newStep;
            do {
                newStep = random.nextInt(board.getInlineDimension());
            } while (board.getSquare(newStep) != 0);
            board.addLogicChoice(newStep);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {

    }
}
