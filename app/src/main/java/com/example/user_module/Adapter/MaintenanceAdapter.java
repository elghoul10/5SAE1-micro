package com.example.user_module.Adapter;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user_module.R;
import com.example.user_module.entity.Maintenance;
import com.bumptech.glide.Glide;

public class MaintenanceAdapter extends ListAdapter<Maintenance, MaintenanceAdapter.RestaurantHolder> {

    private OnItemClickListener listener;

    public MaintenanceAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Maintenance> DIFF_CALLBACK = new DiffUtil.ItemCallback<Maintenance>() {
        @Override
        public boolean areItemsTheSame(@NonNull Maintenance oldItem, @NonNull Maintenance newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Maintenance oldItem, @NonNull Maintenance newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getLocation().equals(newItem.getLocation()) &&
                    oldItem.getType().equals(newItem.getType()) &&
                    oldItem.getCapacite().equals(newItem.getCapacite());
        }
    };

    @NonNull
    @Override
    public RestaurantHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_item, parent, false);
        return new RestaurantHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantHolder holder, int position) {
        Maintenance currentRestaurant = getItem(position);
        holder.textViewName.setText(currentRestaurant.getName());
        holder.textViewLocation.setText(currentRestaurant.getLocation());
        holder.textViewType.setText(currentRestaurant.getType());
        holder.textViewCapacite.setText(currentRestaurant.getCapacite());

        // Load the image using Glide if imageUri is not null
        if (currentRestaurant.getImageUri() != null && !currentRestaurant.getImageUri().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(currentRestaurant.getImageUri())
                    .into(holder.imageViewRestaurant);  // Load image into ImageView
        }

        // Handle the options button click to show the PopupMenu
        holder.imageViewOptions.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(holder.itemView.getContext(), holder.imageViewOptions);
            MenuInflater inflater = popupMenu.getMenuInflater();
            inflater.inflate(R.menu.menu_restaurant_item, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> handleMenuItemClick(item, currentRestaurant));
            popupMenu.show();
        });
    }

    private boolean handleMenuItemClick(MenuItem item, Maintenance restaurant) {
        if (listener != null) {
            if (item.getItemId() == R.id.menu_view) {
                listener.onViewClick(restaurant);
                return true;
            } else if (item.getItemId() == R.id.menu_edit) {
                listener.onEditClick(restaurant);
                return true;
            } else if (item.getItemId() == R.id.menu_delete) {
                listener.onDeleteClick(restaurant);
                return true;
            }
        }
        return false;
    }

    class RestaurantHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final TextView textViewLocation;
        private final TextView textViewType;
        private final TextView textViewCapacite;
        private final ImageView imageViewOptions;
        private final ImageView imageViewRestaurant; // New ImageView for restaurant image

        public RestaurantHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewLocation = itemView.findViewById(R.id.text_view_location);
            textViewType = itemView.findViewById(R.id.text_view_type);
            textViewCapacite = itemView.findViewById(R.id.text_view_capacite);
            imageViewOptions = itemView.findViewById(R.id.image_view_options);
            imageViewRestaurant = itemView.findViewById(R.id.image_view_restaurant); // Initialize ImageView
        }
    }

    public interface OnItemClickListener {
        void onViewClick(Maintenance restaurant);
        void onEditClick(Maintenance restaurant);
        void onDeleteClick(Maintenance restaurant);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
