package com.example.myapplication;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.Serializable;

public class Card implements Serializable {
    private String name;
    private String cardClass;
    private Integer cost;
    private String rarity;
    private String id;
    private String image_url;

    public Card()  {
    }

    public Card(String name, String cardClass, Integer cost, String rarity, String id, String image_url) {
        this.name = name;
        this.cardClass = cardClass;
        this.cost = cost;
        this.rarity = rarity;
        this.id = id;
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardClass() {
        return cardClass;
    }

    public void setCardClass(String cardClass) {
        this.cardClass = cardClass;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    /*
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    */
}
