package com.example.entities;

import com.example.logic.Observable;
import com.example.logic.Observer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board implements Observable<Square> {

    private List<Observer<Square>> observers;
    private int dimension;
    private static final Board board = new Board();
    private int[] inlineBoard;
    private static final int DEFAULT_VALUE = 0;

    public static Board getInstance() {
        return board;
    }

    private Board() {
        observers = new ArrayList<>();
    }

    public void setDimention(int dimension) {
        this.dimension = dimension;
        inlineBoard = new int[dimension * dimension];
        Arrays.fill(inlineBoard, DEFAULT_VALUE);
    }

    //'X'
    public void addTicTacToeChoice(int index) {
        inlineBoard[index] = 1;
        notifyObservers(Square.of(index, "X"));
    }

    //'O'
    public void addLogicChoice(int index) {
        inlineBoard[index] = 2;
        notifyObservers(Square.of(index, "O"));
    }

    public int getSquare(int index) {
        return inlineBoard[index];
    }

    public int getInlineDimension() {
        return inlineBoard.length;
    }

    @Override
    public void registerObserver(Observer<Square> o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer<Square> o) {
        observers.remove(o);
    }

    @Override
    public void removeAllObservers() {
        observers.clear();
    }

    @Override
    public void notifyObservers(Square... square) {
        observers.forEach(observer -> observer.update(square[0]));
    }

    @Override
    public String toString() {
        return Arrays.toString(inlineBoard);
    }

    public int getDimension() {
        return dimension;
    }
}
