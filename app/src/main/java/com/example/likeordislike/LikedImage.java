package com.example.likeordislike;

public class LikedImage {
    private String title;
    private String imageUrl;

    public LikedImage(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

