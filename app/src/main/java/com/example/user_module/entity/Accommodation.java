package com.example.user_module.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

//hotel
@Entity(tableName = "accommodations")
public class Accommodation {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "capacity")
    private int capacity;

    @ColumnInfo(name = "price_per_night")
    private double pricePerNight;

    @ColumnInfo(name = "is_available")
    private boolean isAvailable;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "image_res_id")
    private int imageResId;

    @ColumnInfo(name = "booked_dates")
    private String bookedDates;  // Store the booked dates as a JSON string

    private String imageUri;
    // Default constructor
    public Accommodation() {}

    // Parameterized constructor without imageResId
    public Accommodation(String name, String location, String type, int capacity, double pricePerNight, boolean isAvailable, String title,String imageUri) {

        this.name = name;
        this.location = location;
        this.type = type;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.isAvailable = isAvailable;
        this.title = title;
        this.imageUri = imageUri;
    }

    // Parameterized constructor with imageResId
    public Accommodation(String name, String location, String type, int capacity, double pricePerNight, boolean isAvailable, String title, int imageResId) {
        this.name = name;
        this.location = location;
        this.type = type;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.isAvailable = isAvailable;
        this.title = title;
        this.imageResId = imageResId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    // Getter for imageUri
    public String getImageUri() {
        return imageUri;
    }

    // Setter for imageUri
    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getBookedDates() {
        return bookedDates;
    }

    public void setBookedDates(String bookedDates) {
        this.bookedDates = bookedDates;
    }

    public String getDescription() {
        return name + " located in " + location + ", " + type + " with a capacity of " + capacity +
                ". Price per night: " + pricePerNight + " DT. Available: " + (isAvailable ? "Yes" : "No");
    }

    // toString method
    @Override
    public String toString() {
        return "Accommodation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", type='" + type + '\'' +
                ", capacity=" + capacity +
                ", pricePerNight=" + pricePerNight +
                ", isAvailable=" + isAvailable +
                ", title='" + title + '\'' +
                ", imageResId=" + imageResId +
                '}';
    }
}

