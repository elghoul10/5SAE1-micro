package com.example.user_module.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.user_module.AppDatabase;
import com.example.user_module.R;
import com.example.user_module.entity.Assurances;
import com.google.android.material.textfield.TextInputEditText;

import java.util.concurrent.Executors;

public class AddEditAssurancesActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private TextInputEditText editTextTitle, editTextDescription;
    private Button buttonSaveSite, buttonSelectSiteImage;
    private ImageView imageViewSitePreview;
    private Uri selectedImageUri;
    private int siteId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_site);
        setupBackIcon();
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        imageViewSitePreview = findViewById(R.id.imageViewPreview);
        buttonSaveSite = findViewById(R.id.buttonSaveForum);
        buttonSelectSiteImage = findViewById(R.id.buttonSelectImage);

        // Check if editing an existing site
        siteId = getIntent().getIntExtra("siteId", -1);
        if (siteId != -1) {
            loadSiteData();
        }

        buttonSelectSiteImage.setOnClickListener(v -> openImageSelector());
        buttonSaveSite.setOnClickListener(v -> saveSite());
    }

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
            selectedImageUri = data.getData();
            Glide.with(this).load(selectedImageUri).into(imageViewSitePreview); // Display selected image
        }
    }

    private void loadSiteData() {
        AppDatabase db = AppDatabase.getInstance(this);
        db.assurancesDao().getSiteById(siteId).observe(this, site -> {
            if (site != null) {
                editTextTitle.setText(site.getName());
                editTextDescription.setText(site.getDescription());
                if (site.getImageUri() != null && !site.getImageUri().isEmpty()) {
                    Glide.with(this).load(site.getImageUri()).into(imageViewSitePreview);
                }
            }
        });
    }

    private void saveSite() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String imageUri = selectedImageUri != null ? selectedImageUri.toString() : null;

        Assurances site = new Assurances();
        site.setName(title);
        site.setDescription(description);
        site.setImageUri(imageUri);

        if (siteId != -1) {
            site.setId(siteId); // Set ID if editing
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            if (siteId == -1) {
                db.assurancesDao().insert(site); // Insert new site
            } else {
                db.assurancesDao().update(site); // Update existing site
            }

            runOnUiThread(() -> {
                Toast.makeText(this, "assurance saved", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish(); // Close activity
            });
        });
    }
    private void setupBackIcon() {
        ImageView backIcon = findViewById(R.id.back_icon);
        backIcon.setOnClickListener(v -> finish()); // Close this activity and return to the previous one
    }
}
