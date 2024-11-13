package com.example.user_module.activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.user_module.R;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

    public class MaintenanceDetailActivity extends AppCompatActivity {

        private String arrivalDate;
        private String departureDate;
        private EditText arrivalDateEditText;
        private EditText departureDateEditText;
        private TableLayout availabilityTable;
        private Button checkAvailabilityButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_restaurant_detail);
            setupBackIcon();

            // Retrieve intent extras
            String name = getIntent().getStringExtra("name");
            String location = getIntent().getStringExtra("location");
            String type = getIntent().getStringExtra("type");
            int capacity = getIntent().getIntExtra("capacity", 0);
            String description = getIntent().getStringExtra("description");
            int imageResId = getIntent().getIntExtra("imageResId", -1);

            // Use these values to populate your UI (e.g., TextViews, ImageView)
            TextView nameTextView = findViewById(R.id.restaurant_name);
            TextView locationTextView = findViewById(R.id.restaurant_location);
            ImageView imageView = findViewById(R.id.restaurant_image);

            nameTextView.setText(name);
            locationTextView.setText(location);
            imageView.setImageResource(imageResId);

            // Initialize UI elements
            arrivalDateEditText = findViewById(R.id.arrival_date);
            departureDateEditText = findViewById(R.id.departure_date);
            availabilityTable = findViewById(R.id.availability_table);
            checkAvailabilityButton = findViewById(R.id.check_availability_button);

            // Set click listeners for date pickers
            setupDatePickers();

            // Set click listener for availability button
            checkAvailabilityButton.setOnClickListener(v -> checkAvailability());
        }

        private void setupBackIcon() {
            ImageView backIcon = findViewById(R.id.back_icon);
            backIcon.setOnClickListener(v -> finish()); // Close this activity and return to the previous one
        }

        private List<String> getBookedDatesForAccommodation() {
            return Arrays.asList("15/10/2024", "16/10/2024", "18/10/2024", "22/10/2024");
        }

        private boolean isDateBooked(String selectedDate) {
            Log.d("DateCheck", "Checking availability for selected date: " + selectedDate);
            List<String> bookedDates = getBookedDatesForAccommodation();
            return bookedDates.contains(selectedDate);
        }

        private void checkAvailability() {
            // Check if both arrival and departure dates are selected
            if (arrivalDate == null || departureDate == null) {
                Toast.makeText(this, "Please select both arrival and departure dates.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if selected dates are booked
            if (isDateBooked(arrivalDate) || isDateBooked(departureDate)) {
                showUnavailableMessage();
                disableCheckAvailabilityButton();
            } else {
                hideErrorMessage();
                enableCheckAvailabilityButton();
                showAvailabilityTable();
            }
        }

        private void showAvailabilityTable() {
            availabilityTable.setVisibility(View.VISIBLE);
          //  populateAvailabilityTable();
        }

        private void showUnavailableMessage() {
            // Show an error message or alert that dates are unavailable
            Toast.makeText(this, "Selected dates are not available.", Toast.LENGTH_SHORT).show();
        }

        private void hideErrorMessage() {
            // Hide the error message if dates are available
        }

        private void disableCheckAvailabilityButton() {
            checkAvailabilityButton.setEnabled(false);
        }

        private void enableCheckAvailabilityButton() {
            checkAvailabilityButton.setEnabled(true);
        }

        private void setupDatePickers() {
            arrivalDateEditText.setOnClickListener(v -> showDatePickerDialog(arrivalDateEditText));
            departureDateEditText.setOnClickListener(v -> showDatePickerDialog(departureDateEditText));
        }

        private void showDatePickerDialog(final EditText editText) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String selectedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                        editText.setText(selectedDate);

                        // Store the selected dates
                        if (editText.getId() == R.id.arrival_date) {
                            arrivalDate = selectedDate;
                        } else if (editText.getId() == R.id.departure_date) {
                            departureDate = selectedDate;
                        }

                        // After both dates are selected, calculate the number of nights
                        if (arrivalDate != null && departureDate != null) {
                            // Update total nights
                            calculateAndUpdateTotalNights(arrivalDate, departureDate);

                            // Check availability
                            if (isDateBooked(arrivalDate) || isDateBooked(departureDate)) {
                                showUnavailableMessage(); // Show error message
                                disableCheckAvailabilityButton(); // Disable button
                            } else {
                                hideErrorMessage(); // Hide error message
                                enableCheckAvailabilityButton(); // Enable button
                            }
                        }
                    },
                    year, month, day);

            datePickerDialog.show();
        }

        private void calculateAndUpdateTotalNights(String arrivalDate, String departureDate) {
            // Calculate and display total nights based on selected dates
        }
    }

