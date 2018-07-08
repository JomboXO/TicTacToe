package com.example.logic.impl;

import com.example.TicTacToeFrame;
import com.example.entities.Board;
import com.example.logic.Player;

import java.util.Random;

public class AIPlayer implements Player, Runnable {
    private ResultHandler resultHandler;
    private TicTacToeFrame frame;

    public AIPlayer(ResultHandler resultHandler, TicTacToeFrame frame) {
        this.resultHandler = resultHandler;
        this.frame = frame;
    }

    public void makeMove(int... index) {
        Board board = Board.getInstance();
        Random random = new Random();
        int newStep;
        do {
            newStep = random.nextInt(board.getInlineDimension());
        } while (board.getSquare(newStep) != 0);
        board.addLogicChoice(newStep);
        resultHandler.notifyObservers();
    }

    @Override
    public void run() {
        System.out.println("Start AI");
        while (!resultHandler.isEndGame.get()) {
            synchronized (Board.getInstance()) {
                try {
                    System.out.println("Do wait for O:" + Board.getInstance());
                    Board.getInstance().wait();
                    makeMove();
                } catch (InterruptedException e) {
                    System.err.println("Wait for O interrupted " + e.getMessage());
                }
            }
        }
        System.out.println("Terminate AI");
        frame.startNewGame();
    }
}
