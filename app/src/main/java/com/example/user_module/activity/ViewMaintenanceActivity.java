package com.example.user_module.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.user_module.R;
import com.example.user_module.AppDatabase;
import com.example.user_module.entity.Maintenance;

public class ViewMaintenanceActivity extends AppCompatActivity {

    private TextView textViewName, textViewLocation, textViewType, textViewCapacite;
    private ImageView imageViewRestaurant;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_restaurant);
        setupBackIcon();
        // Initialize TextViews and ImageView
        textViewName = findViewById(R.id.text_view_name);
        textViewLocation = findViewById(R.id.text_view_location);
        textViewType = findViewById(R.id.text_view_type);
        textViewCapacite = findViewById(R.id.text_view_capacite);
        imageViewRestaurant = findViewById(R.id.image_view_restaurant);

        // Get the restaurant ID from the Intent
        int restaurantId = getIntent().getIntExtra("restaurantId", -1);

        if (restaurantId == -1) {
            // If there's no valid ID, show an error and finish the activity
            Toast.makeText(this, "Invalid restaurant ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load restaurant details in a background thread
        new Thread(() -> {
            Maintenance restaurant = AppDatabase.getInstance(this).maintenanceDao().getRestaurantById(restaurantId);
            runOnUiThread(() -> {
                if (restaurant != null) {
                    textViewName.setText(restaurant.getName());
                    textViewLocation.setText(restaurant.getLocation());
                    textViewType.setText(restaurant.getType());
                    textViewCapacite.setText(restaurant.getCapacite());

                    // Check if the URI is null before setting it on the ImageView
                    if (restaurant.getImageUri() != null && !restaurant.getImageUri().isEmpty()) {
                        Glide.with(this)
                                .load(restaurant.getImageUri())  // Load image from URI or URL
                                .into(imageViewRestaurant);
                    } else {
                        imageViewRestaurant.setImageResource(R.drawable.ic_r1); // Set a placeholder image
                    }

                } else {
                    Toast.makeText(ViewMaintenanceActivity.this, "Restaurant not found", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }).start();
    }
    private void setupBackIcon() {
        ImageView backIcon = findViewById(R.id.back_icon);
        backIcon.setOnClickListener(v -> finish()); // Close this activity and return to the previous one
    }
}
