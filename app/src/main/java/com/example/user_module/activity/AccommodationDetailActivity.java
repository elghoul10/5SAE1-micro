package com.example.user_module.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.user_module.AppDatabase;
import com.example.user_module.R;
import com.example.user_module.entity.Accommodation;
import com.example.user_module.entity.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AccommodationDetailActivity extends AppCompatActivity {

    private ImageView accommodationImage;
    private TextView accommodationName, accommodationLocation, accommodationType,
            accommodationCapacity, accommodationPrice, accommodationAvailability, accommodationTitle;
    private TextView roomOccupancyTextView;
    private int selectedRooms = 1;
    private int selectedAdults = 2;
    private int maxRooms;
    private int maxAdults;
    private EditText arrivalDateEditText;
    private EditText departureDateEditText;
    private Button checkAvailabilityButton;
    private TableLayout availabilityTable;
    private TextView errorMessage;
    private String arrivalDate, departureDate;
    private TextView totalNightTextView;
    final String[] selectedRoomDescription = {""};
    final String[] selectedRoomPrice = {""};
    private Accommodation accommodation;
    private int accommodationId;
    private TextView textViewCapacity; // Assuming you have a TextView to show the capacity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation_detail);

        initializeViews();
        setupBackIcon();
        setupDatePickers();
        setupCheckAvailabilityButton();
        displayAccommodationDetails();

        // Setup the confirm button to navigate to ReservationConfirmationActivity
        //Button confirmButton1 = findViewById(R.id.confirm_button);

// Set up the Confirm button
        accommodationId = getIntent().getIntExtra("accommodationId", -1);
        textViewCapacity = findViewById(R.id.accommodation_capacity); // Make sure to define this in your XML layout

        Button confirmButton1 = findViewById(R.id.confirm_button);

        confirmButton1.setOnClickListener(v -> {
            new Thread(() -> {
                // Fetch the accommodation from the database
                Accommodation accommodation = AppDatabase.getInstance(this).accommodationDao().getAccommodationById(accommodationId);

                // Update the capacity
                accommodation.setCapacity(accommodation.getCapacity() - selectedRooms);

                // Update the accommodation in the database
                AppDatabase.getInstance(this).accommodationDao().updateAccommodation(accommodation);

                // After successful update, update the UI
                runOnUiThread(() -> {
                    // Update the capacity TextView directly
                    accommodationCapacity.setText(String.valueOf(accommodation.getCapacity()));

                    // Navigate to the next activity
                    Intent intent = new Intent(AccommodationDetailActivity.this, ReservationConfirmationActivity.class);
                    intent.putExtra("room_description", selectedRoomDescription[0]);
                    intent.putExtra("price", selectedRoomPrice[0]);
                    startActivity(intent);
                    finish();
                });
            }).start();
        });

    }

        private void initializeViews() {
        roomOccupancyTextView = findViewById(R.id.room_occupancy);
        roomOccupancyTextView.setText(selectedRooms + " chambre(s), " + selectedAdults + " adulte(s)");

        roomOccupancyTextView.setOnClickListener(v -> showRoomOccupancyDialog());

        accommodationImage = findViewById(R.id.accommodation_image);
        accommodationName = findViewById(R.id.accommodation_name);
        accommodationLocation = findViewById(R.id.accommodation_location);
        accommodationType = findViewById(R.id.accommodation_type);
        accommodationCapacity = findViewById(R.id.accommodation_capacity);
        accommodationPrice = findViewById(R.id.accommodation_price);
        accommodationAvailability = findViewById(R.id.accommodation_availability);
        accommodationTitle = findViewById(R.id.accommodation_title);

        arrivalDateEditText = findViewById(R.id.arrival_date);
        departureDateEditText = findViewById(R.id.departure_date);
        checkAvailabilityButton = findViewById(R.id.check_availability_button);
        availabilityTable = findViewById(R.id.availability_table);
        //Initialize the TextView to show total nights
        totalNightTextView = findViewById(R.id.total_night_text);
        errorMessage = findViewById(R.id.error_message); // Corrected: Declare here to access it across methods

        // When you detect that the dates are not available:
        errorMessage.setVisibility(View.GONE); // Initially hide the error message


    }

    private void setupBackIcon() {
        ImageView backIcon = findViewById(R.id.back_icon);
        backIcon.setOnClickListener(v -> finish()); // Close this activity and return to the previous one
    }

    private void setupDatePickers() {
        arrivalDateEditText.setOnClickListener(v -> showDatePickerDialog(arrivalDateEditText));
        departureDateEditText.setOnClickListener(v -> showDatePickerDialog(departureDateEditText));
    }

    private void setupCheckAvailabilityButton() {
        checkAvailabilityButton.setOnClickListener(v -> toggleAvailabilityTable());
    }

    private void displayAccommodationDetails() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            accommodationName.setText(extras.getString("name", "Unknown Name"));
            accommodationLocation.setText(extras.getString("location", "Unknown Location"));
            accommodationType.setText(extras.getString("type", "Unknown Type"));
            accommodationCapacity.setText("Capacity: " + extras.getInt("capacity", -1));
            accommodationPrice.setText("Price per night: " + extras.getDouble("price", 0.0) + " DT");
            accommodationAvailability.setText(extras.getBoolean("isAvailable", false) ? "Available" : "Not Available");
            accommodationTitle.setText(extras.getString("title", "Unknown Title"));
            accommodationImage.setImageResource(extras.getInt("imageResId")); // Use a default image if not set
        }
    }

    private void toggleAvailabilityTable() {
        if (arrivalDate == null || departureDate == null) {
            Toast.makeText(this, "Please select both arrival and departure dates.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (availabilityTable.getVisibility() == View.GONE) {
            availabilityTable.setVisibility(View.VISIBLE);
            populateAvailabilityTable();
        } else {
            availabilityTable.setVisibility(View.GONE);
        }

        // Check if the selected dates are booked
        if (isDateBooked(arrivalDate) || isDateBooked(departureDate)) {
            showUnavailableMessage();
            disableCheckAvailabilityButton();
        } else {
            hideErrorMessage();
            enableCheckAvailabilityButton();
        }
    }





    private void populateAvailabilityTable() {
        // Prepare availability data with room types and their base prices
        String[][] availabilityData = {
                {"1 x Chambre Double \nDisponible\nAnnulation gratuite avant le 2024/10/29", "210.60 TND"},
                {"1 x Chambre double avec Salon \nDisponible\nAnnulation gratuite avant le 2024/10/29", "226.80 TND"},
                {"1 x Suite Junior GV Double \nDisponible\nAnnulation gratuite avant le 2024/10/29", "248.40 TND"},
        };

        // Total price TextView (for showing the sum of selected rooms)
        TextView totalAmountTextView = findViewById(R.id.totalAmountTextView);  // Assume you have a TextView with this ID

        // Initialize the total amount variable
        final double[] totalAmount = {0.0};  // Using an array to make it mutable within the lambda expression

        // Variable to store the previously selected checkbox
        final CheckBox[] lastChecked = {null};






        // Get the extra "name" from the Intent
        String name = getIntent().getStringExtra("name");

        // Update the availability data dynamically based on selectedRooms
        for (int i = 0; i < availabilityData.length; i++) {
            // Extract the price from the string and convert it to a double
            double pricePerRoom = Double.parseDouble(availabilityData[i][1].split(" ")[0]);

            // Calculate the total price for the selected number of rooms
            final double totalPrice = pricePerRoom * selectedRooms;

            // Format the total price to show two decimal places
            String formattedPrice = String.format("%.2f TND", totalPrice);

            // Adjust the room type description to include the selected number of rooms

            String roomType = availabilityData[i][0];
            roomType = roomType.replace("1 x", selectedRooms + " x");  // Adjust the number of rooms in the description

            // Replace "Disponible" with the value from extras.getString("name") inside parentheses and make it bold
            if (name != null && !name.isEmpty()) {
                // Adding the name inside parentheses and making it bold
                String boldName = "(" + name + ")";
                SpannableString spannableRoomType = new SpannableString(roomType);
                int start = roomType.indexOf("Disponible");
                int end = start ;

                // Apply the bold style to the name
                spannableRoomType.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                // Replace the text "Disponible" with bold and the added name in parentheses
                roomType = roomType.replace("Disponible", boldName);
                final String[] roomTypeArray = {roomType};
                selectedRoomDescription[0] = roomTypeArray[0];
            }

            // Apply green color to the cancellation text part
            String cancellationText = "Annulation gratuite avant le 2024/10/29";
            SpannableString spannableRoomType = new SpannableString(roomType);
            int start = roomType.indexOf(cancellationText);
            int end = start + cancellationText.length();
            spannableRoomType.setSpan(new ForegroundColorSpan(Color.GREEN), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            // Create a new row for the table
            TableRow row = new TableRow(this);
            row.setPadding(8, 8, 8, 8);

            // Create a CheckBox
            CheckBox checkBox = new CheckBox(this);
            checkBox.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.5f));

            // Set the OnCheckedChangeListener for the CheckBox
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    // Uncheck the last selected CheckBox if it was checked
                    if (lastChecked[0] != null && lastChecked[0] != checkBox) {
                        lastChecked[0].setChecked(false);  // Uncheck the last selected checkbox
                    }
                   // final String[] roomTypeArray = {roomType};

// Wrap roomType and totalPrice in arrays to make them effectively final


                    final double[] totalPriceArray = {totalPrice};
                    // Update the selected room’s description and price using the array values


                    selectedRoomPrice[0] = String.format("%.2f TND", totalPriceArray[0]);  // Dynamic price
                    String.format("1 x", selectedRooms + " x");
                    // Update the total amount
                    totalAmount[0] = totalPriceArray[0];
                    totalAmountTextView.setText("Montant total du séjour: " + selectedRoomPrice[0]);

                    // Store this CheckBox as the last selected
                    lastChecked[0] = checkBox;



                    // Update the total amount with the selected room's price
                    totalAmount[0] = totalPrice;





                    // Update the total amount TextView
                    totalAmountTextView.setText("Montant total du séjour: " + String.format("%.2f TND", totalAmount[0]));

                    // Store this CheckBox as the last selected
                    lastChecked[0] = checkBox;
                } else {
                    // If no CheckBox is selected, reset the total amount
                    totalAmount[0] = 0.0;
                    totalAmountTextView.setText("Montant total du séjour: 0.00 TND");
                    lastChecked[0] = null;
                }
            });

            // Create the room type TextView
            TextView roomTypeTextView = new TextView(this);
            roomTypeTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f));
            roomTypeTextView.setText(spannableRoomType);  // Set the text with color span
            roomTypeTextView.setPadding(8, 8, 8, 8);

            // Create the total price TextView
            TextView totalPriceTextView = new TextView(this);
            totalPriceTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            totalPriceTextView.setText(formattedPrice);  // Set the calculated total price for each room
            totalPriceTextView.setPadding(8, 8, 8, 8);

            // Add the CheckBox, room type, and total price TextViews to the row
            row.addView(checkBox);
            row.addView(roomTypeTextView);
            row.addView(totalPriceTextView);

            // Add the row to the table
            availabilityTable.addView(row, availabilityTable.getChildCount() - 1);  // Add before the last row
        }

        // After populating the table, update the total amount TextView with initial value (0)
        totalAmountTextView.setText("Montant total du séjour: " + String.format("%.2f TND", totalAmount[0]));
    }




    private boolean isDateBooked(String selectedDate) {
        // Log the selected date to check if it's being updated correctly
        Log.d("DateCheck", "Checking availability for selected date: " + selectedDate);

        List<String> bookedDates = getBookedDatesForAccommodation();
        return bookedDates.contains(selectedDate);
    }
    private void disableCheckAvailabilityButton() {
        checkAvailabilityButton.setEnabled(false);
        checkAvailabilityButton.setVisibility(View.INVISIBLE);
        availabilityTable.setVisibility(View.INVISIBLE);
    }

    private void enableCheckAvailabilityButton() {
        checkAvailabilityButton.setEnabled(true);
        checkAvailabilityButton.setVisibility(View.VISIBLE);
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
                        calculateAndUpdateTotalNights(arrivalDate, departureDate);

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
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date arrival = sdf.parse(arrivalDate);
            Date departure = sdf.parse(departureDate);

            if (arrival != null && departure != null) {
                long difference = departure.getTime() - arrival.getTime();
                long daysBetween = difference / (1000 * 60 * 60 * 24); // Convert milliseconds to days

                // Update the TextView with the calculated number of nights
                totalNightTextView.setText("Total " + daysBetween + " nuit" + (daysBetween > 1 ? "s" : ""));
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error in date parsing", Toast.LENGTH_SHORT).show();
        }
    }

    private void showUnavailableMessage() {
        Log.d("DateCheck", "Dates are not available, please choose another date");
        Toast.makeText(AccommodationDetailActivity.this, "Dates are not available, please choose another date", Toast.LENGTH_LONG).show();
        errorMessage.setVisibility(View.VISIBLE); // Show the error message
    }

    private void hideErrorMessage() {
        errorMessage.setVisibility(View.GONE); // Hide the error message
    }


    private void showRoomOccupancyDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_room_occupancy);

        SeekBar roomSeekBar = dialog.findViewById(R.id.room_count_seekbar);
        SeekBar adultSeekBar = dialog.findViewById(R.id.adult_count_seekbar);
        TextView roomCountLabel = dialog.findViewById(R.id.room_count_label);
        TextView adultCountLabel = dialog.findViewById(R.id.adult_count_label);
        Button confirmButton = dialog.findViewById(R.id.dialog_confirm_button);

        // Try to parse the accommodation capacity as an integer
        try {
            maxRooms = Integer.parseInt(accommodationCapacity.getText().toString());
        } catch (NumberFormatException e) {
            Bundle extras = getIntent().getExtras();
            maxRooms = extras != null ? extras.getInt("capacity", 1) : 1; // Default to 1 if parsing fails and extras are null

            if (maxRooms == 0) {
                checkAvailabilityButton.setVisibility(View.INVISIBLE);
            }
            Toast.makeText(this, "Invalid accommodation capacity. Defaulting to 1 room.", Toast.LENGTH_SHORT).show();
        }

        // Ensure maxRooms does not exceed the limit of 4
        if (maxRooms > 4) {
            maxRooms = 4;
        }

        maxAdults = maxRooms * 2;

        roomSeekBar.setMax(maxRooms);
        adultSeekBar.setMax(maxAdults);

        roomSeekBar.setProgress(selectedRooms > 0 ? selectedRooms : 1);
        adultSeekBar.setProgress(selectedAdults > 0 ? selectedAdults : 2);
        roomCountLabel.setText(String.valueOf(selectedRooms));
        adultCountLabel.setText(String.valueOf(selectedAdults));

        roomSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                selectedRooms = progress;
                roomCountLabel.setText(String.valueOf(selectedRooms));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        adultSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                selectedAdults = progress;
                adultCountLabel.setText(String.valueOf(selectedAdults));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        confirmButton.setOnClickListener(v -> {
            if (validateOccupancy(selectedRooms, selectedAdults, maxRooms, maxAdults)) {
                roomOccupancyTextView.setText(selectedRooms + " chambre(s), " + selectedAdults + " adulte(s)");
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Invalid occupancy selection. Please adjust.", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    private boolean validateOccupancy(int rooms, int adults, int maxRooms, int maxAdults) {
        return rooms > 0 && rooms <= maxRooms && adults > 0 && adults <= rooms * 2 && adults <= maxAdults;
    }



    private boolean isDateInRange(String bookedDate, String arrivalDate, String departureDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date booked = sdf.parse(bookedDate);
            Date arrival = sdf.parse(arrivalDate);
            Date departure = sdf.parse(departureDate);
            return booked != null && (booked.after(arrival) && booked.before(departure));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private List<String> getBookedDatesForAccommodation() {
        return Arrays.asList("15/10/2024", "16/10/2024", "18/10/2024", "22/10/2024");
    }

}
