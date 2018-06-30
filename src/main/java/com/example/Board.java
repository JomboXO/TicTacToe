package com.example;

public class Board {
    private int[] board;

    public Board(int dimension){
        board = new int[dimension];
    }

    public int[] getBoard() {
        return board;
    }

    public void setBoard(int[] board) {
        this.board = board;
    }
}
