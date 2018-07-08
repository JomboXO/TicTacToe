package com.example.logic.impl;

import com.example.entities.Board;
import com.example.logic.Player;

/**
 * The class implements the human(main player) game
 */
public class HumanPlayer implements Player {

    private ResultHandler resultHandler;
    public HumanPlayer(ResultHandler resultHandler) {
        this.resultHandler = resultHandler;
    }

    @Override
    public void makeMove(int... index) {
        synchronized (Board.getInstance()) {
            try {
                System.out.println("Do wait for X:"+ Board.getInstance());
                Board board = Board.getInstance();
                board.addTicTacToeChoice(index[0]);
                resultHandler.notifyObservers();
            } catch (Exception e) {
                System.err.println("Wait for X interrupted");
            }
            if (!resultHandler.isEndGame.get())
                Board.getInstance().notifyAll();
        }
    }
}
