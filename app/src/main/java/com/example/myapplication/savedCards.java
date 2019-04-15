package com.example.myapplication;

public class savedCards {
    private static final savedCards ourInstance = new savedCards();

    public static savedCards getInstance() {
        return ourInstance;
    }

    private savedCards() {
    }
}
