package com.example.user_module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user_module.R;
import com.example.user_module.AppDatabase;


import com.example.user_module.Adapter.ReservationTableAdapter;

import com.example.user_module.entity.Reservation;

import java.util.List;
public class ReservationListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReservationTableAdapter reservationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list);
        //BackIcon
        setupBackIcon();
        recyclerView = findViewById(R.id.reservationRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch data in a background thread
        new Thread(() -> {
            List<Reservation> reservations = AppDatabase.getInstance(getApplicationContext())
                    .reservationDao()
                    .getAll();

            runOnUiThread(() -> {
                reservationAdapter = new ReservationTableAdapter(reservations, ReservationListActivity.this);
                recyclerView.setAdapter(reservationAdapter);
            });
        }).start();
    }
    private void setupBackIcon() {
        ImageView backIcon = findViewById(R.id.back_icon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReservationListActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close this activity
            }
        });
    }

}
