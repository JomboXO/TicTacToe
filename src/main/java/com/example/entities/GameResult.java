package com.example.entities;

public enum GameResult {
    PLAYER("X"),
    OPPONENT("O"),
    DRAW("Draw"),
    CONTINUE(" ");

    private String printValue;

    GameResult(String printValue) {
        this.printValue = printValue;
    }

    @Override
    public String toString() {
        return printValue;
    }
}
