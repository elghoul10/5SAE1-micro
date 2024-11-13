package com.example.user_module.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.user_module.R;
import com.example.user_module.entity.Accommodation;

public class AddAccommodationActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText edtName, edtLocation, edtType, edtCapacity, edtPricePerNight, edtTitle;
    private Spinner spinnerAvailability;
    private ImageView imgSelectedImage;
    private Uri imageUri;  // URI for the selected image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_accommodation);

        // Initialize views
        edtName = findViewById(R.id.edtName);
        edtLocation = findViewById(R.id.edtLocation);
        edtType = findViewById(R.id.edtType);
        edtCapacity = findViewById(R.id.edtCapacity);
        edtPricePerNight = findViewById(R.id.edtPricePerNight);
        edtTitle = findViewById(R.id.edtTitle);
        spinnerAvailability = findViewById(R.id.spinnerAvailability);
        imgSelectedImage = findViewById(R.id.imgSelectedImage);

        Button btnSelectImage = findViewById(R.id.btnSelectImage);
        btnSelectImage.setOnClickListener(view -> openImagePicker());
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imgSelectedImage.setImageURI(imageUri);  // Display the selected image
            imgSelectedImage.setVisibility(View.VISIBLE);
        }
    }

    public void onAddAccommodationClick(View view) {
        String name = edtName.getText().toString();
        String location = edtLocation.getText().toString();
        String type = edtType.getText().toString();
        int capacity = Integer.parseInt(edtCapacity.getText().toString());
        double pricePerNight = Double.parseDouble(edtPricePerNight.getText().toString());
        String title = edtTitle.getText().toString();
        boolean isAvailable = spinnerAvailability.getSelectedItem().toString().equals("Yes");

        if (name.isEmpty() || location.isEmpty() || type.isEmpty() || capacity <= 0 || pricePerNight <= 0 || title.isEmpty() || imageUri == null) {
            Toast.makeText(this, "Please fill in all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create Accommodation object and store image URI as a string (if needed in your database)
        Accommodation accommodation = new Accommodation(name, location, type, capacity, pricePerNight, isAvailable, title, imageUri.toString());

        // Insert into the database
        AccommodationViewModel accommodationViewModel = new ViewModelProvider(this).get(AccommodationViewModel.class);
        accommodationViewModel.insertAccommodation(accommodation);

        // Show success message
        Toast.makeText(this, "Accommodation added successfully", Toast.LENGTH_SHORT).show();

        // Optionally, finish activity or clear input fields
        finish();
    }
}
