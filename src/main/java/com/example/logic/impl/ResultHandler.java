package com.example.logic.impl;

import com.example.entities.Board;
import com.example.entities.GameResult;
import com.example.logic.Observable;
import com.example.logic.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * 1='X' 2='O'
 */
public class ResultHandler implements Observable<GameResult> {

    public AtomicBoolean isEndGame = new AtomicBoolean(false);
    private List<Observer<GameResult>> observers;

    public ResultHandler() {
        observers = new ArrayList<>();
    }

    public void reset(){
        isEndGame.set(false);
        Board.getInstance().removeAllObservers();
    }

    private GameResult getWinner() {
        int result;
        GameResult gameResult;
        Board board = Board.getInstance();
        if ((result = checkHorizontalWin(board)) > 0) {
            gameResult = result == 1 ? GameResult.PLAYER : GameResult.OPPONENT;
        } else if ((result = checkVerticalWin(board)) > 0) {
            gameResult = result == 1 ? GameResult.PLAYER : GameResult.OPPONENT;
        } else if ((result = checkDiagonalWin(board)) > 0) {
            gameResult = result == 1 ? GameResult.PLAYER : GameResult.OPPONENT;
        } else {
            gameResult = getDraw(board);
        }
        if (gameResult != GameResult.CONTINUE){
            isEndGame.set(true);
        }
        return gameResult;
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
        int conditionfForBreak = getDimension()*(getDimension() - 1);
        for (int firstIndex = 0; firstIndex <= conditionfForBreak; firstIndex+=getDimension()) {
            result = checkOneCaseForWin(firstIndex+getDimension()-1, firstIndex, firstIndex+1, board,1);
            if (result == -10 && board.getSquare(firstIndex) != 0){
                result = board.getSquare(firstIndex);
                break;
            }
        }
        return result;
    }

    private int checkVerticalWin(Board board) {
        int result = -10;
        //int conditionfForBreak = getDimension()*getDimension() - 1;
        for (int firstIndex = 0; firstIndex < getDimension(); firstIndex++) {
            result = checkOneCaseForWin(getDimension()*(getDimension() - 1)+firstIndex, firstIndex, firstIndex+getDimension(), board,getDimension());
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
        int condition = getDimension() * getDimension() - 1;
        result = checkOneCaseForWin(condition, firstIndex, getDimension() + 1, board, getDimension() + 1);

        //check second diagonal if first didn't find winner
        if (result == -1) {
            firstIndex = getDimension() - 1;
            condition = getDimension() * (getDimension() - 1);
            result = checkOneCaseForWin(condition, firstIndex, firstIndex + (getDimension() - 1), board, getDimension() - 1);
        }
        if (result == -10) result = board.getSquare(firstIndex);

        return result;
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
        int condition = getDimension() * getDimension() - 1;
        result = checkOneCaseForDraw(condition, firstIndex, getDimension() + 1, board, getDimension() + 1);

        //check second diagonal if first nobody can win
        if (result == -1) {
            firstIndex = getDimension() - 1;
            condition = getDimension() * (getDimension() - 1);
            result = checkOneCaseForDraw(condition, firstIndex, firstIndex + (getDimension() - 1), board, getDimension() - 1);
        }
        return result;
    }

    private int checkVerticalDraw(Board board) {
        int result = -10;
        for (int firstIndex = 0; firstIndex < getDimension(); firstIndex++) {
            result = checkOneCaseForDraw(getDimension()*(getDimension() - 1)+firstIndex, firstIndex, firstIndex+getDimension(), board,getDimension());
            if (result == -10) break;
        }
        return result;
    }

    private int checkHorizontalDraw(Board board) {
        int result = -10;

        int conditionfForBreak = getDimension()*(getDimension() - 1);
        for (int firstIndex = 0; firstIndex <= conditionfForBreak; firstIndex+=getDimension()) {
            result = checkOneCaseForDraw(firstIndex+getDimension()-1, firstIndex, firstIndex+1, board,1);
            if (result == -10) break;
        }

        return result;
    }

    private int checkOneCaseForDraw(int condition, int firstIndex, int lastIndex, Board board, int condForLastIndex) {
        int result = -10;
        for (int j = lastIndex; j <= condition; j+=condForLastIndex){
            if(board.getSquare(firstIndex) == 0){
                firstIndex = j;
            } else if (board.getSquare(firstIndex) != board.getSquare(j) && (board.getSquare(firstIndex) != 0 && board.getSquare(j) != 0)) {
                result = -1;
                break;
            }
        }
        return result;
    }

    private int getDimension() {
        return Board.getInstance().getDimension();
    }

    @Override
    public void registerObserver(Observer<GameResult> o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer<GameResult> o) {
        observers.remove(o);
    }

    @Override
    public void removeAllObservers() {
        observers.clear();
    }

    @Override
    public void notifyObservers(GameResult... gameResult) {
        GameResult result = getWinner();
        observers.forEach(observer -> observer.update(result));
    }
}
