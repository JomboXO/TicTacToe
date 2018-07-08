package com.example.logic;

import com.example.entities.Square;

public interface Observer<T> {
    void update (T object);

}
