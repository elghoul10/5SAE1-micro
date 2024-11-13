package com.example.user_module.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.user_module.entity.Assurances;

import java.util.List;

@Dao
public interface AssurancesDao {

    @Insert
    void insert(Assurances site);

    @Update
    void update(Assurances site);

    @Delete
    void delete(Assurances site);

    @Query("SELECT * FROM Site ORDER BY name ASC")
    LiveData<List<Assurances>> getAllSites();

    @Query("SELECT * FROM Site WHERE id = :siteId")
    LiveData<Assurances> getSiteById(int siteId);
}
