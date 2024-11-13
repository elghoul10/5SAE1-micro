package com.example.user_module.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.user_module.R;
import com.example.user_module.Adapter.MaintenanceAdapter;
import com.example.user_module.AppDatabase;
import com.example.user_module.entity.Maintenance;
import java.util.List;

public class ListMaintenanceActivity extends AppCompatActivity {

    private MaintenanceAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_restaurants);
        setupBackIcon();
        // Set up RecyclerView and adapter
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MaintenanceAdapter();
        recyclerView.setAdapter(adapter);

        // Set up the adapter's listener for menu actions
        adapter.setOnItemClickListener(new MaintenanceAdapter.OnItemClickListener() {
            @Override
            public void onViewClick(Maintenance restaurant) {
                Intent intent = new Intent(ListMaintenanceActivity.this, ViewMaintenanceActivity.class);
                intent.putExtra("restaurantId", restaurant.getId());
                startActivity(intent);
            }

            @Override
            public void onEditClick(Maintenance restaurant) {
                Intent intent = new Intent(ListMaintenanceActivity.this, AddEditMaintenanceActivity.class);
                intent.putExtra("restaurantId", restaurant.getId());
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Maintenance restaurant) {
                // Execute delete in the background using AsyncTask
                new DeleteRestaurantTask().execute(restaurant);
            }
        });

        // Load all restaurants and observe for changes
        LiveData<List<Maintenance>> allRestaurants = AppDatabase.getInstance(this).maintenanceDao().getAllRestaurants();
        allRestaurants.observe(this, adapter::submitList);

        // Set up the "Add Restaurant" button
        findViewById(R.id.button_add_restaurant).setOnClickListener(v -> {
            Intent intent = new Intent(ListMaintenanceActivity.this, AddEditMaintenanceActivity.class);
            startActivity(intent);
        });
    }

    // AsyncTask for deleting a restaurant in the background
    private class DeleteRestaurantTask extends AsyncTask<Maintenance, Void, Void> {
        @Override
        protected Void doInBackground(Maintenance... restaurants) {
            AppDatabase.getInstance(ListMaintenanceActivity.this).maintenanceDao().delete(restaurants[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(ListMaintenanceActivity.this, "Restaurant deleted", Toast.LENGTH_SHORT).show();
        }


    }
    private void setupBackIcon() {
        ImageView backIcon = findViewById(R.id.back_icon);
        backIcon.setOnClickListener(v -> finish()); // Close this activity and return to the previous one
    }
}
