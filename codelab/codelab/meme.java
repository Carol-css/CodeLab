package com.example.codelab;

public class meme {
    private String title;
    private int imageRes;
    private int secondImageRes;
    private boolean isExpanded;

    public meme(String title, int imageRes, int secondImageRes) {
        this.title = title;
        this.imageRes = imageRes;
        this.secondImageRes = secondImageRes;
        this.isExpanded = false;
    }

    public String getTitle() { return title; }
    public int getImageRes() { return imageRes; }
    public int getSecondImageRes() { return secondImageRes; }
    public boolean isExpanded() { return isExpanded; }
    public void setExpanded(boolean expanded) { isExpanded = expanded; }
}
