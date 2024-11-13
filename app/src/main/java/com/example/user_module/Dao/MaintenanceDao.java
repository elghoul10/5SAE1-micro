package com.example.user_module.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.user_module.entity.Maintenance;

import java.util.List;

@Dao
public interface MaintenanceDao {
    @Insert
    void insert(Maintenance restaurant);

    @Update
    void update(Maintenance restaurant);

    @Delete
    void delete(Maintenance restaurant);

    @Query("SELECT * FROM Restaurant_table ORDER BY name ASC")
    LiveData<List<Maintenance>> getAllRestaurants();

    @Query("SELECT * FROM Restaurant_table WHERE id = :id")
    Maintenance getRestaurantById(int id);


}
