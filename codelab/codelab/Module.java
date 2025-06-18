package com.example.codelab;

public class Module {
    private String title;
    private String description;
    private boolean isExpanded;

    // Updated Constructor with isExpanded flag
    public Module(String title, String description, boolean isExpanded) {
        this.title = title;
        this.description = description;
        this.isExpanded = isExpanded;
    }

    // Getter methods
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isExpanded() { return isExpanded; }

    // Setter methods
    public void setExpanded(boolean expanded) { isExpanded = expanded; }
}
