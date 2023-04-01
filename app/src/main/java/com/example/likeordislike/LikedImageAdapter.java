package com.example.likeordislike;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharedprefmanager.SharedPrefManager;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LikedImageAdapter extends RecyclerView.Adapter<LikedImageAdapter.ViewHolder> {

    private List<String> mImageUris;
    private Context mContext;

    public LikedImageAdapter(List<String> imageUris , Context context) {
        mImageUris = imageUris;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_card, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imageUri = mImageUris.get(position);
        Picasso.get()
                .load(imageUri)
                .fit()
                .centerCrop()
                .into(holder.imageView);

        holder.deleteBTN.setOnClickListener(view -> {
            Uri uri = Uri.parse(imageUri);
            SharedPrefManager.getInstance(view.getContext()).removeUri(uri);
            mImageUris.remove(imageUri); // Remove the item from the list
            notifyDataSetChanged(); // Notify the adapter that the data has changed
        });

        holder.imageView.setOnClickListener(view -> {
            ClipboardManager clipboard = (ClipboardManager) ContextCompat.getSystemService(mContext, ClipboardManager.class);
            ClipData clip = ClipData.newPlainText("label", imageUri);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(mContext, "Copied to clipboard", Toast.LENGTH_SHORT).show();

        });
    }

    @Override
    public int getItemCount() {
        return mImageUris.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ImageButton deleteBTN;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            deleteBTN = itemView.findViewById(R.id.delete_button);

        }
    }
}

