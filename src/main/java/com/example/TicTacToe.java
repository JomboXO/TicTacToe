package com.example;

public class TicTacToe {
    private int dimension;
    private LogicImpl opponent;
    public TicTacToe(int dimension, LogicImpl opponent) {

    }

    public char getWinner(Board board){
        checkHorizontal(board);
        checkVertical(board);
        checkDiagonal(board);
        return ' ';
    }

    private void checkHorizontal(Board board) {
        int lastIndex = 1;
        while (lastIndex < dimension){
            if (board.getBoard()[0] != board.getBoard()[lastIndex]){
                break;
            }
            else {
                lastIndex++;
            }
        }
    }

    private void checkVertical(Board board) {

    }

    private void checkDiagonal(Board board) {

    }


}
