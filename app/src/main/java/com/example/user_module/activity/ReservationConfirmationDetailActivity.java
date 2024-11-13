package com.example.user_module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.user_module.Adapter.ReservationDetailAdapter;
import com.example.user_module.AppDatabase;
import com.example.user_module.R;
import com.example.user_module.entity.Reservation;

import java.util.HashMap;
import java.util.Map;

public class ReservationConfirmationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_confirmation_detail);
        //BackIcon
        setupBackIcon();

        //rightIcon
        ImageView rightIcon = findViewById(R.id.right_icon);
        rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReservationConfirmationDetailActivity.this, ReservationListActivity.class);
                startActivity(intent);
            }
        });



        RecyclerView recyclerView = findViewById(R.id.reservation_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        int reservationId = getIntent().getIntExtra("reservationId", -1);

        if (reservationId != -1) {
            AppDatabase db = AppDatabase.getInstance(this);
            new Thread(() -> {
                Reservation reservation = db.reservationDao().getReservationById(reservationId);

                // Collect reservation details into a map
                Map<String, String> details = new HashMap<>();
                details.put("First Name", reservation.getFirstName());
                details.put("Last Name", reservation.getLastName());
                details.put("Email", reservation.getEmail());
                details.put("Phone", reservation.getPhone());
               // details.put("Message", reservation.getMessage());
                details.put("Accommodation ID", String.valueOf(reservation.getAccommodationId()));
                details.put("User ID", String.valueOf(reservation.getUserId()));
                details.put("Start Date", reservation.getStartDate());
                details.put("End Date", reservation.getEndDate());
                details.put("Has Breakfast", String.valueOf(reservation.hasBreakfast()));
                details.put("Has Transfer", String.valueOf(reservation.hasTransfer()));

                runOnUiThread(() -> {
                    ReservationDetailAdapter adapter = new ReservationDetailAdapter(details);
                    recyclerView.setAdapter(adapter);
                });
            }).start();
        }
    }

    private void setupBackIcon() {
        ImageView backIcon = findViewById(R.id.back_icon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close this activity and return to the previous one
            }
        });
    }
}
