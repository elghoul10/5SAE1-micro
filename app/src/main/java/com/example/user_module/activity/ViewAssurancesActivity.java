package com.example.user_module.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.user_module.AppDatabase;
import com.example.user_module.R;

public class ViewAssurancesActivity extends AppCompatActivity {

    private TextView textViewName, textViewLocation, textViewDescription, textViewRating;
    private ImageView imageViewSite;
    private int siteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_site);
        setupBackIcon();
        textViewName = findViewById(R.id.textViewName);
        textViewLocation = findViewById(R.id.textViewLocation);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewRating = findViewById(R.id.textViewRating);
        imageViewSite = findViewById(R.id.image_view_restaurant); // Initialize ImageView

        // Retrieve the site ID passed from the adapter
        siteId = getIntent().getIntExtra("siteId", -1);
        loadSiteDetails();
    }

    private void loadSiteDetails() {
        AppDatabase db = AppDatabase.getInstance(this);
        db.assurancesDao().getSiteById(siteId).observe(this, site -> {
            if (site != null) {
                textViewName.setText(site.getName());
                textViewLocation.setText(site.getLocation());
                textViewDescription.setText(site.getDescription());
                textViewRating.setText(String.valueOf(site.getRating()));

                // Load image if available
                if (site.getImageUri() != null && !site.getImageUri().isEmpty()) {
                    Glide.with(this)
                            .load(site.getImageUri())
                            .into(imageViewSite);
                } else {
                    imageViewSite.setImageResource(R.drawable.ic_placeholder); // Placeholder image
                }
            } else {
                Toast.makeText(this, "Site details not found", Toast.LENGTH_SHORT).show();
                finish(); // Close activity if site is not found
            }
        });
    }
    private void setupBackIcon() {
        ImageView backIcon = findViewById(R.id.back_icon);
        backIcon.setOnClickListener(v -> finish()); // Close this activity and return to the previous one
    }
}
