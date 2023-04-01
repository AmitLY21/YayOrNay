package com.example.likeordislike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sharedprefmanager.SharedPrefManager;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private ProgressBar progressBar;
    private ImageButton likeBTN;
    private ImageButton dislikeBTN;
    private ImageButton likedListPageBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        progressBar = findViewById(R.id.progress_bar);
        Log.d("test", "onCreate: " + SharedPrefManager.getInstance(getApplicationContext()).getUris().toString());

        //First time loading
        loadNewImage();
        likeBTN.setOnClickListener(view -> {
            SharedPrefManager.getInstance(getApplicationContext()).addUri(Uri.parse(imageView.getTag().toString()));
            loadNewImage();
        });

        dislikeBTN.setOnClickListener(view -> {
            loadNewImage();
        });

        likedListPageBTN.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LikedImagesActivity.class);
            startActivity(intent);
        });
    }


    private void loadNewImage() {
        // Determine the active product flavor
        boolean isCatsFlavor = BuildConfig.FLAVOR.equals("cats");

        // Get the appropriate API instance
        APIRequest apiInstance = isCatsFlavor ? new Cats() : new Dogs();

        // Call getImageUrl() method on a new thread
        apiInstance.getImageUrl(new ImageUrlCallback() {
            @Override
            public void onSuccess(String imageUrl) {
                Uri currentUri = Uri.parse(imageUrl);
                // Update the UI on the main thread with the result
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setTag(currentUri.toString());
                        //Picasso.get().load(currentUri).into(imageView);

                        Picasso.get().load(currentUri).into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                // Hide the ProgressBar and set the image bitmap on the ImageView
                                progressBar.setVisibility(View.GONE);
                                imageView.setImageBitmap(bitmap);
                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                                // Hide the ProgressBar and show an error message
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Failed to load image", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {
                                // Show the ProgressBar while the image is being loaded
                                progressBar.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                imageView.setImageURI(Uri.parse("https://www.freepik.com/free-vector/oops-404-error-with-broken-robot-concept-illustration_13315300.htm"));
            }
        });
    }


    private void findViews() {
        imageView = findViewById(R.id.image_view);
        likeBTN = findViewById(R.id.like_button);
        dislikeBTN = findViewById(R.id.dislike_button);
        likedListPageBTN = findViewById(R.id.like_page);
    }
}
