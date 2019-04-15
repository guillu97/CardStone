package com.example.myapplication;

public class BackCard {
    private String id;
    private String name;
    private String description;
    private String image_url;
    private String gif_url;

    public BackCard(){}

    public BackCard(String name, String id, String description, String image_url, String gif_url) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.image_url = image_url;
        this.gif_url = gif_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getGif_url() {
        return gif_url;
    }

    public void setGif_url(String gif_url) {
        this.gif_url = gif_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
