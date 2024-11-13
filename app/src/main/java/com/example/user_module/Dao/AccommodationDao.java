package com.example.user_module.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.user_module.entity.Accommodation;

import java.util.List;

@Dao
public interface AccommodationDao {
    @Query("SELECT * FROM accommodations WHERE location = :location AND capacity >= :numPeople")
    List<Accommodation> searchByLocationAndCapacity(String location, int numPeople);

    @Query("SELECT * FROM accommodations WHERE id = :id")
    Accommodation getAccommodationById(int id);

    @Query("SELECT * FROM accommodations WHERE name = :name LIMIT 1")
    Accommodation getAccommodationByName(String name);

    @Insert
    void insertAccommodation(Accommodation accommodation);

    @Update
    void updateAccommodation(Accommodation accommodation);


    @Insert
    void insert(Accommodation accommodation);

    @Insert
    void insertAll(List<Accommodation> accommodations);  // Insert a list of accommodations

    @Query("SELECT * FROM accommodations WHERE location = :destinationName")
    List<Accommodation> getRecommendations(String destinationName);


    @Query("SELECT * FROM accommodations")
    List<Accommodation> getAllAccommodations();


    @Query("SELECT * FROM accommodations WHERE name LIKE :query")
    List<Accommodation> getAccommodationsByName(String query);
}
