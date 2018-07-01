package com.example.entities;

public class Board {
    private int[] board;

    public Board(int dimension){
        board = new int[dimension];
        for (int i = 0; i< dimension*dimension; i++){
            board[i] = 0;
        }
    }

    public int[] getBoard() {
        return board;
    }

    public void setBoard(int[] board) {
        this.board = board;
    }
}
