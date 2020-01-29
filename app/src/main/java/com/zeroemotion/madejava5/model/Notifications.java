package com.zeroemotion.madejava5.model;

public class Notifications {
    private long id;
    private String sender;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Notifications(long id, String sender, String message) {
        this.id = id;
        this.sender = sender;
        this.message = message;
    }

    private String message;


}
