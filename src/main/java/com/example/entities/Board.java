package com.example.entities;

import java.util.Arrays;

public class Board {

    private static final Board board = new Board();
    private int[] inlineBoard;

    public static Board getInstance() {
        return board;
    }

    private Board() {
    }

    public void setDimention(int dimension){
        int defaultValue = 0;
        inlineBoard = new int[dimension*dimension];
        Arrays.fill(inlineBoard, defaultValue);
    }

    //'X'
    public void addTicTacToeChoice(int index){
        inlineBoard[index] = 1;
    }
    //'O'
    public void addLogicChoice(int index){
        inlineBoard[index] = 2;
    }
    public int getSquare(int index) {
        return inlineBoard[index];
    }
    public int getInlineDimension(){
        return inlineBoard.length;
    }

    @Override
    public String toString() {
        return "Board{" +
                "inlineBoard=" + Arrays.toString(inlineBoard) +
                '}';
    }
}
