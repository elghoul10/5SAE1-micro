package com.example.user_module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.user_module.R;
import com.example.user_module.util.SessionManager; // Import SessionManager

public class DashboardActivity extends AppCompatActivity {

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard); // The layout with the included navbar

        // Initialize SessionManager and check if the user is logged in
        sessionManager = new SessionManager(getApplicationContext());
        if (!sessionManager.isLoggedIn()) {
            // Redirect to LoginActivity if not logged in
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // Access the navbar elements
        ImageView backArrowButton = findViewById(R.id.backArrowButton);
        TextView navbarTitle = findViewById(R.id.navbarTitle);
        ImageView menuButton = findViewById(R.id.menuButton);

        // Set up the back button action
        backArrowButton.setOnClickListener(v -> finish()); // Navigates back to the previous screen

        // Customize the navbar title
        navbarTitle.setText("Dashboard"); // Customize the title for the specific page

        // Set up the menu button action
        menuButton.setOnClickListener(v -> showMenu()); // Opens the menu when clicked
    }

    private void showMenu() {
        PopupMenu popupMenu = new PopupMenu(this, findViewById(R.id.menuButton));
        popupMenu.getMenuInflater().inflate(R.menu.dashboard_menu, popupMenu.getMenu());

        // Add click handling for menu items using if statements
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_profile) {
                // Navigate to ProfileActivity
                startActivity(new Intent(DashboardActivity.this, ProfileActivity.class));
                return true;
            } else if (item.getItemId() == R.id.menu_logout) {
                // Logout the user
                logoutUser();
                return true;
            } else {
                return false;
            }
        });
        popupMenu.show();
    }


    private void logoutUser() {
        // Clear session and redirect to LoginActivity
        sessionManager.logoutUser();
        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear activity stack
        startActivity(intent);
        finish();
    }
}
