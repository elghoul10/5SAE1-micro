package com.example.user_module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user_module.R;
import com.example.user_module.AppDatabase;
import com.example.user_module.entity.Accommodation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class DestinationDetailActivity extends AppCompatActivity {

    private RecommendationsAdapter accommodationAdapter;
    private AppDatabase db;
    private RecyclerView recommendationsRecyclerView;
    private List<Accommodation> accommodationList = new ArrayList<>();
    private List<Accommodation> filteredAccommodationList = new ArrayList<>();
    private ImageView destinationImage;
    private TextView destinationName;
    private RecommendationsAdapter recommendationsAdapter;
    private List<Accommodation> allAccommodations = new ArrayList<>();
    private List<Accommodation> filteredAccommodations = new ArrayList<>();
    private EditText searchEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_detail);


        ImageView addIcon = findViewById(R.id.add_icon); // Find the add icon
        addIcon.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddAccommodationActivity.class); // Create the intent
            startActivity(intent); // Start the AddEditRestaurantActivity
        });



        // Initialize UI elements
        destinationImage = findViewById(R.id.destination_image);
        destinationName = findViewById(R.id.destination_name);
        TextView recommendationsTitle = findViewById(R.id.recommendations_title);
        recommendationsRecyclerView = findViewById(R.id.recommendations_recycler_view);
        searchEditText = findViewById(R.id.search_edit_text);  // Search EditText

        // Back icon click listener
        ImageView backIcon = findViewById(R.id.back_icon);
        backIcon.setOnClickListener(v -> finish());

        // Right icon click listener for navigating to ReservationListActivity
        ImageView rightIcon = findViewById(R.id.right_icon);
        rightIcon.setOnClickListener(v -> {
            Intent intent = new Intent(DestinationDetailActivity.this, ReservationListActivity.class);
            startActivity(intent);
        });

        // Get the data passed from the previous activity
        if (getIntent() != null) {
            int imageResId = getIntent().getIntExtra("imageResId", 0);
            String name = getIntent().getStringExtra("name");

            destinationImage.setImageResource(imageResId);
            destinationName.setText(name);
            recommendationsTitle.setText("Recommandations à " + name);

            // Load accommodations based on destination
            //allAccommodations = loadRecommendations(name);
            filteredAccommodations.addAll(allAccommodations);

            // Set up the RecyclerView
            recommendationsAdapter = new RecommendationsAdapter(this, filteredAccommodations);
            recommendationsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false));
            recommendationsRecyclerView.setAdapter(recommendationsAdapter);
        }











        // Initialize RecyclerView
        recommendationsRecyclerView = findViewById(R.id.recommendations_recycler_view);
        recommendationsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Initialize Database
        db = AppDatabase.getInstance(this);

        // Initialize Search EditText
        EditText searchEditText = findViewById(R.id.search_edit_text);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Filter the list based on the search input
                filterAccommodationList(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // Create and Insert Accommodations (for first time or sample data)
        insertAccommodations();
        String name = getIntent().getStringExtra("name");

        // Fetch accommodations from the database and update RecyclerView on a background thread
        if ("Hammamet".equals(name)) {
            // Fetch accommodations from the database and update RecyclerView on a background thread
            Executors.newSingleThreadExecutor().execute(() -> {
                accommodationList = db.accommodationDao().getAllAccommodations();
                filteredAccommodationList.addAll(accommodationList); // Initially show all items
                runOnUiThread(() -> {
                    accommodationAdapter = new RecommendationsAdapter(DestinationDetailActivity.this, filteredAccommodationList);
                    recommendationsRecyclerView.setAdapter(accommodationAdapter);
                });
            });
        } else {

        }

    }

    private void insertAccommodations() {
        // List of sample accommodations to add
        List<Accommodation> accommodations = new ArrayList<>();

        accommodations.add(new Accommodation("ahmed ", "gh", "A", 10, 210.60, true, "Luxury Hotel", R.drawable.h1));
        accommodations.add(new Accommodation("  bvb", "gul", "Guest ", 50, 80.0, true, "Cozy Place", R.drawable.h5));
        accommodations.add(new Accommodation(" youssef ", "yoyo", "A", 30, 60.0, true, "Authentic Experience", R.drawable.sample_image3));
        accommodations.add(new Accommodation("badis ", "gh", "A", 30, 60.0, true, "Authentic Experience", R.drawable.sample_image2));


        // Insert accommodations into the database only if they do not already exist
        Executors.newSingleThreadExecutor().execute(() -> {
            for (Accommodation accommodation : accommodations) {
                // Check if the accommodation already exists in the database
                Accommodation existingAccommodation = db.accommodationDao().getAccommodationByName(accommodation.getName());
                if (existingAccommodation == null) {
                    db.accommodationDao().insertAccommodation(accommodation);
                }
            }
        });
    }


    // Method to filter accommodations based on the search query
    private void filterAccommodationList(String query) {
        filteredAccommodationList.clear();

        // Loop through the list and find matches based on the search query
        for (Accommodation accommodation : accommodationList) {
            if (accommodation.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredAccommodationList.add(accommodation);
            }
        }

        // Notify adapter to update the RecyclerView
        accommodationAdapter.notifyDataSetChanged();
    }
}
