package com.example.logic.impl;

import com.example.entities.Board;
import com.example.logic.Logic;

import java.util.Random;

// 1='X'  2='O'
public class TicTacToe implements Logic{
    private int dimension;
    private LogicImpl opponent;

    public TicTacToe(int dimension) {
        this.dimension = dimension;
    }

    public char getWinner(Board board) {
        int result;
        result = checkHorizontal(board);
        if (result > 0) return board.getSquare(result) == 1 ? 'X' : 'O';
        result = checkVertical(board);
        if (result > 0) return board.getSquare(result) == 1 ? 'X' : 'O';
        result = checkDiagonal(board);
        if (result > 0) return board.getSquare(result) == 1 ? 'X' : 'O';
        return ' ';
    }

    private int checkOneCase(int condition, int firstIndex, int lastIndex, Board board, int condForLastIndex) {
        int result = -10;
        while (lastIndex <= condition) {
            if (board.getSquare(firstIndex) != board.getSquare(firstIndex)) {
                result = -1;
                break;
            } else {
                lastIndex = lastIndex + condForLastIndex;
            }
        }
        return result;
    }

    private int checkHorizontal(Board board) {
        //initial result for checking
        int result = -10;
        for (int firstIndex = 0; firstIndex < dimension; firstIndex++) {

            result = checkOneCase(dimension - 1, firstIndex, firstIndex + 1, board, 1);

            if (result == -10) {
                result = board.getSquare(firstIndex);
                break;
            }
        }
        return result;
    }

    private int checkVertical(Board board) {
        int result = -10;
        int condition = dimension * (dimension - 1);
        for (int firstIndex = 0; firstIndex < dimension; firstIndex++) {

            result = checkOneCase(condition, firstIndex, dimension, board, dimension);

            if (result == -10) {
                result = board.getSquare(firstIndex);
                break;
            }
        }
        return result;
    }

    private int checkDiagonal(Board board) {
        int result;
        //first diagonal
        int firstIndex = 0;
        int condition = dimension * dimension - 1;
        result = checkOneCase(condition, firstIndex, dimension + 1, board, dimension + 1);

        //check second diagonal if first didn't find winner
        if (result == -1) {
            firstIndex = dimension - 1;
            condition = dimension * (dimension - 1);
            result = checkOneCase(condition, firstIndex, firstIndex + (dimension - 1), board, dimension - 1);
        }
        if (result == -10) result = board.getSquare(firstIndex);

        return result;
    }

    @Override
    public void makeMove(int index) {
        synchronized (Board.getInstance()){
            Board board = Board.getInstance();
            board.addTicTacToeChoice(index);
        }
    }
}
