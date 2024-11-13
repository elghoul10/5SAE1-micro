package com.example.user_module.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "site")
public class Assurances {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String location;
    private String description;
    private float rating; // Store rating as a float
    private String imageUri;
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImageUri() { return imageUri; }
    public void setImageUri(String imageUri) { this.imageUri = imageUri; }

    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }
}

