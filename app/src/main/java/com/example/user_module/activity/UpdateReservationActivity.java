package com.example.user_module.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.user_module.R;
import com.example.user_module.AppDatabase;
import com.example.user_module.entity.Reservation;

public class UpdateReservationActivity extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText messageEditText;
    private EditText occupantLastNameEditText;
    private EditText occupantFirstNameEditText;
    private Reservation reservation;
    private CheckBox lateArrivalCheckBox;
    private CheckBox sideBySideCheckBox;
    private CheckBox kingBedCheckBox;

    private RadioButton payment_online;
    private RadioButton payment_agency;

    private CheckBox termsConditionsCheckBox;

    private RadioButton mrCheckBox; // for Mr.
    private RadioButton mmCheckBox; // for Mme.
    private RadioButton mlleCheckBox; // for Mlle.


    private TextView roomDetailsTextView; // To display room details
    private TextView paymentInfoTextView; // To display payment info


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_reservation);

        // Initialize EditText and other UI components
        firstNameEditText = findViewById(R.id.first_name);
        lastNameEditText = findViewById(R.id.last_name);
        emailEditText = findViewById(R.id.email);
        phoneEditText = findViewById(R.id.phone);
        messageEditText = findViewById(R.id.message);
        occupantLastNameEditText = findViewById(R.id.occupant_last_name);
        occupantFirstNameEditText = findViewById(R.id.occupant_first_name);

        // Initialize CheckBoxes for special requests
        lateArrivalCheckBox = findViewById(R.id.check_late_arrival);
        termsConditionsCheckBox= findViewById(R.id.terms_conditions);

        sideBySideCheckBox = findViewById(R.id.check_side_by_side);
        kingBedCheckBox = findViewById(R.id.check_king_bed);
        payment_online = findViewById(R.id.payment_online);
        payment_agency= findViewById(R.id.payment_agency);


        mrCheckBox = findViewById(R.id.radio_mr); // Assuming you have this in your layout
        mmCheckBox = findViewById(R.id.radio_mme); // Assuming you have this in your layout
        mlleCheckBox = findViewById(R.id.radio_mlle);


        // Initialize the TextViews for room details and payment info
        roomDetailsTextView = findViewById(R.id.room_details);
        paymentInfoTextView = findViewById(R.id.payment_info);

        int reservationId = getIntent().getIntExtra("reservationId", -1);

        // Load reservation details
        if (reservationId != -1) {
            new Thread(() -> {
                reservation = AppDatabase.getInstance(this).reservationDao().getReservationById(reservationId);
                runOnUiThread(() -> {
                    // Populate the fields with reservation details
                    if (reservation != null) {
                        firstNameEditText.setText(reservation.getFirstName());
                        lastNameEditText.setText(reservation.getLastName());
                        emailEditText.setText(reservation.getEmail());
                        phoneEditText.setText(reservation.getPhone());
                        messageEditText.setText(reservation.getMessage());
                        occupantLastNameEditText.setText(reservation.getOccupantLastName());
                        occupantFirstNameEditText.setText(reservation.getOccupantFirstName());
                        lateArrivalCheckBox.setChecked(reservation.isLateArrival());
                        termsConditionsCheckBox.setChecked(reservation.isTermsAccepted());
                        sideBySideCheckBox.setChecked(reservation.isSideBySide());
                        kingBedCheckBox.setChecked(reservation.isKingBed());

                        payment_agency.setChecked(reservation.isPaymentAgency());
                        payment_online.setChecked(reservation.isPaymentOnline());

                        mrCheckBox.setChecked(reservation.isMr()); // Set Mr. checkbox status
                        mmCheckBox.setChecked(reservation.isMm()); // Set Mme. checkbox status
                        mlleCheckBox.setChecked(reservation.isMlle());


                        roomDetailsTextView.setText(reservation.getRoomDescription());
                        paymentInfoTextView.setText( reservation.getPrice());

                    }
                });
            }).start();
        }

        Button updateButton = findViewById(R.id.update_button_id);
        updateButton.setOnClickListener(v -> {
            // Update reservation details
            reservation.setFirstName(firstNameEditText.getText().toString());
            reservation.setLastName(lastNameEditText.getText().toString());
            reservation.setEmail(emailEditText.getText().toString());
            reservation.setPhone(phoneEditText.getText().toString());
            reservation.setMessage(messageEditText.getText().toString());
            reservation.setOccupantFirstName(occupantFirstNameEditText.getText().toString());
            reservation.setOccupantLastName(occupantLastNameEditText.getText().toString());
            reservation.setLateArrival(lateArrivalCheckBox.isChecked());
            reservation.setTermsAccepted(termsConditionsCheckBox.isChecked());

            reservation.setPaymentAgency(payment_agency.isChecked());
            reservation.setPaymentOnline(payment_online.isChecked());

            // Update Mr., Mme., Mlle. checkbox states
            reservation.setMr(mrCheckBox.isChecked());
            reservation.setMm(mmCheckBox.isChecked());
            reservation.setMlle(mlleCheckBox.isChecked());


            reservation.setSideBySide(sideBySideCheckBox.isChecked());
            reservation.setKingBed(kingBedCheckBox.isChecked());
            // Set payment method if needed (assuming you have it set up in your Reservation model)

            new Thread(() -> {
                AppDatabase.getInstance(this).reservationDao().updateReservation(reservation);
                finish(); // Close the activity
            }).start();
        });

        // Handle back button click
        ImageView backIcon = findViewById(R.id.back_icon);
        backIcon.setOnClickListener(v -> finish());
    }
}
