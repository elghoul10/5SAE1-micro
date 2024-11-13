package com.example.user_module.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.user_module.AppDatabase;
import com.example.user_module.R;
import com.example.user_module.entity.Maintenance;

public class AddEditMaintenanceActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText editTextName, editTextLocation, editTextType, editTextCapacite;
    private Spinner spinnerAvailability;
    private ImageView imageViewPreview;
    private Uri selectedImageUri;  // URI to store the selected image
    private int restaurantId = -1;  // This will be used to edit an existing restaurant

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_restaurant);
        setupBackIcon();
        // Initialize views
        editTextName = findViewById(R.id.edit_text_name);
        editTextLocation = findViewById(R.id.edit_text_location);
        editTextType = findViewById(R.id.edit_text_type);
        editTextCapacite = findViewById(R.id.edit_text_capacite);
        imageViewPreview = findViewById(R.id.imageViewPreview);

        Button buttonSelectImage = findViewById(R.id.buttonSelectImage);
        Button buttonSave = findViewById(R.id.button_save);

        // Check if editing an existing restaurant
        Intent intent = getIntent();
        if (intent.hasExtra("restaurantId")) {
            setTitle("Edit Restaurant");
            restaurantId = intent.getIntExtra("restaurantId", -1);
            loadRestaurantData(restaurantId); // Load data if editing
        } else {
            setTitle("Add Restaurant");
        }

        // Set listeners
        buttonSelectImage.setOnClickListener(v -> openImageSelector());
        buttonSave.setOnClickListener(v -> saveRestaurant());
    }

    // Method to open the image gallery
    private void openImageSelector() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData(); // Store selected image URI
            imageViewPreview.setImageURI(selectedImageUri); // Display selected image in ImageView
        }
    }

    private void loadRestaurantData(int id) {
        new Thread(() -> {
            // Retrieve restaurant from database
            Maintenance restaurant = AppDatabase.getInstance(this).maintenanceDao().getRestaurantById(id);

            runOnUiThread(() -> {
                if (restaurant != null) {
                    // Set the text fields
                    editTextName.setText(restaurant.getName());
                    editTextLocation.setText(restaurant.getLocation());
                    editTextType.setText(restaurant.getType());
                    editTextCapacite.setText(restaurant.getCapacite());

                    // Check if the image URI is not null or empty and use Glide to load the image
                    // Check if the URI is null before setting it on the ImageView
                    if (restaurant.getImageUri() != null && !restaurant.getImageUri().isEmpty()) {
                        Glide.with(this)
                                .load(restaurant.getImageUri())  // Load image from URI or URL
                                .into(imageViewPreview);

                    } else {
                        imageViewPreview.setImageResource(R.drawable.ic_r1); // Set a placeholder image
                    }
                }
            });
        }).start();
    }

    // Save the restaurant (insert or update)
    private void saveRestaurant() {
        String name = editTextName.getText().toString().trim();
        String location = editTextLocation.getText().toString().trim();
        String type = editTextType.getText().toString().trim();
        String capacite = editTextCapacite.getText().toString().trim();

        // Validate fields
        if (name.isEmpty() || location.isEmpty() || type.isEmpty() || capacite.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get image URI if available, else use null
        String imageUri = selectedImageUri != null ? selectedImageUri.toString() : null;

        // Create the restaurant object
        Maintenance restaurant = new Maintenance(
                restaurantId == -1 ? 0 : restaurantId,
                name,
                location,
                type,
                capacite,
                imageUri  // Pass null if no image is selected
        );

        // Insert or update the restaurant in the database
        new Thread(() -> {
            if (restaurantId == -1) {
                // Insert new restaurant
                AppDatabase.getInstance(this).maintenanceDao().insert(restaurant);
            } else {
                // Update existing restaurant
                AppDatabase.getInstance(this).maintenanceDao().update(restaurant);
            }

            // Show success message
            runOnUiThread(() -> {
                Toast.makeText(this, "Restaurant saved", Toast.LENGTH_SHORT).show();
                finish();  // Optionally finish activity after saving
            });
        }).start();
    }

    private void setupBackIcon() {
        ImageView backIcon = findViewById(R.id.back_icon);
        backIcon.setOnClickListener(v -> finish()); // Close this activity and return to the previous one
    }
}
