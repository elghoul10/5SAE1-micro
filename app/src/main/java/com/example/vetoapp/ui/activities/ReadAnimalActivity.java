package com.example.vetoapp.ui.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vetoapp.R;
import com.example.vetoapp.dao.AnimalDao;
import com.example.vetoapp.models.Animal;
import com.example.vetoapp.utils.MyDataBase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class ReadAnimalActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AnimalAdapter animalAdapter;
    private AnimalDao animalDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_animal);

        // Initialize RecyclerView and Adapter
        recyclerView = findViewById(R.id.animalRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get the instance of the database and DAO
        MyDataBase db = MyDataBase.getInstance(this);
        animalDao = db.animalDao();

        // Initialize the adapter with animalDao for delete functionality
        animalAdapter = new AnimalAdapter(new ArrayList<>(), animalDao);
        recyclerView.setAdapter(animalAdapter);

        // Fetch all animals and update the adapter
        fetchAnimals();
    }

    private void fetchAnimals() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Animal> animals = animalDao.getAllAnimals();
            runOnUiThread(() -> animalAdapter.updateData(animals));
        });
    }
}

