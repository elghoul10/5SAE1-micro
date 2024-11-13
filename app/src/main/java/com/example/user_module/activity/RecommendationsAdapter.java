package com.example.user_module.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user_module.R;
import com.example.user_module.entity.Accommodation;

import java.util.List;

public class RecommendationsAdapter extends RecyclerView.Adapter<RecommendationsAdapter.RecommendationViewHolder> {

    private final Context context;
    private final List<Accommodation> accommodations;

    public RecommendationsAdapter(Context context, List<Accommodation> accommodations) {
        this.context = context;
        this.accommodations = accommodations;
    }

    @NonNull
    @Override
    public RecommendationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommendation, parent, false);
        return new RecommendationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendationViewHolder holder, int position) {
        Accommodation accommodation = accommodations.get(position);

        // Set data for each item in the RecyclerView
        holder.imageView.setImageResource(accommodation.getImageResId());
        holder.nameTextView.setText(accommodation.getName());
        holder.priceTextView.setText(String.format("Price per night: %.2f DT", accommodation.getPricePerNight()));

        // Set click listener for "Voir l'offre" button
        holder.viewOfferButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, AccommodationDetailActivity.class);
            intent.putExtra("accommodationId", accommodation.getId());
            intent.putExtra("name", accommodation.getName());
            intent.putExtra("price", accommodation.getPricePerNight());
            intent.putExtra("imageResId", accommodation.getImageResId());
            intent.putExtra("description", accommodation.getDescription());
            intent.putExtra("location", accommodation.getLocation());
            intent.putExtra("type", accommodation.getType());  // Assuming 'type' is a property of accommodation
            intent.putExtra("capacity", accommodation.getCapacity());  // Assuming 'capacity' is a property of accommodation
            intent.putExtra("isAvailable", accommodation.isAvailable());  // Assuming 'isAvailable' is a boolean property of accommodation
            intent.putExtra("title", accommodation.getTitle());  // Assuming 'title' is a property of accommodation
           // intent.putExtra("maxRooms", accommodation.getMaxRooms());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return accommodations.size();
    }

    static class RecommendationViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView nameTextView;
        private final TextView priceTextView;
        private final Button viewOfferButton;

        public RecommendationViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recommendation_image);
            nameTextView = itemView.findViewById(R.id.recommendation_name);
            priceTextView = itemView.findViewById(R.id.recommendation_price);
            viewOfferButton = itemView.findViewById(R.id.view_offer_button);
        }
    }
}
