// DestinationAdapter.java
package com.example.user_module.Adapter;
// DestinationAdapter.java

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.user_module.R;
import com.example.user_module.entity.Destination;

import java.util.List;

public class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Destination destination);
    }

    private final List<Destination> destinations;
    private final OnItemClickListener listener;

    public DestinationAdapter(List<Destination> destinations, OnItemClickListener listener) {
        this.destinations = destinations;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_destination, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Destination destination = destinations.get(position);
        holder.imageView.setImageResource(destination.getImageResId());
        holder.textView.setText(destination.getName());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(destination));
    }

    @Override
    public int getItemCount() {
        return destinations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.destination_image);
            textView = itemView.findViewById(R.id.destination_name);
        }
    }
}
