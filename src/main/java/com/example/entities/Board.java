package com.example.entities;

import com.example.logic.Observable;
import com.example.logic.Observer;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class Board implements Observable {

    private List<Observer> observers;
    private static final Board board = new Board();
    private int[] inlineBoard;
    private final int defaultValue = 0;

    public static Board getInstance() {
        return board;
    }

    private Board() {
        observers = new LinkedList<>();
    }

    public void setDimention(int dimension) {
        inlineBoard = new int[dimension * dimension];
        Arrays.fill(inlineBoard, defaultValue);
    }

    public boolean isFull(){
        boolean match = IntStream.of(inlineBoard).anyMatch(v -> v == defaultValue);
        return !match;
    }

    //'X'
    public void addTicTacToeChoice(int index) {
        inlineBoard[index] = 1;
        notifyObservers(Square.of(index,"X"));
    }

    //'O'
    public void addLogicChoice(int index) {
        inlineBoard[index] = 2;
        notifyObservers(Square.of(index,"O"));
    }

    public int getSquare(int index) {
        return inlineBoard[index];
    }

    public int getInlineDimension() {
        return inlineBoard.length;
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(Square square) {
        observers.forEach(observer -> observer.update(square));
    }

    @Override
    public String toString() {
        return "Board{" +
                "inlineBoard=" + Arrays.toString(inlineBoard) +
                '}';
    }
}
