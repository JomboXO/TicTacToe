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

    public TicTacToe(int dimension) {
        this.dimension = dimension;
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
        for (int firstIndex = 0; firstIndex < dimension; firstIndex++) {
            result = checkOneCaseForDraw(dimension*(dimension - 1)+firstIndex, firstIndex, firstIndex+dimension, board,dimension);
            if (result == -10) break;
        }
        return result;
    }

    private int checkHorizontalDraw(Board board) {
        int result = -10;

        int conditionfForBreak = dimension*(dimension - 1);
        for (int firstIndex = 0; firstIndex <= conditionfForBreak; firstIndex+=dimension) {
            result = checkOneCaseForDraw(firstIndex+dimension-1, firstIndex, firstIndex+1, board,1);
            if (result == -10) break;
        }

        return result;
    }

    private int checkOneCaseForDraw(int condition, int firstIndex, int lastIndex, Board board, int condForLastIndex) {
        int result = -10;
        for (int j = lastIndex; j <= condition; j+=condForLastIndex){
            if (board.getSquare(firstIndex) != board.getSquare(j) && (board.getSquare(firstIndex) != 0 && board.getSquare(j) != 0)) {
                result = -1;
                break;
            }
        }
        return result;
    }

    public GameResult getWinner() {
        int result;
        Board board = Board.getInstance();
        result = checkHorizontalWin(board);
        if (result > 0) return result == 1 ? GameResult.PLAYER : GameResult.OPPONENT;
        result = checkVerticalWin(board);
        if (result > 0) return result == 1 ? GameResult.PLAYER : GameResult.OPPONENT;
        result = checkDiagonalWin(board);
        if (result > 0) return result == 1 ? GameResult.PLAYER : GameResult.OPPONENT;
        return getDraw(board);
        //return GameResult.CONTINUE;
    }

    private int checkOneCaseForWin(int condition, int firstIndex, int lastIndex, Board board, int condForLastIndex) {
        int result = -10;
        for (int j = lastIndex; j <= condition; j+=condForLastIndex){
            if (board.getSquare(firstIndex) != board.getSquare(j)){
                result = -1;
                break;
            }
        }
        return result;
    }

    private int checkHorizontalWin(Board board) {
        //initial result for checking
        int result = -10;
        int conditionfForBreak = dimension*(dimension - 1);
        for (int firstIndex = 0; firstIndex <= conditionfForBreak; firstIndex+=dimension) {
            result = checkOneCaseForWin(firstIndex+dimension-1, firstIndex, firstIndex+1, board,1);
            if (result == -10 && board.getSquare(firstIndex) != 0){
                result = board.getSquare(firstIndex);
                break;
            }
        }
        return result;
    }

    private int checkVerticalWin(Board board) {
        int result = -10;
        //int conditionfForBreak = dimension*dimension - 1;
        for (int firstIndex = 0; firstIndex < dimension; firstIndex++) {
            result = checkOneCaseForWin(dimension*(dimension - 1)+firstIndex, firstIndex, firstIndex+dimension, board,dimension);
            if (result == -10 && board.getSquare(firstIndex) != 0){
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
        synchronized (Board.getInstance()) {
            try {

                System.out.println("Do wait for X:"+ Board.getInstance());
                Board board = Board.getInstance();
                board.addTicTacToeChoice(index[0]);
                //Board.getInstance().wait();
            } catch (Exception e) {
                System.err.println("Wait for X interrupted");
            }

            Board.getInstance().isLogicStep.set(true);
            Board.getInstance().notifyAll();
        }
    }
}
