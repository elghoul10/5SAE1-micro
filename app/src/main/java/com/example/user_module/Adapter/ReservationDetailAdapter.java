package com.example.user_module.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user_module.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReservationDetailAdapter extends RecyclerView.Adapter<ReservationDetailAdapter.ViewHolder> {

    private final List<Map.Entry<String, String>> reservationDetails;

    public ReservationDetailAdapter(Map<String, String> details) {
        this.reservationDetails = new ArrayList<>(details.entrySet());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reservation_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Map.Entry<String, String> entry = reservationDetails.get(position);
        holder.label.setText(entry.getKey());
        holder.value.setText(entry.getValue());
    }

    @Override
    public int getItemCount() {
        return reservationDetails.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView label, value;

        public ViewHolder(View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.label);
            value = itemView.findViewById(R.id.value);
        }
    }
}

