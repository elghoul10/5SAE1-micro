package com.example.user_module;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.user_module.Dao.AccommodationDao;
import com.example.user_module.Dao.ReservationDao;
import com.example.user_module.Dao.MaintenanceDao;
import com.example.user_module.Dao.UserDao;
import com.example.user_module.Dao.AssurancesDao;
import com.example.user_module.entity.Accommodation;
import com.example.user_module.entity.Converters;
import com.example.user_module.entity.Reservation;
import com.example.user_module.entity.Maintenance;
import com.example.user_module.entity.Assurances;
import com.example.user_module.entity.User;

@Database(entities = {User.class, Assurances.class, Maintenance.class, Reservation.class, Accommodation.class }, version = 41) // Increment version if needed
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance; // Declare as a static variable
    public abstract UserDao userDao();
    public abstract AssurancesDao assurancesDao();
    public abstract MaintenanceDao maintenanceDao();
    public abstract AccommodationDao accommodationDao();
    public abstract ReservationDao reservationDao();


    // Synchronized method to get a single instance
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "MobileApp")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }






}
