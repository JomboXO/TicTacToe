package com.example.logic.impl;

import com.example.entities.Board;
import com.example.entities.GameResult;
import com.example.logic.Logic;

import java.util.Random;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

// 1='X'  2='O'
public class TicTacToe implements Logic {
    private int dimension;
    private ReentrantLock lock;
    // private LogicImpl opponent;

    public TicTacToe(int dimension, ReentrantLock lock) {
        this.dimension = dimension;
        this.lock = lock;
    }

    public GameResult getDraw(Board board) {
        if (checkHorizontalDraw(board) == -10) return GameResult.CONTINUE;
        if (checkVerticalDraw(board) == -10) return GameResult.CONTINUE;
        if (checkDiagonalDraw(board) == -10) return GameResult.CONTINUE;
        return GameResult.DRAW;
    }

    private int checkDiagonalDraw(Board board) {
        int result;
        //first diagonal
        int firstIndex = 0;
        int condition = dimension * dimension - 1;
        result = checkOneCaseForDraw(condition, firstIndex, dimension + 1, board, dimension + 1);

        //check second diagonal if first nobody can win
        if (result == -1) {
            firstIndex = dimension - 1;
            condition = dimension * (dimension - 1);
            result = checkOneCaseForDraw(condition, firstIndex, firstIndex + (dimension - 1), board, dimension - 1);
        }
        return result;
    }

    private int checkVerticalDraw(Board board) {
        int result = -10;
        int condition = dimension * (dimension - 1);
        for (int firstIndex = 0; firstIndex < dimension; firstIndex++) {
            result = checkOneCaseForDraw(condition, firstIndex, dimension, board, dimension);
            if (result == -10) break;
        }
        return result;
    }

    private int checkHorizontalDraw(Board board) {
        int result = -10;
        for (int firstIndex = 0; firstIndex < dimension; firstIndex++) {
            result = checkOneCaseForDraw(dimension - 1, firstIndex, firstIndex + 1, board, 1);
            if (result == -10) break;
        }
        return result;
    }

    private int checkOneCaseForDraw(int condition, int firstIndex, int lastIndex, Board board, int condForLastIndex) {
        int result = -10;
        while (lastIndex <= condition) {
            if (board.getSquare(firstIndex) != board.getSquare(lastIndex) && (board.getSquare(firstIndex) != 0 && board.getSquare(lastIndex) != 0)) {
                result = -1;
                break;
            } else {
                lastIndex = lastIndex + condForLastIndex;
            }
        }
        return result;
    }

    public GameResult getWinner() {
        int result;
        Board board = Board.getInstance();
        result = checkHorizontalWin(board);
        if (result > 0) return board.getSquare(result) == 1 ? GameResult.PLAYER : GameResult.OPPONENT;
        result = checkVerticalWin(board);
        if (result > 0) return board.getSquare(result) == 1 ? GameResult.PLAYER : GameResult.OPPONENT;
        result = checkDiagonalWin(board);
        if (result > 0) return board.getSquare(result) == 1 ? GameResult.PLAYER : GameResult.OPPONENT;

        return getDraw(board);
    }

    private int checkOneCaseForWin(int condition, int firstIndex, int lastIndex, Board board, int condForLastIndex) {
        int result = -10;
        while (lastIndex <= condition) {
            if (board.getSquare(firstIndex) != board.getSquare(lastIndex)) {
                result = -1;
                break;
            } else {
                lastIndex = lastIndex + condForLastIndex;
            }
        }
        return result;
    }

    private int checkHorizontalWin(Board board) {
        //initial result for checking
        int result = -10;
        for (int firstIndex = 0; firstIndex < dimension; firstIndex++) {

            result = checkOneCaseForWin(dimension - 1, firstIndex, firstIndex + 1, board, 1);

            if (result == -10) {
                result = board.getSquare(firstIndex);
                break;
            }
        }
        return result;
    }

    private int checkVerticalWin(Board board) {
        int result = -10;
        int condition = dimension * (dimension - 1);
        for (int firstIndex = 0; firstIndex < dimension; firstIndex++) {

            result = checkOneCaseForWin(condition, firstIndex, dimension, board, dimension);

            if (result == -10) {
                result = board.getSquare(firstIndex);
                break;
            }
        }
        return result;
    }

    private int checkDiagonalWin(Board board) {
        int result;
        //first diagonal
        int firstIndex = 0;
        int condition = dimension * dimension - 1;
        result = checkOneCaseForWin(condition, firstIndex, dimension + 1, board, dimension + 1);

        //check second diagonal if first didn't find winner
        if (result == -1) {
            firstIndex = dimension - 1;
            condition = dimension * (dimension - 1);
            result = checkOneCaseForWin(condition, firstIndex, firstIndex + (dimension - 1), board, dimension - 1);
        }
        if (result == -10) result = board.getSquare(firstIndex);

        return result;
    }

    @Override
    public void makeMove(int... index) {
        lock.lock();
        try {
            Board board = Board.getInstance();
            board.addTicTacToeChoice(index[0]);
        }finally {
            lock.unlock();
        }
    }
}
