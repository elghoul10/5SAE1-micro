package com.example.user_module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.user_module.Dao.UserDao;
import com.example.user_module.R;
import com.example.user_module.AppDatabase;
import com.example.user_module.entity.Reservation;
import com.example.user_module.entity.User;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ReservationConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_confirmation);
        setupBackIcon();





        // In your Activity or Fragment
        RadioGroup paymentRadioGroup = findViewById(R.id.payment_radio_group);
        LinearLayout paymentMethodsLayout = findViewById(R.id.payment_methods_layout);

// Set up the listener for the payment RadioGroup
        paymentRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.payment_online) {
                    // Show payment options for "Paiement en ligne"
                    paymentMethodsLayout.setVisibility(View.VISIBLE);
                } else {
                    // Hide payment options if another payment method is selected
                    paymentMethodsLayout.setVisibility(View.GONE);
                }
            }
        });







        Button confirmButton = findViewById(R.id.confirm_button_id);
        confirmButton.setOnClickListener(view -> {
            // Collect data from the form fields
            String firstName = ((EditText) findViewById(R.id.first_name)).getText().toString();
            String lastName = ((EditText) findViewById(R.id.last_name)).getText().toString();
            String email = ((EditText) findViewById(R.id.email)).getText().toString();
            String phone = ((EditText) findViewById(R.id.phone)).getText().toString();
            String message = ((EditText) findViewById(R.id.message_field)).getText().toString(); // Ensure this field exists

            // Other fields like room details, payment method, etc.
            String occupantFirstName = ((EditText) findViewById(R.id.occupant_first_name)).getText().toString();
            String occupantLastName = ((EditText) findViewById(R.id.family_name)).getText().toString();

            boolean paymentOnline = ((RadioButton) findViewById(R.id.payment_online)).isChecked();
            boolean payment_agency = ((RadioButton) findViewById(R.id.payment_agency)).isChecked();

            boolean lateArrival = ((CheckBox) findViewById(R.id.late_arrival_checkbox)).isChecked();
            boolean sideBySide = ((CheckBox) findViewById(R.id.side_by_side_rooms_checkbox)).isChecked();
            boolean kingBed = ((CheckBox) findViewById(R.id.king_bed_checkbox)).isChecked();

            boolean mr = ((RadioButton) findViewById(R.id.radio_mr)).isChecked();
            boolean mm = ((RadioButton) findViewById(R.id.radio_mme)).isChecked();
            boolean mlle = ((RadioButton) findViewById(R.id.radio_mlle)).isChecked();

            boolean termsAccepted = ((CheckBox) findViewById(R.id.terms_conditions_checkbox)).isChecked();

            // Get room description and price
            TextView roomDescriptionTextView = findViewById(R.id.room_details);
            String roomDescription = roomDescriptionTextView.getText().toString(); // Get the room description

            TextView priceTextView = findViewById(R.id.payment_info);
            String price = priceTextView.getText().toString(); // Get the price info

            // Create Reservation object
            Reservation reservation = new Reservation(
                    firstName,
                    lastName,
                    email,
                    phone,
                    occupantFirstName,
                    occupantLastName,
                    message,
                    lateArrival,
                    sideBySide,
                    kingBed,
                    paymentOnline,
                    termsAccepted,
                    payment_agency, // New field
                    mr, // New field
                    mm, // New field
                    mlle,
                    price,
                    roomDescription
            );

            // Save to database
            AppDatabase db = AppDatabase.getInstance(this);
            new Thread(() -> {
                db.reservationDao().insertReservation(reservation);

                // After saving, retrieve the ID of the saved reservation
                int reservationId = db.reservationDao().getLastInsertedId();

                // Pass ID to detail activity
                Intent intent = new Intent(ReservationConfirmationActivity.this, ReservationConfirmationDetailActivity.class);
                intent.putExtra("reservationId", reservationId);
                startActivity(intent);

                // You can also send an email confirmation after saving the reservation
                sendEmail(email, "Reservation Confirmation", "Your reservation has been confirmed. Details: " + roomDescription + " Price: " + price);
            }).start();
        });

        // Set the content to extend into the system windows
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        // Apply padding for system bars
        View mainView = findViewById(R.id.main);  // Ensure your root layout has this ID
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return WindowInsetsCompat.CONSUMED;
            });
        }

        // Retrieve the passed data (room description and price)
        String roomDescription = getIntent().getStringExtra("room_description");
        String price = getIntent().getStringExtra("price");

        // Update the UI elements with the passed values
        TextView roomDetailsTextView = findViewById(R.id.room_details);
        TextView priceTextView = findViewById(R.id.payment_info);

        if (roomDescription != null) {
            roomDetailsTextView.setText(roomDescription); // Set dynamic room description
        }

        if (price != null) {
            priceTextView.setText("Je paie le montant total à l'agence " + price); // Set dynamic price
        }
    }

    private void setupBackIcon() {
        ImageView backIcon = findViewById(R.id.back_icon);
        backIcon.setOnClickListener(v -> finish()); // Close this activity and return to the previous one
    }

    private void sendEmail(String recipient, String subject, String body) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("nesrine.gatousi@gmail.com", "rsfp llpf ewjt vfdi");
                }
            });

            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress("nesrine.gatousi@gmail.com"));
                String email = ((EditText) findViewById(R.id.email)).getText().toString();
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                message.setSubject(subject);
                message.setText(body);

                Transport.send(message);
                Log.i("ReservationConfirmation", "Email sent successfully to: " + recipient);
            } catch (MessagingException e) {
                Log.e("ReservationConfirmation", "Failed to send email to: " + recipient, e);
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Failed to send email.", Toast.LENGTH_SHORT).show());
            } finally {
                executor.shutdown();
            }
        });





    }
}
