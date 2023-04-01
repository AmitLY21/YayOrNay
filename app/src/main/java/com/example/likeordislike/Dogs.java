package com.example.likeordislike;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.likeordislike.APIRequest;
import com.example.likeordislike.ImageUrlCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Dogs implements APIRequest {
    private static final String TAG = "Dogs";
    private ImageUrlCallback callback;

    @Override
    public void getImageUrl(ImageUrlCallback callback) {
        this.callback = callback;
        Thread thread = new Thread(new DownloadImageUrlRunnable());
        thread.start();
    }

    private class DownloadImageUrlRunnable implements Runnable {
        @Override
        public void run() {
            Looper.prepare();
            Handler handler = new Handler();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://dog.ceo/api/breeds/image/random")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                JSONObject jsonObject = new JSONObject(response.body().string());
                final String imageUrl = jsonObject.getString("message");

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(imageUrl);
                    }
                });
            } catch (final JSONException | IOException e) {
                Log.e(TAG, "Error downloading image URL", e);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(new RuntimeException(e));
                    }
                });
            }
            Looper.loop();
        }
    }
}
