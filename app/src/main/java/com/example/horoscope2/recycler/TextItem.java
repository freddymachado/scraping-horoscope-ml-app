package com.example.horoscope2.recycler;

public class TextItem {

    private String postText;
    private int imgUser;
    private String time;

    public TextItem(String postText) {
        this.postText = postText;

    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }
}
