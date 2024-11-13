package com.example.user_module.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user_module.R;
import com.example.user_module.AppDatabase;
import com.example.user_module.activity.ReservationConfirmationDetailActivity;
import com.example.user_module.activity.UpdateReservationActivity;
import com.example.user_module.entity.Reservation;

import java.util.List;

public class ReservationTableAdapter extends RecyclerView.Adapter<ReservationTableAdapter.ReservationViewHolder> {

    private final List<Reservation> reservationList;
    private final Context context;

    public ReservationTableAdapter(List<Reservation> reservationList, Context context) {
        this.reservationList = reservationList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reservation_table, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        Reservation reservation = reservationList.get(position);
        holder.reservationId.setText(String.valueOf(reservation.getId()));
        //holder.reservationStartDate.setText(reservation.getStartDate());
        //holder.reservationEndDate.setText(reservation.getEndDate());
        //holder.reservationUserId.setText(String.valueOf(reservation.getUserId()));
        holder.reservationFirstName.setText(reservation.getLastName()); // Bind first name

        // Update button click listener
        holder.updateButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateReservationActivity.class);
            intent.putExtra("reservationId", reservation.getId());
            context.startActivity(intent);
        });

        // Delete button click listener
        holder.deleteButton.setOnClickListener(v -> {
            new Thread(() -> {
                AppDatabase.getInstance(context).reservationDao().cancelReservation(reservation);
                // Remove the deleted reservation from the list and notify the adapter
                reservationList.remove(position);
                ((Activity) context).runOnUiThread(() -> notifyItemRemoved(position));
            }).start();
        });

    // View Details button click listener
    holder.viewDetailsButton.setOnClickListener(v -> {
        Intent intent = new Intent(context, ReservationConfirmationDetailActivity.class);
        intent.putExtra("reservationId", reservation.getId());
        context.startActivity(intent);
    });}
    @Override
    public int getItemCount() {
        return reservationList.size();
    }

    static class ReservationViewHolder extends RecyclerView.ViewHolder {
        TextView reservationId, reservationStartDate, reservationEndDate, reservationUserId, reservationFirstName; // Add first name TextView
        ImageButton updateButton, deleteButton,viewDetailsButton;
        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            reservationId = itemView.findViewById(R.id.reservationId);
            reservationFirstName = itemView.findViewById(R.id.reservationFirstName); // Initialize first name TextView
          //  reservationStartDate = itemView.findViewById(R.id.reservationStartDate);
          //  reservationEndDate = itemView.findViewById(R.id.reservationEndDate);
           // reservationUserId = itemView.findViewById(R.id.reservationUserId);
            updateButton = itemView.findViewById(R.id.updateButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            viewDetailsButton = itemView.findViewById(R.id.viewDetailsButton);
        }
    }
}
