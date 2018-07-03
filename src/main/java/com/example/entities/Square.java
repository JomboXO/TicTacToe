package com.example.entities;

import com.example.logic.Observer;

public class Square {
    private int index;
    private String name;

    public static Square of(int index, String name){
        return new Square(index, name);
    }

    public Square(int index, String name) {
        this.index = index;
        this.name = name;
    }


    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

}
