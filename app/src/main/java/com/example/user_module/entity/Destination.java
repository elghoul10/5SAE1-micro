package com.example.user_module.entity;

public class Destination {
    private int imageResId;
    private String name;

    public Destination(int imageResId, String name) {
        this.imageResId = imageResId;
        this.name = name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getName() {
        return name;
    }
}

