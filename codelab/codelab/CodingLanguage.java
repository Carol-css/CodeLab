package com.example.codelab;

public class CodingLanguage {
    private String name;
    private int imageResource;

    public CodingLanguage(String name, int imageResource) {
        this.name = name;
        this.imageResource = imageResource;
    }

    public String getName() { return name; }
    public int getImageResource() { return imageResource; }
}
