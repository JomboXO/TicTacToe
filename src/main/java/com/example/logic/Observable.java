package com.example.logic;

import com.example.entities.Square;

public interface Observable {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers(Square square);
}
