package com.example.user_module.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.user_module.entity.Reservation;

import java.util.List;

@Dao
public interface ReservationDao {
    @Query("SELECT * FROM Reservation WHERE userId = :userId")
    List<Reservation> getReservationsByUser(int userId);

    @Insert
    void makeReservation(Reservation reservation);

    @Insert
    void insertReservation(Reservation reservation);

    @Query("SELECT * FROM Reservation WHERE id = :reservationId")
    Reservation getReservationById(int reservationId);

    @Delete
    void cancelReservation(Reservation reservation);

    // Update an existing reservation (optional)
    @Update
    void updateReservation(Reservation reservation);

    // New method to get all reservations
    @Query("SELECT * FROM Reservation")
    List<Reservation> getAll(); // Add this method to retrieve all reservations


    @Query("SELECT MAX(id) FROM Reservation")
    int getLastInsertedId();
}
