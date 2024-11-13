package com.example.user_module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user_module.Adapter.AssurancesAdapter;
import com.example.user_module.AppDatabase;
import com.example.user_module.R;

public class AssurancesListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewSites;
    private Button buttonAddSite;
    private AssurancesAdapter assurancesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_site);
        setupBackIcon();
        recyclerViewSites = findViewById(R.id.recyclerViewSites);
        buttonAddSite = findViewById(R.id.buttonAddSite);

        recyclerViewSites.setLayoutManager(new LinearLayoutManager(this));
        loadSites();

        buttonAddSite.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddEditAssurancesActivity.class);
            startActivity(intent);
        });
    }

    private void loadSites() {
        AppDatabase db = AppDatabase.getInstance(this);
        db.assurancesDao().getAllSites().observe(this, sites -> {
            assurancesAdapter = new AssurancesAdapter(this, sites);
            recyclerViewSites.setAdapter(assurancesAdapter);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSites(); // Refresh the list when returning to this activity
    }
    private void setupBackIcon() {
        ImageView backIcon = findViewById(R.id.back_icon);
        backIcon.setOnClickListener(v -> finish()); // Close this activity and return to the previous one
    }
}
