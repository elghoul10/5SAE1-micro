package com.example.vetoapp.ui.activities;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vetoapp.R;
import com.example.vetoapp.dao.AnimalDao;
import com.example.vetoapp.models.Animal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.Executors;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {

    private List<Animal> animals;
    private AnimalDao animalDao;

    public AnimalAdapter(List<Animal> animals, AnimalDao animalDao) {
        this.animals = animals;
        this.animalDao = animalDao;
    }

    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_animal, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        Animal animal = animals.get(position);
        holder.nameTextView.setText(animal.getName());
        holder.typeTextView.setText(animal.getType());
        holder.ageTextView.setText(animal.getAge());
        holder.sexTextView.setText(animal.getSex());
        holder.weightTextView.setText(animal.getWeight());
        holder.behaviorTextView.setText(animal.getBehavior());

        // Set up the delete button click listener
        holder.deleteButton.setOnClickListener(v -> deleteAnimal(animal, position));
    }

    @Override
    public int getItemCount() {
        return animals != null ? animals.size() : 0;
    }

    public void updateData(List<Animal> animals) {
        this.animals = animals;
        notifyDataSetChanged();
    }

    private void deleteAnimal(Animal animal, int position) {
        // Delete the animal in a background thread
        Executors.newSingleThreadExecutor().execute(() -> {
            animalDao.delete(animal);
            animals.remove(position);

            // Notify the adapter of item removed on the main thread
            new Handler(Looper.getMainLooper()).post(() -> notifyItemRemoved(position));
        });
    }

    public static class AnimalViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, typeTextView, ageTextView, sexTextView, weightTextView, behaviorTextView;
        FloatingActionButton deleteButton;

        public AnimalViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.animalName);
            typeTextView = itemView.findViewById(R.id.animalType);
            ageTextView = itemView.findViewById(R.id.animalAge);
            sexTextView = itemView.findViewById(R.id.animalSex);
            weightTextView = itemView.findViewById(R.id.animalWeight);
            behaviorTextView = itemView.findViewById(R.id.animalBehavior);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
