package com.example.codelab;

public class Language {
    private String name;
    private int imageResId;

    public Language(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }
}
