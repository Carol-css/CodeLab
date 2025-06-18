package com.example.codelab;

public class Notification {
    private String title;
    private String message;
    private String type; // "update" or "reminder"
    private String date;

    public Notification(String title, String message, String type, String date) {
        this.title = title;
        this.message = message;
        this.type = type;
        this.date = date;
    }

    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public String getType() { return type; }
    public String getDate() { return date; }
}
