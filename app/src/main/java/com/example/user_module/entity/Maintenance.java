package com.example.user_module.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Restaurant_table")
public class Maintenance {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String location;
    private String type;
    private String capacite;
     // Mark imageUri as nullable
    private String imageUri;
    // Default constructor (Room requires this)
    public Maintenance() {
    }

    // Constructor with parameters (to use for initializing the Restaurant object)
    public Maintenance(int id, String name, String location, String type, String capacite, String imageUri) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.type = type;
        this.capacite = capacite;
        this.imageUri = imageUri;
    }

    // Getter and Setter methods
    public int getId() { return id; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getType() { return type; }
    public String getCapacite() { return capacite; }
    public String getImageUri() { return imageUri; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setLocation(String location) { this.location = location; }
    public void setType(String type) { this.type = type; }
    public void setCapacite(String capacite) { this.capacite = capacite; }
    public void setImageUri(String imageUri) { this.imageUri = imageUri; }

    @Override
    public String toString() {
        return name + " - " + type + " - " + location + " - capacite: " + capacite;
    }
}
