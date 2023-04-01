package com.example.likeordislike;

public interface ImageUrlCallback {
    void onSuccess(String imageUrl);

    void onError(Exception e);
}

