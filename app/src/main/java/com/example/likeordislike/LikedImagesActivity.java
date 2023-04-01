package com.example.likeordislike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sharedprefmanager.SharedPrefManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LikedImagesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LikedImageAdapter adapter;
    private List<String> likedImageUris;

    private ImageButton trashBTN;

    private ImageButton exportAllBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_images);

        trashBTN = findViewById(R.id.trash_button);
        exportAllBTN = findViewById(R.id.export_all);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        trashBTN.setOnClickListener(view -> {
            if (!SharedPrefManager.getInstance(this).getUris().isEmpty()) {
                SharedPrefManager.getInstance(this).clearUris();
                Toast.makeText(this, "Cleared all data!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Data is empty!", Toast.LENGTH_SHORT).show();
            }
            recreate(); // Refresh the current activity
        });

        exportAllBTN.setOnClickListener(view -> {
            List<String> exportList = SharedPrefManager.getInstance(getApplicationContext()).getUris();

            // Combine all links into a single string separated by newlines
            StringBuilder linksBuilder = new StringBuilder();
            for (String uri : exportList) {
                linksBuilder.append(uri);
                linksBuilder.append(System.lineSeparator());
            }
            String linksText = linksBuilder.toString();

            // Create a sharing intent with the links text
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, linksText);

            // Start the sharing activity
            startActivity(Intent.createChooser(sharingIntent, "Export Links"));
        });

        // Load liked images from shared preferences
        likedImageUris = SharedPrefManager.getInstance(getApplicationContext()).getUris();
        Log.d("test", "onCreate: " + likedImageUris.toString());

        // Set adapter to the recyclerview
        adapter = new LikedImageAdapter(likedImageUris, this);
        recyclerView.setAdapter(adapter);
    }

}