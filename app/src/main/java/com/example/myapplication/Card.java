package com.example.myapplication;

import java.io.Serializable;

public class Card implements Serializable {
    private String name;
    private String cardClass;
    private Integer cost;
    private Integer attack;
    private Integer health;
    private String flavor;
    private String cardText;
    private String rarity;
    private String id;
    private String image_url;
    private String image_url_512;
    private String gif_url;
    private boolean saved;
    private String type;


    public Card()  {
    }

    public Card(String name, String cardClass, Integer cost, Integer attack, Integer health, String flavor, String cardText, String rarity, String id, String image_url, String image_url_512, String gif_url, boolean saved, String type) {
        this.name = name;
        this.cardClass = cardClass;
        this.cost = cost;
        this.attack = attack;
        this.health = health;
        this.flavor = flavor;
        this.cardText = cardText;
        this.rarity = rarity;
        this.id = id;
        this.image_url = image_url;
        this.image_url_512 = image_url_512;
        this.gif_url = gif_url;
        this.saved = saved;
        this.type = type;
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



    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getCardText() {
        return cardText;
    }

    public void setText(String cardText) {
        this.cardText = cardText;
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getGif_url() {
        return gif_url;
    }

    public void setGif_url(String gif_url) {
        this.gif_url = gif_url;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage_url_512() {
        return image_url_512;
    }

    public void setImage_url_512(String image_url_512) {
        this.image_url_512 = image_url_512;
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
