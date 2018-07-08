package com.example.logic;

import com.example.entities.Square;

public interface Observable<T> {
    void registerObserver(Observer<T> o);
    void removeObserver(Observer<T> o);
    void removeAllObservers();
    void notifyObservers(T... object);
}
