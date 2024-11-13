package com.example.user_module.activity;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.user_module.AppDatabase;
import com.example.user_module.Dao.AccommodationDao;
import com.example.user_module.entity.Accommodation;

public class AccommodationViewModel extends AndroidViewModel {

    private AccommodationDao accommodationDao;

    public AccommodationViewModel(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        accommodationDao = db.accommodationDao();
    }

    public void insertAccommodation(Accommodation accommodation) {
        new Thread(() -> accommodationDao.insertAccommodation(accommodation)).start();
    }


}

