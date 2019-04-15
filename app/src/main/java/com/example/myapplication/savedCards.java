package com.example.myapplication;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class savedCards {

    public static List<Card> savedCardsList = new ArrayList<Card>();

    public savedCards() {
    }

    public static void addCard(Card newSavedCard){
        savedCardsList.add(newSavedCard);
    }

    public static  void deleteCard(Card savedCard){
        Log.d("MySavedCard","i've deleted this card");
        for(int i = 0; i < savedCardsList.size(); i++){
            if (savedCard.getId().equals(savedCardsList.get(i).getId())) savedCardsList.remove(savedCardsList.get(i));
        }
    }

    public static void printList(){
        for(int i = 0; i < savedCardsList.size(); i++){
            Log.d("MySavedCard2", "mes cartes saved " + savedCardsList.get(i).getName()+ " id : "+ savedCardsList.get(i).getId());
        }
        Log.d("MySavedCard","DEBUG TEST " + savedCardsList.size());
    }

    public static boolean compare(Card cardToCompare){
        for(int i = 0; i < savedCardsList.size(); i++){
            if (cardToCompare.getId().equals(savedCardsList.get(i).getId())) return true;
        }
        return false;
    }
}
