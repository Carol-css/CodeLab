package com.example.codelab;

public class NewsHeadline {
    private String headline;
    private String description;

    public NewsHeadline(String headline, String description) {
        this.headline = headline;
        this.description = description;
    }

    public String getHeadline() {
        return headline;
    }

    public String getDescription() {
        return description;
    }
}
